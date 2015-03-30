package com.example.zorbel.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaime on 30/03/15.
 */
public class GetPoliticalParties extends AsyncTask<URL, Void, Void> {

    private HttpURLConnection con;
    private static final String TAG_SECTION_ID = "id";
    private static final String TAG_SECTION_NAME = "name";

    @Override
    protected Void doInBackground(URL... urls) {
        StringBuilder builder = new StringBuilder();

        try {

            // Establecer la conexión
            con = (HttpURLConnection) urls[0].openConnection();

            // Obtener el estado del recurso
            int statusCode = con.getResponseCode();

            if (statusCode == 200) {
                InputStream in = new BufferedInputStream(con.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(GetPoliticalParties.class.toString(), "Failed to get JSON object");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("JSON", "     :      " + builder.toString() + "  ");
        getPoliticalParties(builder.toString());

        return null;
    }

    private void getPoliticalParties(String jsonStr) {

        List<PoliticalParty> al = new ArrayList<PoliticalParty>();

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray sections = new JSONArray(jsonStr);


                for (int i = 0; i < sections.length(); i++) {
                    JSONObject s = sections.getJSONObject(i);

                    int id = s.getInt(TAG_SECTION_ID);
                    String name = s.getString(TAG_SECTION_NAME);

                    // TODO fill in the section with the proper attributes (id_section)
                    PoliticalParty sec = new PoliticalParty(id, name, null, null);

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
