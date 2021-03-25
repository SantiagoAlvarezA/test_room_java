package com.example.testroomjava.model;

import androidx.room.ColumnInfo;
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
    @ColumnInfo(defaultValue = "1")
    private byte isSelected;

    public Card(long cardNumber, String internationalBrand, String dateOfExpiry, String nameOfOwner) {
        this.cardNumber = cardNumber;
        this.internationalBrand = internationalBrand;
        this.dateOfExpiry = dateOfExpiry;
        this.nameOfOwner = nameOfOwner;
        this.isSelected = 1;
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

    public byte getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(byte isSelected) {
        this.isSelected = isSelected;
    }
}
