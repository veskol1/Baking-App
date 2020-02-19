package com.example.vesko.bakingapp.UI.Main.Steps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vesko.bakingapp.Model.Recipe;
import com.example.vesko.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeInstructionFragment extends Fragment{
    private Recipe recipe;
    private int recipePosition;
    @BindView(R.id.step_instruction_view)TextView stepInstructionView;
    private final String INSTRUCTION_POSITION = "last_video_position";
    private final String RECIPE = "last_instruction_recipe";

    public  RecipeInstructionFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instruction_step,container,false);
        ButterKnife.bind(this,rootView);

        if (savedInstanceState!=null){
            recipe= savedInstanceState.getParcelable(RECIPE);
            recipePosition=savedInstanceState.getInt(INSTRUCTION_POSITION);
        }

        stepInstructionView.setText(recipe.getSteps().get(recipePosition).getDescription());

        return rootView;
    }


    public void setRecipe(Recipe recipe){
        this.recipe=recipe;
    }

    public void setRecipePosition(int recipePosition){
        this.recipePosition=recipePosition;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE,recipe);
        outState.putInt(INSTRUCTION_POSITION,recipePosition);
    }
}
