package com.example.zorbel.apptfg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zorbel.data_structures.Comment;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;
import com.example.zorbel.data_structures.Section;

import java.util.ArrayList;

/**
 * Created by javier on 15/04/15.
 */
public class TopItemAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Section> list;

    public TopItemAdapter(Context mContext, ArrayList<Section> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.top_ranking_item, null);
        }


        ImageView partyLogo = (ImageView) convertView.findViewById(R.id.partyLogoTop);

        TextView titleSection = (TextView) convertView.findViewById(R.id.titleSectionTop);
        TextView nameParty = (TextView) convertView.findViewById(R.id.namePartyTop);

        TextView numLikes = (TextView) convertView.findViewById(R.id.numLikesTop);
        TextView numNotUnderstood = (TextView) convertView.findViewById(R.id.numNotUnderstoodTop);
        TextView numDislikes = (TextView) convertView.findViewById(R.id.numDislikesTop);
        TextView numComments = (TextView) convertView.findViewById(R.id.numCommentsTop);
        TextView numViews = (TextView) convertView.findViewById(R.id.numViewsTop);

        Section item = (Section) getItem(position);


        PoliticalParty pol = PoliticalGroups.getInstance().getPoliticalParty(item.getmPoliticalParty());

        partyLogo.setImageBitmap(pol.getmLogo());

        titleSection.setText(item.getmTitle());

        nameParty.setText(pol.getmName());

        numLikes.setText(Integer.toString(item.getNumLikes()));
        numNotUnderstood.setText(Integer.toString(item.getNumNotUnderstoods()));
        numDislikes.setText(Integer.toString(item.getNumDislikes()));
        numComments.setText(Integer.toString(item.getNumComments()));
        numViews.setText(Integer.toString(item.getNumViews()));

        return convertView;



    }
}
