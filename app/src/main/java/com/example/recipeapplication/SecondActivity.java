package com.example.recipeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.analytics.FirebaseAnalytics;

public class SecondActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    YouTubePlayer.PlayerStateChangeListener onPlayerStateChangeListener;
    private Button buttonClose;
    private FirebaseAnalytics firebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtubePlayerView);

        onPlayerStateChangeListener=new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(String s) {

            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {

            }

            @Override
            public void onVideoEnded() {
                Bundle bundle = new Bundle();
                firebaseAnalytics.setUserProperty("saw_complete_video","true");
                String btnName="video_ended";
                firebaseAnalytics.logEvent(btnName,bundle);
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        };

        onInitializedListener= new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("5MgBikgcWnY");
                youTubePlayer.setPlayerStateChangeListener(onPlayerStateChangeListener);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        firebaseAnalytics= FirebaseAnalytics.getInstance(this);
        youTubePlayerView.initialize("<API CREDENTIALS>",onInitializedListener);
        buttonClose=(Button)findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("ButtonID",buttonClose.getId());
                String btnName="video_engagment_close";
                firebaseAnalytics.logEvent(btnName,bundle);
                Intent intent_info = new Intent(SecondActivity.this,MainActivity.class);
                startActivity(intent_info);
                finish();
            }
        });

    }
}