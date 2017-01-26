package fr.lab.bbox.bboxapidemo.Demo;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import fr.lab.bbox.bboxapirunner.IService;
import fr.lab.bbox.bboxapirunner.R;

/**
 * Created by AlexandreBigot on 26/01/2017.
 */

public class ListOfDevices extends Fragment {

    private final static String TAG = "LIST_OF_DEVICES";

    private View view;
    private Timer timer;
    private int refreshPeriod = 60 * 1000;
    private ArrayList<String> listDevices;

    private IService service;
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.i(TAG, "onServiceConnected");
            service = IService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
            service = null;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_devices_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent i = new Intent();
        i.setComponent(new ComponentName("fr.lab.bbox.bboxapirunner", "fr.lab.bbox.bboxapirunner.MyService"));

        getActivity().bindService(i, conn, getActivity().getBaseContext().BIND_AUTO_CREATE);
        Log.i(TAG, "Service bond");

        // Call the service to know and display all the detected bluetooth devices
        final Handler handler = new Handler();
        timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            if (service != null) {
                                listDevices = (ArrayList<String>) service.getDevicesList();
                                for (String s:
                                     listDevices) {
                                    Log.i(TAG, "NAME " + s);
                                }
                            }
                            Log.i(TAG, "Timer (ListOfDevices) OK");
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.i(TAG, "Timer Error : " + e);
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 1000, refreshPeriod);
    }

    @Override
    public void onPause() {
        super.onPause();
        //timer.cancel();
        Log.i(TAG, "onPause : Timer ListOfDevices canceled");
    }

    @Override
    public void onStop() {
        super.onStop();
        //timer.cancel();
        Log.i(TAG, "onStop : Timer ListOfDevices canceled");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //timer.cancel();
        Log.i(TAG, "onDestroy : Timer ListOfDevices canceled");
    }

}
