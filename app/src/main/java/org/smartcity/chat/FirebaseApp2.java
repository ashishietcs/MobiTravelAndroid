package org.smartcity.chat;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by divyanshu on 25-08-2016.
 */
public class FirebaseApp2 extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}