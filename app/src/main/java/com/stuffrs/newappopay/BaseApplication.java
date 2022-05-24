package com.stuffrs.newappopay;

import android.app.Application;
import android.content.Context;


import androidx.multidex.MultiDex;

import com.onesignal.OneSignal;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.google.GoogleEmojiProvider;


/**
 * Created by mayank on 11/2/17.
 */

public class BaseApplication extends Application {

    private static final String ONESIGNAL_APP_ID = "57708791-f9a1-4d04-bbca-cafe1b6588ef";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //OneSignal.provideUserConsent(true);
        /*OneSignal.initWithContext(this)
                .setNotificationOpenedHandler(new MyOnesignalNotificationOpenedHandler(this))
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();*/
        OneSignal.initWithContext(this);
        OneSignal.setNotificationOpenedHandler(new MyOnesignalNotificationOpenedHandler(this));
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        EmojiManager.install(new GoogleEmojiProvider());



    }

}
