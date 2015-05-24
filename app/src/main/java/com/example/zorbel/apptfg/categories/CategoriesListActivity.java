package com.example.zorbel.apptfg.categories;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;

public class CategoriesListActivity extends MenuActivity {

    public static final String ARG_CATEGORY = "ARG_CATEGORY";
    public static final String ARG_ID_CATEGORY = "ARG_ID_CATEGORY";
    public static final String ARG_CATEGORYLOGO = "ARG_CATEGORYLOGO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        super.getSupportActionBar().setIcon(R.mipmap.ic_topics);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.setMenus(findViewById(R.id.drawer_layout), 3);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF9900")));

        setCategoriesButtonsListeners();

    }

    private void setCategoriesButtonsListeners() {

        // SET BUTTON HEALTH LISTENER
        Button btnHealth = (Button) findViewById(R.id.btn_Health);

        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(CategoriesListActivity.this, CategoryActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryHealth));
                b.putInt(ARG_ID_CATEGORY, 1);
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_health_cross);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON EDUCATION LISTENER
        Button btnEducation = (Button) findViewById(R.id.btn_Education);

        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(CategoriesListActivity.this, CategoryActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryEducation));
                b.putInt(ARG_ID_CATEGORY, 2);
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_education);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON EMPLOYMENT LISTENER
        Button btnEmployment = (Button) findViewById(R.id.btn_Employment);

        btnEmployment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(CategoriesListActivity.this, CategoryActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryEmployment));
                b.putInt(ARG_ID_CATEGORY, 3);
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_employment);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON HOMES LISTENER
        Button btnHomes = (Button) findViewById(R.id.btn_Homes);

        btnHomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(CategoriesListActivity.this, CategoryActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryHomes));
                b.putInt(ARG_ID_CATEGORY, 4);
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_houses);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON TAXES LISTENER
        Button btnTaxes = (Button) findViewById(R.id.btn_Taxes);

        btnTaxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(CategoriesListActivity.this, CategoryActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryTaxes));
                b.putInt(ARG_ID_CATEGORY, 5);
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_taxes);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON CULTURE LISTENER
        Button btnCulture = (Button) findViewById(R.id.btn_Culture);

        btnCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(CategoriesListActivity.this, CategoryActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryCulture));
                b.putInt(ARG_ID_CATEGORY, 6);
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_culture);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON OTHERS LISTENER
        Button btnOthers = (Button) findViewById(R.id.btn_Others);

        btnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(CategoriesListActivity.this, CategoryActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryOthers));
                b.putInt(ARG_ID_CATEGORY, 7);
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_others);
                in.putExtras(b);
                startActivity(in);
            }
        });



    }

}
