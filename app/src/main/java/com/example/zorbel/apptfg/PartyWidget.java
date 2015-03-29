package com.example.zorbel.apptfg;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by javier on 25/03/15.
 */
public class PartyWidget extends RelativeLayout {

    private ImageView logo;
    private TextView partyName;


    public PartyWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public PartyWidget(Context context) {
        super(context);
        initializeView();
    }

    public void initializeView() {

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li =
                (LayoutInflater)getContext().getSystemService(infService);
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
}
