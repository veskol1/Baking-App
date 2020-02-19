package com.example.vesko.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.vesko.bakingapp.Model.Ingredients;
import com.example.vesko.bakingapp.R;


import java.util.ArrayList;


/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {
    public static ArrayList<Ingredients> ingredientsArrayList=null;
    public static String recipeName="";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId , int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);

        if(ingredientsArrayList!=null) {
            views.setTextViewText(R.id.widget_title, "Ingredients:");
            views.setTextViewText(R.id.widget_recipe_title,recipeName);
            Intent intent = new Intent(context, ListWidgetService.class);
            views.setRemoteAdapter(R.id.widget_recipe_one_list_view, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingAppWidget.class));

        if (intent.getAction().equals("android.appwidget.action.CUSTOM_INTENT")) {
            ingredientsArrayList = new ArrayList<>();
            Bundle data = intent.getExtras();
            try{
                ingredientsArrayList = data.getParcelableArrayList("ingredient array list");
                recipeName = data.getString("recipe ingredient name");
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipe_one_list_view);
                onUpdate(context, appWidgetManager, appWidgetIds);

            }catch (Exception e){
                Log.d("errrror","erorrr");
            }

            super.onReceive(context, intent);
      }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId ,appWidgetIds);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}


