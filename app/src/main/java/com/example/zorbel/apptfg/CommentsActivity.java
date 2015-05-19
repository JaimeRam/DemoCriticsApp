package com.example.zorbel.apptfg;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.service.GetComments;
import com.example.zorbel.service.PostComment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CommentsActivity extends MenuActivity {

    private ListView listViewComments;
    private EditText editTextComment;
    private Button buttonSendComment;

    private int sectionId;
    private int politicalPartyIndex;
    private Section currentSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        listViewComments = (ListView) findViewById(R.id.listViewComments);
        editTextComment = (EditText) findViewById(R.id.editTextComment);
        buttonSendComment = (Button) findViewById(R.id.buttonSendComment);
        buttonSendComment.setEnabled(false);

        politicalPartyIndex = getIntent().getExtras().getInt("PoliticalPartyIndex");
        sectionId = getIntent().getExtras().getInt("SectionId");

        currentSection = PoliticalGroups.getInstance().getSection(politicalPartyIndex, sectionId);

        final int politicalPartyId = currentSection.getmPoliticalParty();

        getSectionComments(sectionId, politicalPartyId);

        buttonSendComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                URL link;

                try {
                    link = new URL(MainActivity.SERVER + "/politicalParty/" + politicalPartyId + "/section/" + sectionId + "/comment");

                    //TODO: set the user for the comment

                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id_user", Integer.toString(1)));
                    params.add(new BasicNameValuePair("text", editTextComment.getText().toString()));

                    PostComment task = new PostComment(CommentsActivity.this, params, findViewById(R.id.activityCommentsLayout));
                    task.execute(link);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

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

    private void getSectionComments(int id_section, int id_politicalParty) {
        URL link;
        try {
            link = new URL(MainActivity.SERVER + "/politicalParty/" + id_politicalParty + "/section/" + id_section + "/comment");

            GetComments task = new GetComments(this, findViewById(R.id.activityCommentsLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
