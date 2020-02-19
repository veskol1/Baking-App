package com.example.vesko.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Ingredients implements Parcelable{

    private String ingredient;
    private String quantity;
    private String measure;

    public static final Parcelable.Creator CREATOR =  new Parcelable.Creator() {
        @Override
        public Ingredients createFromParcel(Parcel source) {
            return new Ingredients(source);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    public Ingredients(Parcel in){
        this.ingredient= in.readString();
        this.measure = in.readString();
        this.quantity = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ingredient);
        dest.writeString(this.measure);
        dest.writeString(this.quantity);
    }

    public Ingredients(){ }

    public Ingredients(String ingredient,String quantity,String measure) {
        this.ingredient=ingredient;
        this.quantity=quantity;
        this.measure=measure;
    }

    public String getIngredient(){
        return ingredient;
    }

    public String getQuantity(){
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }
}
