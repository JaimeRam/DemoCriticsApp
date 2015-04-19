package com.example.zorbel.apptfg;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zorbel.data_structures.Section;

import java.util.List;

/**
 * Created by javier on 3/04/15.
 */
public class ListIndexAdapter extends ArrayAdapter {

    public ListIndexAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }


        TextView name = (TextView) convertView.findViewById(R.id.lblListItem);

        Section item = (Section) getItem(position);
        name.setText(item.getmTitle());
        name.setTextColor(Color.BLACK);

        return convertView;
    }
}
