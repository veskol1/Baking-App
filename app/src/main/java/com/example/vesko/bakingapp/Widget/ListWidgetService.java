package com.example.vesko.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.vesko.bakingapp.Model.Ingredients;
import com.example.vesko.bakingapp.R;

import java.util.ArrayList;
import static com.example.vesko.bakingapp.Widget.BakingAppWidget.ingredientsArrayList;



public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(),ingredientsArrayList);
    }
}


class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
     Context mContext;
     ArrayList<Ingredients> ingredients;
     int numberOfItems=0;


    public ListRemoteViewsFactory(Context applicationContext,ArrayList<Ingredients> ingredientsArrayList){

        mContext=applicationContext;
        this.ingredients=ingredientsArrayList;
        this.numberOfItems = this.ingredients.size();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
            this.ingredients=ingredientsArrayList;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return numberOfItems;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.widget_recipe_ingredients, "* "+ingredients.get(position).getQuantity()+" "+ingredients.get(position).getMeasure()+" "+ingredients.get(position).getIngredient());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
