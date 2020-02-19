package com.example.vesko.bakingapp;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import com.example.vesko.bakingapp.UI.Main.MainActivity;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class TestMainMenuRecipe {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onClickRecipeOneStartsNewActivity(){

        Intents.init();
        onView((withId(R.id.recipe_one))).perform(click());
        onView(allOf(instanceOf(TextView.class), withParent(withResourceName("action_bar"))))
                .check(matches(withText("Nutella Pie")));

        onView(withId(R.id.step_view)).perform(actionOnItemAtPosition(0, click()));
        onView(allOf(instanceOf(TextView.class), withParent(withResourceName("action_bar"))))
                .check(matches(withText("Nutella Pie - Recipe Introduction")));

        onView(allOf(withId(R.id.playerView),withClassName(is(SimpleExoPlayerView.class.getName()))));
        onView(withId(R.id.button_next_container)).perform(click());

        onView(allOf(instanceOf(TextView.class), withParent(withResourceName("action_bar"))))
                .check(matches(withText("Nutella Pie - Starting prep")));

        onView(allOf(withId(R.id.playerView),withClassName(not(SimpleExoPlayerView.class.getName()))));

        onView(withId(R.id.button_previous_container)).perform(click());


        Intents.release();
    }

//    @Test
//    public void onClickRecipeTwoStartsNewActivity(){
//        Intents.init();
//        onView((withId(R.id.recipe_two))).perform(click());
//        intended(hasComponent(StepsActivity.class.getName()));
//        Intents.release();
//    }
//
//
//    @Test
//    public void onClickRecipeThreeStartsNewActivity(){
//        Intents.init();
//        onView((withId(R.id.recipe_tree))).perform(click());
//        intended(hasComponent(StepsActivity.class.getName()));
//        Intents.release();
//    }
//
//    @Test
//    public void onClickRecipeFourStartsNewActivity(){
//        Intents.init();
//        onView((withId(R.id.recipe_four))).perform(click());
//        intended(hasComponent(StepsActivity.class.getName()));
//        Intents.release();
//    }

}
