package com.example.zorbel.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.apptfg.ListIndexAdapter;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.Section;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by javier on 2/04/15.
 */

public class GetSectionContent extends AsyncTask<URL, Void, Void> {

    private static final String TAG_SECTION_ID = "section";
    private static final String TAG_SECTION_TITLE = "title";
    private static final String TAG_SECTION_TEXT = "text";
    private static final String TAG_SECTION_LIKES = "likes";
    private static final String TAG_SECTION_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_SECTION_DISLIKES = "dislikes";
    private static final String TAG_SECTION_NUM_COMMENTS = "comments";
    private HttpURLConnection con;
    private Context mContext;
    private View mRootView;
    private int politicalProgramGroupIndex;

    private Section currentSection;

    private ProgressDialog pDialog;

    public GetSectionContent(Context con, View rootView, int index) {
        this.mContext = con;
        this.mRootView = rootView;
        this.politicalProgramGroupIndex = index;

    }


    @Override
    protected Void doInBackground(URL... urls) {

        StringBuilder builder = new StringBuilder();

        try {

            con = (HttpURLConnection) urls[0].openConnection();

            int statusCode = con.getResponseCode();

            if (statusCode == 200) {
                InputStream in = new BufferedInputStream(con.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(GetSectionContent.class.toString(), "Failed to get JSON object");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        getSectionData(builder.toString());

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getString(R.string.text_dialog_downloading));
        pDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        updateView();

        pDialog.dismiss();
    }

    protected void updateView() {

        TextView sectionTitle = (TextView) mRootView.findViewById(R.id.sectionTitle);
        TextView sectionText = (TextView) mRootView.findViewById(R.id.textSection);

        Button commentButton = (Button) mRootView.findViewById(R.id.buttonComment);
        Button likeButton = (Button) mRootView.findViewById(R.id.buttonLike);
        Button notUnderstoodButton = (Button) mRootView.findViewById(R.id.buttonNotUnderstood);
        Button dislikeButton = (Button) mRootView.findViewById(R.id.buttonDislike);

        if (currentSection.getmTitle().equalsIgnoreCase("null")) { //Check if Section doesn't have text (its text is "null")
            sectionTitle.setText("No text");
        } else {
            sectionTitle.setText(currentSection.getmTitle());
        }

        sectionText.setText(currentSection.getmText());

        if (sectionText.getText().toString().length() > 0) {
            likeButton.setText(mContext.getString(R.string.name_buttonLike) + "\n" + "(" + currentSection.getNumLikes() + ")");
            dislikeButton.setText(mContext.getString(R.string.name_buttonDislike) + "\n" + "(" + currentSection.getNumDislikes() + ")");
            notUnderstoodButton.setText(mContext.getString(R.string.name_buttonNotUnderstood) + "\n" + "(" + currentSection.getNumNotUnderstoods() + ")");
            commentButton.setText(mContext.getString(R.string.name_buttonComment) + "\n" + "(" + currentSection.getNumComments() + ")");
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
            ListView mIndexListView = (ListView) mRootView.findViewById(R.id.indexListView);
            mIndexListView.setAdapter(new ListIndexAdapter(mContext, currentSection.getlSections()));
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
