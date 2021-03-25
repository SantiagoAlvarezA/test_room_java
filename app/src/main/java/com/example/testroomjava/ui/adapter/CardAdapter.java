package com.example.testroomjava.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testroomjava.R;
import com.example.testroomjava.model.Card;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Card> cards;
    private ICardAdapter mListener;

    public CardAdapter(List<Card> cards, ICardAdapter listener) {
        this.cards = cards;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardNumber.setText(String.valueOf(cards.get(position).getCardNumber()));
        holder.internationalBrand.setText(cards.get(position).getInternationalBrand());
        holder.dateOfExpiry.setText(cards.get(position).getDateOfExpiry());
        holder.nameOfOwner.setText(cards.get(position).getNameOfOwner());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView cardNumber, internationalBrand, dateOfExpiry, nameOfOwner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cardNumber = itemView.findViewById(R.id.cardNumber);
            internationalBrand = itemView.findViewById(R.id.internationalBrand);
            dateOfExpiry = itemView.findViewById(R.id.dateOfExpiry);
            nameOfOwner = itemView.findViewById(R.id.nameOfOwner);
        }

        @Override
        public void onClick(View v) {
            mListener.onClickItem(cards.get(getAdapterPosition()).getId());
        }
    }


    public interface ICardAdapter {
        void onClickItem(int id);
    }
}
