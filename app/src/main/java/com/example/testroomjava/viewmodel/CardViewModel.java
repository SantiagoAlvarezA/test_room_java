package com.example.testroomjava.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.testroomjava.repository.CardRepository;
import com.example.testroomjava.room.Card;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private CardRepository repository;
    private LiveData<List<Card>> cards;
    private LiveData<Card> card;

    public CardViewModel(@NonNull Application application) {
        super(application);
        repository = new CardRepository(application);
        cards = repository.getAllCards();
        card = repository.getCardById(1);
    }


    public LiveData<List<Card>> getCards() {
        return cards;
    }


    public void insertCard(Card card) {
        repository.insertCard(card);
    }

    public void insertCards(List<Card> cards) {
        repository.insertCards(cards);
    }

    public void updateCard(Card card) {
        repository.updateCard(card);
    }

    public void deleteCard(Card card) {
        repository.deleteCard(card);
    }

    public void deleteCards(List<Card> cards) {
        repository.deleteCards(cards);
    }

    public LiveData<Card> getCardById() {
        return card;
    }

    public void getCardById(int id) {
        card = repository.getCardById(id);
    }

}
