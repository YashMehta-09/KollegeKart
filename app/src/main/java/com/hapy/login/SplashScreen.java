package com.hapy.login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3;
    ImageView imageView5;
    ImageView imageView2;
    Animation fromtop;
    Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        Splash sp = new Splash();
        sp.start();

        imageView2= (ImageView)findViewById(R.id.imageView2);
        imageView5= (ImageView)findViewById(R.id.imageView5);
        fromtop= AnimationUtils.loadAnimation(this,R.anim.fromtop);
        imageView5.setAnimation(fromtop);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        imageView2.setAnimation(frombottom);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);*/
    }


    private class Splash extends Thread{

        public void run(){

            try{

                sleep(1000*SPLASH_TIME_OUT);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            SplashScreen.this.finish();
        }
    }
}
