package com.example.greenq.fino;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by GreenQ on 21.02.2015.
 */
public class GameActivity extends Activity {
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        image1 = (ImageView) findViewById(R.id.imageView2);
        image2 = (ImageView) findViewById(R.id.imageView4);
        image3 = (ImageView) findViewById(R.id.imageView3);
        image4 = (ImageView) findViewById(R.id.imageView);

        getImages("1");
    }

    protected  void getImages(String id)
    {
        Word v = new Word("1");
        int i = 0;
       // for (String s : v.picture.sources) {
         //   System.out.println("Next item: " + s);
            //image4.setImageResource(getResId("w1_1", Drawable.class));
        //Drawable d = getResources().getDrawable(android.R.drawable.ic_dialog_email);
        //ImageView image = (ImageView)findViewById(R.id.image);
        //image.setImageDrawable(d);
       // getDrawable(22);
        //getSt
        int curLev = R.string.currentLevel;
        int s = getResources().getIdentifier("w1_1", "drawable", this.getPackageName());
        image1.setImageResource(s);
        int ss = getResources().getIdentifier("w1_2", "drawable", this.getPackageName());
        image2.setImageResource(ss);
        int sss = getResources().getIdentifier("w1_3", "drawable", this.getPackageName());
        image3.setImageResource(sss);
        int ssss = getResources().getIdentifier("w1_4", "drawable", this.getPackageName());
        image4.setImageResource(ssss);


        //image4.setImageResource(getResId("w1_1", Drawable.class));
    //image2.setImageResource(R.drawable.w1_2);
            //image2.setImageResource();
        //}
       // v.picture.sources(1);


    }

    public static int getResId(String variableName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
