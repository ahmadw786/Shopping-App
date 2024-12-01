package com.example.shoppingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView logo = findViewById(R.id.logo);

        // Add Translate Animation
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -500, 0);
        translateAnimation.setDuration(1000);

        // Add Scale Animation
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setStartOffset(1000);

        // Start Animations
        logo.startAnimation(translateAnimation);
        logo.startAnimation(scaleAnimation);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Delay navigation until animations are complete
        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                // User is logged in, navigate to Shopping List
                startActivity(new Intent(this, ShoppingListActivity.class));
            } else {
                // User is not logged in, navigate to Login
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        }, 2000);
    }
}
