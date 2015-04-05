package com.dailyappslab.fino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by GreenQ on 05.04.2015.
 */
public class AboutUsActivity extends Activity {
    TextView textViewCurrentLevel;
    TextView textViewGold;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //requestWindowFeature(Window.PROGRESS_VISIBILITY_OFF);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.about);

        RelativeLayout btnBack = (RelativeLayout) findViewById(R.id.goBackFromPopup);
        View.OnClickListener closeOCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        StoredPreferences storedPreferences = new StoredPreferences(this, 0, 0);
        Button btnDismiss = (Button) findViewById(R.id.btnClose);
        ImageButton imgBtnBack = (ImageButton)  findViewById(R.id.btnGoBackFromPopup);
        imgBtnBack.setOnClickListener(closeOCL);
        btnDismiss.setOnClickListener(closeOCL);
        btnBack.setOnClickListener(closeOCL);

        TextView curLevel = (TextView) findViewById(R.id.textViewCurLevel);
        curLevel.setText(String.valueOf(storedPreferences.GetCurrentLevel()));

        TextView curGold = (TextView) findViewById(R.id.textViewGold);
        curGold.setText(String.valueOf(storedPreferences.GetGoldAmount()));
    }

    public void ShowGameShop(View view)
    {
        Log.d("animButton", "Click");
        Intent i = new Intent (AboutUsActivity.this, MarketActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(AboutUsActivity.this, MarketActivity.class);
                startActivity(i);
            }
        }, 100);
    }
}
