package fr.lab.bbox.bboxapidemo.Demo;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import fr.bouyguestelecom.bboxapi.bboxapi.Bbox;
import fr.bouyguestelecom.bboxapi.bboxapi.callback.IBboxDisplayToast;
import fr.lab.bbox.bboxapirunner.IService;
import fr.lab.bbox.bboxapirunner.R;
import okhttp3.Request;


/**
 * Created by AlexandreBigot on 24/01/2017.
 */

public class DemoDetection extends Fragment {

    private final static String TAG = "DEMO_DETECTION";
    private Boolean showToast1 = false;
    private Boolean showToast2 = false;
    private Boolean showToast3 = false;

    public Context context;
    private View view;
    private Timer timer;

    private int refreshPeriod = 5 * 1000;

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

        view = inflater.inflate(R.layout.detection_layout, container, false);
        context = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent i = new Intent();
        i.setComponent(new ComponentName("fr.lab.bbox.bboxapirunner", "fr.lab.bbox.bboxapirunner.MyService"));

        getActivity().bindService(i, conn, getActivity().getBaseContext().BIND_AUTO_CREATE);
        Log.i(TAG, "Service bond");

        // Draw the Bbox and waves
        drawBbox();

        // Call 'displayDevices()' every 'refreshPeriod' to check the presence of the different registered devices
        final Handler handler = new Handler();
        timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            try {
                                if (service != null) {
                                    // Device 1 (One Plus Alex)
                                    if(service.getDevice(DemoConstants.macAddress1)) {
                                        Log.i(TAG, DemoConstants.nameDevice1 + " visible");
                                        DemoConstants.deviceVisible1 = true;
                                        showToast(1);
                                        showToast1 = true;
                                    } else {
                                        DemoConstants.deviceVisible1 = false;
                                        showToast1 = false;
                                    }

                                    // Device 2 (Galaxy J5 Victor)
                                    if(service.getDevice(DemoConstants.macAddress2)) {
                                        Log.i(TAG, DemoConstants.nameDevice2 + " visible");
                                        DemoConstants.deviceVisible2 = true;
                                        showToast(2);
                                        showToast2 = true;
                                    } else {
                                        DemoConstants.deviceVisible2 = false;
                                        showToast2 = false;
                                    }

                                    // Device 3 (Beacon SIGNUL)
                                    if(service.getDevice(DemoConstants.macAddress3)) {
                                        Log.i(TAG, DemoConstants.nameDevice3 + " visible");
                                        DemoConstants.deviceVisible3 = true;
                                        showToast(3);
                                        showToast3 = true;
                                    } else {
                                        DemoConstants.deviceVisible3 = false;
                                        showToast3 = false;
                                    }
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                            // Display existing devices
                            displayDevices();
                            Log.i(TAG, "Timer OK");
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.i(TAG, "Timer Error : " + e);
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 1000, refreshPeriod);
    }

    /**
     * Display or not the different registered devices
     */
    public void displayDevices() {

        // Device 1
        ImageView phone1 = (ImageView) view.findViewById(R.id.smartphone1);
        phone1.setImageResource(R.drawable.oneplus3);

        TextView name1 = (TextView) view.findViewById(R.id.namePhone1);
        name1.setText(DemoConstants.nameDevice1);

        if (DemoConstants.deviceVisible1) {
            phone1.setVisibility(View.VISIBLE);
            name1.setVisibility(View.VISIBLE);
            phone1.animate().alpha(1.0f).setDuration(800);
            name1.animate().alpha(1.0f).setDuration(800);
        } else {
            phone1.animate().alpha(0.0f).setDuration(800);
            name1.animate().alpha(0.0f).setDuration(800);
        }

        // Device 2
        ImageView phone2 = (ImageView) view.findViewById(R.id.smartphone2);
        phone2.setImageResource(R.drawable.galaxyj5);

        TextView name2 = (TextView) view.findViewById(R.id.namePhone2);
        name2.setText(DemoConstants.nameDevice2);

        if (DemoConstants.deviceVisible2) {
            phone2.setVisibility(View.VISIBLE);
            name2.setVisibility(View.VISIBLE);
            phone2.animate().alpha(1.0f).setDuration(800);
            name2.animate().alpha(1.0f).setDuration(800);
        } else {
            phone2.animate().alpha(0.0f).setDuration(800);
            name2.animate().alpha(0.0f).setDuration(800);
        }

        // Device 3
        ImageView phone3 = (ImageView) view.findViewById(R.id.beacon);
        phone3.setImageResource(R.drawable.beacon);

        TextView name3 = (TextView) view.findViewById(R.id.nameBeacon);
        name3.setText(DemoConstants.nameDevice3);

        if (DemoConstants.deviceVisible3) {
            phone3.setVisibility(View.VISIBLE);
            name3.setVisibility(View.VISIBLE);
            phone3.animate().alpha(1.0f).setDuration(800);
            name3.animate().alpha(1.0f).setDuration(800);
        } else {
            phone3.animate().alpha(0.0f).setDuration(800);
            name3.animate().alpha(0.0f).setDuration(800);
        }
    }

