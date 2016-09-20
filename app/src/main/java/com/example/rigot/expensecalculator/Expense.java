package com.example.rigot.expensecalculator;
/*
    Name: Rodrigo Trejo
    Name: Anal Shah
    HW2
 */

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by rigot on 9/8/2016.
 */
public class Expense implements Parcelable{

    public String name;
    public String category;
    public Double amount;
    public String date;
    public String imageUri;

    public Expense(String name, String category, Double amount, String date) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public Expense(String name, String category, Double amount, String date, String receipt){
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.imageUri = receipt;
    }

    protected Expense(Parcel in) {
        name = in.readString();
        category = in.readString();
        date = in.readString();
        amount = in.readDouble();
        imageUri = in.readString();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", imageUri='" + imageUri + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeString(date);
        parcel.writeDouble(amount);
        parcel.writeString(imageUri);
    }
}