package com.example.performancepractice.anr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.performancepractice.R;
import com.example.server.IMyAidlInterface;

public class ANRActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "JJJ_ANRActivity";

    private TextView mTv;
    private Button mBtnCreateService;
    private Button mBtnBindService;
    private Button mBtnAIDL;
    private Intent mServiceIntent;
    private Intent mAIDLIntent;
    private ServiceConnection mConnService = null;
    private ServiceConnection mConnAIDL = null;
    private IMyAidlInterface mAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);
        mTv = findViewById(R.id.tv_anr);
        mBtnCreateService = findViewById(R.id.btn_anr_create_service);
        mBtnBindService = findViewById(R.id.btn_anr_bind_service);
        mBtnAIDL = findViewById(R.id.btn_anr_aidl);
        mBtnCreateService.setOnClickListener(this);
        mBtnBindService.setOnClickListener(this);
        mBtnAIDL.setOnClickListener(this);
        mTv.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        if (mServiceIntent != null) {
            stopService(mServiceIntent);
        }
        if (mConnAIDL != null) {
            unbindService(mConnAIDL);
        }

        if (mConnService != null) {
            unbindService(mConnService);
        }
        super.onDestroy();



    }

    @Override
    public void onClick(View v) {
        if (mServiceIntent == null) {
            mServiceIntent = new Intent(ANRActivity.this, ANRService.class);
        }
        switch (v.getId()){
            case R.id.btn_anr_create_service:
                startService(mServiceIntent);
                break;
            case R.id.btn_anr_bind_service:
                if (mConnService == null) {
                    mConnService = new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            Log.d(TAG, "onServiceConnected");
                            ANRService.ANRBinder binder = (ANRService.ANRBinder) service;
                            binder.showList();
                            binder.showToast();
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) {
                            Log.d(TAG, "onServiceDisconnected");

                        }
                    };
                }
                bindService(mServiceIntent, mConnService, BIND_AUTO_CREATE);
                break;
            case R.id.btn_anr_aidl:
                if(mAIDLIntent == null){
                    mAIDLIntent = new Intent();
                    mAIDLIntent.setComponent(new ComponentName("com.example.server","com.example.server.service.MyService"));
                }
                if(mConnAIDL == null){
                    mConnAIDL = new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            mAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                            try {
                                int result = mAidlInterface.add(1,2);
                                Log.d(TAG," mAidlInterface.add(1,2) = " + result);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) {
                            mAidlInterface = null;
                        }
                    };
                }
                bindService(mAIDLIntent, mConnAIDL, BIND_AUTO_CREATE);
                break;
            case R.id.tv_anr:
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}