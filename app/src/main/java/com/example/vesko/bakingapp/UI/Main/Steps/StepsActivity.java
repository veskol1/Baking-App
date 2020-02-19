package com.example.vesko.bakingapp.UI.Main.Steps;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vesko.bakingapp.Model.Recipe;
import com.example.vesko.bakingapp.R;
import com.example.vesko.bakingapp.UI.Main.Ingredients.RecipeIngredientFragment;
import com.example.vesko.bakingapp.UI.Main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.vesko.bakingapp.UI.Main.MainActivity.ARRAY_LIST_RECIPES;

public class StepsActivity extends AppCompatActivity implements RecipeIngredientFragment.onIngredientViewListener, StepsAdapter.ListItemClickListener {
    Recipe recipe;
    StepsAdapter adapter;
    private boolean twoPane;
    private int recipePosition;
    public final static String SELECTED_STEP_POSITION="selected step position";
    public final static String RECIPE_TO_DETAILED_ACTIVITY="recipe to detailed activity";
    @Nullable @BindView(R.id.button_previous_container)Button previousButton;
    @Nullable @BindView(R.id.button_next_container)Button nextButton;
    @BindView(R.id.step_view)RecyclerView mStepsList;


    @Override
    public void onIngredientViewClicked(int position) { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        recipe =(Recipe) data.getParcelable(ARRAY_LIST_RECIPES);
        setTitle(recipe.getName());


        FragmentManager fm1 = getSupportFragmentManager();

        RecipeIngredientFragment recipeIngredientFragment = new RecipeIngredientFragment();
        recipeIngredientFragment.setRecipe(recipe);
        fm1.beginTransaction()
                .add(R.id.ingredient_view_container, recipeIngredientFragment)
                .commit();

        mStepsList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mStepsList.setLayoutManager(layoutManager);
        adapter = new StepsAdapter(recipe.getSteps().size(), recipe, this);
        mStepsList.setAdapter(adapter);


        if(findViewById(R.id.linear_layout_detailed_steps)!=null){
            twoPane=true;
            nextButton.setVisibility(View.GONE);
            previousButton.setVisibility(View.GONE);
            recipePosition=0; //default start position
            FragmentManager fm = getSupportFragmentManager();

            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setRecipe(recipe);
            videoFragment.setRecipePosition(recipePosition);

            RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment();
            recipeInstructionFragment.setRecipe(recipe);
            recipeInstructionFragment.setRecipePosition(recipePosition);

            fm.beginTransaction()
                    .add(R.id.recipe_video, videoFragment)
                    .add(R.id.recipe_step_instruction, recipeInstructionFragment)
                    .commit();
        }

    }

    @Override
    public void onListItemClick(int clickedItemPosition) {

        if(twoPane) {
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setRecipe(recipe);
            videoFragment.setRecipePosition(clickedItemPosition);

            RecipeInstructionFragment recipeInstructionFragment = new RecipeInstructionFragment();
            recipeInstructionFragment.setRecipe(recipe);
            recipeInstructionFragment.setRecipePosition(clickedItemPosition);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_video, videoFragment)
                    .replace(R.id.recipe_step_instruction, recipeInstructionFragment)
                    .commit();

        }
        else{
            Intent intent = new Intent(this, DetailedStepActivity.class);
            intent.putExtra(RECIPE_TO_DETAILED_ACTIVITY, recipe);
            intent.putExtra(SELECTED_STEP_POSITION, clickedItemPosition);
            startActivity(intent);
        }
    }
}
