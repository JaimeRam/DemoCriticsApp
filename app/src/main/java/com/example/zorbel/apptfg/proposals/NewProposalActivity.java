package com.example.zorbel.apptfg.proposals;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewProposalActivity extends MenuActivity {

    private Spinner spinnerCategory;
    private Button mButtonSubmit;
    private Button mButtonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_proposal);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        super.getSupportActionBar().setIcon(R.mipmap.ic_proposals);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B800")));

        mButtonSubmit = (Button) findViewById(R.id.addNewProposal);
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            URL link;

            @Override
            public void onClick(View view) {
                try {
                    link = new URL(MainActivity.SERVER + "/proposal");

                    //TODO: set the user for the comment

                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id_user", Integer.toString(1)));
                    params.add(new BasicNameValuePair("text", ""));
                    params.add(new BasicNameValuePair("how", ""));
                    params.add(new BasicNameValuePair("cost", ""));
                    params.add(new BasicNameValuePair("id_image", ""));

                    /*PostComment task = new PostComment(CommentsSectionActivity.this, params, findViewById(R.id.activityCommentsLayout));
                    task.execute(link);*/

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        mButtonSubmit = (Button) findViewById(R.id.CancelNewProposal);

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
