package com.example.zorbel.apptfg.proposals;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.service_connection.PostProposal;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewProposalActivity extends MenuActivity {

    private Spinner spinnerCategory;
    private FloatingActionButton mButtonSubmit;
    private FloatingActionButton mButtonCancel;
    private EditText mEditTextTitleProposal;
    private EditText mEditTextTextProposal;
    private EditText mEditTextHowProposal;
    private EditText mEditTextCostProposal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_proposal);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        super.getSupportActionBar().setIcon(R.mipmap.ic_proposals);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B800")));

        mButtonSubmit = (FloatingActionButton) findViewById(R.id.addNewProposal);
        mEditTextTitleProposal = (EditText) findViewById(R.id.txtTitleProposal);
        mEditTextTextProposal = (EditText) findViewById(R.id.txtTextProposal);
        mEditTextHowProposal = (EditText) findViewById(R.id.txtHowProposal);
        mEditTextCostProposal = (EditText) findViewById(R.id.txtMoneyProposal);

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            URL link;

            @Override
            public void onClick(View view) {
                try {
                    link = new URL(MainActivity.SERVER + "/proposal");

                    //TODO: set the user for the proposal

                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id_user", Integer.toString(1)));
                    params.add(new BasicNameValuePair("title", mEditTextTitleProposal.getText().toString()));
                    params.add(new BasicNameValuePair("text", mEditTextTextProposal.getText().toString()));
                    params.add(new BasicNameValuePair("how", mEditTextHowProposal.getText().toString()));
                    params.add(new BasicNameValuePair("cost", mEditTextCostProposal.getText().toString()));
                    params.add(new BasicNameValuePair("id_category", Integer.toString(spinnerCategory.getSelectedItemPosition())));
                    int index = spinnerCategory.getSelectedItemPosition() + 1;
                    params.add(new BasicNameValuePair("id_image", Integer.toString(index)));

                    PostProposal task = new PostProposal(NewProposalActivity.this, params, findViewById(R.id.addNewProposal));
                    task.execute(link);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        mButtonCancel = (FloatingActionButton) findViewById(R.id.cancelNewProposal);

        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategoryProposal);

        List<String> list = new ArrayList<String>();
        list.add("#Sanidad");
        list.add("#Educación");
        list.add("#Empleo");
        list.add("#Vivienda");
        list.add("#Impuestos");
        list.add("#Cultura");
        list.add("#Otros");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(dataAdapter);
    }
}
