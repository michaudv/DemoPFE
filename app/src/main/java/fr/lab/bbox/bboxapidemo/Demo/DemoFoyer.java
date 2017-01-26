package fr.lab.bbox.bboxapidemo.Demo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.lab.bbox.bboxapirunner.R;

/**
 * Created by AlexandreBigot on 25/01/2017.
 */

public class DemoFoyer extends Fragment {

    private final static String TAG = "DEMO_FOYER";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.foyer_layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

