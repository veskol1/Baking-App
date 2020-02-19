package com.example.vesko.bakingapp.UI.Main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vesko.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment {
    @BindView(R.id.recipe_image_view) ImageView imageView;

    private int indexImg;
    public RecipeFragment(){
    }

    onImageClickListener mCallback;

    public interface onImageClickListener{
         void onImageSelected(int position);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (onImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe,container,false);
         ButterKnife.bind(this,rootView);
        switch (indexImg) {
            case 0:
                imageView.setImageResource(R.drawable.nutella_pie);
                break;
            case 1:
                imageView.setImageResource(R.drawable.brownies);
                break;
            case 2:
                imageView.setImageResource(R.drawable.yellow_cake);
                break;
            case 3:
                imageView.setImageResource(R.drawable.cheesecake);
                break;
        }
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCallback.onImageSelected(indexImg);
            }
        });

        return rootView;
    }


    public void setIndexImg(int indexImg) {
        this.indexImg = indexImg;
    }
}
