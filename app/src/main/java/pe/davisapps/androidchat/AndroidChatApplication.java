package pe.davisapps.androidchat;

import android.app.Application;

import com.firebase.client.Firebase;

import pe.davisapps.androidchat.lib.GlideImageLoader;
import pe.davisapps.androidchat.lib.ImageLoader;


/**
 * Created by ykro.
 */
public class AndroidChatApplication extends Application {
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();

    }

    private void setupFirebase(){
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
    private void setupImageLoader() {
        imageLoader = new GlideImageLoader(this);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
