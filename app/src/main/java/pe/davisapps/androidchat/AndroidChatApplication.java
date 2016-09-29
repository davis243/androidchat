package pe.davisapps.androidchat;

import android.app.Application;

import com.firebase.client.Firebase;


/**
 * Created by ykro.
 */
public class AndroidChatApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();

    }

    private void setupFirebase(){
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
