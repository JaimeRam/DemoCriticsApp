package com.example.zorbel.apptfg;


import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.service.GetPoliticalParties;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    //Necessary for the tabs
    private FragmentTabHost mTabHost;

    //Left Menu
    private ListView drawerListLeft;
    private String[] tagTitles;

    //Right Menu Index
    private ExpandableListView drawerListRight;
    private ExpandableListAdapter mListAdapter;
    private List<String> mListDataHeader;

    private String titleTab1;
    private String titleTab2;

    //Nav Drawer menus
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getProgramsData();

        //TABS

        titleTab1 = getString(R.string.titleTab1);
        titleTab2 = getString(R.string.titleTab2);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec(titleTab1).setIndicator(titleTab1, null),
                ProgramsFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec(titleTab2).setIndicator(titleTab2, null),
                ProposalsFragment.class, null);


        //MENU LEFT NAV DRAWER

        //Obtener arreglo de strings desde los recursos
        tagTitles = getResources().getStringArray(R.array.MenuEntries);
        //Obtener drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Obtener listview
        drawerListLeft = (ListView) findViewById(R.id.left_drawer);
        drawerListRight = (ExpandableListView) findViewById(R.id.right_drawer);

        //Fill the index right menu
        getIndexTitles(0);

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
                getSupportActionBar().setTitle(getString(R.string.app_name));
                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }

            public void onDrawerOpened(View drawerView) {
                //Acciones que se ejecutan cuando se despliega el drawer
                if (drawerLayout.isDrawerVisible(Gravity.END)) {
                    getSupportActionBar().setTitle(getString(R.string.titleIndex));
                } else {
                    getSupportActionBar().setTitle(getString(R.string.titleMenu));
                }
                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }
        };
        //Seteamos la escucha

        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        PoliticalGroups p = PoliticalGroups.getInstance();
        int i = 0;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // If the nav drawer is open, hide action items related to the content view
        for (int i = 0; i < menu.size(); i++) {

            if (drawerLayout.isDrawerOpen(drawerListLeft) || drawerLayout.isDrawerOpen(drawerListRight)) {
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

        if (id == R.id.index_action) {
            drawerLayout.openDrawer(Gravity.END);
        }


        if (drawerToggle.onOptionsItemSelected(item)) {
            // Toma los eventos de selección del toggle aquí

            if (drawerLayout.isDrawerVisible(Gravity.END)) {
                drawerLayout.closeDrawer(Gravity.END);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar el estado del drawer
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Cambiar las configuraciones del drawer si hubo modificaciones
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {

        TextView tv = (TextView) mTabHost.getTabContentView().findViewById(R.id.textFragmentTab);

        String article = getResources().getStringArray(R.array.MenuEntries)[position];

        tv.setText(article);

        /*//Reemplazar contenido
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainLayout, fragment).commit();*/

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


    private void getIndexTitles(int political_party_id) {
        // Request to the database the table of contents that matches the id of the political party.
        HashMap<String, List<String>> listDataChild = generateData();
        mListDataHeader = new ArrayList<String>();
        mListDataHeader.add("0. Preámbulo");
        mListDataHeader.add("1. Democracia, participación y transparencia");
        mListDataHeader.add("2. Economía al servicio de la ciudadanía");
        mListDataHeader.add("3. Políticas Sociales");
        mListDataHeader.add("4. Derechos civiles");
        mListDataHeader.add("5. Red, derechos de autor y cultura libre");
        mListDataHeader.add("6. Medio Ambiente");

        mListAdapter = new ExpandableListAdapter(this, mListDataHeader, listDataChild);
        drawerListRight.setAdapter(mListAdapter);
    }

    private void getProgramsData() {
        URL link = null;
        try {
            link = new URL("http://10.0.2.2/ServiceRest/public/PoliticalParty");
            //link = new URL("http://10.0.2.2/service/public/getPoliticalProgram/" + getPoliticalPartyId(politicalParty));
            //link = new URL ("http://10.0.2.2/service/public/getPoliticalProgram/1/01010000/getContent");
            GetPoliticalParties task = new GetPoliticalParties();
            //GetJSONTask task = new GetJSONTask();
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    public class GetJSONTask extends AsyncTask<URL, Void, ArrayList<Section>> {

        HttpURLConnection con;

        private static final String TAG_SECTION_TITLE = "title";
        private static final String TAG_SECTION_ID = "section";
        private static final String TAG_SECTION_TEXT = "text";

        @Override
        protected ArrayList<Section> doInBackground(URL... urls) {

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
                    Log.e(MainActivity.class.toString(), "Failed to get JSON object");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("JSON", "     :      " + builder.toString() + "  ");
            ArrayList<Section> ar = getSections(builder.toString());
            //ArrayList<Section> ar = getSectionText(builder.toString());

            Section root = new Section(0,0,null,null,null);
            createIndex(root, ar, 0);

            return ar;

        }

        @Override
        protected void onPostExecute(ArrayList<Section> s) {
            super.onPostExecute(s);


        }

        protected ArrayList<Section> getSectionText(String jsonStr) {

            ArrayList<Section> al = new ArrayList<Section>();

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    //JSONArray sections = jsonObj.getJSONArray("");

                    JSONArray sections = new JSONArray(jsonStr);


                    for (int i = 0; i < sections.length(); i++) {
                        JSONObject s = sections.getJSONObject(i);

                        String title = s.getString(TAG_SECTION_TITLE);
                        String text = s.getString(TAG_SECTION_TEXT);

                        // TODO fill in the section with the proper attributes (id_section)
                        Section sec = new Section(0, 0, title, text, null);

                        al.add(sec);
                        Log.d("JSON Text", "    :     " + al.get(i).getmTitle() + "  -----  " + al.get(i).getmText());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return al;



        }

        protected ArrayList<Section> getSections(String jsonStr) {

            ArrayList<Section> al = new ArrayList<Section>();

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    //JSONArray sections = jsonObj.getJSONArray("");

                    JSONArray sections = new JSONArray(jsonStr);


                    for (int i = 0; i < sections.length(); i++) {
                        JSONObject s = sections.getJSONObject(i);

                        String title = s.getString(TAG_SECTION_TITLE);
                        int id_section = s.getInt(TAG_SECTION_ID);

                        // TODO fill in the section with the proper attributes (id_section)
                        Section sec = new Section(id_section, 0, title, null, null);

                        al.add(sec);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return al;
        }

        protected int createIndex (Section parent, ArrayList<Section> JSONResult, int index) {

            // X = currentSection
            Section currentSection = JSONResult.get(index);

            if (parent.getlSections() == null) {
                parent.setlSections(new ArrayList<Section>());
            }
            parent.addSubSection(currentSection);
            Log.d("Index", currentSection.getmSection() + "  :  " + currentSection.getmTitle());
            //P.hijo(X) ??

            // c = nextIndex
            int nextIndex = index + 1;

            while ((nextIndex < JSONResult.size()) && (getLevel(JSONResult.get(nextIndex)) >= getLevel(currentSection))) {

                if (getLevel(JSONResult.get(nextIndex)) == getLevel(currentSection)) {

                    currentSection = JSONResult.get(nextIndex);
                    nextIndex++;
                    parent.addSubSection(currentSection);
                    Log.d("Index", currentSection.getmSection() + "  :  " + currentSection.getmTitle());
                }

                else if (getLevel(JSONResult.get(nextIndex)) > getLevel(currentSection)) {

                    nextIndex = createIndex(currentSection, JSONResult, nextIndex);

                }
            }

            return nextIndex;

        }

        protected int getLevel(Section sec) {

            int id_sec = sec.getmSection();
            int level = 0;

            if (id_sec % 100 != 0) {

                level = 4;
                return level;

            } else if (id_sec % 10000 != 0) {

                level = 3;
                return level;

            } else if (id_sec % 1000000 != 0) {

                level = 2;
                return level;

            } else {

                level = 1;
                return level;
            }

        }

    }

    private String getPoliticalPartyId(String politicalParty) {
        return "1";
    }

    private HashMap<String, List<String>> generateData() {
        HashMap<String, List<String>> listDataSection = new HashMap<String, List<String>>();

        List<String> listDataChild = new ArrayList<String>();
        listDataChild.add("- Los derechos humanos");
        listDataChild.add("- Los compromisos piratas");
        listDataChild.add("- Una Unión Europea de las personas");
        listDataSection.put("0. Preámbulo", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("1.1 Participación ciudadana y gobierno abierto");
        listDataChild.add("1.2 Calidad legislativa");
        listDataChild.add("1.3 Diversidad");
        listDataChild.add("1.4 Resiliencia");
        listDataSection.put("1. Democracia, participación y transparencia", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("2.1 Derecho al trabajo");
        listDataChild.add("2.2 Derecho a la propiedad privada y colectiva");
        listDataChild.add("2.3 Economía al servicio del bienestar social");
        listDataChild.add("2.4 Igualdad de oportunidades");
        listDataSection.put("2. Economía al servicio de la ciudadanía", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("3.1 Servicios públicos universales");
        listDataChild.add("3.2 Garantía de protección de los consumidores y usuarios");
        listDataChild.add("3.3 Igualdad de oportunidades en la educación");
        listDataChild.add("3.4 Sanidad");
        listDataSection.put("3. Políticas Sociales", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("4.1 Salud sexual y reproductiva");
        listDataChild.add("4.2 Derechos de la infancia");
        listDataChild.add("4.3 Justicia independiente, gratuita e igual para todos");
        listDataChild.add("4.4 Libertad de prensa");
        listDataSection.put("4. Derechos civiles", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("5.1 Derechos de autor");
        listDataChild.add("5.2 Software Libre, Cultura Libre y Conocimiento Libre");
        listDataChild.add("5.3 Open Access y Open Data");
        listDataChild.add("5.4 Patentes");
        listDataSection.put("5. Red, derechos de autor y cultura libre", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("6.1. Endurecimiento de la lucha contra la minería no sostenible");
        listDataChild.add("6.2. Contaminación del litoral y de las aguas");
        listDataChild.add("6.3. Lucha activa contra la aceleración del cambio climático");
        listDataChild.add("6.4. El Ártico, Santuario Global");
        listDataSection.put("6. Medio Ambiente", listDataChild);

        return listDataSection;
    }

}
