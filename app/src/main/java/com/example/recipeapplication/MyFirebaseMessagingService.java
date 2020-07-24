package com.example.recipeapplication;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;



public class MyFirebaseMessagingService extends FirebaseMessagingService {

    static String TAG = MyFirebaseMessagingService.class.getSimpleName();
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }
}
