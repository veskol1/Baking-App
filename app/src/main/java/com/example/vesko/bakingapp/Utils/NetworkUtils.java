package com.example.vesko.bakingapp.Utils;


import android.util.Log;

import com.example.vesko.bakingapp.Model.Ingredients;
import com.example.vesko.bakingapp.Model.Recipe;
import com.example.vesko.bakingapp.Model.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {

    public static final String JSON_MOVIE_ID = "id";

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<Recipe> parseMovieJson(String json) {
        String recipeId=null;
        String name=null;
        ArrayList<Recipe> recipesArray = new ArrayList<>();
        ArrayList<Ingredients> ingredientsArrayList;
        ArrayList<Steps> stepsArrayList;


        String ingredient=null;
        String quantity=null;
        String measure=null;

         String stepId=null;
         String shortDescription=null;
         String description=null;
         String videoURL=null;
         String thumbnailURL=null;

        Ingredients ingredientsObject=null ;
        Steps stepsObject=null;
        Recipe recipeObject=null;

        try {
            JSONObject jsonRecipeObj;
            JSONObject jsonIngredientObj;
            JSONObject jsonStepsObj;

            JSONArray jsonRecipeArr = new JSONArray(json);
            for(int i=0; i<jsonRecipeArr.length(); i++){
                jsonRecipeObj = jsonRecipeArr.getJSONObject(i);
                recipeId=jsonRecipeObj.optString("id");
                name=jsonRecipeObj.optString("name");

                JSONArray jsonIngredientArr = jsonRecipeObj.getJSONArray("ingredients");
                ingredientsArrayList = new ArrayList<>();
                for(int j=0; j<jsonIngredientArr.length();j++){
                    jsonIngredientObj = jsonIngredientArr.getJSONObject(j);
                    ingredient=jsonIngredientObj.optString("ingredient");
                    quantity=jsonIngredientObj.optString("quantity");
                    measure=jsonIngredientObj.optString("measure");
                    ingredientsObject = new Ingredients(ingredient, quantity, measure);
                    ingredientsArrayList.add(ingredientsObject);

                }
                stepsArrayList= new ArrayList<>();
                JSONArray jsonStepsArr = jsonRecipeObj.getJSONArray("steps");
               // Log.d("jsonStepsArr.length()","jsonStepsArr.length()="+jsonStepsArr.length());
                for(int h=0; h<jsonStepsArr.length();h++){
                    jsonStepsObj = jsonStepsArr.getJSONObject(h);
                    stepId=jsonStepsObj.optString("id");
                    shortDescription=jsonStepsObj.optString("shortDescription");
                    description=jsonStepsObj.optString("description");
                    videoURL=jsonStepsObj.optString("videoURL");
                    thumbnailURL=jsonStepsObj.optString("thumbnailURL");
                    stepsObject = new Steps(stepId,shortDescription, description, videoURL,thumbnailURL);
                    stepsArrayList.add(stepsObject);
                }
                recipeObject = new Recipe(recipeId,name,ingredientsArrayList,stepsArrayList);
                recipesArray.add(recipeObject);
            }

            }

         catch (JSONException e) {
            Log.d("error","Error!");
        }

        return recipesArray;
    }


}