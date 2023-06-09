package com.brands.deathtimer;

import android.app.Application;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class DeathTimerApp extends Application {

    private static final String API_KEY = "6b09df39-b25a-4943-b6b4-233c0439e174";

    @Override
    public void onCreate() {
        super.onCreate();
        // Creating an extended library configuration.
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(API_KEY).build();
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this);
    }
}