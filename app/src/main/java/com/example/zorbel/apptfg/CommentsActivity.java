package com.example.zorbel.apptfg;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zorbel.service.PostComment;

import java.net.MalformedURLException;
import java.net.URL;


public class CommentsActivity extends ActionBarActivity {

    private ListView listViewComments;
    private EditText editTextComment;
    private Button buttonSendComment;
    private int SectionId;
    private int PoliticalPartyIndex;
    private int UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        listViewComments = (ListView) findViewById(R.id.listViewComments);
        editTextComment = (EditText) findViewById(R.id.editTextComment);
        buttonSendComment = (Button) findViewById(R.id.buttonSendComment);

        PoliticalPartyIndex = getIntent().getExtras().getInt("PoliticalPartyIndex");
        SectionId = getIntent().getExtras().getInt("SectionId");

        buttonSendComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                URL link = null;

                try {
                    link = new URL("http://10.0.2.2/ServiceRest/public/politicalParty/" + PoliticalPartyIndex + "/section/" + SectionId + "/comment");

                    //Prepare post arguments
                    //String parameters = "section=" + URLEncoder.encode(Integer.toString(id_section), "UTF-8") + "&id_political_party=" + URLEncoder.encode(Integer.toString(id_politicalParty), "UTF-8");

                    PostComment task = new PostComment(1, SectionId, PoliticalPartyIndex, editTextComment.getText().toString());
                    task.execute(link);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
