package fr.lab.bbox.bboxapidemo.Demo;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import fr.bouyguestelecom.bboxapi.bboxapi.Bbox;
import fr.bouyguestelecom.bboxapi.bboxapi.callback.IBboxGetCurrentChannel;
import fr.bouyguestelecom.bboxapi.bboxapi.model.Channel;
import fr.lab.bbox.bboxapirunner.R;
import okhttp3.Request;

/**
 * Created by AlexandreBigot on 24/01/2017.
 */

public class DemoApprentissage extends Fragment {

    private final static String TAG = "DEMO_APPRENTISSAGE";

    private View view;
    private int previouschannel = 0;
    private Timer timer;
    private Channel currentChannel;
    private int refreshPeriod = 5 * 1000;
    private int vues[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.apprentissage_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        channelDeamon();
    }

    public void channelDeamon() {

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String ip = sharedPref.getString("bboxip", "");
        Log.i("x-ip", ip);

        final Handler handler = new Handler();
        timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            Bbox.getInstance().getCurrentChannel(ip,
                                    getResources().getString(fr.bouyguestelecom.bboxapi.R.string.APP_ID),
                                    getResources().getString(fr.bouyguestelecom.bboxapi.R.string.APP_SECRET),
                                    new IBboxGetCurrentChannel() {
                                        @Override
                                        public void onResponse(final Channel channel) {
                                            //Choper ici lrs info
                                            currentChannel = channel;
                                            Log.i("CURRENT_CHANNEL", channel.toString());
                                        }

                                        @Override
                                        public void onFailure(Request request, int errorCode) {
                                            Log.d("CURRENT_CHANNEL", "Get current channel failed");
                                        }
                                    });

                            if(currentChannel != null){

                                //changement de chaine
                                if(previouschannel != currentChannel.getPositionId()) {

                                    ImageView bubble=null;

                                    switch (currentChannel.getPositionId()) {
                                        case 1: bubble = (ImageView) getView().findViewById(R.id.bubbleGeneraliste);
                                            displayChannel(1);
                                            break;
                                        case 2: bubble = (ImageView) getView().findViewById(R.id.bubbleGeneraliste);
                                            displayChannel(2);
                                            break;
                                        case 3 : bubble = (ImageView) getView().findViewById(R.id.bubbleGeneraliste);
                                            displayChannel(3);
                                            break;
                                        case 5: bubble = (ImageView) getView().findViewById(R.id.bubbleCulture);
                                            displayChannel(5);
                                            break;
                                        case 6 : bubble = (ImageView) getView().findViewById(R.id.bubbleGeneraliste);
                                            displayChannel(6);
                                            break;
                                        case 7 : bubble = (ImageView) getView().findViewById(R.id.bubbleCulture);
                                            displayChannel(7);
                                            break;
                                        case 8: bubble = (ImageView) getView().findViewById(R.id.bubbleGeneraliste);
                                            displayChannel(8);
                                            break;
                                        case 9: bubble = (ImageView) getView().findViewById(R.id.bubbleGeneraliste);
                                            displayChannel(9);
                                            break;
                                        case 10 : bubble = (ImageView) getView().findViewById(R.id.bubbleGeneraliste);
                                            displayChannel(10);
                                            break;
                                        case 11: bubble = (ImageView) getView().findViewById(R.id.bubbleCulture);
                                            displayChannel(11);
                                            break;
                                        case 12 : bubble = (ImageView) getView().findViewById(R.id.bubbleGeneraliste);
                                            displayChannel(12);
                                            break;
                                        case 13 : bubble = (ImageView) getView().findViewById(R.id.bubbleInformation);
                                            displayChannel(13);
                                            break;
                                        case 14: bubble = (ImageView) getView().findViewById(R.id.bubbleCulture);
                                            displayChannel(14);
                                            break;
                                        case 15: bubble = (ImageView) getView().findViewById(R.id.bubbleInformation);
                                            displayChannel(15);
                                            break;
                                        case 16 : bubble = (ImageView) getView().findViewById(R.id.bubbleInformation);
                                            displayChannel(16);
                                            break;
                                        case 17: bubble = (ImageView) getView().findViewById(R.id.bubbleMusique);
                                            displayChannel(17);
                                            break;
                                        case 18 : bubble = (ImageView) getView().findViewById(R.id.bubbleJeunesse);
                                            displayChannel(18);
                                            break;
                                        case 19 : bubble = (ImageView) getView().findViewById(R.id.bubbleCulture);
                                            displayChannel(19);
                                            break;
                                        case 21: bubble = (ImageView) getView().findViewById(R.id.bubbleSport);
                                            displayChannel(21);
                                            break;
                                        default:
                                            break;
                                    }

                                    if(bubble != null){
                                        updateBubble(bubble);
                                    }

                                    previouschannel = currentChannel.getPositionId();
                                }
                            }
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

    public void updateBubble(ImageView bubble){

        bubble.setVisibility(View.VISIBLE);
        bubble.getLayoutParams().height = bubble.getHeight() + 20;
        bubble.getLayoutParams().width = bubble.getWidth() + 20;
        bubble.requestLayout();
    }

    public void displayChannel(int posID){

        vues[posID]++;
        ImageView logo1 = (ImageView) getView().findViewById(R.id.logo1);
        ImageView logo2 = (ImageView) getView().findViewById(R.id.logo2);
        ImageView logo3 = (ImageView) getView().findViewById(R.id.logo3);

        TextView textchannel1 = (TextView) getView().findViewById(R.id.chaine1);
        TextView textchannel2 = (TextView) getView().findViewById(R.id.chaine2);
        TextView textchannel3 = (TextView) getView().findViewById(R.id.chaine3);

        textchannel3.setText(textchannel2.getText());
        textchannel2.setText(textchannel1.getText());
        textchannel1.setText("visonnée " + vues[posID] + " fois");
        textchannel1.setVisibility(View.VISIBLE);
        textchannel2.setVisibility(View.VISIBLE);
        textchannel3.setVisibility(View.VISIBLE);

        logo3.setImageDrawable(logo2.getDrawable());
        logo2.setImageDrawable(logo1.getDrawable());

        switch (posID){
            case 1 : logo1.setImageResource(R.drawable.tf1);
                break;
            case 2 : logo1.setImageResource(R.drawable.france2);
                break;
            case 3 : logo1.setImageResource(R.drawable.france3);
                break;
            case 5 : logo1.setImageResource(R.drawable.arte);
                break;
            case 6 : logo1.setImageResource(R.drawable.m6);
                break;
            case 7 : logo1.setImageResource(R.drawable.france5);
                break;
            case 8 : logo1.setImageResource(R.drawable.c8);
                break;
            case 9 : logo1.setImageResource(R.drawable.w9);
                break;
            case 10 : logo1.setImageResource(R.drawable.tmc);
                break;
            case 11 : logo1.setImageResource(R.drawable.nt1);
                break;
            case 12 : logo1.setImageResource(R.drawable.nrj12);
                break;
            case 13 : logo1.setImageResource(R.drawable.lcp);
                break;
            case 14 : logo1.setImageResource(R.drawable.france4);
                break;
            case 15 : logo1.setImageResource(R.drawable.bfm);
                break;
            case 16 : logo1.setImageResource(R.drawable.itele);
                break;
            case 17 : logo1.setImageResource(R.drawable.cstar);
                break;
            case 18 : logo1.setImageResource(R.drawable.gulli);
                break;
            case 19 : logo1.setImageResource(R.drawable.franceo);
                break;
            case 21  :logo1.setImageResource(R.drawable.equipe21);
            default:
                break;
        }
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

}