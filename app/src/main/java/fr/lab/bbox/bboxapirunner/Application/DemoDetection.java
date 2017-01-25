package fr.lab.bbox.bboxapirunner.Application;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.bouyguestelecom.bboxapi.bboxapi.Bbox;
import fr.bouyguestelecom.bboxapi.bboxapi.callback.IBboxGetChannelListOnBox;
import fr.bouyguestelecom.bboxapi.bboxapi.callback.IBboxGetCurrentChannel;
import fr.bouyguestelecom.bboxapi.bboxapi.model.Channel;
import fr.bouyguestelecom.bboxapi.bboxapi.model.ChannelLight;
import fr.lab.bbox.bboxapirunner.R;
import okhttp3.Request;

/**
 * Created by AlexandreBigot on 24/01/2017.
 */

public class DemoDetection extends Fragment {

    private final static String TAG = "DEMO_DETECTION";

    private Context ctxt;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        View view = inflater.inflate(R.layout.frag_media_getcurrentprogram, container, false);

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


        return inflater.inflate(R.layout.detection_layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}