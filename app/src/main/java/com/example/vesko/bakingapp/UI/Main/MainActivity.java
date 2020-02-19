package com.example.vesko.bakingapp.UI.Main;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.vesko.bakingapp.Model.Recipe;
import com.example.vesko.bakingapp.R;
import com.example.vesko.bakingapp.UI.Main.Steps.StepsActivity;
import com.example.vesko.bakingapp.Utils.NetworkUtils;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeFragment.onImageClickListener {
    ArrayList<Recipe> arrayRecipes;
    public final static String ARRAY_LIST_RECIPES="array list recipes";
    final static String LINK_TO_JSON="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Recipe> arrayRecipes = new ArrayList<>();
        new QueryTask().execute(LINK_TO_JSON);

        FragmentManager fm = getSupportFragmentManager();

        RecipeFragment firstRecipeFragment =new RecipeFragment();
        firstRecipeFragment.setIndexImg(0);
        fm.beginTransaction()
                .add(R.id.recipe_one,firstRecipeFragment)
                .commit();


        RecipeFragment secondRecipeFragment =new RecipeFragment();
        secondRecipeFragment.setIndexImg(1);
        fm.beginTransaction()
                .add(R.id.recipe_two,secondRecipeFragment)
                .commit();


        RecipeFragment thirdRecipeFragment =new RecipeFragment();
        thirdRecipeFragment.setIndexImg(2);
        fm.beginTransaction()
                .add(R.id.recipe_tree,thirdRecipeFragment)
                .commit();


        RecipeFragment fourthRecipeFragment =new RecipeFragment();
        fourthRecipeFragment.setIndexImg(3);
        fm.beginTransaction()
                .add(R.id.recipe_four,fourthRecipeFragment)
                .commit();

    }

    public void onImageSelected(int position) {
        Intent intent = new Intent(this,StepsActivity.class);
        intent.putExtra(ARRAY_LIST_RECIPES, arrayRecipes.get(position));

        startActivity(intent);
    }


    public class QueryTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String searchUrl = params[0];
            String movieDbSearchJsonResults = null;
            try {
                URL url = new URL(searchUrl);
                movieDbSearchJsonResults = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieDbSearchJsonResults;
        }

        @Override
        protected void onPostExecute(String movieDbSearchJsonResults) {
            arrayRecipes = NetworkUtils.parseMovieJson(movieDbSearchJsonResults);
        }
    }


}
