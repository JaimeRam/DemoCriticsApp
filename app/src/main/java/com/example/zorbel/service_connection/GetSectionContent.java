package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.ListIndexAdapter;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.Section;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;


public class GetSectionContent extends ConnectionGet {

    private static final String TAG_SECTION_ID = "section";
    private static final String TAG_SECTION_TITLE = "title";
    private static final String TAG_SECTION_TEXT = "text";
    private static final String TAG_SECTION_LIKES = "likes";
    private static final String TAG_SECTION_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_SECTION_DISLIKES = "dislikes";
    private static final String TAG_SECTION_NUM_COMMENTS = "comments";

    private int politicalProgramId;

    private Section currentSection;

    public GetSectionContent(Context mContext, View mRootView, int id) {
        super(mContext, mRootView);
        this.politicalProgramId = id;
    }

    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        getSectionData(super.getJson());

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        updateView();
    }

    protected void updateView() {

        TextView sectionTitle = (TextView) super.getmRootView().findViewById(R.id.sectionTitle);
        WebView sectionText = (WebView) super.getmRootView().findViewById(R.id.textSection);

        sectionText.setBackgroundColor(0x00000000);

        Button commentButton = (Button) super.getmRootView().findViewById(R.id.buttonComment);
        Button likeButton = (Button) super.getmRootView().findViewById(R.id.buttonLike);
        Button notUnderstoodButton = (Button) super.getmRootView().findViewById(R.id.buttonNotUnderstood);
        Button dislikeButton = (Button) super.getmRootView().findViewById(R.id.buttonDislike);

        if (currentSection.getmTitle().equalsIgnoreCase("null")) { //Check if Section doesn't have text (its text is "null")
            sectionTitle.setText("No text");
        } else {
            sectionTitle.setText(currentSection.getmTitle());
        }

        String text = "<html><body style=\"text-align:justify\"> " + currentSection.getmText()  + "</body></Html>";

        sectionText.loadDataWithBaseURL(null, text, "text/html", "UTF-8", null);

        if (currentSection.getmText().toString().length() > 0) {
            likeButton.setText(" " + currentSection.getNumLikes() + " ");
            dislikeButton.setText(" " + currentSection.getNumDislikes() + " ");
            notUnderstoodButton.setText(" " + currentSection.getNumNotUnderstoods() + " ");
            commentButton.setText(" " + currentSection.getNumComments() + " ");
        } else {

            sectionText.setVisibility(View.GONE);
            likeButton.setVisibility(View.GONE);
            dislikeButton.setVisibility(View.GONE);
            notUnderstoodButton.setVisibility(View.GONE);
            commentButton.setVisibility(View.GONE);
        }


        if (currentSection.getlSections() != null) { //Check if Section doesn't have subsections
            ListView mIndexListView = (ListView) super.getmRootView().findViewById(R.id.indexListView);
            mIndexListView.setAdapter(new ListIndexAdapter(super.getmContext(), currentSection.getlSections()));
        } else {
            ListView mIndexListView = (ListView) super.getmRootView().findViewById(R.id.indexListView);
            mIndexListView.setVisibility(View.GONE);
        }

    }

    protected void getSectionData(String jsonStr) {

        if (jsonStr != null) {
            try {

                JSONObject s = new JSONObject(jsonStr);

                int id_section = s.getInt(TAG_SECTION_ID);
                String title = s.getString(TAG_SECTION_TITLE);
                String text = s.getString(TAG_SECTION_TEXT);
                int likes = s.getInt(TAG_SECTION_LIKES);
                int not_understood = s.getInt(TAG_SECTION_NOT_UNDERSTOOD);
                int dislikes = s.getInt(TAG_SECTION_DISLIKES);

                int num_comments = s.getInt(TAG_SECTION_NUM_COMMENTS);

                currentSection = PoliticalGroups.getInstance().getSection(politicalProgramId, id_section);

                currentSection.setmText(text);
                currentSection.setNumLikes(likes);
                currentSection.setNumDislikes(dislikes);
                currentSection.setNumNotUnderstoods(not_understood);
                currentSection.setNumComments(num_comments);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}