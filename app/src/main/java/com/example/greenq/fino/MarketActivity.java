package com.example.greenq.fino;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.android.vending.billing.IInAppBillingService;
import com.anjlab.android.iab.v3.BillingProcessor;

/**
 * Created by GreenQ on 26.03.2015.
 */
public class MarketActivity extends Activity {
    private RelativeLayout btn1000;
    private RelativeLayout btn2500;
    private RelativeLayout btn5000;
    private RelativeLayout btn10000;
    private RelativeLayout btn25000;
    private int currentPrise;
    private String currentPriseTag ="";
    private boolean readyToPurchase;
    private BillingProcessor bps;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameshop);

        btn1000 = ( RelativeLayout ) findViewById( R.id.m1 );
        btn2500 = ( RelativeLayout ) findViewById( R.id.m2 );
        btn5000 = ( RelativeLayout ) findViewById( R.id.m3 );
        btn10000 = ( RelativeLayout ) findViewById( R.id.m4 );
        btn25000 = ( RelativeLayout ) findViewById( R.id.m5 );

        if( btn25000 != null )
        {
            btn25000.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(25000, "4P1WRc25000" );
                }
            });
        }


    }

    private void clickPrised(int integer , String id )
    {

        currentPrise = integer;
        currentPriseTag = id;
        if (!readyToPurchase) {
            //showToast("Billing not initialized.");
            return;
        }
        bps.purchase( currentPriseTag );
//        bps.purchase("android.test.purchased");

//        bps.getPurchaseTransactionDetails("coins_100");
//        bps.release();
//        Application.getInstance().getUserAccount().setUserPoint( integer );
//        Intent intent = new Intent();
//        setResult( Activity.RESULT_OK, intent );
//        finish();
    }
}
