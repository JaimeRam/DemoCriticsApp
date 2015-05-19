package com.example.zorbel.apptfg;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MenuActivity extends ActionBarActivity {

    //Left Menu
    private ListView drawerListLeft;
    private String[] tagTitles;

    //Nav Drawer menus
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private int currentMenuPosition;

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
        View header = getLayoutInflater().inflate(R.layout.menu_left_header, drawerListLeft, false);
        drawerListLeft.addHeaderView(header);

        //New list of drawer items
        ArrayList<MenuLeftItem> items = new ArrayList<MenuLeftItem>();
        items.add(new MenuLeftItem(tagTitles[0], v.getResources().getDrawable(R.mipmap.ic_home_icon_blue)));
        items.add(new MenuLeftItem(tagTitles[1]));
        items.add(new MenuLeftItem(tagTitles[2]));
        items.add(new MenuLeftItem(tagTitles[3]));
        items.add(new MenuLeftItem(tagTitles[4]));
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

                Intent in = new Intent(this, PartiesActivity.class);
                startActivity(in);

            } else if (position == 3) {  //Comparatives Menu Option

               //TODO: launch the activity

            } else if (position == 4) {  //Proposals Menu Option

                //TODO: launch the activity

            } else if (position == 5) {  //Polls Menu Option

                //TODO: launch the activity

            } else if (position == 6) {  //Favourites Menu Option

                //TODO: launch the activity

            }
        }

        drawerListLeft.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerListLeft);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

}