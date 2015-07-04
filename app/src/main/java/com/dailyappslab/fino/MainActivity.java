package com.dailyappslab.fino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.*;


//import ads.adapter.*;
import com.google.ads.*;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import com.dailyappslab.fino.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.startad.lib.SADView;
import org.w3c.dom.Text;

public class MainActivity extends Activity {
    ImageView imgBtn;
    ImageView buttonImage;
    AnimationDrawable animation;
    StoredPreferences storedPreferences;
    private static int SPLASH_TIME_OUT = 220;
    int DefaultLevel = 1;
    int DefaultGold = 200;
    TextView textViewCurrentLevel;
    TextView textViewGold;
    int goldBonus;
    TextView textViewgoldBonus;
    TextView textViewDays;
    int days;
    PopupWindow popupWindow;
    PopupWindow popupWindowCoins;
    boolean wasShown = false;
    AdView adView;
    SADView sadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //requestWindowFeature(Window.PROGRESS_VISIBILITY_OFF);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.main);


        storedPreferences = new StoredPreferences(this, DefaultLevel, DefaultGold);
        //getDailyCoins();
        textViewCurrentLevel = (TextView) findViewById(R.id.textViewCurLevel);
        textViewCurrentLevel.setText(String.valueOf(storedPreferences.GetCurrentLevel()));

        textViewGold = (TextView) findViewById(R.id.textViewGold);
        textViewGold.setText(String.valueOf(storedPreferences.GetGoldAmount()));

//загрузка ImageView заранее подготовленной анимацией из ресурса:
        buttonImage = (ImageView) findViewById(R.id.imageButtonPlay);
        buttonImage.setBackgroundResource(R.drawable.btnanim);
//загрузка объекта анимации:


        animation = (AnimationDrawable) buttonImage.getBackground();
//анимация будет проигрываться только 1 раз:


        //imgBtn = (ImageView) findViewById(R.id.imageButtonPlay);
      /*  imgBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){

                Intent i = new Intent (MainActivity.this, GameActivity.class);
                //i = new Intent(main.this, )
                //startActivity(i);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, GameActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, SPLASH_TIME_OUT);
                overridePendingTransition(R.animator.layouts_transition1, R.animator.layout_transition2);
            }
        });*/
        animation.setOneShot(true);


       /* adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-3376890691318599/3908610460");
        adView.setAdSize(AdSize.BANNER); //Размер баннера
        LinearLayout layout = (LinearLayout)findViewById(R.id.admob);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        /*AdRequest adRequest =new com.google.android.gms.ads.AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("0123456789ABCDEF").build();*/
        //adView.loadAd(adRequest);
        //ShowWinPopUp();
        //getDailyCoins();
        //ShowDailyCoins();
        //ShowAboutPopUp(null);
        AdView adView = (AdView)this.findViewById(R.id.adView);
        try
        {
        //adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId("ca-app-pub-3376890691318599/3908610460");
        //AdRequest adRequest = new AdRequest.Builder().build();


          //  adView.loadAd(adRequest);

            sadView = new SADView(this, getResources().getString(R.string.sadViewMiniBanner));
            LinearLayout layout = (LinearLayout)findViewById(R.id.admob);

            // Add the adView to it
            layout.addView(this.sadView);
            sadView.loadAd(SADView.LANGUAGE_RU);

            //((Application) getApplication()).getTracker(Application.TrackerName.APP_TRACKER);
           // ((AppTracker) getApplication()).getTracker(AppTracker.TrackerName.APP_TRACKER);

//            Application application = new Application();
//            Tracker t =  application.getTracker(Application.TrackerName.APP_TRACKER);
//            t.setScreenName("MainActivity");
//            t.send(new HitBuilders.AppViewBuilder().build());
            //Tracker t = (application.getTracker(
            //        Application.TrackerName.APP_TRACKER));

            // Set screen name.
            // Where path is a String representing the screen name.
            //t.setScreenName("MainActivity");

            // Send a screen view.
            //t.send(new HitBuilders.AppViewBuilder().build());

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
        Globals.RateRequestDone = true;
        ShowRateUs();

    }

//    @Override
//    public void onStart()
//    {
//        try {
//            GoogleAnalytics.getInstance(this).reportActivityStart(this);
//        }
//        catch (Exception ex)
//        {
//            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
//            dlgAlert.setMessage("Error occured" + ex.getMessage());
//            dlgAlert.setTitle("Error occured");
//            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    //dismiss the dialog
//                }
//            });
//            dlgAlert.setCancelable(true);
//            dlgAlert.create().show();
//        }
//    }
//
//    @Override
//    public void onStop()
//    {
//        try{
//        GoogleAnalytics.getInstance(this).reportActivityStop(this);
//    }
//    catch (Exception ex)
//    {
//        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
//        dlgAlert.setMessage("Error occured" + ex.getMessage());
//        dlgAlert.setTitle("Error occured");
//        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                //dismiss the dialog
//            }
//        });
//        dlgAlert.setCancelable(true);
//        dlgAlert.create().show();
//    }
//}


    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        if(hasFocus && !wasShown) {
            getDailyCoins();
            wasShown = true;
        }

