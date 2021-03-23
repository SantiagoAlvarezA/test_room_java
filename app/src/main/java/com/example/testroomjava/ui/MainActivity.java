package com.example.testroomjava.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testroomjava.R;
import com.example.testroomjava.room.Card;
import com.example.testroomjava.ui.adapter.CardAdapter;
import com.example.testroomjava.viewmodel.CardViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CardAdapter.ICardAdapter {

    private CardViewModel viewModel;
    private RecyclerView recyclerView;
    private CardAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.floating).setOnClickListener(this);


        viewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(CardViewModel.class);


        viewModel.getCards().observe(this, cards -> {
            adapter = new CardAdapter(cards, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
//            Toast.makeText(this, "Total " + cards.size(), Toast.LENGTH_SHORT).show();
        });

        viewModel.getCardById().observe(this, card -> {
            Toast.makeText(this, card.getInternationalBrand(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.floating) {
            showAlert();
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
        Button save = view.findViewById(R.id.save);
        builder.setView(view);

        AlertDialog dialog = builder.create();

        save.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), cardNumber.getText().toString(), Toast.LENGTH_SHORT).show();
            viewModel.insertCard(new Card(
                            Long.parseLong(cardNumber.getText().toString()),
                            internationalBrand.getText().toString(),
                            dateOfExpiry.getText().toString(),
                            nameOfOwner.getText().toString()
                    )
            );
            dialog.dismiss();
        });

        dialog.show();

    }

    @Override
    public void onClickItem(int id) {
        viewModel.getCardById(id);
    }
}