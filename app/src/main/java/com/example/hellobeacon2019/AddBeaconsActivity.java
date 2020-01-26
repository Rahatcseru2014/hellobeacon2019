package com.example.hellobeacon2019;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddBeaconsActivity extends AppCompatActivity {

    String[] scannedBeaconsTableHeaders = {"UUID","MAJOR","MINOR"};
    ScannedBeaconsAdapter adapter;
    ListView scannedBeaconsListView;
    Button addBeacons;
    ArrayList<Beacon> scannedBeaconsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beacons);

        Bundle extras = getIntent().getExtras();
        scannedBeaconsListView = (ListView) findViewById(R.id.scanned_beacons);
        addBeacons = (Button) findViewById(R.id.add_beacon);

        if (extras != null) {
            Bundle scannedBeaconsBundle = extras.getBundle(ServiceImpl.INTENT_KEY);

            scannedBeaconsList = (ArrayList)scannedBeaconsBundle.getSerializable(ServiceImpl.BUNDLE_NAME);
            Log.d("Scanned Beacons Count: ", ""+scannedBeaconsList.size());

            if(scannedBeaconsList.size() > 0) {
                adapter = new ScannedBeaconsAdapter(this, R.layout.scanned_beacons_adapter_view, scannedBeaconsList);
                scannedBeaconsListView.setAdapter((ListAdapter) adapter);
            } else {
                if(adapter != null) {
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                }
                Toast.makeText(this, "No beacons found.", Toast.LENGTH_LONG).show();
            }

            setListHeight(scannedBeaconsList.size());
        }

        addBeacons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ServiceImpl.BUNDLE_NAME, scannedBeaconsList);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra(ServiceImpl.INTENT_KEY, bundle);
                startActivity(intent);
            }
        });
    }

    public void setListHeight(int beaconsCount) {
        if(beaconsCount < 6) {
            ViewGroup.LayoutParams params = scannedBeaconsListView.getLayoutParams();
            params.height = beaconsCount * 150;
            scannedBeaconsListView.setLayoutParams(params);
            scannedBeaconsListView.requestLayout();
        } else {
            ViewGroup.LayoutParams params = scannedBeaconsListView.getLayoutParams();
            params.height = 800;
            scannedBeaconsListView.setLayoutParams(params);
            scannedBeaconsListView.requestLayout();
        }
    }
}

