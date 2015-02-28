package com.example.greenq.fino;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by GreenQ on 21.02.2015.
 */
public class GameActivity extends Activity {
    public static final String curLevel = "Current Level1";


    View.OnClickListener CentralImagesOCL;
    ImageButton image1;
    ImageButton image2;
    ImageButton image3;
    ImageButton image4;
    TextView textView;
    int DefaultLevel = 1;
    SharedPreferences preferences;
    String Lvl = "LVL";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.game);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game);
        preferences = this.getSharedPreferences(Lvl, Context.MODE_PRIVATE);

        image1 = (ImageButton) findViewById(R.id.imageView1);
        image2 = (ImageButton) findViewById(R.id.imageView2);
        image3 = (ImageButton) findViewById(R.id.imageView3);
        image4 = (ImageButton) findViewById(R.id.imageView4);
        textView = (TextView) findViewById(R.id.textView);
        CentralImagesOCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int curLevel = GetCurrentLevel();
                switch (v.getId()) {
                    case R.id.imageView1:
                        EditLevel(GetCurrentLevel() + 1);
                        SetImagesByLevel(GetCurrentLevel());
                        //textView.setText(String.valueOf(GetCurrentLevel()));
                        break;
                    case R.id.imageView2:
                        EditLevel(GetCurrentLevel() - 1);
                        SetImagesByLevel(GetCurrentLevel());
                        //textView.setText(String.valueOf(GetCurrentLevel()));
                        break;
                    case R.id.imageView3:
                        break;
                    case R.id.imageView4:
                        break;
                }
            }
        };
        image1.setOnClickListener(CentralImagesOCL);
        image2.setOnClickListener(CentralImagesOCL);
        image3.setOnClickListener(CentralImagesOCL);
        image4.setOnClickListener(CentralImagesOCL);
        DefaultLevel = GetCurrentLevel();



        SetImagesByLevel(GetCurrentLevel());

    }



    private void SetImagesByLevel(int lvl)
    {
        image1.setImageResource(getResources().getIdentifier("w" + lvl + "_1", "drawable", this.getPackageName()));
        image2.setImageResource(getResources().getIdentifier("w" + lvl + "_2", "drawable", this.getPackageName()));
        image3.setImageResource(getResources().getIdentifier("w" + lvl + "_3", "drawable", this.getPackageName()));
        image4.setImageResource(getResources().getIdentifier("w" + lvl + "_4", "drawable", this.getPackageName()));
    }

    private boolean CheckLevelRange(int i)
    {
        if(i > 0 && i <400)
            return true;
        else
            return false;
    }

    public int GetCurrentLevel()
    {
        int temp = preferences.getInt(Lvl, 1) ;

        if (CheckLevelRange(temp))
            return temp;
        else
            return DefaultLevel;
        //return preferences.getInt(curLevel, 1);
    }

    private void EditLevel(int i)
    {
        if (!CheckLevelRange(i))
            return;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Lvl, i);
        editor.commit();
        DefaultLevel = i;
    }
}
