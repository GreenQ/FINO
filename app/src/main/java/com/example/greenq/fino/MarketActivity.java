package com.example.greenq.fino;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.android.vending.billing.IInAppBillingService;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

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

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameshop);

        btn1000 = ( RelativeLayout ) findViewById( R.id.m1 );
        btn2500 = ( RelativeLayout ) findViewById( R.id.m2 );
        btn5000 = ( RelativeLayout ) findViewById( R.id.m3 );
        btn10000 = ( RelativeLayout ) findViewById( R.id.m4 );
        btn25000 = ( RelativeLayout ) findViewById( R.id.m5 );
        btnClose = (Button) findViewById(R.id.btnClose);


        if( btn25000 != null )
        {
            btn25000.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(25000, "4P1WRuC25000" );
                }
            });
        }

        if( btn10000 != null )
        {
            btn10000.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(10000, "4P1WRuC10000"  );
                }
            });
        }

        if( btn5000 != null )
        {
            btn5000.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(5000, "4P1WRuC5000" );
                }
            });
        }

        if( btn2500 != null )
        {
            btn2500.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(2500, "4P1WRuC5000" );
                }

            });
        }

        if( btn1000 != null )
        {
            btn1000.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPrised(1000 , "coins1500" );
                }
            });
        }

        if( btnClose != null )
        {
            btnClose.setOnClickListener( clickListener );
        }

        bps = new BillingProcessor( this ,  getResources().getString( R.string.base64cod), new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
//                showToast("onProductPurchased: " + productId);
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
            //Application.getInstance().sendGAnalitics( "prise " + currentPriseTag  );
            bps.consumePurchase(currentPriseTag);
            //Application.getInstance().getUserAccount().setUserPoint( currentPrise );
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
//        bps.purchase("android.test.purchased");

//        bps.getPurchaseTransactionDetails("coins_100");
//        bps.release();
//        Application.getInstance().getUserAccount().setUserPoint( integer );
//        Intent intent = new Intent();
//        setResult( Activity.RESULT_OK, intent );
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

    View.OnClickListener clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent();
            intent.putExtra("test" , "test2");
            setResult( Activity.RESULT_OK, intent );
            if ( bps != null)
                bps.release();
            finish();
        }
    };
}
