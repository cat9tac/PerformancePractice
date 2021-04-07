package com.example.performancepractice.anr;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.TaskStackBuilder;

import com.example.performancepractice.MainActivity;
import com.example.performancepractice.R;

public class ANRService extends Service {

    private final static String TAG = "JJJ_ANRService";
    private IBinder mBinder = new ANRBinder();

    public ANRService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return  mBinder ;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG,"onRebind");

        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind");

        return super.onUnbind(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        super.onCreate();
        showNotification();

    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        stopForeground(1);
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG,"onConfigurationChanged");

        super.onConfigurationChanged(newConfig);
    }





    private String channeId = "ANRService";

    private void showNotification() {
        Log.d(TAG,"showNotification");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification =
                new Notification.Builder(this)
                        .setContentTitle(getText(R.string.app_name))
                        .setContentText(getText(R.string.anr_btn_start_service))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .setTicker(getText(R.string.anr_btn_bind_service))
                        .build();
        startForeground(1, notification);
    }







    public static class ANRBinder extends Binder{
        public void showToast() {
            Log.e(TAG, "showToast");
        }

        public void showList() {
            Log.e(TAG, "showList");
        }
    }

}