package com.example.greenq.fino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    PopupWindow popupWindow;

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
        getDailyCoins();
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



        //ShowWinPopUp();

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

    private void getDailyCoins()
    {

        int daysFromLastVisit = DailyCoins.dateVadidation(storedPreferences.GetLastVisitDate(), getResources().getConfiguration().locale);
        switch (daysFromLastVisit)
        {
            case 7:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+10);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                showAmountOfAddedGold(10);
                break;
            case 6:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+15);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                showAmountOfAddedGold(15);
                break;
            case 5:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+20);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                showAmountOfAddedGold(20);
                break;
            case 4:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+25);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                showAmountOfAddedGold(25);
                break;
            case 3:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+30);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                showAmountOfAddedGold(30);
                break;
            case 2:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+35);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                showAmountOfAddedGold(35);
                break;
            case 1:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+40);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                showAmountOfAddedGold(40);
                break;
            case 8:
                storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+10);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());
                showAmountOfAddedGold(10);
                break;
            default:
                /*storedPreferences.EditGoldAmount(storedPreferences.GetGoldAmount()+10);
                storedPreferences.EditLastVisitDate(System.currentTimeMillis());*/
                showAmountOfAddedGold(0);
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
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(i, 1);
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                //finish();
            }
        }, SPLASH_TIME_OUT);
    }
    public void gameshopButtonClick(View v)
    {
        Log.d("animButton", "Click");
        animation.stop();
        animation.start();
        Intent i = new Intent (MainActivity.this, MarketActivity.class);
        //i = new Intent(main.this, )
        //startActivity(i);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, MarketActivity.class);
                //startActivityForResult(i, 1);
                startActivity(i);
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                //finish();
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

    public void ShowAboutPopUp(View view)
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        View popupView = layoutInflater.inflate(R.layout.about, null);
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        RelativeLayout btnBack = (RelativeLayout) popupView.findViewById(R.id.goBackFromPopup);
        View.OnClickListener closeOCL = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                };
        Button btnDismiss = (Button)popupView.findViewById(R.id.btnClose);
        ImageButton imgBtnBack = (ImageButton) popupView.findViewById(R.id.btnGoBackFromPopup);
        imgBtnBack.setOnClickListener(closeOCL);
        btnDismiss.setOnClickListener(closeOCL);
        btnBack.setOnClickListener(closeOCL);

        TextView curLevel = (TextView) popupWindow.getContentView().findViewById(R.id.textViewCurLevel);
        curLevel.setText(textViewCurrentLevel.getText());

        TextView curGold = (TextView) popupWindow.getContentView().findViewById(R.id.textViewGold);
        curGold.setText(textViewGold.getText());

        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(findViewById(R.id.rootLayoutMain), 0,0,-10);
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
