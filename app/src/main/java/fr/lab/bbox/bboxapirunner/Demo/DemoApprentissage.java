package fr.lab.bbox.bboxapirunner.Demo;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
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
    private TextView channelView;

    private int currentChannel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.apprentissage_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int counter = 0;
        ImageView informationbBubble = (ImageView) view.findViewById(R.id.imageView);
        channelView = (TextView) view.findViewById(R.id.positionid);

        channelDeamon();

    }

    private void setViewChannel(int channel) {

        channelView.setText(Integer.toString(channel));
    }

    public void channelDeamon() {

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String ip = sharedPref.getString("bboxip", "");
        Log.i("x-ip", ip);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Bbox.getInstance().getCurrentChannel(ip,
                                getResources().getString(fr.bouyguestelecom.bboxapi.R.string.APP_ID),
                                getResources().getString(fr.bouyguestelecom.bboxapi.R.string.APP_SECRET),
                                new IBboxGetCurrentChannel() {
                                    @Override
                                    public void onResponse(final Channel channel) {
                                        //Choper ici lrs info
                                        currentChannel = channel.getPositionId();
                                        Log.i("CURRENT_CHANNEL", channel.toString());

                               /* handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctxt,
                                                "mediaService : " + channel.getMediaService() + "\n" +
                                                        "mediaState : " + channel.getMediaState() + "\n" +
                                                        "mediaTitle : " + channel.getMediaTitle() + "\n" +
                                                        "positionId : " + channel.getPositionId()
                                                , Toast.LENGTH_LONG).show();
                                    }
                                });*/
                                    }

                                    @Override
                                    public void onFailure(Request request, int errorCode) {
                                        Log.d("CURRENT_CHANNEL", "Get current channel failed");
                                    }
                                });
                        setViewChannel(currentChannel);
                    }
                });
            }
        };
        timer.schedule(task,1000,1000);
    }
}