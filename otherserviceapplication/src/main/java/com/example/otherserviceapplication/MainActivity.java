package com.example.otherserviceapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.asdl.IMyAidlInterface;

public class MainActivity extends Activity {
    Intent intent;
    Button startService;
    Button stopService;
    Button bindService;
    Button unbindService;
    Button asyButton;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent();
        intent.setComponent(new ComponentName("com.example.administrator.asdl","com.example.administrator.asdl.MyService"));

        startService = (Button) findViewById(R.id.startService);
        startService.setOnClickListener(onClickListener);
        stopService = (Button) findViewById(R.id.stopService);
        stopService.setOnClickListener(onClickListener);
        bindService = (Button) findViewById(R.id.bindService);
        bindService.setOnClickListener(onClickListener);
        unbindService = (Button) findViewById(R.id.unbindService);
        unbindService.setOnClickListener(onClickListener);
        asyButton = (Button) findViewById(R.id.asyData);
        asyButton.setOnClickListener(onClickListener);

        editText = (EditText) findViewById(R.id.editText);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bindService:
                    bindService(intent,connection, Context.BIND_AUTO_CREATE);
                    break;
                case R.id.unbindService:
                    unbindService(connection);
                    break;
                case R.id.startService:
                    startService(intent);
                    break;
                case R.id.stopService:
                    stopService(intent);

                    break;
                case R.id.asyData:
                    if (iMyAidlInterface != null){
                        try {
                            iMyAidlInterface.setData(editText.getText().toString());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("Bind Service");
            System.out.println(service);

            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            //iMyAidlInterface = (IMyAidlInterface)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }


    };
    private IMyAidlInterface iMyAidlInterface = null;
}
