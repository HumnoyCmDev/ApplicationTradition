package project.sample.com.applicationtradition;

import android.app.Application;

import project.sample.com.applicationtradition.manager.Contextor;

/**
 * Created by humnoy on 12/2/59.
 */
public class MianApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
