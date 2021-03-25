package com.example.testroomjava.room;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.testroomjava.model.Card;

@Database(
        entities = {
                Card.class
        },
        version = 1
)
public abstract class CardDatabase extends RoomDatabase {

    private static CardDatabase instance;

    public abstract CardDao cardDao();

    public static synchronized CardDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.
                    databaseBuilder(
                            context.getApplicationContext(),
                            CardDatabase.class,
                            "card_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private CardDao cardDao;

        private PopulateDbAsyncTask(CardDatabase cardDatabase) {
            cardDao = cardDatabase.cardDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cardDao.insertCard(new Card(1000200030004000L, "VISA", "12/31", "SANTIAGO ALVAREZ A."));
            cardDao.insertCard(new Card(1001200030004000L, "MASTERCARD", "10/31", "SANTIAGO ALVAREZ A."));
            cardDao.insertCard(new Card(1002200030004000L, "BBVA", "09/31", "SANTIAGO ALVAREZ A."));
            return null;
        }
    }

}
