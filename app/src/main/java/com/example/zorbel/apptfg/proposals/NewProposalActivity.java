package com.example.zorbel.apptfg.proposals;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.MenuActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.collaborate.CollaborativeProposalsActivity;
import com.example.zorbel.data_structures.User;
import com.example.zorbel.service_connection.PostProposal;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.swellrt.android.service.SwellRTService;
import org.swellrt.model.generic.Model;
import org.swellrt.model.generic.TextType;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewProposalActivity extends MenuActivity implements ServiceConnection, SwellRTService.SwellRTServiceCallback  {

    private SwellRTService mSwellRT;

    private Spinner spinnerCategory;
    private FloatingActionButton mButtonSubmit;
    private FloatingActionButton mButtonCancel;
    private EditText mEditTextTitleProposal;
    private EditText mEditTextTextProposal;
    private EditText mEditTextHowProposal;
    private EditText mEditTextCostProposal;

    private boolean createHowWave;
    private boolean createCostWave;

    protected void bindSwellRTService() {

        if (mSwellRT == null) {
            final Intent mWaveServiceIntent = new Intent(this, SwellRTService.class);
            bindService(mWaveServiceIntent, this, Context.BIND_AUTO_CREATE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_proposal);

        super.setMenus(findViewById(R.id.drawer_layout), 0);

        super.getSupportActionBar().setIcon(R.mipmap.ic_proposals);
        super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00B800")));

        mButtonSubmit = (FloatingActionButton) findViewById(R.id.addNewProposal);
        mButtonSubmit.setEnabled(false);
        mEditTextTitleProposal = (EditText) findViewById(R.id.txtTitleProposal);
        mEditTextTextProposal = (EditText) findViewById(R.id.txtTextProposal);
        mEditTextHowProposal = (EditText) findViewById(R.id.txtHowProposal);
        mEditTextCostProposal = (EditText) findViewById(R.id.txtMoneyProposal);

        bindSwellRTService();

        mEditTextTitleProposal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        mEditTextTextProposal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            URL link;

            @Override
            public void onClick(View view) {

                String title = mEditTextTitleProposal.getText().toString();
                String text = mEditTextTextProposal.getText().toString();
                String how = mEditTextHowProposal.getText().toString();
                String cost = mEditTextCostProposal.getText().toString();

                if(how.isEmpty()) {
                    createHowWave = true;
                } else {
                    createHowWave = false;
                }

                if(cost.isEmpty()) {
                    createCostWave = true;
                } else {
                    createCostWave = false;
                }

                if (how.isEmpty() || cost.isEmpty()) {

                    AlertDialog dialog = new AlertDialog.Builder(NewProposalActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                            .setTitle(getResources().getString(R.string.title_dialogProposalText))
                            .setMessage(getResources().getString(R.string.text_dialogCollaborativeProposal))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    // Crear una wave/model que irá asociada a la propuesta
                                    mSwellRT.createModel();

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(R.mipmap.ic_action_person)
                            .show();

                } else {

                    postProposal();

                }
            }
        });

        mButtonCancel = (FloatingActionButton) findViewById(R.id.cancelNewProposal);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategoryProposal);


        String[] list = getResources().getStringArray(R.array.CategoriesEntries);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(dataAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSwellRT != null) {
            mSwellRT.stopSession();
            unbindService(this);
            mSwellRT = null;
        }
    }

    private void enableSubmitIfReady() {

        int minChars = 3; // Minimal characters that every TextView must have.
        boolean isReady = mEditTextTextProposal.getText().toString().length() > minChars && mEditTextTitleProposal.getText().toString().length() > minChars;

        if (isReady)
            mButtonSubmit.setEnabled(true);
        else
            mButtonSubmit.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent in = new Intent(NewProposalActivity.this, ProposalsActivity.class);
        in.putExtra("FocusTab", 2);
        startActivity(in);
    }

    private void goToMyProposals() {
        this.finish();
        Intent in = new Intent(NewProposalActivity.this, ProposalsActivity.class);
        in.putExtra("FocusTab", 0);
        startActivity(in);
    }

    private void goToMyCollaborativeProposals() {
        this.finish();
        Intent in = new Intent(NewProposalActivity.this, CollaborativeProposalsActivity.class);
        in.putExtra("FocusTab", 0);
        startActivity(in);
    }

    private void postProposal() {

        URL link;

        if(super.isNetworkAvailable()) {
            try {
                link = new URL(MainActivity.SERVER + "/proposal");

                ArrayList<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("id_user", User.ID_USER));
                params.add(new BasicNameValuePair("title", mEditTextTitleProposal.getText().toString()));
                params.add(new BasicNameValuePair("text", mEditTextTextProposal.getText().toString()));
                params.add(new BasicNameValuePair("how", mEditTextHowProposal.getText().toString()));
                params.add(new BasicNameValuePair("cost", mEditTextCostProposal.getText().toString()));
                int index = spinnerCategory.getSelectedItemPosition() + 1;
                params.add(new BasicNameValuePair("id_category", Integer.toString(index)));
                params.add(new BasicNameValuePair("id_image", Integer.toString(index)));

                PostProposal task = new PostProposal(NewProposalActivity.this, params, findViewById(R.id.addNewProposal));
                task.execute(link);
                finish();
                goToMyProposals();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }

    private void postCollaborativeProposal(String idWave) {

        URL link;

        if(super.isNetworkAvailable()) {
            try {
                link = new URL(MainActivity.SERVER + "/proposal");

                ArrayList<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("id_user", User.ID_USER));
                params.add(new BasicNameValuePair("title", mEditTextTitleProposal.getText().toString()));
                params.add(new BasicNameValuePair("text", mEditTextTextProposal.getText().toString()));

                int index = spinnerCategory.getSelectedItemPosition() + 1;
                params.add(new BasicNameValuePair("id_category", Integer.toString(index)));
                params.add(new BasicNameValuePair("id_image", Integer.toString(index)));

                if(createHowWave || createCostWave) {
                    params.add(new BasicNameValuePair("id_wave", idWave));
                }

                if (!createHowWave) {
                    params.add(new BasicNameValuePair("how", mEditTextHowProposal.getText().toString()));
                }

                if(!createCostWave) {
                    params.add(new BasicNameValuePair("cost", mEditTextCostProposal.getText().toString()));
                }

                PostProposal task = new PostProposal(NewProposalActivity.this, params, findViewById(R.id.addNewProposal));
                task.execute(link);
                finish();
                goToMyCollaborativeProposals();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }

    //Wave methods

    public void doStartSession() {

        // Crear la wave

        // Abrir sesión con usuario genérico de la app
        try {
            mSwellRT.startSession(MainActivity.WAVE_SERVER,
                    "" + User.ID_USER + "@local.net", "password");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InvalidParticipantAddress invalidParticipantAddress) {
            invalidParticipantAddress.printStackTrace();
        }
    }

    // SwellRT Service Callbacks

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mSwellRT = ((SwellRTService.SwellRTBinder) service).getService(this);
        doStartSession();
        Log.d(this.getClass().getSimpleName(), "SwellRT Service Bound");

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mSwellRT = null;
        Log.d(this.getClass().getSimpleName(), "SwellRT Service unBound");

    }

    // Wave Service Operational Callbacks

    // Implementar el siguiente callback en la activity

    public void onStartSessionSuccess(String session) {


    }

    // Implementar el siguiente callback en la activity

    public void onCreate(Model model) {

        // Obtener el Id de la wave/model a guardar en la BBDD asociada a la propuesta
        WaveId idW = model.getWaveId();

        String idWave = idW.toString();

        String id = "";

        id = idWave.substring(8, idWave.length() - 1);

        //TODO: store in the bbdd
        postCollaborativeProposal(id);

        // Crear un documento de texto para cada Pad
        TextType padHow = model.createText("¿Cómo lo harías?");
        TextType padCost = model.createText("¿Cómo lo financiarias?");

        // Incluir el documento como parte de la wave/model en el mapa "root"
        if(createHowWave)
            model.getRoot().put("padHow", padHow);

        if(createCostWave)
            model.getRoot().put("padCost", padCost);

        // Permitir que el usuario actual lea y escriba el texto del Pad
        model.addParticipant("" + User.ID_USER + "@local.net");


    }


    public void onStartSessionFail(String message) {
        Toast.makeText(this, "Login Error: " + message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClose(boolean everythingCommitted) {
        Toast.makeText(this, "Connection closed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdate(int inFlightSize, int notAckedSize, int unCommitedSize) {
        if (inFlightSize == 0 && notAckedSize == 0 && unCommitedSize == 0)
            Toast.makeText(this, "All data sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDebugInfo(String message) {
        Toast.makeText(this, "Debug: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOpen(Model model) {
    }



}
