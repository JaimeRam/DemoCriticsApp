package com.example.zorbel.apptfg;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TableOfContentsActivity extends ActionBarActivity {

    private ExpandableListView mIndexListView;
    private ExpandableListAdapter mListAdapter;
    private List<String> mListDataHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_of_contents);
        mIndexListView =(ExpandableListView) findViewById(R.id.expandableListView);
        getIndexTitles(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table_of_contents, menu);
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

        return super.onOptionsItemSelected(item);
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
        mIndexListView.setAdapter(mListAdapter);
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
