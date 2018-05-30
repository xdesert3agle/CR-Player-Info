package es.xdesert3agle.crplayerinfo.Util;

import android.app.Application;

public class Init extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefManager.init(getApplicationContext());
    }
}