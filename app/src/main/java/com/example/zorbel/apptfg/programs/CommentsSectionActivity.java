package com.example.zorbel.apptfg.programs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.data_structures.User;
import com.example.zorbel.service_connection.GetComments;
import com.example.zorbel.service_connection.PostComment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CommentsSectionActivity extends MenuActivity {

    private ListView listViewComments;
    private EditText editTextComment;
    private Button buttonSendComment;

    private int sectionId;
    private int politicalPartyId;
    private Section currentSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_section);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4775FF")));

        listViewComments = (ListView) findViewById(R.id.listViewComments);
        editTextComment = (EditText) findViewById(R.id.editTextComment);
        buttonSendComment = (Button) findViewById(R.id.buttonSendComment);
        buttonSendComment.setEnabled(false);

        politicalPartyId = getIntent().getExtras().getInt("PoliticalPartyId");
        sectionId = getIntent().getExtras().getInt("SectionId");

        currentSection = PoliticalGroups.getInstance().getSection(politicalPartyId, sectionId);

        final int politicalPartyId = currentSection.getmPoliticalParty();

        if(super.isNetworkAvailable())
            getSectionComments(sectionId, politicalPartyId);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem itemParty = menu.add("");
        itemParty.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM); // ShowAsAction
        itemParty.setIcon(new BitmapDrawable(getResources(), PoliticalGroups.getInstance().getPoliticalParty(currentSection.getmPoliticalParty()).getmLogo())); // Icon
        itemParty.setEnabled(false);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_political_program_index, menu);
        return true;
    }

    private void postComment() {
        URL link;

        if(super.isNetworkAvailable()) {
            try {
                link = new URL(MainActivity.SERVER + "/politicalParty/" + politicalPartyId + "/section/" + sectionId + "/comment");

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("id_user", User.ID_USER));
                params.add(new BasicNameValuePair("text", editTextComment.getText().toString()));

                PostComment task = new PostComment(CommentsSectionActivity.this, params, findViewById(R.id.activityCommentsLayout));
                task.execute(link);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
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
