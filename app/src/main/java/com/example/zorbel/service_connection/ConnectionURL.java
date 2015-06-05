package com.example.zorbel.service_connection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.example.zorbel.apptfg.R;

import java.net.URL;

public class ConnectionURL extends AsyncTask<URL, Void, Void> {

    private Context mContext;
    private View mRootView;
    private ProgressDialog pDialog;

    public ConnectionURL(Context mContext, View mRootView) {
        this.mContext = mContext;
        this.mRootView = mRootView;
    }

    @Override
    protected Void doInBackground(URL... params) {
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


    public View getmRootView() {
        return mRootView;
    }

    public Context getmContext() {
        return mContext;
    }
}
