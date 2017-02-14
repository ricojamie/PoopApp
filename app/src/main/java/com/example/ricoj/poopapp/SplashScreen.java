package com.example.ricoj.poopapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent loginIntent = new Intent(getApplicationContext(), SignIn.class);
                    Intent mainIntent = new Intent(getApplicationContext(), Main2.class);
                    auth = FirebaseAuth.getInstance();
                    if (auth.getCurrentUser() != null) {
                        // User is logged in
                        startActivity(mainIntent);
                    } else {
                        if (auth.getCurrentUser() == null) {
                            startActivity(loginIntent);
                        }
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        myThread.start();
    }
}
