package com.example.recipeapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener,GestureDetector.OnGestureListener {

    public static final int SWIPE_THRESHOLD = 100;
    public static final int VELOCITY_THRESHOLD = 100;
    private ImageView imageView;
    private GestureDetector gestureDetector ;
    private LinearLayout linearLayout;
    private Button buttonVideo,buttonImage;
    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=(ImageView)findViewById(R.id.imageView);
        gestureDetector=new GestureDetector(this,this);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        mAuth = FirebaseAuth.getInstance();
        firebaseAnalytics= FirebaseAnalytics.getInstance(this);

        firebaseAnalytics.setUserId(mAuth.getCurrentUser().getUid());

        linearLayout.setOnTouchListener(this);

        buttonVideo=(Button)findViewById(R.id.buttonVideoDemo);
        buttonImage=(Button)findViewById(R.id.buttonImageDemo);


        buttonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("ButtonID",buttonVideo.getId());
                String btnName="video_engagment_shown";
                firebaseAnalytics.setUserProperty("opened_video","true");
                firebaseAnalytics.logEvent(btnName,bundle);
//                FirebaseFunctions mFunctions;
//                mFunctions = FirebaseFunctions.getInstance();
//                mFunctions.getHttpsCallable("ewfe").call();


                Intent intent_info = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up,R.anim.no_change);
                finish();
            }
        });

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("ButtonID",buttonVideo.getId());
                String btnName="image_engagment_shown";
                firebaseAnalytics.setUserProperty("opened_image","true");
                firebaseAnalytics.logEvent(btnName,bundle);

                Intent intent_info = new Intent(MainActivity.this,ImageActivity.class);
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up,R.anim.no_change);
                finish();
            }
        });

        ((TextView)findViewById(R.id.textLogout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    // Gesture Listener Methods

    private String TAG= "Main Activity";

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.i(TAG,"onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.i(TAG,"On ShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.i(TAG,"On SingleTap");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.i(TAG,"On Scroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.i(TAG,"On LongPress");
    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float x, float y) {
        Log.i(TAG,"On Fling");
        boolean result=false;
        float diffX=moveEvent.getX()-downEvent.getX();
        float diffY=moveEvent.getY()-downEvent.getY();

        if(Math.abs(diffX)>Math.abs((diffY))){
            // for right and left
            if(Math.abs(diffX)> SWIPE_THRESHOLD && Math.abs(x)> VELOCITY_THRESHOLD){
                if(diffX>0){
                    //swipeRight;
                }
                else {
                    //swipeLeft;
                }
            }
            result = true;
        }
        else {
            // for up and down
            if(Math.abs(diffY)> SWIPE_THRESHOLD && Math.abs(y)> VELOCITY_THRESHOLD){
                if(diffY>0){
                    //swipeDown;
                }
                else {
                    swipeUp();

                }
            }
            result = true;
        }
        return result;
    }

    private void swipeUp(){
        Intent intent_info = new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent_info);
        overridePendingTransition(R.anim.slide_up,R.anim.no_change);
        finish();
    }

}