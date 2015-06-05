package com.example.zorbel.apptfg.proposals;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.User;
import com.example.zorbel.service_connection.GetComments;
import com.example.zorbel.service_connection.PostComment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CommentsProposalActivity extends MenuActivity {

    private ListView listViewComments;
    private EditText editTextComment;
    private Button buttonSendComment;

    private int proposalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_proposal);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B800")));

        proposalId = getIntent().getExtras().getInt("ProposalId");

        if(super.isNetworkAvailable())
            getProposalComments(proposalId);

        listViewComments = (ListView) findViewById(R.id.listViewComments);
        editTextComment = (EditText) findViewById(R.id.editTextComment);
        buttonSendComment = (Button) findViewById(R.id.buttonSendComment);
        //buttonSendComment.setEnabled(false);

        buttonSendComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                postComment();

                editTextComment.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextComment.getWindowToken(), 0);
            }
        });

        editTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableSubmitIfReady();
            }
        });

    }

    private void enableSubmitIfReady() {

        boolean isReady = editTextComment.getText().toString().length() > 3;

        if (isReady)
            buttonSendComment.setEnabled(true);
        else
            buttonSendComment.setEnabled(false);
    }

    private void postComment() {

        URL link;

        if(super.isNetworkAvailable()) {
            try {
                link = new URL(MainActivity.SERVER + "/proposal/" + proposalId + "/comment");

                //TODO: set the user for the comment

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("id_user", User.ID_USER));
                params.add(new BasicNameValuePair("text", editTextComment.getText().toString()));

                PostComment task = new PostComment(CommentsProposalActivity.this, params, findViewById(R.id.activityCommentsLayout));
                task.execute(link);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }

    private void getProposalComments(int id_prop) {
        URL link;
        try {
            link = new URL(MainActivity.SERVER + "/proposal/" + id_prop + "/comment");

            GetComments task = new GetComments(this, findViewById(R.id.activityCommentsLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


}