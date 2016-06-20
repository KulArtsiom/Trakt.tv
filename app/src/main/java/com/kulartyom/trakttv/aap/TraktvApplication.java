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
        RealmMigration migration = new RealmMigration() {
            @Override
            public long execute(Realm realm, long version) {
                if (version == 0) {
                    version++;
                }
                return version;
            }
        };

        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .name(Constans.REALM_NAME)
                .schemaVersion(0)
                .setModules(new RealmModule())
                .migration(migration)
                .inMemory()
                .build();

        Realm.setDefaultConfiguration(config);
        Realm.migrateRealm(config);
    }


}
