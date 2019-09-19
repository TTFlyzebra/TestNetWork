package com.flyzebra.testnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        Log.d("TTTT", "onCreate");
        Network[] networks = cm.getAllNetworks();
        Log.d("TTTT", "networks="+networks+",size="+networks.length);
        boolean result = false;
        for (int i = 0; i < networks.length; i++) {
            NetworkInfo netInfo = cm.getNetworkInfo(networks[i]);
            Log.d("TTTT", "type="+netInfo.getType());
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Log.d("TTTT", "Foundpotentialnetwork:settingdefault...");
                result = ConnectivityManager.setProcessDefaultNetwork(networks[i]);
                Log.d("TTTT", "Result:" + result);
            }
            if (result) {
                Log.d("TTTT", "Success!Restrictedto:" + netInfo.toString());
                break;
            }
        }
    }
}
