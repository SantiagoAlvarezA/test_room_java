package com.example.testroomjava.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.testroomjava.model.Card;

import java.util.List;

@Dao
public abstract class CardDao {

    @Query("SELECT * FROM Card ORDER BY Card.id DESC")
    public abstract LiveData<List<Card>> getAllCards();

    @Query("SELECT * FROM Card WHERE isSelected = 1")
    public abstract LiveData<Card> getCardSelected();

    @Update
    public abstract void updateCard(Card card);

    @Transaction
    public void insertCard(Card card) {
        unSelected();
        insert(card);
    }

    @Insert
    public abstract void insert(Card card);

    @Query("UPDATE Card SET isSelected = 0 WHERE isSelected = 1")
    public abstract void unSelected();


    @Insert
    public abstract void insertCards(List<Card> cards);

    @Delete
    public abstract void deleteCard(Card card);

    @Delete
    public abstract void deleteCards(List<Card> cards);


    @Query("UPDATE Card SET isSelected = 1 where id = :id")
    public abstract void selected(int id);

    @Transaction
    public void changeStatusSelected(int id) {
        unSelected();
        selected(id);
    }

}
