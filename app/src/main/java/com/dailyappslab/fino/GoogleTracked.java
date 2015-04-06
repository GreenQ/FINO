package com.dailyappslab.fino;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by GreenQ on 06.04.2015.
 */
public class GoogleTracked extends GoogleAnalytics
{

    private Tracker mGaTrackerSataAd;
    private Tracker mGaTracker;

    protected GoogleTracked(Context context)
    {
        super(context);
        try {

            String id = "UA-61200838-1";
            mGaTracker = getTracker();
            //String idSad = context.getResources().getString( R.string.ga_trackingStatAdId );
            //mGaTrackerSataAd = getTracker( idSad );
        }
        catch (Exception exception)
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
            dlgAlert.setMessage("Error occured" + exception.getMessage());
            dlgAlert.setTitle("Error occured");
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
    }

    public Tracker getTracker()
    {
        return mGaTracker;
    }

    public Tracker getTrackerStatAd()
    {
        return mGaTrackerSataAd;
    }
}