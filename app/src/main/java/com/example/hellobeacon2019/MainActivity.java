package com.example.hellobeacon2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    //Delimiter used in file
    public static final String COMMA_DELIMITER = ",";

    //new line
    private static final String NEW_LINE_SEPARATOR = "\n";

    //file header
    private static final String FILE_HEADER = "UUID,MAJOR,MINOR";

    //TODO: add needed fields
    AddedBeaconsAdapter adapter;
    EditText inputSecondsText;
    Button startScan;
    Button stopScan;
    ListView addedBeaconsListView;
    long seconds;
    ArrayList<Beacon> addedBeaconsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputSecondsText = (EditText)findViewById(R.id.InputIntervalField);
        startScan   = (Button)findViewById(R.id.startscanbtn);
        stopScan    = (Button)findViewById(R.id.stopscanbtn);
        //addedBeaconsListView = (ListView)findViewById(R.id.added_beacons);

        startScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputSecondsText.getText().toString().trim().length() > 0) {
                    startServiceByButtonClick(v);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter seconds.", Toast.LENGTH_LONG).show();
                }
            }
        });

        stopScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopServiceByButtonClick(v);
            }
        });
        //TODO: get intent

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Bundle addedBeaconsBundle = extras.getBundle(ServiceImpl.INTENT_KEY);
            addedBeaconsList = (ArrayList)addedBeaconsBundle.getSerializable(ServiceImpl.BUNDLE_NAME);
            Log.d("Added Beacons Count: ", "#"+addedBeaconsList.size());

            if(addedBeaconsList.size() > 0) {
                adapter = new AddedBeaconsAdapter(this, R.layout.added_beacons_adapter_view, addedBeaconsList);
                addedBeaconsListView.setAdapter(adapter);
            } else {
                if(adapter != null) {
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                }
                Toast.makeText(this, "No beacons found.", Toast.LENGTH_LONG).show();
            }

            setListHeight(addedBeaconsList.size());
        }
    }

    private void setListHeight(int beaconsCount) {
        if(beaconsCount < 5) {
            ViewGroup.LayoutParams params = addedBeaconsListView.getLayoutParams();
            params.height = beaconsCount * 150;
            addedBeaconsListView.setLayoutParams(params);
            addedBeaconsListView.requestLayout();
        } else {
            ViewGroup.LayoutParams params = addedBeaconsListView.getLayoutParams();
            params.height = 600;
            addedBeaconsListView.setLayoutParams(params);
            addedBeaconsListView.requestLayout();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO: get intent and add beacons to List
        //showBeaconsInLinearLayout();
    }

    private void showBeaconsInLinearLayout() {
        //TODO: implement this
    }

    //Do not change this!
    protected void writeBeaconSimulationFile(){

        //Create new beacon objects
        Beacon beacon1 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,1);
        Beacon beacon2 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,2);
        Beacon beacon3 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,3);
        Beacon beacon4 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,4);
        Beacon beacon5 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,5);
        Beacon beacon6 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,8);
        Beacon beacon7 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,9);
        Beacon beacon8 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,10);
        Beacon beacon9 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,10);
        Beacon beacon10 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,9);
        Beacon beacon11 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,8);
        Beacon beacon12 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,5);
        Beacon beacon13 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,4);
        Beacon beacon14 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,3);
        Beacon beacon15 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,2);
        Beacon beacon16 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,1);

        //Create a new list of beacons objects
        ArrayList<Beacon> beacons = new ArrayList<Beacon>();
        beacons.add(beacon1);
        beacons.add(beacon2);
        beacons.add(beacon3);
        beacons.add(beacon4);
        beacons.add(beacon5);
        beacons.add(beacon6);
        beacons.add(beacon7);
        beacons.add(beacon8);
        beacons.add(beacon9);
        beacons.add(beacon10);
        beacons.add(beacon11);
        beacons.add(beacon12);
        beacons.add(beacon13);
        beacons.add(beacon14);
        beacons.add(beacon15);
        beacons.add(beacon16);
        beacons.add(beacon15);
        beacons.add(beacon14);
        beacons.add(beacon3);
        beacons.add(beacon2);
        beacons.add(beacon1);


        try{
            FileOutputStream testFile = openFileOutput("Beacons.txt", Context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(testFile);
            outputStreamWriter.append(FILE_HEADER.toString());
            outputStreamWriter.append(NEW_LINE_SEPARATOR);

            for (Beacon beacon : beacons) {
                outputStreamWriter.append(String.valueOf(beacon.getUUID()));
                outputStreamWriter.append(COMMA_DELIMITER);
                outputStreamWriter.append(String.valueOf(beacon.getMajor()));
                outputStreamWriter.append(COMMA_DELIMITER);
                outputStreamWriter.append(String.valueOf(beacon.getMinor()));
                outputStreamWriter.append(NEW_LINE_SEPARATOR);
            }

            outputStreamWriter.close();
        }
        catch (IOException ex){
            Log.d("Message", ex.getMessage());
        }
    }

    //TODO: button starts the service
    public void startServiceByButtonClick(View v) {
        //TODO: Get user input
        ServiceImpl.shouldContinue = true;
        seconds = Long.parseLong(inputSecondsText.getText().toString());

        //Do not change this!
        File dir = getFilesDir();
        File file = new File(dir, "Beacons.txt");
        boolean deleted = file.delete();

        //this method writes the file containing simulated beacon data
        writeBeaconSimulationFile();

        //TODO: Service is started via intent
        Intent intent = new Intent(MainActivity.this, ServiceImpl.class);
        intent.putExtra("seconds",seconds);
        startService(intent);

        Toast.makeText(this, "Beacon scan has started with "+seconds+" seconds interval.", Toast.LENGTH_LONG).show();

    }

    //TODO: stop service
    public void stopServiceByButtonClick(View v) {
        //implement this
        ServiceImpl.shouldContinue = false;
        Toast.makeText(this, "Scan for beacons has been stopped", Toast.LENGTH_LONG).show();
    }
}
