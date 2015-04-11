package com.dailyappslab.fino;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by GreenQ on 11.04.2015.
 */
public class RateUsActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate);

        RelativeLayout rateUs = (RelativeLayout) findViewById(R.id.btnRate);
        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        RelativeLayout later = (RelativeLayout) findViewById(R.id.btnLater);
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RelativeLayout forget = (RelativeLayout) findViewById(R.id.btnForget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredPreferences storedPreferences = new StoredPreferences(getBaseContext(), 0, 0);
                storedPreferences.EditAskForRate();
                finish();
            }
        });
    }

    public void NeverAsk(View view)
    {


    }

}
