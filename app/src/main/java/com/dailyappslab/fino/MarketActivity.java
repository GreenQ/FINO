package com.dailyappslab.fino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.dailyappslab.fino.R;

/**
 * Created by GreenQ on 26.03.2015.
 */
public class MarketActivity extends Activity {
    private RelativeLayout btn1000;
    private RelativeLayout btn2500;
    private RelativeLayout btn5000;
    private RelativeLayout btn10000;
    private RelativeLayout btn25000;
    private Button btnClose;
    private int currentPrise;
    private String currentPriseTag ="";
    private boolean readyToPurchase;
    private BillingProcessor bps;
    StoredPreferences storedPreferences;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameshop);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        btn1000 = ( RelativeLayout ) findViewById( R.id.m1 );
        btn2500 = ( RelativeLayout ) findViewById( R.id.m2 );
        btn5000 = ( RelativeLayout ) findViewById( R.id.m3 );
        btn10000 = ( RelativeLayout ) findViewById( R.id.m4 );
        btn25000 = ( RelativeLayout ) findViewById( R.id.m5 );
        btnClose = (Button) findViewById(R.id.btnClose);

        btn25000.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(25000, "4p1wruc25000" );
                }
            });

        btn10000.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(10000, "4p1wruc10000"  );
                }
            });

        btn5000.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(5000, "4p1wruc5000" );
                }
            });

        btn2500.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(2500, "4p1wruc2500" );
                }

            });

        btn1000.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(1000 , "4p1wruc1000" );
                }
            });

        btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( bps != null)
                        bps.release();
                    finish();
                }
            });


        bps = new BillingProcessor( this ,  getResources().getString( R.string.base64cod), new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {

            }
            @Override
            public void onBillingError(int errorCode, Throwable error) {
            }
            @Override
            public void onBillingInitialized() {
                readyToPurchase = true;

            }
            @Override
            public void onPurchaseHistoryRestored() {

            }
    });}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        bps.loadOwnedPurchasesFromGoogle();
        if(  bps.isPurchased( currentPriseTag ) )
        {
            bps.consumePurchase(currentPriseTag);
            storedPreferences.EditGoldAmount(currentPrise);
            currentPriseTag = "";
            currentPrise = 0;
        }
        if (!bps.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if ( bps != null)
            bps.release();
        super.onDestroy();
    }

    private void clickPrised(int integer , String id )
    {
        currentPrise = integer;
        currentPriseTag = id;
        if (!readyToPurchase) {
            showAlert("Billing not initialized.");
            return;
        }
        bps.purchase( currentPriseTag );
//        finish();
    }

    private void showAlert(String message)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle("App Title");
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}
