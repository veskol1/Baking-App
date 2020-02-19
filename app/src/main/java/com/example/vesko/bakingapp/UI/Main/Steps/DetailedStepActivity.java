package com.example.vesko.bakingapp.UI.Main.Steps;


import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.vesko.bakingapp.Model.Recipe;
import com.example.vesko.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.vesko.bakingapp.UI.Main.Steps.StepsActivity.RECIPE_TO_DETAILED_ACTIVITY;
import static com.example.vesko.bakingapp.UI.Main.Steps.StepsActivity.SELECTED_STEP_POSITION;

public class DetailedStepActivity extends AppCompatActivity{

    private Recipe recipe;
    private int recipePosition;
    private final String POSITION = "last_position";

    @BindView(R.id.button_next_container) Button nextButton;
    @BindView(R.id.button_previous_container) Button previousButton;
    @BindView(R.id.recipe_video) FrameLayout videoLayoutFrame;
    @BindView(R.id.recipe_step_instruction) FrameLayout instructionLayoutFrame;

    public DetailedStepActivity(){}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_step);
        ButterKnife.bind(this);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            videoLayoutFrame.setLayoutParams(new LinearLayout.LayoutParams(size.x,size.y-30));
            getSupportActionBar().hide();

            instructionLayoutFrame.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            previousButton.setVisibility(View.GONE);
        }


        Bundle data = getIntent().getExtras();
        recipe= data.getParcelable(RECIPE_TO_DETAILED_ACTIVITY);

        if(savedInstanceState!=null)
            recipePosition = savedInstanceState.getInt(POSITION);
        else
            recipePosition = data.getInt(SELECTED_STEP_POSITION);
        setTitle(recipe.getName()+" - "+recipe.getSteps().get(recipePosition).getShortDescription());

        if(savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();

            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setRecipe(recipe);
            videoFragment.setRecipePosition(recipePosition);

            RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment();
            recipeInstructionFragment.setRecipe(recipe);
            recipeInstructionFragment.setRecipePosition(recipePosition);

            fm.beginTransaction()
                    .add(R.id.recipe_step_instruction, recipeInstructionFragment)
                    .add(R.id.recipe_video, videoFragment)
                    .commit();
        }
        previousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fm =getSupportFragmentManager();

                if(recipePosition>0) {
                    recipePosition--;
                    setTitle(recipe.getName()+" - "+recipe.getSteps().get(recipePosition).getShortDescription());
                    VideoFragment videoFragment = new VideoFragment();
                    videoFragment.setRecipe(recipe);
                    videoFragment.setRecipePosition(recipePosition);

                    RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment();
                    recipeInstructionFragment.setRecipe(recipe);
                    recipeInstructionFragment.setRecipePosition(recipePosition);

                    fm.beginTransaction()
                            .replace(R.id.recipe_step_instruction, recipeInstructionFragment)
                            .replace(R.id.recipe_video, videoFragment)
                            .commit();
                }
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fm =getSupportFragmentManager();

                if(recipePosition<recipe.getSteps().size()-1) {
                    recipePosition++;
                    setTitle(recipe.getName()+" - "+recipe.getSteps().get(recipePosition).getShortDescription());

                    VideoFragment videoFragment = new VideoFragment();
                    videoFragment.setRecipe(recipe);
                    videoFragment.setRecipePosition(recipePosition);

                    RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment();
                    recipeInstructionFragment.setRecipe(recipe);
                    recipeInstructionFragment.setRecipePosition(recipePosition);

                    fm.beginTransaction()
                            .replace(R.id.recipe_step_instruction, recipeInstructionFragment)
                            .replace(R.id.recipe_video, videoFragment)
                            .commit(); //stepDetailesDescription.setText(recipe.getSteps().get(recipePosition).getDescription());
                }
            }
        });

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, recipePosition);
    }


}
