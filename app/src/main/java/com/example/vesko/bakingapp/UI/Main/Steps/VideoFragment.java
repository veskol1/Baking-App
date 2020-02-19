package com.example.vesko.bakingapp.UI.Main.Steps;

import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.vesko.bakingapp.Model.Recipe;
import com.example.vesko.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {
    @BindView(R.id.playerView)SimpleExoPlayerView mPlayerView;
    @BindView(R.id.image_player_view)ImageView imageview;
    private SimpleExoPlayer mExoPlayer;
    private Recipe recipe;
    private int recipePosition;
    private long playerPosition=0;
    private boolean isPlayWhenReady=true;
    private final String VIDEO_POSITION = "last_video_position";
    private final String VIDEO_PLAY_STATE = "video_play_state";
    private final String RECIPE_POSITION = "last_recipe_position";
    private final String RECIPE = "last_recipe";

    public VideoFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video,container,false);
        ButterKnife.bind(this,rootView);
        if (savedInstanceState!=null){
            recipe= savedInstanceState.getParcelable(RECIPE);
            recipePosition=savedInstanceState.getInt(RECIPE_POSITION);
            playerPosition=savedInstanceState.getLong(VIDEO_POSITION);
            isPlayWhenReady=savedInstanceState.getBoolean(VIDEO_PLAY_STATE);
        }

        if(recipe.getSteps().get(recipePosition).getVideoURL().isEmpty()) {
            mPlayerView.setVisibility(View.GONE);
            imageview.setVisibility(View.VISIBLE);
            try {
                Picasso.get()
                        .load(recipe.getSteps().get(recipePosition).getThumbnailURL())
                        .placeholder(R.drawable.loading_image)
                        .error(R.drawable.error_image)
                        .into(imageview);
            }
            catch (IllegalArgumentException e){
                imageview.setImageResource(R.drawable.error_image);
            }

        }
        else
            initializePlayer(Uri.parse(recipe.getSteps().get(recipePosition).getVideoURL()));



        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        }
        return rootView;
    }


    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "baking player");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(isPlayWhenReady);
            if(playerPosition!=0) {
                Log.d("player position=",""+playerPosition);
                mExoPlayer.seekTo(playerPosition);

            }
        }
        else{
            String userAgent = Util.getUserAgent(getActivity(), "baking player");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(isPlayWhenReady);
            if(playerPosition!=0) {
                Log.d("player position=",""+playerPosition);
                mExoPlayer.seekTo(playerPosition);

            }
        }
    }


    public void setRecipe(Recipe recipe){
        this.recipe=recipe;
    }

    public void setRecipePosition(int recipePosition){
        this.recipePosition=recipePosition;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mExoPlayer!=null)
            releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer!=null) {
            isPlayWhenReady=mExoPlayer.getPlayWhenReady();
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE,recipe);
        outState.putInt(RECIPE_POSITION,recipePosition);
        outState.putLong(VIDEO_POSITION,mExoPlayer.getCurrentPosition());
        outState.putBoolean(VIDEO_PLAY_STATE, isPlayWhenReady);

    }

}
