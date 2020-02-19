package com.example.vesko.bakingapp.UI.Main.Ingredients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vesko.bakingapp.Model.Ingredients;
import com.example.vesko.bakingapp.Model.Recipe;
import com.example.vesko.bakingapp.R;
import com.example.vesko.bakingapp.UI.Main.Ingredients.DetailedIngredientsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientFragment extends Fragment  {
    @BindView(R.id.ingredient_view) TextView ingredientTextView;
    onIngredientViewListener mCallback;
    private Recipe recipe;
    private ArrayList<Ingredients> ingredients = new ArrayList<>();

    public RecipeIngredientFragment(){ }

    public interface onIngredientViewListener{
        void onIngredientViewClicked(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback=(onIngredientViewListener)context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients,container,false);
        ButterKnife.bind(this, rootView);
        ingredientTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DetailedIngredientsActivity.class);
                ingredients = recipe.getIngredients();
                intent.putExtra("recipe name",recipe.getName());
                intent.putExtra("ingredient",ingredients);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void setRecipe(Recipe recipe){
        this.recipe=recipe;

    }
}
