package com.example.zorbel.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.zorbel.apptfg.CommentListAdapter;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.Comment;

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
 * Created by javier on 8/04/15.
 */
public class GetComments extends AsyncTask<URL, Void, Void> {

    private HttpURLConnection con;

    private static final String TAG_COMMENT_USER = "nickname";
    private static final String TAG_COMMENT_TEXT = "text";
    private static final String TAG_COMMENT_DATE = "date";

    private Context mContext;
    private View mRootView;
    private ProgressDialog pDialog;

    private List<Comment> listComments;


    public GetComments(Context con, View rootView) {
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
        }  finally {
            if (con != null) {
                con.disconnect();
            }
        }

        Log.d("JSON", "     :      " + builder.toString() + "  ");
        getComments(builder.toString());

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
        ListView listview = (ListView) mRootView.findViewById(R.id.listViewComments);
        listview.setAdapter(new CommentListAdapter(mContext, listComments));
    }

    private void getComments(String jsonStr) {

        listComments = new ArrayList<Comment>();

        if (jsonStr != null) {
            try {

                JSONArray sections = new JSONArray(jsonStr);


                for (int i = 0; i < sections.length(); i++) {
                    JSONObject s = sections.getJSONObject(i);

                    String user = s.getString(TAG_COMMENT_USER);
                    String text = s.getString(TAG_COMMENT_TEXT);
                    String date = s.getString(TAG_COMMENT_DATE);

                    Comment com = new Comment(user, date, text);

                    listComments.add(com);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
}
