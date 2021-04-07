package com.example.testroomjava.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testroomjava.R;
import com.example.testroomjava.model.Card;
import com.example.testroomjava.ui.adapter.CardAdapter;
import com.example.testroomjava.viewmodel.CardViewModel;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CardAdapter.ICardAdapter {

    private static final int CAMERA_PIC_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE_CAMERA = 10;
    private CardViewModel viewModel;
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private TextView tv_card_number, tv_name_of_owner, tv_date_of_expiry, tv_international_brand;
    private ImageView image_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.floating).setOnClickListener(this);

        tv_card_number = findViewById(R.id.tv_card_number);
        tv_name_of_owner = findViewById(R.id.tv_name_of_owner);
        tv_date_of_expiry = findViewById(R.id.tv_date_of_expiry);
        tv_international_brand = findViewById(R.id.tv_international_brand);
        image_view = findViewById(R.id.image_view);


        viewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(CardViewModel.class);


        viewModel.getCards().observe(this, cards -> {
            adapter = new CardAdapter(cards, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        });

        viewModel.getCard().observe(this, card -> {
            if (card != null) {
                tv_card_number.setText(String.valueOf(card.getCardNumber()));
                tv_name_of_owner.setText(card.getNameOfOwner());
                tv_date_of_expiry.setText(card.getDateOfExpiry());
                tv_international_brand.setText(card.getInternationalBrand());
                Bitmap bitmap = BitmapFactory.decodeByteArray(card.getBlob(), 0, card.getBlob().length);
                image_view.setImageBitmap(bitmap);
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.floating) {
            showAlert();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
            viewModel.setBlob(baos.toByteArray());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    openCamera();
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE_CAMERA);
                    Log.d("TAG_MAIN", "onRequestPermissionsResult: denied");
                }
                break;
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog, null);
        TextView cardNumber = view.findViewById(R.id.cardNumber);
        TextView internationalBrand = view.findViewById(R.id.internationalBrand);
        TextView dateOfExpiry = view.findViewById(R.id.dateOfExpiry);
        TextView nameOfOwner = view.findViewById(R.id.nameOfOwner);
        ImageView ivCamera = view.findViewById(R.id.ivCamera);
        Button save = view.findViewById(R.id.save);
        Button camera = view.findViewById(R.id.camera);
        builder.setView(view);

        viewModel.getBlob().observe(MainActivity.this, blob -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
            ivCamera.setImageBitmap(bitmap);
        });

        AlertDialog dialog = builder.create();

        camera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE_CAMERA);
            }
        });

        save.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), cardNumber.getText().toString(), Toast.LENGTH_SHORT).show();
            viewModel.insertCard(new Card(
                    Long.parseLong(cardNumber.getText().toString()),
                    internationalBrand.getText().toString(),
                    dateOfExpiry.getText().toString(),
                    nameOfOwner.getText().toString(),
                    viewModel.getBlob().getValue())
            );
            dialog.dismiss();
        });

        dialog.show();

    }

    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }

    @Override
    public void onClickItem(int id) {
        viewModel.setCard(id);
    }
}