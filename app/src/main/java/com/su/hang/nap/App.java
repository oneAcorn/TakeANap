package com.su.hang.nap;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/3/22.
 */

public class App extends Application {
    private boolean isBackGround;

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.i("bo", "Created"+activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.i("bo", "Started"+activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (isBackGround) {
                    isBackGround = false;
                    Log.i("bo", "APP回到了前台");
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i("bo", "Paused"+activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.i("bo", "Stopped"+activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.i("bo", "SaveInstance"+activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.i("bo", "destroyed"+activity);
            }
        });
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i("bo", "level:"+level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackGround = true;
            Log.i("bo", "APP遁入后台");
        }
    }

}
