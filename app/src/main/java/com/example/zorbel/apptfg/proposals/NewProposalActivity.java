package com.example.zorbel.apptfg.proposals;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;

import java.util.ArrayList;
import java.util.List;

public class NewProposalActivity extends MenuActivity {

    private Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_proposal);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        super.getSupportActionBar().setIcon(R.mipmap.ic_proposals);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B800")));

        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategoryProposal);

        List<String> list = new ArrayList<String>();
        list.add("#Sanidad");
        list.add("#Educación");
        list.add("#Empleo");
        list.add("#Cultura");
        list.add("#Impuestos");
        list.add("#Vivienda");
        list.add("#Otros");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(dataAdapter);


    }

}
