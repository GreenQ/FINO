package com.dailyappslab.fino;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.analytics.tracking.android.Fields;
import com.google.android.gms.analytics.*;
import com.google.android.*;
import com.dailyappslab.fino.GoogleTracked;
import java.util.HashMap;

/**
 * Created by GreenQ on 05.04.2015.
 */
public class Application extends android.app.Application {

    private static final String PROPERTY_ID = "UA-61200838-1";

    //Logging TAG
    private static final String TAG = "MyApp";

    public static int GENERAL_TRACKER = 0;

    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public Application() {
        super();
    }

    synchronized Tracker getTracker(TrackerName trackerId) {
        try {
            if (!mTrackers.containsKey(trackerId)) {

                GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
                Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
                        : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                        : analytics.newTracker(R.xml.ecommerce_tracker);
                mTrackers.put(trackerId, t);

            }
            return mTrackers.get(trackerId);
        }
        catch (Exception ex)
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Error occured" + ex.getMessage());
            dlgAlert.setTitle("Error occured");
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        return mTrackers.get(trackerId);
    }

//    GoogleTracked gTracked;
//
//
//
//    public Application(Context context)
//    {
//        try {
//            gTracked = new GoogleTracked(context);
//        }
//        catch(Exception exception)
//        {
//            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
//            dlgAlert.setMessage("Error occured" + exception.getMessage());
//            dlgAlert.setTitle("Error occured");
//            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    //dismiss the dialog
//                }
//            });
//            dlgAlert.setCancelable(true);
//            dlgAlert.create().show();
//        }
//    }
//
//    public void sendAnalytics(String str)
//    {
//        try {
//            HashMap<String, String> hitParameters = new HashMap<String, String>();
//            hitParameters.put(Fields.HIT_TYPE, "appview");
//            hitParameters.put(Fields.SCREEN_NAME, str);
//            gTracked.getTracker().send(hitParameters);
//        }
//        catch(Exception exception)
//        {
//            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
//            dlgAlert.setMessage("Error occured" + exception.getMessage());
//            dlgAlert.setTitle("Error occured");
//            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    //dismiss the dialog
//                }
//            });
//            dlgAlert.setCancelable(true);
//            dlgAlert.create().show();
//        }
//    }
//    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
//
//    synchronized Tracker getTracker(TrackerName trackerId) {
//        if (!mTrackers.containsKey(trackerId)) {
//
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
//                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
//                    : analytics.newTracker(R.xml.ecommerce_tracker);
//            mTrackers.put(trackerId, t);
//
//        }
//        return mTrackers.get(trackerId);
//    }
//
//    public void getScreen()
//    {
//        // Get tracker.
//        Tracker t = ((AnalyticsSampleApp) getActivity().getApplication()).getTracker(
//                TrackerName.APP_TRACKER);
//
//// Set screen name.
//        t.setScreenName("screenName");
//
//// Send a screen view.
//        t.send(new HitBuilders.ScreenViewBuilder().build());
//    }
}
