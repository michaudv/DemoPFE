package fr.lab.bbox.bboxapidemo.Demo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import fr.lab.bbox.bboxapirunner.R;

/**
 * Created by AlexandreBigot on 25/01/2017.
 */

public class DemoStatistiques extends Fragment {

    private final static String TAG = "DEMO_STATISTIQUES";

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statistiques_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView stats = (ImageView) view.findViewById(R.id.statsView);
        stats.setImageResource(R.drawable.statistiques);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