        /*ads.adapter. ad = new ru.chinaprices.lib.ads.AdMob(activity, adMobId);

        ru.chinaprices.lib.ads.AdManager manager = new AdManager(ad);
        manager.show(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);*/
    }
    private void showAmountOfAddedGold(int amount)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Amount of added gold: " + amount);
        dlgAlert.setTitle("App Title");
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public void ShowRateUs()
    {
        boolean s = storedPreferences.AskForRate();
        if(storedPreferences.AskForRate()) {
            if (Globals.RateRequestDone == true) {
                Globals.RateRequestDone = false;
                //Log.d("animButton", "Click");
                //Intent i = new Intent(MainActivity.this, RateUsActivity.class);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, RateUsActivity.class);
                        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                        startActivity(i);
                    }
                }, 1000 * 60 * 5);
            }
        }
    }

    private void getDailyCoins()
    {

        int daysFromLastVisit = DailyCoins.getAmountOfSequencialVisits(storedPreferences.GetLastVisitDate(), getResources().getConfiguration().locale, storedPreferences.GetSequencialVisitsAmount());
        switch (daysFromLastVisit)
        {
            case 0:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+10);
                storedPreferences.EditSeqvencialVisitAmount(1);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                //showAmountOfAddedGold(10);
                goldBonus = 10;
                ShowDailyCoins();
                break;
            case 10:
                break;
            case 7:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+40);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                storedPreferences.EditSeqvencialVisitAmount(storedPreferences.GetSequencialVisitsAmount()+1);
                //showAmountOfAddedGold(40);
                goldBonus = 40;
                ShowDailyCoins();
                break;
            case 6:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+35);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                storedPreferences.EditSeqvencialVisitAmount(storedPreferences.GetSequencialVisitsAmount() + 1);
                //showAmountOfAddedGold(35);
                goldBonus = 35;
                ShowDailyCoins();
                break;
            case 5:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+30);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                storedPreferences.EditSeqvencialVisitAmount(storedPreferences.GetSequencialVisitsAmount() + 1);
                //showAmountOfAddedGold(30);
                goldBonus = 30;
                ShowDailyCoins();
                break;
            case 4:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+25);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                storedPreferences.EditSeqvencialVisitAmount(storedPreferences.GetSequencialVisitsAmount() + 1);
                //showAmountOfAddedGold(25);
                goldBonus = 25;
                ShowDailyCoins();
                break;
            case 3:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+20);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                storedPreferences.EditSeqvencialVisitAmount(storedPreferences.GetSequencialVisitsAmount() + 1);
                //showAmountOfAddedGold(20);
                goldBonus = 20;
                ShowDailyCoins();
                break;
            case 2:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+15);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                storedPreferences.EditSeqvencialVisitAmount(storedPreferences.GetSequencialVisitsAmount() + 1);
                //showAmountOfAddedGold(15);
                goldBonus = 15;
                ShowDailyCoins();
                break;
            case 1:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+10);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                storedPreferences.EditSeqvencialVisitAmount(storedPreferences.GetSequencialVisitsAmount()+1);
                //showAmountOfAddedGold(10);
                goldBonus = 10;
                ShowDailyCoins();
                break;
            default:
                /*storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+10);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());*/
                //showAmountOfAddedGold(0);
                break;
        }
        storedPreferences.EditLastVisitDate(System.currentTimeMillis());
    }

    public void animButtonClick(View v)
    {
        Log.d("animButton", "Click");
        animation.stop();
        animation.start();
        Intent i = new Intent (MainActivity.this, GameActivity.class);
        //i = new Intent(main.this, )
        //startActivity(i);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(i, 1);
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                //finish();
            }
        }, SPLASH_TIME_OUT);
        textViewGold.setText(String.valueOf(storedPreferences.GetGoldAmount()));
    }

    public void gameshopButtonClick(View v)
    {
        Log.d("animButton", "Click");
        Intent i = new Intent (MainActivity.this, MarketActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, MarketActivity.class);
                startActivity(i);
            }
        }, SPLASH_TIME_OUT);
    }


    public void ShowGameshopPopUp(View view)
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        View popupView = layoutInflater.inflate(R.layout.gameshop, null);
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        //popupWindow.setBackgroundDrawable(getDrawable(R.drawable.fon2));

        Button btnDismiss = (Button)popupView.findViewById(R.id.btnClose);
        btnDismiss.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                //NextLevelClick(null);
                popupWindow.dismiss();
            }});


        popupWindow.showAtLocation(findViewById(R.id.rootLayoutMain), 0,0,-10);
    }

    public void ShowDailyCoins()
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        View popupViewCoins = layoutInflater.inflate(R.layout.daily_coins, null);

        popupWindowCoins = new PopupWindow(
                popupViewCoins,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        //popupWindow.setBackgroundDrawable(getDrawable(R.drawable.fon2));

        textViewgoldBonus = (TextView) popupViewCoins.findViewById(R.id.textViewCoins);
        textViewgoldBonus.setText(String.valueOf(goldBonus));

        textViewDays = (TextView) popupViewCoins.findViewById(R.id.textViewDay);
        textViewDays.setText("День " + String.valueOf(storedPreferences.GetSequencialVisitsAmount()));

        Button btnDismiss = (Button)popupViewCoins.findViewById(R.id.btnClose);
        btnDismiss.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                //NextLevelClick(null);
                popupWindowCoins.dismiss();
            }});

        popupWindowCoins.showAtLocation(findViewById(R.id.rootLayoutMain), 0,0,-10);
        //popupWindowCoins.showAsDropDown(findViewById(R.id.rootLayoutMain));
    }

    public void ShowAboutPopUp(View view)
    {

        Log.d("animButton", "Click");
        Intent i = new Intent (MainActivity.this, AboutUsActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(i);
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

 //   @Override
   // public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
     //   int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
         //   return true;
        //}

        //return super.onOptionsItemSelected(item);
    //}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            textViewCurrentLevel.setText(String.valueOf(data.getIntExtra("CurrentLevel")));
            num2 = data.getIntExtra(Number2Code);
        }*/
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    textViewCurrentLevel.setText(String.valueOf(data.getIntExtra("CurrentLevel", 1)));
                    textViewGold.setText(String.valueOf(data.getIntExtra("CurrentGoldAmount", 2)));
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
