package com.example.greenq.fino;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by GreenQ on 21.02.2015.
 */
public class GameActivity extends ActionBarActivity{
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        image1 = (ImageView) findViewById(R.id.imageView);
        image2 = (ImageView) findViewById(R.id.imageView2);
        image3 = (ImageView) findViewById(R.id.imageView3);
        image4 = (ImageView) findViewById(R.id.imageView4);
    }

    protected  void getImages(String id)
    {
        Word v = new Word("1");
       // v.picture.sources(1);
    }

}
