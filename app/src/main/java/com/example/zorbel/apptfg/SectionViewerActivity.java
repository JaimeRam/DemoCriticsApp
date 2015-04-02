package com.example.zorbel.apptfg;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.Section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SectionViewerActivity extends ActionBarActivity {

    //Left Menu
    private ListView drawerListLeft;
    private String[] tagTitles;

    //Right Menu Index
    private ExpandableListView drawerListRight;
    private ExpandableIndexAdapter mRightIndexAdapter;

    //Nav Drawer menus
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;


    private TextView sectionTitle;
    private TextView sectionText;

    private Button commentButton;
    //private TextView numComments;
    private Button likeButton;
    //private TextView numLikes;
    private Button notUnderstoodButton;
    //private TextView numNotUnderstood;
    private Button dislikeButton;
    //private TextView numDislikes;

    private Section currentSection;
    private int politicalPartyGroupIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_viewer);

        sectionTitle = (TextView) findViewById(R.id.sectionTitle);
        sectionText = (TextView) findViewById(R.id.textSection);

        //REMOVE
        sectionTitle.setText("1.1 Este es el titulo de la seccion");
        sectionText.setText("Programa de inversiones y políticas públicas para la reactivación económica,\n" +
                "la creación de empleo de calidad y la reconversión del modelo productivo\n" +
                "hacia una economía basada en la innovación que contribuya al bien común\n" +
                "teniendo en cuenta criterios de responsabilidad social, ética y medioambiental.\n" +
                "Promoción del protagonismo de la pequeña y mediana empresa en la\n" +
                "creación de empleo, resaltando el papel de las entidades de la economía\n" +
                "social. Política de contratación pública favorable a la pequeña y mediana\n" +
                "empresa que incluya cláusulas sociales en la adjudicación de los contratos.\n" +
                "Reducción de la jornada laboral a 35 horas semanales y de la edad de\n" +
                "jubilación a 60 años, como mecanismos para redistribuir equitativamente el\n" +
                "trabajo y la riqueza, favoreciendo la conciliación familiar. Prohibición de los\n" +
                "despidos en empresas con beneficios. Derogación de las reformas laborales\n" +
                "implantadas desde el estallido de la crisis: 2010, 2012 y RD 3/2014.\n" +
                "Establecimiento de mecanismos para combatir la precarización del empleo,\n" +
                "especialmente en el empleo joven para contrarrestar el exilio juvenil.\n" +
                "Eliminación de las Empresas de Trabajo Temporal. Incremento significativo del\n" +
                "salario mínimo interprofesional y establecimiento de un salario máximo\n" +
                "vinculado proporcionalmente al salario mínimo interprofesional . Derecho a\n" +
                "disfrutar de una pensión pública no contributiva, de calidad y que garantice\n" +
                "una vida decente tras la jubilación,su cuantía igualará como mínimo el salario\n" +
                "mínimo interprofesional. Derogación de la última reforma de las pensiones y\n" +
                "prohibición de la privatización o recortes del sistema público de pensiones.\n" +
                "\n" +
                "Establecimiento de políticas redistributivas para la reducción de la\n" +
                "desigualdad social en el marco nacional y comunitario. Convergencia del\n" +
                "gasto social sobre el PIB respecto al promedio de la Unión.");

        commentButton = (Button) findViewById(R.id.buttonComment);
        likeButton = (Button) findViewById(R.id.buttonLike);
        notUnderstoodButton = (Button) findViewById(R.id.buttonNotUnderstood);
        dislikeButton  = (Button) findViewById(R.id.buttonDislike);

        setMenus();

        //createRightIndex();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void setMenus() {

        //MENU LEFT NAV DRAWER

        tagTitles = getResources().getStringArray(R.array.MenuEntries);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerListLeft = (ListView) findViewById(R.id.left_drawer);

        drawerListRight = (ExpandableListView) findViewById(R.id.right_drawer);

        //Fill the index right menu
        //getIndexTitles(0);

        //Nueva lista de drawer items
        ArrayList<MenuLeftItem> items = new ArrayList<MenuLeftItem>();
        items.add(new MenuLeftItem(tagTitles[0]));
        items.add(new MenuLeftItem(tagTitles[1]));
        items.add(new MenuLeftItem(tagTitles[2]));
        items.add(new MenuLeftItem(tagTitles[3]));


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

                getSupportActionBar().setTitle(getString(R.string.app_name));
                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }

            public void onDrawerOpened(View drawerView) {

                if (drawerLayout.isDrawerVisible(Gravity.END)) {
                    getSupportActionBar().setTitle(getString(R.string.titleIndex));
                } else {
                    getSupportActionBar().setTitle(getString(R.string.titleMenu));
                }
                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);


    }

    public void createRightIndex() {

        List<Section> headers = PoliticalGroups.getInstance().getMlistOfPoliticalParties().get(politicalPartyGroupIndex).getmSectionRoot().getlSections();
        HashMap<Section, List<Section>> listDataChild = generateSubSections(headers);

        mRightIndexAdapter = new ExpandableIndexAdapter(this, headers, listDataChild);
        drawerListRight.setAdapter(mRightIndexAdapter);


    }

    private HashMap<Section, List<Section>> generateSubSections(List<Section> list) {

        HashMap<Section, List<Section>> listSubSections = new HashMap<Section, List<Section>>();

        for (int i = 0; i < list.size(); i++) {

            List<Section> listDataChild = list.get(i).getlSections();
            listSubSections.put(list.get(i), listDataChild);
        }

        return listSubSections;

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
        getMenuInflater().inflate(R.menu.menu_section_viewer, menu);
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

}
