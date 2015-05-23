package com.example.zorbel.apptfg.programs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;

public class CategorizedProgramsActivity extends MenuActivity {

    private ListView categorizedSectionsListView;

    public static final String ARG_CATEGORY = "ARG_CATEGORY";
    public static final String ARG_CATEGORYLOGO = "ARG_CATEGORYLOGO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        String title = getIntent().getExtras().getString(ARG_CATEGORY);
        int photoRes = getIntent().getExtras().getInt(ARG_CATEGORYLOGO);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4775FF")));

        //Set the icon category for the action bar
        super.getSupportActionBar().setIcon(photoRes);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Set the title category for the action bar
        super.getSupportActionBar().setTitle(title);



    }

}