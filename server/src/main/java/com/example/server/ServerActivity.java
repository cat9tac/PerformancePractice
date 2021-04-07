package com.example.server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.server.service.MyService;

public class ServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
      //  startService(new Intent(this, MyService.class));
    }

    @Override
    protected void onDestroy() {
       // stopService(new Intent(this, MyService.class));
        super.onDestroy();
    }
}