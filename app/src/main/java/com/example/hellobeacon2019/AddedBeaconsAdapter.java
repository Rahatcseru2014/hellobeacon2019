package com.example.hellobeacon2019;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AddedBeaconsAdapter extends ArrayAdapter<Beacon> {

    private Context aContext;
    int aResource;

    public AddedBeaconsAdapter(Context context, int resource, ArrayList<Beacon> objects) {
        super(context, resource, objects);
        aContext = context;
        aResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int nextPostion = position + 1;
        LayoutInflater inflater = LayoutInflater.from(aContext);
        if(position < getCount() -1) {
            convertView = inflater.inflate(aResource, parent, false);

            String uuidLeft = getItem(position).getUUID();
            int majorLeft = getItem(position).getMajor();
            int minorLeft = getItem(position).getMinor();

            TextView uuidTextLeft = (TextView) convertView.findViewById(R.id.scanned_uuid_left);
            TextView majorTextLeft = (TextView) convertView.findViewById(R.id.scanned_major_left);
            TextView minorTextLeft = (TextView) convertView.findViewById(R.id.scanned_minor_left);
            TextView uuidTextRight = (TextView) convertView.findViewById(R.id.scanned_uuid_right);
            TextView majorTextRight = (TextView) convertView.findViewById(R.id.scanned_major_right);
            TextView minorTextRight = (TextView) convertView.findViewById(R.id.scanned_minor_right);


            String uuidRight = getItem(nextPostion).getUUID();
            int majorRight = getItem(nextPostion).getMajor();
            int minorRight = getItem(nextPostion).getMinor();

            uuidTextLeft.setText(uuidLeft);
            majorTextLeft.setText(Integer.toString(majorLeft));
            minorTextLeft.setText(Integer.toString(minorLeft));
            uuidTextRight.setText(uuidRight);
            majorTextRight.setText(Integer.toString(majorRight));
            minorTextRight.setText(Integer.toString(minorRight));
        } else {
            convertView = inflater.inflate(R.layout.empty_row, null);
        }

        return convertView;
    }
}

