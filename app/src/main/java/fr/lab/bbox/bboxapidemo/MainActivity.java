package fr.lab.bbox.bboxapidemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import fr.lab.bbox.bboxapidemo.Demo.DemoApprentissage;
import fr.lab.bbox.bboxapidemo.Demo.DemoDetection;
import fr.lab.bbox.bboxapidemo.Demo.DemoFoyer;
import fr.lab.bbox.bboxapidemo.Demo.DemoStatistiques;
import fr.lab.bbox.bboxapidemo.Demo.ListOfDevices;
import fr.lab.bbox.bboxapirunner.IService;
import fr.lab.bbox.bboxapirunner.R;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MAIN_ACTIVITY";

    public Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BboxIpAddrDefault();

        fragment = new DemoDetection();
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    public void BboxIpAddrDefault()
    {
        String bboxip = "192.168.1.59";
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("bboxip", bboxip);
        editor.commit();
    }

    /**
     * Get the remote events and the keys pressed
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            Log.e(TAG, "Key down, code " + event.getKeyCode());
            switch (event.getKeyCode()) {
                case 19 :
                    fragment = new ListOfDevices();
                    break;
                case 20 :
                    fragment = new DemoStatistiques();
                    break;
                case 21 :
                    fragment = new DemoApprentissage();
                    break;
                case 22 :
                    fragment = new DemoFoyer();
                    break;
                case 23 :
                    fragment = new DemoDetection();
                    break;
                default:
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy()");
    }
}
