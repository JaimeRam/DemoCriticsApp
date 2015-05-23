package com.example.zorbel.apptfg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.example.zorbel.apptfg.adapters.MenuLeftListAdapter;
import com.example.zorbel.apptfg.adapters.PartyWidgetAdapter;
import com.example.zorbel.apptfg.programs.CategorizedProgramsActivity;
import com.example.zorbel.apptfg.programs.PoliticalProgramIndexActivity;
import com.example.zorbel.apptfg.proposals.CategorizedProposalsActivity;
import com.example.zorbel.apptfg.proposals.NewProposalActivity;
import com.example.zorbel.apptfg.proposals.ProposalViewerActivity;
import com.example.zorbel.apptfg.views.MenuLeftItem;
import com.example.zorbel.apptfg.views.TopHeaderItem;
import com.example.zorbel.apptfg.views.TopItem;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.service_connection.GetPoliticalParties;
import com.example.zorbel.service_connection.GetTopIndex;
import com.example.zorbel.service_connection.GetTopSections;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TabPageFragment extends Fragment {

    public static final String ARG_INFOTYPE = "ARG_INFOTYPE"; // Could be 1 for programs or 2 for proposals or 3 for categories or 4 for favorites
    public static final String ARG_PAGETAB = "ARG_TOPTYPE";

    public static final String ARG_CATEGORY = "ARG_CATEGORY";
    public static final String ARG_CATEGORYLOGO = "ARG_CATEGORYLOGO";

    private int infType;
    private int pageTab;

    public static TabPageFragment newInstance(int pageTop, int infType) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGETAB, pageTop);
        args.putInt(ARG_INFOTYPE, infType);
        TabPageFragment fragment = new TabPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageTab = getArguments().getInt(ARG_PAGETAB);
        infType = getArguments().getInt(ARG_INFOTYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;

        if (infType == 1) { //Programs Activity

            if (pageTab == 7) { //Categories Tab

                view = inflater.inflate(R.layout.tab_page_categories, container, false);

                setCategoryProgramsButtonsListeners(view);


            } else if (pageTab == 1) { //Political Parties Tab

                view = inflater.inflate(R.layout.tab_page_political_parties, container, false);

                GridView gridview = (GridView) view.findViewById(R.id.gridview);

                if (PoliticalGroups.getInstance().getMlistOfPoliticalParties() == null) {

                    getPoliticalPartiesData(view);

                } else {

                    gridview.setAdapter(new PartyWidgetAdapter(getActivity(), PoliticalGroups.getInstance().getMlistOfPoliticalParties()));

                }

                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {


                        Intent in = new Intent(getActivity(), PoliticalProgramIndexActivity.class);
                        Bundle b = new Bundle();

                        b.putInt("PoliticalPartyIndex", position);
                        in.putExtras(b);

                        startActivity(in);

                    }
                });

            } else { //Top tab

                view = inflater.inflate(R.layout.tab_page_top_fragment, container, false);

                FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.btnAddNewContent);

                addButton.setVisibility(View.INVISIBLE);

                int limit = 10;
                String sLink = new String(MainActivity.SERVER + "/top/sections/");
                URL link;
                switch (pageTab) {
                    case 2: //
                        try {
                            link = new URL(sLink + "/likes/" + limit);
                            GetTopSections task = new GetTopSections(getActivity(), view);
                            task.execute(link);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3: // Like
                        break;
                    case 4: // Comments
                        break;
                    case 5: // Not understood
                        break;
                    case 6: // Dislike
                        break;
                }
            }

        } else if (infType == 2) { // PROPOSALS Activity

            if (pageTab == 7) { //Categories Tab

                view = inflater.inflate(R.layout.tab_page_categories, container, false);

                setCategoryProposalsButtonsListeners(view);

            } else { //Top Tabs

                view = inflater.inflate(R.layout.tab_page_top_fragment, container, false);

                FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.btnAddNewContent);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent in = new Intent(getActivity(), NewProposalActivity.class);
                        startActivity(in);

                    }
                });

                ListView lv = (ListView) view.findViewById(R.id.topTabPageListView);

                //TODO: set the correct list
                String[] tagTitles = view.getResources().getStringArray(R.array.MenuEntries);

                //New list of drawer items
                ArrayList<MenuLeftItem> items = new ArrayList<MenuLeftItem>();
                items.add(new MenuLeftItem("Pim", view.getResources().getDrawable(R.drawable.ic_health_cross)));
                items.add(new MenuLeftItem("Pam", view.getResources().getDrawable(R.drawable.ic_houses)));
                items.add(new MenuLeftItem("Propuesta", view.getResources().getDrawable(R.drawable.ic_culture)));
                items.add(new MenuLeftItem("Pim", view.getResources().getDrawable(R.drawable.ic_taxes)));
                items.add(new MenuLeftItem("Pam", view.getResources().getDrawable(R.drawable.ic_others)));
                items.add(new MenuLeftItem("Propuesta", view.getResources().getDrawable(R.drawable.ic_employment)));
                items.add(new MenuLeftItem("Pim", view.getResources().getDrawable(R.drawable.ic_education)));
                items.add(new MenuLeftItem("Pam", view.getResources().getDrawable(R.drawable.ic_health_cross)));
                items.add(new MenuLeftItem("Propuesta", view.getResources().getDrawable(R.drawable.ic_taxes)));
                items.add(new MenuLeftItem("Pim", view.getResources().getDrawable(R.drawable.ic_culture)));
                items.add(new MenuLeftItem("Pam", view.getResources().getDrawable(R.drawable.ic_education)));
                items.add(new MenuLeftItem("Propuesta", view.getResources().getDrawable(R.drawable.ic_others)));
                items.add(new MenuLeftItem("Pim", view.getResources().getDrawable(R.drawable.ic_houses)));
                items.add(new MenuLeftItem("Pam", view.getResources().getDrawable(R.drawable.ic_employment)));
                items.add(new MenuLeftItem("Propuesta", view.getResources().getDrawable(R.drawable.ic_taxes)));


                // Set the adapter
                lv.setAdapter(new MenuLeftListAdapter(getActivity(), items));

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //TODO: set the correct listener

                        Intent in = new Intent(getActivity(), ProposalViewerActivity.class);
                        startActivity(in);

                    }
                });
            }
        } else if (infType == 3) { // CATEGORIES Activity

            if(pageTab == 1) { //Sections Tab

                //TODO: set the list

            } else { //Proposals Tab

                //TODO: set the list

            }

        } else if (infType == 4) { // FAVORITES Activity

            if(pageTab == 1) { //Sections Tab

                //TODO: set the list

            } else { //Proposals Tab

                //TODO: set the list

            }

        }

        return view;
    }

    private void getPoliticalPartiesData(View v) { //For downloading Pol Parties data in proper Programs Tab
        URL link = null;
        try {
            link = new URL(MainActivity.SERVER + "/politicalParty");
            GetPoliticalParties task = new GetPoliticalParties(getActivity(), v.findViewById(R.id.partiesLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void setCategoryProgramsButtonsListeners(View v) {

        // SET BUTTON HEALTH LISTENER
        Button btnHealth = (Button) v.findViewById(R.id.btn_Health);

        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProgramsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryHealth));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_health_cross);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON EDUCATION LISTENER
        Button btnEducation = (Button) v.findViewById(R.id.btn_Education);

        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProgramsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryEducation));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_education);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON EMPLOYMENT LISTENER
        Button btnEmployment = (Button) v.findViewById(R.id.btn_Employment);

        btnEmployment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProgramsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryEmployment));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_employment);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON HOMES LISTENER
        Button btnHomes = (Button) v.findViewById(R.id.btn_Homes);

        btnHomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProgramsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryHomes));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_houses);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON TAXES LISTENER
        Button btnTaxes = (Button) v.findViewById(R.id.btn_Taxes);

        btnTaxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProgramsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryTaxes));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_taxes);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON CULTURE LISTENER
        Button btnCulture = (Button) v.findViewById(R.id.btn_Culture);

        btnCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProgramsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryCulture));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_culture);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON OTHERS LISTENER
        Button btnOthers = (Button) v.findViewById(R.id.btn_Others);

        btnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProgramsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryOthers));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_others);
                in.putExtras(b);
                startActivity(in);
            }
        });



    }

    private void setCategoryProposalsButtonsListeners(View v) {

        // SET BUTTON HEALTH LISTENER
        Button btnHealth = (Button) v.findViewById(R.id.btn_Health);

        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProposalsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryHealth));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_health_cross);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON EDUCATION LISTENER
        Button btnEducation = (Button) v.findViewById(R.id.btn_Education);

        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProposalsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryEducation));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_education);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON EMPLOYMENT LISTENER
        Button btnEmployment = (Button) v.findViewById(R.id.btn_Employment);

        btnEmployment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProposalsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryEmployment));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_employment);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON HOMES LISTENER
        Button btnHomes = (Button) v.findViewById(R.id.btn_Homes);

        btnHomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProposalsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryHomes));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_houses);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON TAXES LISTENER
        Button btnTaxes = (Button) v.findViewById(R.id.btn_Taxes);

        btnTaxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProposalsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryTaxes));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_taxes);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON CULTURE LISTENER
        Button btnCulture = (Button) v.findViewById(R.id.btn_Culture);

        btnCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProposalsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryCulture));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_culture);
                in.putExtras(b);
                startActivity(in);
            }
        });

        // SET BUTTON OTHERS LISTENER
        Button btnOthers = (Button) v.findViewById(R.id.btn_Others);

        btnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getActivity(), CategorizedProposalsActivity.class);

                Bundle b = new Bundle();

                b.putString(ARG_CATEGORY, getString(R.string.categoryOthers));
                b.putInt(ARG_CATEGORYLOGO, R.drawable.ic_others);
                in.putExtras(b);
                startActivity(in);
            }
        });



    }

    private void setListListeners(View v) {

        //set more Views header

        ListView topIndexListView = (ListView) v.findViewById(R.id.topTabPageListView);

        topIndexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TopItem it = (TopItem) parent.getItemAtPosition(position);

                if (it != null) {

                    if (it.isSection()) {

                        // TODO: launch the SectionViewerActivity

                        Section sec = (Section) it;

                        PoliticalParty pol = PoliticalGroups.getInstance().getPoliticalParty(sec.getmPoliticalParty());

                        if (pol.getmSectionRoot() == null) {
                            //getProgramSectionsData();
                        }

                    } else {

                        TopHeaderItem header = (TopHeaderItem) it;

                        Intent in = new Intent(getActivity(), Top10Activity.class);

                        Bundle b = new Bundle();
                        b.putString("TopURL", header.getHeaderType());

                        in.putExtras(b);

                        startActivity(in);

                    }
                }
            }
        });

    }

    private void getTop3Ranking(View rootView) {
        URL link;
        try {
            link = new URL(MainActivity.SERVER + "/top");

            GetTopIndex task = new GetTopIndex(this.getActivity(), rootView);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}