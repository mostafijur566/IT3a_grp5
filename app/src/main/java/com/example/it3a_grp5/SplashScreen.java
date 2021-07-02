package com.example.it3a_grp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.it3a_grp5.MainActivity;
import com.example.it3a_grp5.R;

public class SplashScreen extends AppCompatActivity {

    Animation topAnim, sideAnim, rightSideAnimation;
    ImageView image;
    TextView t1, t2, t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_animation);
        rightSideAnimation = AnimationUtils.loadAnimation(this, R.anim.right_side_animation);

        image = (ImageView)findViewById(R.id.image);
        t1 = (TextView)findViewById(R.id.t1);
        t2 = (TextView)findViewById(R.id.t2);
        t3 = (TextView)findViewById(R.id.t3);

        image.setAnimation(topAnim);
        t1.setAnimation(sideAnim);
        t2.setAnimation(rightSideAnimation);
        t3.setAnimation(rightSideAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}