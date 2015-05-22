package com.example.zorbel.service_connection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;

import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GetPoliticalParties extends ConnectionGet {

    private static final String TAG_SECTION_ID = "id";
    private static final String TAG_SECTION_NAME = "name";
    private static final String TAG_SECTION_LOGO = "logo";

    public GetPoliticalParties(Context mContext, View mRootView) {
        super(mContext, mRootView);
    }


    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        getPoliticalParties(super.getJson());

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private void getPoliticalParties(String jsonStr) {

        List<PoliticalParty> al = new ArrayList<PoliticalParty>();

        if (jsonStr != null) {
            try {

                JSONArray sections = new JSONArray(jsonStr);


                for (int i = 0; i < sections.length(); i++) {
                    JSONObject s = sections.getJSONObject(i);

                    int id = s.getInt(TAG_SECTION_ID);
                    String name = s.getString(TAG_SECTION_NAME);
                    String encodedLogo = s.getString(TAG_SECTION_LOGO);

                    //Decode Base 64 String image to Bitmap
                    byte[] decodedString = Base64.decode(encodedLogo, Base64.DEFAULT);
                    Bitmap decodedLogo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                    PoliticalParty sec = new PoliticalParty(id, name, decodedLogo, null);

                    al.add(sec);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        // Save data on PoliticalGroups
        PoliticalGroups.getInstance().setMlistOfPoliticalParties(al);
    }
}
