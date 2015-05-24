package com.example.zorbel.apptfg.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.zorbel.apptfg.views.PartyWidgetView;
import com.example.zorbel.data_structures.PoliticalParty;

import java.util.List;

public class PartyWidgetAdapter extends BaseAdapter {

    private Context mContext;
    private List<PoliticalParty> mPartiesList;

    public PartyWidgetAdapter(Context c, List<PoliticalParty> partiesList) {
        mContext = c;
        this.mPartiesList = partiesList;
    }

    @Override
    public int getCount() {
        return mPartiesList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPartiesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PartyWidgetView partyView;

        if (convertView == null) {
            partyView = new PartyWidgetView(mContext, mPartiesList.get(position));

            PoliticalParty p = mPartiesList.get(position);

            Bitmap bm = p.getmLogo();
            partyView.setLogo(bm);

            String name = p.getmName();
            partyView.setPartyName(name);

        } else {
            partyView = (PartyWidgetView) convertView;
        }
        return partyView;
    }
}
