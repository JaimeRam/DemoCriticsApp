package com.example.zorbel.apptfg;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.service.GetTop10;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Top10Activity extends ActionBarActivity {

    //Left Menu
    private ListView drawerListLeft;
    private String[] tagTitles;

    //Nav Drawer menus
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private ListView top10ListView;

    private static final String TAG_VIEWS = "views";
    private static final String TAG_LIKES = "likes";
    private static final String TAG_DISLIKES = "dislikes";
    private static final String TAG_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_COMMENTS = "comments";

    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        url = getIntent().getExtras().getString("TopURL");

        this.top10ListView = (ListView) findViewById(R.id.top10ListView);

        View headerViews = getLayoutInflater().inflate(R.layout.top_ranking_header, null);

        ImageView imV = (ImageView) headerViews.findViewById(R.id.headerTopImage);
        TextView tvV = (TextView) headerViews.findViewById(R.id.headerTopText);

        switch (url) {

            case TAG_VIEWS:
                imV.setImageResource(R.mipmap.ic_views_eye);
                tvV.setText(getString(R.string.name_headerMoreViews));
                break;


            case TAG_LIKES:
                imV.setImageResource(R.mipmap.ic_greenlike);
                tvV.setText(getString(R.string.name_headerMoreLikes));
                break;

            case TAG_DISLIKES:
                imV.setImageResource(R.mipmap.ic_reddislike);
                tvV.setText(getString(R.string.name_headerMoreDislikes));
                break;

            case TAG_NOT_UNDERSTOOD:
                imV.setImageResource(R.mipmap.ic_bluenotunderstood);
                tvV.setText(getString(R.string.name_headerMoreNotUnderstood));
                break;

            case TAG_COMMENTS:
                imV.setImageResource(R.mipmap.ic_commentwhite);
                tvV.setText(getString(R.string.name_headerMoreComments));
                break;

            default: break;

        }

        top10ListView.addHeaderView(headerViews, null, true);

        createMenus();

        getTop10();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
                getSupportActionBar().setTitle(getString(R.string.title_activity_political_program_index));
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
        getMenuInflater().inflate(R.menu.menu_top10, menu);
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void getTop10() {

        URL link;
        try {
            link = new URL("http://10.0.2.2/ServiceRest/public/top/" + url);

            GetTop10 task = new GetTop10(this, findViewById(R.id.layoutTop10));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
