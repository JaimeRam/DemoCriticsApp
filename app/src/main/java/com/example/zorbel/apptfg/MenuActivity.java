package com.example.zorbel.apptfg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.apptfg.adapters.MenuLeftListAdapter;
import com.example.zorbel.apptfg.categories.CategoriesListActivity;
import com.example.zorbel.apptfg.collaborate.CollaborativeProposalsActivity;
import com.example.zorbel.apptfg.programs.ProgramsActivity;
import com.example.zorbel.apptfg.proposals.ProposalsActivity;
import com.example.zorbel.apptfg.views.MenuLeftItem;
import com.example.zorbel.data_structures.User;
import com.example.zorbel.service_connection.PostNameUser;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MenuActivity extends ActionBarActivity {

    //Left Menu
    private ListView drawerListLeft;
    private String[] tagTitles;

    //Nav Drawer menus
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private View headerView;
    private FloatingActionButton userButton;
    private TextView userName;

    private int currentMenuPosition; //current section of the application, so if the user presses it the app doesn't reloads the same activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void setMenus(View v, int menuOption) {

        currentMenuPosition = menuOption;

        //MENU LEFT NAV DRAWER

        tagTitles = v.getResources().getStringArray(R.array.MenuEntries);

        drawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);

        drawerListLeft = (ListView) v.findViewById(R.id.left_drawer);

        //ListView Header
        headerView = getLayoutInflater().inflate(R.layout.menu_left_header, drawerListLeft, false);
        drawerListLeft.addHeaderView(headerView);

        //New list of drawer items
        ArrayList<MenuLeftItem> items = new ArrayList<MenuLeftItem>();
        items.add(new MenuLeftItem(tagTitles[0], v.getResources().getDrawable(R.mipmap.ic_home_icon_blue)));
        items.add(new MenuLeftItem(tagTitles[1], v.getResources().getDrawable(R.mipmap.ic_programs)));
        items.add(new MenuLeftItem(tagTitles[2], v.getResources().getDrawable(R.mipmap.ic_topics)));
        items.add(new MenuLeftItem(tagTitles[3], v.getResources().getDrawable(R.mipmap.ic_proposals)));
        items.add(new MenuLeftItem(tagTitles[4], v.getResources().getDrawable(R.mipmap.ic_collaborate)));
        items.add(new MenuLeftItem(tagTitles[5], v.getResources().getDrawable(R.mipmap.ic_starfav_yellow)));


        // Set the adapter
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
                getSupportActionBar().setTitle(getString(R.string.app_name));
                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }

            public void onDrawerOpened(View drawerView) {
                //Acciones que se ejecutan cuando se despliega el drawer

                getSupportActionBar().setTitle(getString(R.string.titleMenu));

                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }
        };


        drawerLayout.setDrawerListener(drawerToggle);

        userName = (TextView) headerView.findViewById(R.id.userNameMenu);
        userName.setText(User.NICKNAME);

        userButton = (FloatingActionButton) headerView.findViewById(R.id.userButtonMenu);

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showUserDialog();
            }
        });

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        // Cambiar las configuraciones del drawer si hubo modificaciones
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {

        if (position != currentMenuPosition) {

            if (position == 1) { //Home Menu Option

                Intent in = new Intent(this, MainActivity.class);
                startActivity(in);

            } else if (position == 2) {  //Political Parties Menu Option

                Intent in = new Intent(this, ProgramsActivity.class);
                startActivity(in);

            } else if (position == 3) {  //Comparatives Menu Option

                Intent in = new Intent(this, CategoriesListActivity.class);
                startActivity(in);

            } else if (position == 4) {  //Proposals Menu Option

                Intent in = new Intent(this, ProposalsActivity.class);
                startActivity(in);

            } else if (position == 5) {  //Colaborative Proposals Menu Option

                Intent in = new Intent(this, CollaborativeProposalsActivity.class);
                in.putExtra("FocusTab", 2);
                startActivity(in);

            } else if (position == 6) {  //Favourites Menu Option

                Intent in = new Intent(this, FavoritesActivity.class);
                startActivity(in);

            }
        }

        drawerListLeft.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerListLeft);
    }

    private void showUserDialog() {

        // Set an EditText view to get user input
        final EditText userInput = new EditText(this);
        userInput.setTextColor(Color.WHITE);
        userInput.setGravity(Gravity.CENTER);

        AlertDialog dialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle(getResources().getString(R.string.userDialogTitle))
                .setMessage(getResources().getString(R.string.userDialogText))
                .setView(userInput)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        User.NICKNAME = userInput.getText().toString();
                        userName.setText(User.NICKNAME);
                        setUserName();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(R.mipmap.ic_action_person)
                .show();


    }

    private void setUserName() {
        URL link;

        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("id_user", User.ID_USER));
        params.add(new BasicNameValuePair("new_nickname", User.NICKNAME));

        try {
            link = new URL(MainActivity.SERVER + "/user");
            PostNameUser task = new PostNameUser(this, null, params);
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

    public boolean isNetworkAvailable() {

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            if (netInfo == null || !netInfo.isConnectedOrConnecting()) {

                AlertDialog dialog = new AlertDialog.Builder(MenuActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setTitle(getResources().getString(R.string.title_dialogNoConnection))
                        .setMessage(getResources().getString(R.string.text_dialogNoConnection))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.mipmap.ic_action_person)
                        .show();

            }

            return netInfo != null && netInfo.isConnectedOrConnecting();

    }

}