    /**
     * Draw the Bbox Miami and its waves on Screen
     */
    public void drawBbox() {

        // Draw Bbox Miami
        ImageView bbox = (ImageView) getView().findViewById(R.id.bbox_miami);
        bbox.setImageResource(R.drawable.bbox_miami);

        // Bbox Name
        TextView foyer = (TextView) getView().findViewById(R.id.foyerID);
        foyer.setText("Foyer Martin");

        // Bbox Localization
        TextView loc = (TextView) getView().findViewById(R.id.locBbox);
        loc.setText("Paris");

        // Draw Waves Circles
        // Circle 1
        final ImageView circle1 = (ImageView) getView().findViewById(R.id.circle1);

        int alpha1 = 255;
        Paint paint1 = new Paint();
        paint1.setColor(Color.argb(alpha1, 135, 206, 250));
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(4.5f);

        Bitmap bmp1 = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

        Canvas canvas1 = new Canvas(bmp1);
        canvas1.drawCircle(bmp1.getWidth() / 2, bmp1.getHeight() / 2, 230, paint1);

        circle1.setImageBitmap(bmp1);

        // Circle 2
        ImageView circle2 = (ImageView) getView().findViewById(R.id.circle2);

        int alpha2 = 180;
        Paint paint2 = new Paint();
        paint2.setColor(Color.argb(alpha2, 135, 206, 250));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(4.5f);

        Bitmap bmp2 = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

        Canvas canvas2 = new Canvas(bmp2);
        canvas2.drawCircle(bmp2.getWidth() / 2, bmp2.getHeight() / 2, 230, paint2);

        circle2.setImageBitmap(bmp2);

        // Circle 3
        ImageView circle3 = (ImageView) getView().findViewById(R.id.circle3);

        int alpha3 = 100;
        Paint paint3 = new Paint();
        paint3.setColor(Color.argb(alpha3, 135, 206, 250));
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(4.5f);

        Bitmap bmp3 = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

        Canvas canvas3 = new Canvas(bmp3);
        canvas3.drawCircle(bmp3.getWidth() / 2, bmp3.getHeight() / 2, 230, paint3);

        circle3.setImageBitmap(bmp3);

        // Circle 4
        ImageView circle4 = (ImageView) getView().findViewById(R.id.circle4);

        int alpha4 = 40;
        Paint paint4 = new Paint();
        paint4.setColor(Color.argb(alpha4, 135, 206, 250));
        paint4.setStyle(Paint.Style.STROKE);
        paint4.setStrokeWidth(4.5f);

        Bitmap bmp4 = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

        Canvas canvas4 = new Canvas(bmp4);
        canvas4.drawCircle(bmp4.getWidth() / 2, bmp4.getHeight() / 2, 250, paint4);

        circle4.setImageBitmap(bmp4);
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showToast(int nb){

        String name;

        switch (nb){
            case 1 :
                name = "Alex";
                break;
            case 2 :
                name = "Victor";
                break;
            case 3 :
                name = "Benjamin";
                break;
            default :
                name = "";
                break;

        }

        if(nb == 1 && showToast1 == false || nb == 2 && showToast2 == false || nb == 3 && showToast3 == false){

            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

            final String ip = sharedPref.getString("bboxip", "");
            Log.i("x-ip", ip);

            Log.i("Toast","Send toast for " + name);

            Bbox.getInstance().displayToast(ip,
                    getResources().getString(fr.bouyguestelecom.bboxapi.R.string.APP_ID),
                    getResources().getString(fr.bouyguestelecom.bboxapi.R.string.APP_SECRET),
                    "Bonjour " + name, "#f0f0f0", "4000", "550", "950",
                    new IBboxDisplayToast() {
                        @Override
                        public void onResponse() {
                            Log.i("Toast","response");
                        }

                        @Override
                        public void onFailure(Request request, int errorCode) {
                            Log.i("Toast","Failure");
                        }
                    });
        }
    }
}