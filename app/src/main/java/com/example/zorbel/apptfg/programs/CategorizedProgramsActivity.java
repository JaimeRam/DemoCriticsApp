package com.example.zorbel.apptfg.programs;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.service_connection.GetTopSections;

import java.net.MalformedURLException;
import java.net.URL;

public class CategorizedProgramsActivity extends MenuActivity {

    public static final String ARG_CATEGORY = "ARG_CATEGORY";
    public static final String ARG_ID_CATEGORY = "ARG_ID_CATEGORY";
    public static final String ARG_CATEGORYLOGO = "ARG_CATEGORYLOGO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorized_programs);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        String title = getIntent().getExtras().getString(ARG_CATEGORY);
        int photoRes = getIntent().getExtras().getInt(ARG_CATEGORYLOGO);
        int id_category = getIntent().getExtras().getInt(ARG_ID_CATEGORY);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4775FF")));

        //Set the icon category for the action bar
        super.getSupportActionBar().setIcon(photoRes);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Set the title category for the action bar
        super.getSupportActionBar().setTitle(title);

        if(super.isNetworkAvailable())
            getSectionsData(id_category);
    }

    private void getSectionsData(int id_category) {

            int limit = 10;
            URL link;

            try {
                link = new URL(MainActivity.SERVER + "/category/" + id_category + "/section/" + limit);
                GetTopSections task = new GetTopSections(this, findViewById(R.id.layoutTopCategorizedSections));
                task.execute(link);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

    }
}