package com.example.testroomjava.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM Card")
    LiveData<List<Card>> getAllCards();

    @Query("SELECT * FROM Card WHERE id = :id")
    LiveData<Card> getCardById(int id);

    @Update
    void updateCard(Card card);

    @Insert
    void insertCard(Card card);

    @Insert
    void insertCards(List<Card> cards);

    @Delete
    void deleteCard(Card card);

    @Delete
    void deleteCards(List<Card> cards);

}
