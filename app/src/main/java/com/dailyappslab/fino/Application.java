package com.dailyappslab.fino;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.*;
import com.google.android.*;

import java.util.HashMap;

/**
 * Created by GreenQ on 05.04.2015.
 */
public class Application extends android.app.Application {
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

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
//        t.setScreenName(screenName);
//
//// Send a screen view.
//        t.send(new HitBuilders.ScreenViewBuilder().build());
//    }
}
