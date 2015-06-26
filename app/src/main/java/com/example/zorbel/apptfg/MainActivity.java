package com.example.zorbel.apptfg;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zorbel.apptfg.categories.CategoriesListActivity;
import com.example.zorbel.apptfg.collaborate.CollaborativeProposalsActivity;
import com.example.zorbel.apptfg.programs.ProgramsActivity;
import com.example.zorbel.apptfg.proposals.ProposalsActivity;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.User;
import com.example.zorbel.service_connection.GetPoliticalParties;
import com.example.zorbel.service_connection.GetUser;

import org.swellrt.android.service.SwellRTActivity;
import org.swellrt.android.service.SwellRTService;
import org.swellrt.model.generic.Model;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends MenuActivity implements ServiceConnection, SwellRTService.SwellRTServiceCallback {

    private SwellRTService mSwellRT;

    AsyncTask<String, Void, Boolean> mRegisterTask;

    public static String WAVE_SERVER = "https://wave.p2pvalue.eu";
    public static String WAVE_ADMIN = "admin.dm";
    public static String WAVE_HOST = "@prototype.p2pvalue.eu";
    public static String SERVER = "https://apptfg-servicerest.rhcloud.com";

    private Button btnPrograms;
    private Button btnComparatives;
    private Button btnProposals;
    private Button btnCollaborate;

    protected void bindSwellRTService() {

        if (mSwellRT == null) {
            final Intent mWaveServiceIntent = new Intent(this, SwellRTService.class);
            bindService(mWaveServiceIntent, this, Context.BIND_AUTO_CREATE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        User.ID_USER = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);

        if(super.isNetworkAvailable()) {
            this.getUserData();
            if (PoliticalGroups.getInstance().getMlistOfPoliticalParties() == null)
                getPoliticalPartiesData();
        }

        super.setMenus(findViewById(R.id.drawer_layout), 1);


        btnPrograms = (Button) findViewById(R.id.btn_Programs);
        btnPrograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, ProgramsActivity.class);
                startActivity(in);
            }
        });

        btnComparatives = (Button) findViewById(R.id.btn_Comparatives);
        btnComparatives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, CategoriesListActivity.class);
                startActivity(in);
            }
        });

        btnProposals = (Button) findViewById(R.id.btn_Proposals);
        btnProposals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, ProposalsActivity.class);
                in.putExtra("FocusTab", 2);
                startActivity(in);
            }
        });

        btnCollaborate = (Button) findViewById(R.id.btn_collaborate);
        btnCollaborate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, CollaborativeProposalsActivity.class);
                in.putExtra("FocusTab", 1);
                startActivity(in);
            }
        });

        bindSwellRTService();

        //In case user is not registered in wave server, try to do so
        registerWaveUser();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSwellRT != null) {
            unbindService(this);
            mSwellRT = null;
        }
    }

    private void getPoliticalPartiesData() {

        URL link;
        try {
            link = new URL(SERVER + "/politicalParty");
            GetPoliticalParties task = new GetPoliticalParties(this, findViewById(R.id.activityPartiesLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void getUserData() {

        URL link;
        try {
            link = new URL(SERVER + "/user/" + User.ID_USER);
            GetUser task = new GetUser(this, findViewById(R.id.drawer_layout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    // WAVE methods

    public void registerWaveUser() {

        mRegisterTask = new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                return mSwellRT.registerUser(params[0], params[1], params[2]);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result)
                    Toast.makeText(MainActivity.this, "Wave user created successfully", Toast.LENGTH_LONG).show();
            }
        };

        mRegisterTask.execute(WAVE_SERVER,
                "" + User.ID_USER + WAVE_HOST, "password");

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mSwellRT = ((SwellRTService.SwellRTBinder) service).getService(this);
        Log.d(this.getClass().getSimpleName(), "SwellRT Service Bound");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mSwellRT = null;
        Log.d(this.getClass().getSimpleName(), "SwellRT Service unBound");
    }

    @Override
    public void onStartSessionSuccess(String s) {

    }

    @Override
    public void onStartSessionFail(String s) {

    }

    @Override
    public void onCreate(Model model) {

    }

    @Override
    public void onOpen(Model model) {

    }

    @Override
    public void onClose(boolean b) {

    }

    @Override
    public void onUpdate(int i, int i1, int i2) {

    }

    @Override
    public void onError(String s) {

    }

    @Override
    public void onDebugInfo(String s) {

    }
}