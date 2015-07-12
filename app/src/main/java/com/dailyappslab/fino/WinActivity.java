package com.dailyappslab.fino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dailyappslab.fino.R;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Game;
import com.startad.lib.SADView;

/**
 * Created by GreenQ on 15.03.2015.
 */
public class WinActivity extends Activity{
    SADView sadView;
    StoredPreferences storedPreferences;
    //InterstitialAd interstitial;
    GameActivity gameActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.game);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.win);
        storedPreferences = new StoredPreferences(this, 1, 200);
        gameActivity = new GameActivity();
        ShowWinPopUp();

        if(Globals.interstitialAd.isLoaded())
            Globals.interstitialAd.show();

//        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
//        RelativeLayout nestedLayout = (RelativeLayout) findViewById(R.id.nestedLayout);
//        mainLayout.setBackgroundColor(getResources().getColor(R.color.mainLayoutColor));
//        nestedLayout.setBackgroundColor(getResources().getColor(R.color.nestedLayoutColor));


    }

    private void ShowWinPopUp()
    {


//        LayoutInflater layoutInflater
//                = (LayoutInflater)getBaseContext()
//                .getSystemService(LAYOUT_INFLATER_SERVICE);
//
//        View popupView = layoutInflater.inflate(R.layout.win, null);
//        final PopupWindow popupWindowWin = new PopupWindow(
//                popupView,
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
        try
        {
            sadView = new SADView(this, getResources().getString(R.string.sadViewMiniBanner));
            LinearLayout layout = (LinearLayout) findViewById(R.id.admob);
            layout.addView(this.sadView);
            sadView.loadAd(SADView.LANGUAGE_RU);
        }
        catch (Exception ex)
        {}

        printGuessedWord();
        printQuotation();
        Button btnDismiss = (Button) findViewById(R.id.buttonClose);
        btnDismiss.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(storedPreferences.GetCurrentLevel() == 250)
                    storedPreferences.EditFirstPackCompletness();
                //gameActivity.NextLevelClick(null);
                Globals.GameAct.finish();
                finish();
                startActivity(Globals.RestartingIntent);
            }});


        try {
//            if(interstitial.isLoaded())
//            interstitial.show();
            // DelayedAdsShow();
        }
        catch (Exception ex)
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Error occured" + ex.getMessage());
            dlgAlert.setTitle("Error occured");
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
    }

    private void printQuotation()
    {//return imageView.getResources().getIdentifier("w" + storedPreferences.GetCurrentLevel() + "_" + String.valueOf(btnNum), "drawable", this.getPackageName());

        try {
            TextView quotation = (TextView) findViewById(R.id.textViewQuotation);
            String quote = getResources().getString(getResources().getIdentifier("q" + storedPreferences.GetCurrentLevel(), "string", this.getPackageName()));
            quotation.setText(quote);
        }
        catch(Exception exception)
        {}
    }
    private void printGuessedWord()
    {
        //setContentView(R.layout.win);
        TextView winWord = (TextView) findViewById(R.id.guessWordTextView);
        StringBuilder builder = new StringBuilder();
        builder.append(" ");
        int id = getResources().getIdentifier("w" + storedPreferences.GetCurrentLevel(), "string", this.getPackageName());
        // String word = getResources().getString(id);
        String[] word = (getResources().getString(id)).split("(?!^)");
        for(String s : word) {
            builder.append(s);
            builder.append(" ");
        }
        winWord.setText(builder.toString());
    }

    @Override
    public void onBackPressed()
    {
        Globals.GameAct.finish();
        finish();
        startActivity(Globals.RestartingIntent);
    }

}
