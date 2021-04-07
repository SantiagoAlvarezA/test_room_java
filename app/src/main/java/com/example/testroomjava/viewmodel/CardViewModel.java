package com.example.testroomjava.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.testroomjava.repository.CardRepository;
import com.example.testroomjava.model.Card;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private CardRepository repository;
    private LiveData<List<Card>> cards;
    private LiveData<Card> card;
    private MutableLiveData<byte[]> blob;

    public CardViewModel(@NonNull Application application) {
        super(application);
        repository = new CardRepository(application);
        cards = repository.getAllCards();
        card = repository.getCardSelected();
        blob = new MutableLiveData<>();
    }

    public void setBlob(byte[] blob) {
        this.blob.setValue(blob);
    }

    public LiveData<byte[]> getBlob() {
        return blob;
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

    public LiveData<Card> getCard() {
        return card;
    }

    public void setCard(int id) {
        repository.changeStatusSelected(id);
    }

}
