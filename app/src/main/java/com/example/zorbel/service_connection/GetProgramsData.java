package com.example.zorbel.service_connection;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.adapters.ListIndexAdapter;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.data_structures.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GetProgramsData extends ConnectionGet {

    private static final String TAG_SECTION_TITLE = "title";
    private static final String TAG_SECTION_ID = "section";

    private int politicalPartyId;

    private int currentSectionId;

    public GetProgramsData(Context mContext, View mRootView, int id) {
        super(mContext, mRootView);
        this.politicalPartyId = id;
        this.currentSectionId = 0;
    }

    public GetProgramsData(Context mContext, View mRootView, int id_pol, int id_sec) {
        super(mContext, mRootView);
        this.politicalPartyId = id_pol;
        this.currentSectionId = id_sec;
    }

    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        getPoliticalProgram(super.getJson(), politicalPartyId);

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (currentSectionId == 0) { //Show the political party program index

            if (super.getmRootView() != null) {
                List<Section> index = PoliticalGroups.getInstance().getPoliticalParty(politicalPartyId).getmSectionRoot().getlSections();

                ListView mIndexListView = (ListView) super.getmRootView().findViewById(R.id.indexListView);
                mIndexListView.setAdapter(new ListIndexAdapter(super.getmContext(), index));
            }

        } else { //Show the specific section info

            getSectionContentData(currentSectionId, politicalPartyId);

        }

        //TODO: Set info section data

    }

    private void getPoliticalProgram(String jsonStr, int id) {

        List<Section> al = new ArrayList<Section>();

        if (jsonStr != null) {
            try {

                JSONArray sections = new JSONArray(jsonStr);


                for (int i = 0; i < sections.length(); i++) {
                    JSONObject s = sections.getJSONObject(i);

                    String title = s.getString(TAG_SECTION_TITLE);
                    int id_section = s.getInt(TAG_SECTION_ID);


                    Section sec = new Section(id_section, id, title, null, null);

                    al.add(sec);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Section root = new Section(0, id, null, null, null);
        createIndex(root, al, 0);
        PoliticalGroups.getInstance().getPoliticalParty(politicalPartyId).setmSectionRoot(root);

    }

    protected int createIndex(Section parent, List<Section> JSONResult, int index) {
        // X = currentSection
        Section currentSection = JSONResult.get(index);

        if (parent.getlSections() == null) {
            parent.setlSections(new ArrayList<Section>());
        }
        parent.addSubSection(currentSection);

        // c = nextIndex
        int nextIndex = index + 1;

        while ((nextIndex < JSONResult.size()) && (getLevel(JSONResult.get(nextIndex)) >= getLevel(currentSection))) {

            if (getLevel(JSONResult.get(nextIndex)) == getLevel(currentSection)) {
                currentSection = JSONResult.get(nextIndex);
                nextIndex++;
                parent.addSubSection(currentSection);

            } else if (getLevel(JSONResult.get(nextIndex)) > getLevel(currentSection))
                nextIndex = createIndex(currentSection, JSONResult, nextIndex);
        }

        return nextIndex;
    }

    protected int getLevel(Section sec) {

        int id_sec = sec.getmSection(), level;

        if (id_sec % 100 != 0) {
            level = 4;

        } else if (id_sec % 10000 != 0) {
            level = 3;

        } else if (id_sec % 1000000 != 0) {
            level = 2;

        } else
            level = 1;

        return level;
    }

    private void getSectionContentData(int id_section, int id_politicalParty) {
        URL link;
        try {
            link = new URL(MainActivity.SERVER + "/politicalParty/" + id_politicalParty + "/section/" + id_section);

            //Prepare post arguments
            //String parameters = "section=" + URLEncoder.encode(Integer.toString(id_section), "UTF-8") + "&id_political_party=" + URLEncoder.encode(Integer.toString(id_politicalParty), "UTF-8");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_user", User.ID_USER));

            PostSectionContent task = new PostSectionContent(getmContext(), params, super.getmRootView(), id_politicalParty);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
