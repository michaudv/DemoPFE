package fr.lab.bbox.bboxapidemo.Demo;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.lab.bbox.bboxapirunner.IService;
import fr.lab.bbox.bboxapirunner.R;

/**
 * Created by AlexandreBigot on 26/01/2017.
 */

public class ListOfDevices extends Fragment {

    private final static String TAG = "LIST_OF_DEVICES";

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
        return inflater.inflate(R.layout.list_devices_layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent i = new Intent();
        i.setComponent(new ComponentName("fr.lab.bbox.bboxapirunner", "fr.lab.bbox.bboxapirunner.MyService"));

        getActivity().bindService(i, conn, getActivity().getBaseContext().BIND_AUTO_CREATE);
        Log.i(TAG, "Service bond");
    }
}
