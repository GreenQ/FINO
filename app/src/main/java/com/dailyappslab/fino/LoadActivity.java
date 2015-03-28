package com.dailyappslab.fino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dailyappslab.fino.R;

/**
 * Created by TOPAC on 08.03.2015.
 */
public class LoadActivity extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        setContentView(R.layout.load);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LoadActivity.this, MainActivity.class);
                startActivity(i);

                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}