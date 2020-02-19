package com.example.vesko.bakingapp.UI.Main.Steps;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vesko.bakingapp.Model.Recipe;
import com.example.vesko.bakingapp.Model.Steps;
import com.example.vesko.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    private static int viewHolderCount;
    private int nNumberItems;
    private Recipe recipe;
    final private ListItemClickListener mOnClickListener;
    private int selectedPosition = -1;

    public StepsAdapter(int nNumberItems, Recipe recipe, ListItemClickListener mOnClickListener){
        this.nNumberItems=nNumberItems;
        this.recipe=recipe;
        this.mOnClickListener=mOnClickListener;
        viewHolderCount=0;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public int getItemCount() {
        return nNumberItems;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.step_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        StepViewHolder stepViewHolder = new StepViewHolder(view);

        viewHolderCount++;
        return stepViewHolder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.bind(position);
        if (selectedPosition == position) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#4c73c0fb"));
        }
        else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#1AA5D8FF"));
        }
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.step_tv_view)TextView stepView;
        @BindView(R.id.recycler_card_view)CardView cardView;

        public StepViewHolder(View viewHolder) {
            super(viewHolder);
            ButterKnife.bind(this, viewHolder);
            viewHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickPosition);
            selectedPosition=clickPosition;
            notifyDataSetChanged();

        }

        public void bind(int position){
            ArrayList<Steps> steps = recipe.getSteps();
            stepView.setText(steps.get(position).getShortDescription());
        }
    }

}
