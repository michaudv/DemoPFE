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
import android.widget.Toast;

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

    private Context ctxt;
    private Handler handler;
    private int currentChannel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.apprentissage_layout, container, false);
        ctxt = getActivity().getApplicationContext();
        handler = new Handler();

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int counter = 0;
        ImageView informationbBubble = (ImageView) getView().findViewById(R.id.imageView);

        returnLiveChannel();

    }

    public void returnLiveChannel() {

        ctxt = getActivity().getApplicationContext();
        handler = new Handler();

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String ip = sharedPref.getString("bboxip", "");
        Log.i("x-ip", ip);

        Bbox.getInstance().getCurrentChannel(ip,
                getResources().getString(fr.bouyguestelecom.bboxapi.R.string.APP_ID),
                getResources().getString(fr.bouyguestelecom.bboxapi.R.string.APP_SECRET),
                new IBboxGetCurrentChannel() {
                    @Override
                    public void onResponse(final Channel channel) {
                        //Choper ici lrs info
                        currentChannel = channel.getPositionId();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ctxt,
                                        "mediaService : " + channel.getMediaService() + "\n" +
                                                "mediaState : " + channel.getMediaState() + "\n" +
                                                "mediaTitle : " + channel.getMediaTitle() + "\n" +
                                                "positionId : " + channel.getPositionId()
                                        , Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Request request, int errorCode) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ctxt, "Get current channel failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

    }

}
