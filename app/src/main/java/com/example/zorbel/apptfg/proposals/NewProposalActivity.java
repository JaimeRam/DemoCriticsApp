package com.example.zorbel.apptfg.proposals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.collaborate.CollaborativeProposalsActivity;
import com.example.zorbel.data_structures.User;
import com.example.zorbel.service_connection.PostProposal;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
        mButtonSubmit.setEnabled(false);
        mEditTextTitleProposal = (EditText) findViewById(R.id.txtTitleProposal);
        mEditTextTextProposal = (EditText) findViewById(R.id.txtTextProposal);
        mEditTextHowProposal = (EditText) findViewById(R.id.txtHowProposal);
        mEditTextCostProposal = (EditText) findViewById(R.id.txtMoneyProposal);

        mEditTextTitleProposal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        mEditTextTextProposal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            URL link;

            @Override
            public void onClick(View view) {

                String title = mEditTextTitleProposal.getText().toString();
                String text = mEditTextTextProposal.getText().toString();
                String how = mEditTextHowProposal.getText().toString();
                String cost = mEditTextCostProposal.getText().toString();

                if (how.isEmpty() || cost.isEmpty()) {

                    AlertDialog dialog = new AlertDialog.Builder(NewProposalActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                            .setTitle(getResources().getString(R.string.title_dialogProposalText))
                            .setMessage(getResources().getString(R.string.text_dialogCollaborativeProposal))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    //TODO: create collaborative proposal and go to CollaborateActivity

                                    goToMyCollaborativeProposals();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(R.mipmap.ic_action_person)
                            .show();

                } else {

                    postProposal();

                }
            }
        });

        mButtonCancel = (FloatingActionButton) findViewById(R.id.cancelNewProposal);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategoryProposal);


        String[] list = getResources().getStringArray(R.array.CategoriesEntries);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(dataAdapter);
    }

    private void enableSubmitIfReady() {

        int minChars = 3; // Minimal characters that every TextView must have.
        boolean isReady = mEditTextTextProposal.getText().toString().length() > minChars && mEditTextTitleProposal.getText().toString().length() > minChars;

        if (isReady)
            mButtonSubmit.setEnabled(true);
        else
            mButtonSubmit.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent in = new Intent(NewProposalActivity.this, ProposalsActivity.class);
        in.putExtra("FocusTab", 2);
        startActivity(in);
    }

    private void goToMyProposals() {
        this.finish();
        Intent in = new Intent(NewProposalActivity.this, ProposalsActivity.class);
        in.putExtra("FocusTab", 0);
        startActivity(in);
    }

    private void goToMyCollaborativeProposals() {
        this.finish();
        Intent in = new Intent(NewProposalActivity.this, CollaborativeProposalsActivity.class);
        in.putExtra("FocusTab", 0);
        startActivity(in);
    }


    private void postProposal() {

        URL link;

        if(super.isNetworkAvailable()) {
            try {
                link = new URL(MainActivity.SERVER + "/proposal");

                ArrayList<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("id_user", User.ID_USER));
                params.add(new BasicNameValuePair("title", mEditTextTitleProposal.getText().toString()));
                params.add(new BasicNameValuePair("text", mEditTextTextProposal.getText().toString()));
                params.add(new BasicNameValuePair("how", mEditTextHowProposal.getText().toString()));
                params.add(new BasicNameValuePair("cost", mEditTextCostProposal.getText().toString()));
                int index = spinnerCategory.getSelectedItemPosition() + 1;
                params.add(new BasicNameValuePair("id_category", Integer.toString(index)));
                params.add(new BasicNameValuePair("id_image", Integer.toString(index)));

                PostProposal task = new PostProposal(NewProposalActivity.this, params, findViewById(R.id.addNewProposal));
                task.execute(link);
                finish();
                goToMyProposals();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }
}
