package com.example.greenq.fino;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.*;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MainActivity extends Activity {
    ImageView imgBtn;
    ImageView buttonImage;
    AnimationDrawable animation;
    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //requestWindowFeature(Window.PROGRESS_VISIBILITY_OFF);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.main);


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
                startActivity(i);
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
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

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
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        Button btnDismiss = (Button)popupView.findViewById(R.id.btnClose);
        btnDismiss.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                //NextLevelClick(null);
                popupWindow.dismiss();
            }});

        popupWindow.showAtLocation(findViewById(R.id.rootLayoutMain), 0,0,-10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
