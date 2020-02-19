package com.example.vesko.bakingapp.UI.Main.Ingredients;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vesko.bakingapp.Model.Ingredients;
import com.example.vesko.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedIngredientsActivity extends AppCompatActivity {
    @BindView(R.id.ingredients_tv) TextView ingredientView;
    @BindView(R.id.button_broadcaster) Button buttonBroadcast;

    private ArrayList<Ingredients> ingredientsArray;
    private String recipeName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_ingredients);
        setTitle("Ingredients");
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        ingredientsArray = data.getParcelableArrayList("ingredient");
        recipeName = data.getString("recipe name");

        for (int i=0; i<ingredientsArray.size(); i++) {
            ingredientView.append(i+1+"."+ingredientsArray.get(i).getIngredient()+"\n\n");
        }

        buttonBroadcast.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ingredient array list",ingredientsArray);
                intent.putExtra("recipe ingredient name",recipeName);
                intent.setAction("android.appwidget.action.CUSTOM_INTENT");
                sendBroadcast(intent);
            }
        });
    }


}
