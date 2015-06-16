package com.example.zorbel.apptfg.collaborate;


import android.os.Bundle;
import android.widget.EditText;

import com.example.zorbel.apptfg.MainActivity;
import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.User;

import org.swellrt.android.service.SwellRTActivity;
import org.swellrt.android.service.WaveDocEditorBinder;
import org.swellrt.model.generic.Model;
import org.swellrt.model.generic.TextType;
import org.swellrt.model.generic.Type;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;

import java.net.MalformedURLException;

public class EditWaveActivity extends SwellRTActivity {

    private EditText mEditor;
    private String mModelId;
    private Model mModel;
    private TextType mText;

    private String padName;

    private WaveDocEditorBinder mDocBinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wave);

        mEditor = (EditText) findViewById(R.id.textWave);

        //super.getSupportActionBar().setIcon(R.mipmap.ic_collaborate);
        //super.getSupportActionBar().setDisplayShowHomeEnabled(true);

        //super.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF1919")));


    }

    public void doStartSession() {

        // Crear la wave

        // Abrir sesión con usuario genérico de la app
        try {
            getService().startSession(MainActivity.WAVE_SERVER,
                    "" + User.ID_USER + "@local.net", "password");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InvalidParticipantAddress invalidParticipantAddress) {
            invalidParticipantAddress.printStackTrace();
        }
    }

    // This method is invoked when the activity is ready to use the SwellRT service
    @Override
    public void onConnect() {

        // Here startSession, open/create the model for the Pad, get a reference to the Pad's TextType object

        doStartSession();

        // An example guessing you have already open a session and open the model in another activity
        // We receive the modelId to use in the intent
        if (mModelId == null)
            mModelId = getIntent().getStringExtra("MODEL_ID");

        padName = getIntent().getStringExtra("PAD_NAME");

        // Gets a reference to an already open Model
        mModel = getService().getModel(mModelId);

        //Add actual participant
        mModel.addParticipant("" + User.ID_USER + "@local.net");

        // Get the text document supporting the Pad from the Model
        Type instance = mModel.getRoot().get(padName);
        mText = (TextType) instance;

        // Connect the EditText component to the Wave's Doc
        mDocBinder = WaveDocEditorBinder.bind(mEditor, getService().getReadableDocument(mText));

    }

    @Override
    public void onDisconnect() {
        // The service is not available via getService()
        // Eventually unbind the EditText from the Doc
        mDocBinder.unbind();
    }

    @Override
    public void onBackPressed() {
        getService().closeModel(mModelId);
        getService().stopSession();
        super.onBackPressed();
    }
}
