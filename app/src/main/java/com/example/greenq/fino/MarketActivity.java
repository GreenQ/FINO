package com.example.greenq.fino;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.android.vending.billing.IInAppBillingService;

/**
 * Created by GreenQ on 26.03.2015.
 */
public class MarketActivity extends Activity {
    private RelativeLayout btn1000;
    private RelativeLayout btn2500;
    private RelativeLayout btn5000;
    private RelativeLayout btn10000;
    private RelativeLayout btn25000;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameshop);

        btn1000 = ( RelativeLayout ) findViewById( R.id.m1 );
        btn2500 = ( RelativeLayout ) findViewById( R.id.m2 );
        btn5000 = ( RelativeLayout ) findViewById( R.id.m3 );
        btn10000 = ( RelativeLayout ) findViewById( R.id.m4 );
        btn25000 = ( RelativeLayout ) findViewById( R.id.m5 );


    }
}
