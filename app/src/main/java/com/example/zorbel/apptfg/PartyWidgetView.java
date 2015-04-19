package com.example.zorbel.apptfg;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zorbel.data_structures.PoliticalParty;

/**
 * Created by javier on 25/03/15.
 */
public class PartyWidgetView extends RelativeLayout {

    private ImageView logo;
    private TextView partyName;
    private PoliticalParty pol_party;


    public PartyWidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public PartyWidgetView(Context context, PoliticalParty p) {
        super(context);
        initializeView();
        this.pol_party = p;
    }

    public void initializeView() {

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li =
                (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.party_widget, this, true);

        logo = (ImageView) findViewById(R.id.partyLogo);
        partyName = (TextView) findViewById(R.id.partyTitle);

    }


    public void setLogo(Bitmap b) {

        logo.setImageBitmap(b);

    }

    public void setPartyName(String pN) {

        partyName.setText(pN);
    }

    public PoliticalParty getPol_party() {
        return pol_party;
    }
}
