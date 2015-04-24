package com.example.zorbel.apptfg;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.service.GetComments;
import com.example.zorbel.service.PostComment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class CommentsActivity extends ActionBarActivity {

    //Left Menu
    private ListView drawerListLeft;
    private String[] tagTitles;

    //Nav Drawer menus
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private ListView listViewComments;
    private EditText editTextComment;
    private Button buttonSendComment;

    private int sectionId;
    private int politicalPartyIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        createMenus();

        listViewComments = (ListView) findViewById(R.id.listViewComments);
        editTextComment = (EditText) findViewById(R.id.editTextComment);
        buttonSendComment = (Button) findViewById(R.id.buttonSendComment);
        buttonSendComment.setEnabled(false);

        politicalPartyIndex = getIntent().getExtras().getInt("PoliticalPartyIndex");
        sectionId = getIntent().getExtras().getInt("SectionId");

        Section sec = PoliticalGroups.getInstance().getSection(politicalPartyIndex, sectionId);

        final int politicalPartyId = sec.getmPoliticalParty();

        getSectionComments(sectionId, politicalPartyId);

        buttonSendComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                URL link;

                try {
                    link = new URL(MainActivity.SERVER + "/politicalParty/" + politicalPartyId + "/section/" + sectionId + "/comment");

                    //TODO: set the user for the comment
                    PostComment task = new PostComment(CommentsActivity.this, 1, editTextComment.getText().toString(), findViewById(R.id.activityCommentsLayout));
                    task.execute(link);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                editTextComment.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextComment.getWindowToken(), 0);
            }
        });

        editTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableSubmitIfReady();
            }
        });
    }

    private void enableSubmitIfReady() {

        boolean isReady = editTextComment.getText().toString().length() > 3;

        if (isReady)
            buttonSendComment.setEnabled(true);
        else
            buttonSendComment.setEnabled(false);
    }

    public void createMenus() {

        //MENU LEFT NAV DRAWER

        //Obtener arreglo de strings desde los recursos
        tagTitles = getResources().getStringArray(R.array.MenuEntries);
        //Obtener drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Obtener listview
        drawerListLeft = (ListView) findViewById(R.id.left_drawer);

        //Nueva lista de drawer items
        ArrayList<MenuLeftItem> items = new ArrayList<MenuLeftItem>();
        items.add(new MenuLeftItem(tagTitles[0]));
        items.add(new MenuLeftItem(tagTitles[1]));
        items.add(new MenuLeftItem(tagTitles[2]));
        items.add(new MenuLeftItem(tagTitles[3]));

        // Relacionar el adaptador y la escucha de la lista del drawer
        drawerListLeft.setAdapter(new MenuLeftListAdapter(this, items));

        drawerListLeft.setOnItemClickListener(new DrawerItemClickListener());


        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                //R.mipmap.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                //Acciones que se ejecutan cuando se cierra el drawer
                getSupportActionBar().setTitle(getString(R.string.title_activity_comment));
                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }

            public void onDrawerOpened(View drawerView) {

                getSupportActionBar().setTitle(getString(R.string.titleMenu));

                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }
        };
        //Seteamos la escucha

        drawerLayout.setDrawerListener(drawerToggle);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // If the nav drawer is open, hide action items related to the content view
        for (int i = 0; i < menu.size(); i++) {

            if (drawerLayout.isDrawerOpen(drawerListLeft)) {
                menu.getItem(i).setVisible(false);
            } else {
                menu.getItem(i).setVisible(true);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            // Toma los eventos de selección del toggle aquí

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {

        if (position == 0) {  //Parties Menu

            Intent in = new Intent(this, PartiesActivity.class);
            startActivity(in);

        }

        // Se actualiza el item seleccionado y el título, después de cerrar el drawer
        drawerListLeft.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerListLeft);
    }

    private void getSectionComments(int id_section, int id_politicalParty) {
        URL link;
        try {
            link = new URL(MainActivity.SERVER + "/politicalParty/" + id_politicalParty + "/section/" + id_section + "/comment");

            GetComments task = new GetComments(this, findViewById(R.id.activityCommentsLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
