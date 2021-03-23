package com.example.testroomjava.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Card {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long cardNumber;
    private String internationalBrand;
    private String dateOfExpiry;
    private String nameOfOwner;

    public Card(long cardNumber, String internationalBrand, String dateOfExpiry, String nameOfOwner) {
        this.cardNumber = cardNumber;
        this.internationalBrand = internationalBrand;
        this.dateOfExpiry = dateOfExpiry;
        this.nameOfOwner = nameOfOwner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public String getInternationalBrand() {
        return internationalBrand;
    }

    public String getDateOfExpiry() {
        return dateOfExpiry;
    }

    public String getNameOfOwner() {
        return nameOfOwner;
    }
}
