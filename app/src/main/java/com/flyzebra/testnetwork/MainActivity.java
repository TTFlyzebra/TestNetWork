package com.flyzebra.testnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("TTTT", "onCreate");
                Network[] networks = cm.getAllNetworks();
                Log.d("TTTT", "networks="+networks+",size="+networks.length);
                boolean result = false;
                for (int i = 0; i < networks.length; i++) {
                    NetworkInfo netInfo = cm.getNetworkInfo(networks[i]);
                    Log.d("TTTT", "type="+netInfo.getType());
                    if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            result =cm.bindProcessToNetwork(networks[i]);
                        } else {
                            // 23后这个方法舍弃了
                            result =ConnectivityManager.setProcessDefaultNetwork(networks[i]);
                        }
                        Log.d("TTTT", "Result:" + result);
                    }
                    if (result) {
                        Log.d("TTTT", "Success!Restrictedto:" + netInfo.toString());
                        break;
                    }
                }
            }
        }).start();

    }
}
