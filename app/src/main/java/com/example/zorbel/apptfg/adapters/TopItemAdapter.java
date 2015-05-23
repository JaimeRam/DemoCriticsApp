package com.example.zorbel.apptfg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.views.TopHeaderItem;
import com.example.zorbel.apptfg.views.TopItem;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;
import com.example.zorbel.data_structures.Proposal;
import com.example.zorbel.data_structures.Section;

import java.util.List;

public class TopItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<TopItem> list;

    public TopItemAdapter(Context mContext, List<TopItem> list) {
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

        final TopItem it = list.get(position);

        if (it != null) {

            if (it.isSection()) {

                LayoutInflater inflater = (LayoutInflater) parent.getContext().
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.top_ranking_item, null);

                ImageView partyLogo = (ImageView) convertView.findViewById(R.id.logoTop);

                TextView titleSection = (TextView) convertView.findViewById(R.id.titleTop);
                TextView nameParty = (TextView) convertView.findViewById(R.id.subTitleTop);

                TextView numLikes = (TextView) convertView.findViewById(R.id.numLikesTop);
                TextView numNotUnderstood = (TextView) convertView.findViewById(R.id.numNotUnderstoodTop);
                TextView numDislikes = (TextView) convertView.findViewById(R.id.numDislikesTop);
                TextView numComments = (TextView) convertView.findViewById(R.id.numCommentsTop);
                TextView numViews = (TextView) convertView.findViewById(R.id.numViewsTop);

                Section item = (Section) it;

                PoliticalParty pol = PoliticalGroups.getInstance().getPoliticalParty(item.getmPoliticalParty());

                partyLogo.setImageBitmap(pol.getmLogo());

                titleSection.setText(item.getmTitle());

                nameParty.setText(pol.getmName());

                numLikes.setText(Integer.toString(item.getNumLikes()));
                numNotUnderstood.setText(Integer.toString(item.getNumNotUnderstoods()));
                numDislikes.setText(Integer.toString(item.getNumDislikes()));
                numComments.setText(Integer.toString(item.getNumComments()));
                numViews.setText(Integer.toString(item.getNumViews()));


            } else if (it.isProposal()) { //is a Proposal

                LayoutInflater inflater = (LayoutInflater) parent.getContext().
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.top_ranking_item, null);

                ImageView propLogo = (ImageView) convertView.findViewById(R.id.logoTop);

                TextView titleProp = (TextView) convertView.findViewById(R.id.titleTop);
                TextView nameUser = (TextView) convertView.findViewById(R.id.subTitleTop);

                TextView numLikes = (TextView) convertView.findViewById(R.id.numLikesTop);
                TextView numNotUnderstood = (TextView) convertView.findViewById(R.id.numNotUnderstoodTop);
                TextView numDislikes = (TextView) convertView.findViewById(R.id.numDislikesTop);
                TextView numComments = (TextView) convertView.findViewById(R.id.numCommentsTop);
                TextView numViews = (TextView) convertView.findViewById(R.id.numViewsTop);

                Proposal item = (Proposal) it;

                propLogo.setImageResource(item.getResLogo());

                titleProp.setText(item.getTitleProp());

                nameUser.setText(item.getUser());

                numLikes.setText(Integer.toString(item.getNumLikes()));
                numNotUnderstood.setText(Integer.toString(item.getNumNotUnderstoods()));
                numDislikes.setText(Integer.toString(item.getNumDislikes()));
                numComments.setText(Integer.toString(item.getNumComments()));
                numViews.setText(Integer.toString(item.getNumViews()));


            } else {  //item is a header

                    LayoutInflater inflater = (LayoutInflater) parent.getContext().
                            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.top_ranking_header, null);

                    ImageView headerIcon = (ImageView) convertView.findViewById(R.id.headerTopImage);
                    TextView headerText = (TextView) convertView.findViewById(R.id.headerTopText);

                    TopHeaderItem header = (TopHeaderItem) it;

                    headerIcon.setImageResource(header.getIconId());
                    headerText.setText(header.getTitle());

                }

        }

        return convertView;

    }
}
