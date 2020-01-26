package com.example.hellobeacon2019;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class ServiceImpl extends IntentService {
    private static final String TAG = "ServiceImpl";
    private final String CHANNEL_ID = "beacon_notifications";
    public static final String INTENT_KEY = "THE_BEACON";
    public static final String BUNDLE_NAME = "BEACONS";
    public static volatile boolean shouldContinue = true;
    public ArrayList<Beacon> scannedBeacons = new ArrayList<Beacon>();

    //TODO: include needed fields
    private long seconds;
    FileInputStream is;
    BufferedReader reader;

    public ServiceImpl() {
        super("ServiceImpl");
    }

    protected void onHandleIntent(Intent intent) {

        //TODO: uncomment this, when implemented setupInputReader();

        //TODO: get the seconds from intent
        seconds = intent.getLongExtra("seconds", 0);
        Log.d("Seconds: ", "Intent recieved " + String.valueOf(seconds) + " seconds interval.");

        //how long the service should sleep, in milliseconds
        long millis = seconds * 1000;
        Random rand = new Random();
        int NOTIFICATION_ID = rand.nextInt(50);

        while (true) {
            try {
                if (shouldContinue == false) {
                    stopSelf();
                    return;
                }
                Beacon beacon = scanBeacon();
                Log.d("Beacon: ", beacon.toString());

                if(beacon != null){
                    //TODO: add beacons to the List of scanned beacons
                    scannedBeacons.add(beacon);

                    //TODO: Notification
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence name = "Beacon Alert";
                        String description = "New Beacon Found";
                        int importance = NotificationManager.IMPORTANCE_DEFAULT;

                        NotificationChannel nc = new NotificationChannel(CHANNEL_ID, name, importance);
                        nc.setDescription(description);

                        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        nm.createNotificationChannel(nc);
                    }

                    Log.d("Scanned Beacons: ", ""+scannedBeacons.size());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(BUNDLE_NAME, scannedBeacons);

                    //TODO: intent to AddBeaconsActivity
                    Intent addBeaconsIntent = new Intent(this, AddBeaconsActivity.class);
                    addBeaconsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    addBeaconsIntent.putExtra(INTENT_KEY, bundle);

                    PendingIntent addBeaconsPendingIntent = PendingIntent.getActivity(this,0, addBeaconsIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
                    builder.setSmallIcon(R.drawable.ic_beacon_found);
                    builder.setContentTitle("New Beacon Found");
                    builder.setContentText(beacon.getUUID() +","+beacon.getMajor()+","+beacon.getMinor());
                    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    builder.setAutoCancel(true);
                    builder.setContentIntent(addBeaconsPendingIntent);

                    NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
                    nmc.notify(NOTIFICATION_ID,builder.build());
//                    ++NOTIFICATION_ID;

                    //put the service to sleep
                    Thread.sleep(millis);
                } else {
                    shouldContinue = false;
                    stopSelf();
                    break;
                }

            } catch (Exception iEx) {
                Log.d("Message", iEx.getMessage());
            }
        }
    }

    private void setupInputReader() {

        //TODO: read the file "Beacon.txt"
        //read the header in advance to exclude it from the output


    }

    private Beacon scanBeacon() throws FileNotFoundException {
        //TODO: Read a line and split one row into the beacon components uuid, major and minor
        //create a new beacon and return it
        FileInputStream fis = null;

        String text;
        String[] beaconDetails = null;
        Beacon beaconCheck = null;

        try {
            fis = openFileInput("Beacons.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            int i = 0;

            while ((text = br.readLine()) != null) {
                if (i != 0) {
                    beaconDetails = text.split(",");
                    beaconCheck = new Beacon(beaconDetails[0],Integer.parseInt(beaconDetails[1]),Integer.parseInt(beaconDetails[2]));
                    if(scannedBeacons.contains(beaconCheck)) {
                        beaconCheck = null;
                    } else {
                        break;
                    }
                }
                ++i;
            }

            Log.d("Beacons: ", i+") "+beaconDetails[0]);
        }catch (Exception ex) {
            Log.d("Exception scanBeacon: ", ex.toString());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return beaconCheck;
    }

    public void onDestroy() {
        //TODO: implement this
        super.onDestroy();
    }
}

