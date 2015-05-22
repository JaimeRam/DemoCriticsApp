package com.example.zorbel.apptfg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.Comment;

import java.util.List;

/**
 * Created by javier on 8/04/15.
 */
public class CommentListAdapter extends ArrayAdapter {

    public CommentListAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_item, null);
        }


        TextView user = (TextView) convertView.findViewById(R.id.commentUser);
        TextView date = (TextView) convertView.findViewById(R.id.commentDate);
        TextView text = (TextView) convertView.findViewById(R.id.commentText);

        Comment item = (Comment) getItem(position);

        user.setText(item.getUser());
        date.setText(item.getDate());
        text.setText(item.getText());

        return convertView;
    }
}