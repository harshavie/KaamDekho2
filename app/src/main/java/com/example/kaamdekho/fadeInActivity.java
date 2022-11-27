package com.example.kaamdekho;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class fadeInActivity extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fade_in);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        anaEkranaGec();
    }
    private void anaEkranaGec(){
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.fade);
        imageView = findViewById(R.id.girislogo);
        anim.reset();
        imageView.clearAnimation();
        imageView.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(fadeInActivity.this,MainActivity.class);
                startActivity(intent);

                fadeInActivity.this.finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}