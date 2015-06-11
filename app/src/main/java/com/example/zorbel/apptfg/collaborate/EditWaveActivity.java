package com.example.zorbel.apptfg.collaborate;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.example.zorbel.apptfg.R;

public class EditWaveActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wave);

        super.getSupportActionBar().setIcon(R.mipmap.ic_collaborate);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1919")));
    }

}
