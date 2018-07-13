package com.morningstar.birthdaycard;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.waynell.library.DropAnimationView;

import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView, popUpBalloon, pictures;
    private MediaPlayer mediaPlayer;
    private ExplosionField explosionField;
    private DropAnimationView dropAnimationView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dropAnimationView = findViewById(R.id.dropanimationview);
        dropAnimationView.setDrawables(R.drawable.popballoon);
        dropAnimationView.startAnimation();
        textView = findViewById(R.id.wish);
        imageView = findViewById(R.id.ballons);
        imageView.setAlpha(0.9f);
        popUpBalloon = findViewById(R.id.popUpBalloon);
        pictures = findViewById(R.id.pictures);
        explosionField = ExplosionField.attach2Window(this);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, -550.0f);
        translateAnimation.setDuration(3000);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                popUpBalloon.setVisibility(View.VISIBLE);
                popUpBalloon.animate().alpha(0.7f).setDuration(3000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        explosionField.explode(popUpBalloon);
                        textView.setAlpha(0);
                        textView.animate().alpha(1).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(R.string.line1);
                            }
                        });
                        textView.animate().alpha(0).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                textView.setAlpha(1);
                                textView.setText(R.string.line1);
                                textView.animate().alpha(0).setDuration(12000).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView.setAlpha(1);
                                        textView.setText(R.string.line2);
                                        textView.animate().alpha(0).setDuration(15000).withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                textView.setAlpha(1);
                                                textView.setText(R.string.line3);
                                                textView.animate().alpha(0).setDuration(8000).withEndAction(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        explosionField.explode(textView);
                                                        pictures.setImageResource(R.drawable.happybirthday);
                                                        dropAnimationView.stopAnimation();
                                                        dropAnimationView.refreshDrawableState();
                                                        dropAnimationView.setDrawables(R.drawable.pic1, R.drawable.pic2, R.drawable.pic4, R.drawable.pic6, R.drawable.pic7);
                                                        dropAnimationView.startAnimation();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.song);
        imageView.setVisibility(View.VISIBLE);
        mediaPlayer.start();
        imageView.setAnimation(translateAnimation);
    }

    @Override
    public void onBackPressed() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        dropAnimationView.stopAnimation();
        super.onBackPressed();
    }
}
