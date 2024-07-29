package me.patrick.aikodigital.pontocerto;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;


import me.patrick.aikodigital.pontocerto.network.NetworkAdapter;
import me.patrick.aikodigital.pontocerto.receiver.NetworkChangeReceiver;
import me.patrick.aikodigital.pontocerto.util.DialogUtil;
import me.patrick.aikodigital.pontocerto.view.GMapView;



public class MainActivity extends AppCompatActivity{

    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);




        if (!NetworkAdapter.isNetworkAvailable(this)) {
            DialogUtil.showOfflineDialog(this);
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, new GMapView());
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }


}