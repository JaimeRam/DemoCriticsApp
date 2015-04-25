package com.example.zorbel.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.example.zorbel.apptfg.R;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaime on 30/03/15.
 */
public class GetPoliticalParties extends AsyncTask<URL, Void, Void> {

    private static final String TAG_SECTION_ID = "id";
    private static final String TAG_SECTION_NAME = "name";
    private static final String TAG_SECTION_LOGO = "logo";
    private HttpURLConnection con;
    private Context mContext;
    private View mRootView;
    private ProgressDialog pDialog;


    public GetPoliticalParties(Context con, View rootView) {
        this.mContext = con;
        this.mRootView = rootView;
    }

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
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        Log.d("JSON", "     :      " + builder.toString() + "  ");
        getPoliticalParties(builder.toString());

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
        pDialog.dismiss();
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
