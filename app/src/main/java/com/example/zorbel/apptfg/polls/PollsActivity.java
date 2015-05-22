package com.example.zorbel.apptfg.polls;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;

public class PollsActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls);

        super.setMenus(findViewById(R.id.drawer_layout), 5);

        super.getSupportActionBar().setIcon(R.mipmap.ic_polls);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1919")));
    }


}
