package com.example.vesko.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    private String id;
    private String name;
    private ArrayList<Ingredients> ingredients = new ArrayList<>();
    private ArrayList<Steps> steps = new ArrayList<>();

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public Recipe(Parcel in){
        this.id = in.readString();
        this.name=in.readString();
        in.readTypedList(ingredients, Ingredients.CREATOR);
        in.readTypedList(steps,Steps.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

    public Recipe(){ }

    public Recipe(String id,String name, ArrayList<Ingredients> ingredients,ArrayList<Steps> steps) {
        this.id = id;
        this.name=name;
        this.ingredients=ingredients;
        this.steps=steps;
    }

    public String getId(){
            return id;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
}
