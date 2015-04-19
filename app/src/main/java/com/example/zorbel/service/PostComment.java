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

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaime on 7/04/15.
 */
public class PostComment extends AsyncTask<URL, Void, Void> {

    private static final String TAG_COMMENT_USER = "nickname";
    private static final String TAG_COMMENT_TEXT = "text";
    private static final String TAG_COMMENT_DATE = "date";

    private HttpURLConnection con;
    private int id_user;
    private int section;
    private int id_political_party;
    private String text;
    private List<NameValuePair> params;
    private List<Comment> listComments;

    private Context mContext;
    private ProgressDialog pDialog;
    private View mRootView;

    public PostComment(Context context, int id_user, String text, View rootView) {
        this.id_user = id_user;
        this.section = section;
        this.id_political_party = id_political_party;
        this.text = text;
        this.mRootView = rootView;
        this.mContext = context;

        this.params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id_user", Integer.toString(id_user)));
        params.add(new BasicNameValuePair("text", text));
    }

    @Override
    protected Void doInBackground(URL... urls) {

        StringBuilder builder = new StringBuilder();

        try {
            // Establecer la conexión
            con = (HttpURLConnection) urls[0].openConnection();
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();

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

            con.connect();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        getComments(builder.toString());

        return null;
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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getString(R.string.text_dialog_uploading));
        pDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ListView listview = (ListView) mRootView.findViewById(R.id.listViewComments);
        listview.setAdapter(new CommentListAdapter(mContext, listComments));
        pDialog.dismiss();
    }

    protected String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
