package com.example.zorbel.apptfg;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.zorbel.data_structures.PoliticalParty;

import java.util.List;

/**
 * Created by javier on 31/03/15.
 */
public class PartyWidgetAdapter extends BaseAdapter {

    private Context mContext;
    private List<PoliticalParty> mPartiesList;

    public PartyWidgetAdapter(Context c, List<PoliticalParty> partiesList) {
        mContext = c;
        this.mPartiesList = partiesList;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mPartiesList.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
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
