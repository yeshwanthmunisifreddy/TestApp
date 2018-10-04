package technology.nine.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        preferences = getApplicationContext().getSharedPreferences("LoginValues", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        final boolean login = preferences.getBoolean("Login", false);
        Log.e("Login", login + "");
        editor.apply();
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
              if (login){
                  startActivity(new Intent(SplashActivity.this, MainActivity.class));
                  finish();
              }
              if (!login){
                  startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                  finish();
              }

           }
       },2000);

    }


}
