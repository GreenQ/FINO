package com.dailyappslab.fino;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.dailyappslab.fino.R;

/**
 * Created by GreenQ on 18.03.2015.
 */
public class PopUps extends Activity {

    public void ShowGameshop()
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
                popupWindow.dismiss();
            }});

        popupWindow.showAtLocation(findViewById(R.id.rootLayoutMain), 0,0,-10);
    }

}
