package com.example.hellobeacon2019;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ScannedBeaconsAdapter extends ArrayAdapter<Beacon> {

    private Context aContext;
    int aResource;

    public ScannedBeaconsAdapter(Context context, int resource, ArrayList<Beacon> objects) {
        super(context, resource, objects);
        aContext = context;
        aResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String uuid = getItem(position).getUUID();
        int major = getItem(position).getMajor();
        int minor = getItem(position).getMinor();

        LayoutInflater inflater = LayoutInflater.from(aContext);
        convertView = inflater.inflate(aResource, parent, false);

        TextView uuidText = (TextView) convertView.findViewById(R.id.scanned_uuid);
        TextView majorText = (TextView) convertView.findViewById(R.id.scanned_major);
        TextView minorText = (TextView) convertView.findViewById(R.id.scanned_minor);

        uuidText.setText(uuid);
        majorText.setText(Integer.toString(major));
        minorText.setText(Integer.toString(minor));

        return convertView;
    }
}
