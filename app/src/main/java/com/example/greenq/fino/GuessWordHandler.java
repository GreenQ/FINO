package com.example.greenq.fino;

import android.app.Activity;
import android.content.Context;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by GreenQ on 04.03.2015.
 */
public class GuessWordHandler extends Activity {

    public String GetWordByLvl(int lvl)
    {//getResources().getIdentifier("w" + lvl + "_1", "drawable", this.getPackageName())
        return getResources().getString(getResources().getIdentifier("w" + lvl, "strings", this.getPackageName()));
        //getResources().getString("w" + lvl)
    }

    public String[] GetWordCharArrayByLvl(int lvl)
    {
        return GetWordByLvl(lvl).split("(?!^)");
    }

    public void BuildWordContainers(LinearLayout layout, Context context, int lvl)
    {
        LinearLayout row = new LinearLayout(context);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        int maxJ = GetWordCharArrayByLvl(lvl).length;
        for (int j = 0; j < maxJ; j++) {
            ImageView btnTag = new ImageView(context);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setImageResource(R.drawable.ic_launcher);//setText("Button " + (j + 1 + (i * 4)));
            //btnTag.setBackgroundColor();
            btnTag.setId(j);
            row.addView(btnTag);
        }
    }

}
