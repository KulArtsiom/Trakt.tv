package com.kulartyom.trakttv.aap;

import android.app.Application;

import com.kulartyom.trakttv.constans.Constans;
import com.kulartyom.trakttv.database.module.RealmModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;


public class TraktvApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .name(Constans.REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .setModules(new RealmModule())
                .build();

        Realm.setDefaultConfiguration(config);

    }
}
