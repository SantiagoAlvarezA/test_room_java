package com.example.testroomjava.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.testroomjava.room.Card;
import com.example.testroomjava.room.CardDao;
import com.example.testroomjava.room.CardDatabase;

import java.util.List;

public class CardRepository {

    private CardDao cardDao;
    private LiveData<List<Card>> cards;

    public CardRepository(Application application) {
        CardDatabase database = CardDatabase.getInstance(application);
        cardDao = database.cardDao();
        cards = cardDao.getAllCards();
    }

    public void insertCard(Card card) {
        new InsertCardAsyncTask(cardDao).execute(card);
    }

    public void updateCard(Card card) {
        new UpdateCardAsyncTask(cardDao).execute(card);
    }

    public void deleteCard(Card card) {
        new DeleteCardAsyncTask(cardDao).execute(card);

    }

    public void deleteCards(List<Card> cards) {
        new DeleteCardsAsyncTask(cardDao).execute(cards);
    }

    public LiveData<Card> getCardById(int id) {
        return cardDao.getCardById(id);
    }

    public LiveData<List<Card>> getAllCards() {
        return cards;
    }

    public void insertCards(List<Card> cards) {
        new InsertCardsAsyncTask(cardDao).execute(cards);
    }


    private static class InsertCardAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDao cardDao;

        private InsertCardAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            cardDao.insertCard(cards[0]);
            return null;
        }
    }

    private static class UpdateCardAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDao cardDao;

        private UpdateCardAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            cardDao.updateCard(cards[0]);
            return null;
        }
    }

    private static class DeleteCardAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDao cardDao;

        private DeleteCardAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            cardDao.deleteCard(cards[0]);
            return null;
        }
    }

    private static class DeleteCardsAsyncTask extends AsyncTask<List<Card>, Void, Void> {

        private CardDao cardDao;

        private DeleteCardsAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(List<Card>... cards) {
            cardDao.deleteCards(cards[0]);
            return null;
        }
    }

    private static class InsertCardsAsyncTask extends AsyncTask<List<Card>, Void, Void> {

        private CardDao cardDao;

        private InsertCardsAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }

        @Override
        protected Void doInBackground(List<Card>... cards) {
            cardDao.insertCards(cards[0]);
            return null;
        }
    }

}
