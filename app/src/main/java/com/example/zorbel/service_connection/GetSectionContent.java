package com.example.zorbel.service_connection;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.apptfg.adapters.ListIndexAdapter;
import com.example.zorbel.apptfg.R;
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

    private int politicalProgramGroupIndex;

    private Section currentSection;

    private ProgressDialog pDialog;

    public GetSectionContent(Context mContext, View mRootView, int index) {
        super(mContext, mRootView);
        this.politicalProgramGroupIndex = index;
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
        TextView sectionText = (TextView) super.getmRootView().findViewById(R.id.textSection);

        Button commentButton = (Button) super.getmRootView().findViewById(R.id.buttonComment);
        Button likeButton = (Button) super.getmRootView().findViewById(R.id.buttonLike);
        Button notUnderstoodButton = (Button) super.getmRootView().findViewById(R.id.buttonNotUnderstood);
        Button dislikeButton = (Button) super.getmRootView().findViewById(R.id.buttonDislike);

        if (currentSection.getmTitle().equalsIgnoreCase("null")) { //Check if Section doesn't have text (its text is "null")
            sectionTitle.setText("No text");
        } else {
            sectionTitle.setText(currentSection.getmTitle());
        }

        sectionText.setText(currentSection.getmText());

        if (sectionText.getText().toString().length() > 0) {
            likeButton.setText("(" + currentSection.getNumLikes() + ")");
            dislikeButton.setText("(" + currentSection.getNumDislikes() + ")");
            notUnderstoodButton.setText("(" + currentSection.getNumNotUnderstoods() + ")");
            commentButton.setText(super.getmContext().getString(R.string.name_buttonComment) + "  (" + currentSection.getNumComments() + ")");
        } else {
         /*
         * Esto sirve para eliminar los botones cuando la sección no contiene texto.
         * Funciona perfectamente, pero el layout no se adapta bien cuando eliminas los elementos.
         * Habrá que cambiar las propiedades del ListView.

            RelativeLayout rl = (RelativeLayout) mRootView.findViewById(R.id.activitySectionViewerLayout);
            rl.removeView(likeButton);
            rl.removeView(dislikeButton);
            rl.removeView(notUnderstoodButton);
            rl.removeView(commentButton);
            */

            likeButton.setEnabled(false);
            dislikeButton.setEnabled(false);
            notUnderstoodButton.setEnabled(false);
            commentButton.setEnabled(false);
        }

        if (currentSection.getlSections() != null) { //Check if Section doesn't have subsections
            ListView mIndexListView = (ListView) super.getmRootView().findViewById(R.id.indexListView);
            mIndexListView.setAdapter(new ListIndexAdapter(super.getmContext(), currentSection.getlSections()));
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

                currentSection = PoliticalGroups.getInstance().getSection(politicalProgramGroupIndex, id_section);

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
