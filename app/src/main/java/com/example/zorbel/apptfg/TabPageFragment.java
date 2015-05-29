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

import com.example.zorbel.apptfg.adapters.PartyWidgetAdapter;
import com.example.zorbel.apptfg.programs.CategorizedProgramsActivity;
import com.example.zorbel.apptfg.programs.PoliticalProgramIndexActivity;
import com.example.zorbel.apptfg.programs.SectionViewerActivity;
import com.example.zorbel.apptfg.proposals.CategorizedProposalsActivity;
import com.example.zorbel.apptfg.proposals.NewProposalActivity;
import com.example.zorbel.apptfg.proposals.ProposalViewerActivity;
import com.example.zorbel.apptfg.views.TopItem;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;
import com.example.zorbel.data_structures.Proposal;
import com.example.zorbel.data_structures.Section;
import com.example.zorbel.data_structures.User;
import com.example.zorbel.service_connection.GetPoliticalParties;
import com.example.zorbel.service_connection.GetTopProposals;
import com.example.zorbel.service_connection.GetTopSections;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.net.MalformedURLException;
import java.net.URL;

public class TabPageFragment extends Fragment {

    public static final String ARG_INFOTYPE = "ARG_INFOTYPE"; // Could be 1 for programs or 2 for proposals or 3 for categories or 4 for favorites
    public static final String ARG_PAGETAB = "ARG_TOPTYPE";

    public static final String ARG_CATEGORY = "ARG_CATEGORY";
    public static final String ARG_ID_CATEGORY = "ARG_ID_CATEGORY";
    public static final String ARG_CATEGORYLOGO = "ARG_CATEGORYLOGO";

    private int infType;
    private int pageTab;
    private int categoryId;

    public static TabPageFragment newInstance(int pageTop, int infType, int idCat) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGETAB, pageTop);
        args.putInt(ARG_INFOTYPE, infType);
        args.putInt(ARG_ID_CATEGORY, idCat);
        TabPageFragment fragment = new TabPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageTab = getArguments().getInt(ARG_PAGETAB);
        infType = getArguments().getInt(ARG_INFOTYPE);
        categoryId = getArguments().getInt(ARG_ID_CATEGORY);
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

                final GridView gridview = (GridView) view.findViewById(R.id.gridview);

                if (PoliticalGroups.getInstance().getMlistOfPoliticalParties() == null) {

                    getPoliticalPartiesData(view);

                } else {

                    gridview.setAdapter(new PartyWidgetAdapter(getActivity(), PoliticalGroups.getInstance().getMlistOfPoliticalParties()));

                }

                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {

                        PoliticalParty polParty = (PoliticalParty) gridview.getItemAtPosition(position);

                        Intent in = new Intent(getActivity(), PoliticalProgramIndexActivity.class);
                        Bundle b = new Bundle();

                        b.putInt("PoliticalPartyId", polParty.getmId());
                        in.putExtras(b);

                        startActivity(in);

                    }
                });

            } else { //Top tab sections

                view = inflater.inflate(R.layout.tab_page_top_fragment, container, false);

                FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.btnAddNewContent);

                addButton.setVisibility(View.INVISIBLE);

                int limit = 10;
                String sLink = "";

                switch (pageTab) {
                    case 2: // Views
                        sLink = MainActivity.SERVER + "/top/sections/views/";
                        break;
                    case 3: // Like
                        sLink = MainActivity.SERVER + "/top/sections/likes/";
                        break;
                    case 4: // Comments
                        sLink = MainActivity.SERVER + "/top/sections/comments/";
                        break;
                    case 5: // Not understood
                        sLink = MainActivity.SERVER + "/top/sections/not_understood/";
                        break;
                    case 6: // Dislike
                        sLink = MainActivity.SERVER + "/top/sections/dislikes/";
                        break;
                }

                URL link;

                try {
                    link = new URL(sLink + limit);
                    GetTopSections task = new GetTopSections(getActivity(), view);
                    task.execute(link);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                setListTopListeners(view);
            }

        } else if (infType == 2) { // PROPOSALS Activity

            if (pageTab == 2) { //Categories Tab

                view = inflater.inflate(R.layout.tab_page_categories, container, false);

                setCategoryProposalsButtonsListeners(view);

            } else { //Top Tabs

                int limit = 10;
                String sLink = "";

                if (categoryId == 0) { //Not categorized tabs

                    view = inflater.inflate(R.layout.tab_page_top_fragment, container, false);

                    FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.btnAddNewContent);

                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().finish();
                            Intent in = new Intent(getActivity(), NewProposalActivity.class);
                            startActivity(in);
                        }
                    });

                    switch (pageTab) {
                        case 1: // My Proposals
                            sLink = MainActivity.SERVER + "/proposal/user/" + User.ID_USER;
                            break;
                        case 3: // Date
                            sLink = MainActivity.SERVER + "/top/proposals/date/" + limit;
                            break;
                        case 4: // Views
                            sLink = MainActivity.SERVER + "/top/proposals/views/" + limit;
                            break;
                        case 5: // Like
                            sLink = MainActivity.SERVER + "/top/proposals/likes/" + limit;
                            break;
                        case 6: // Comments
                            sLink = MainActivity.SERVER + "/top/proposals/comments/" + limit;
                            break;
                        case 7: // Not understood
                            sLink = MainActivity.SERVER + "/top/proposals/not_understood/" + limit;
                            break;
                        case 8: // Dislike
                            sLink = MainActivity.SERVER + "/top/proposals/dislikes/" + limit;
                            break;
                    }

                } else { // categorized tabs

                    view = inflater.inflate(R.layout.tab_page_top_fragment, container, false);

                    FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.btnAddNewContent);

                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent in = new Intent(getActivity(), NewProposalActivity.class);
                            startActivity(in);

                        }
                    });

                    switch (pageTab) {
                        case 1: // Date
                            sLink = MainActivity.SERVER + "/category/" + categoryId + "/proposal/date/" + limit;
                            break;
                        case 2: // Views
                            sLink = MainActivity.SERVER + "/category/" + categoryId + "/proposal/views/" + limit;
                            break;
                        case 3: // Like
                            sLink = MainActivity.SERVER + "/category/" + categoryId + "/proposal/likes/" + limit;
                            break;
                        case 4: // Comments
                            sLink = MainActivity.SERVER + "/category/" + categoryId + "/proposal/comments/" + limit;
                            break;
                        case 5: // Not understood
                            sLink = MainActivity.SERVER + "/category/" + categoryId + "/proposal/not_understood/" + limit;
                            break;
                        case 6: // Dislike
                            sLink = MainActivity.SERVER + "/category/" + categoryId + "/proposal/dislikes/" + limit;
                            break;
                    }

                }

                URL link;

                try {
                    link = new URL(sLink);
                    GetTopProposals task = new GetTopProposals(getActivity(), view);
                    task.execute(link);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                setListTopListeners(view);
            }

        } else if (infType == 3) { // CATEGORIES Activity

            if(pageTab == 1) { //Sections Tab

                view = inflater.inflate(R.layout.tab_page_top_fragment, container, false);

                FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.btnAddNewContent);

                addButton.setVisibility(View.INVISIBLE);

                getSectionsData(view, categoryId);

                setListTopListeners(view);

            } else { //Proposals Tab

                view = inflater.inflate(R.layout.tab_page_top_fragment, container, false);

                FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.btnAddNewContent);

                addButton.setVisibility(View.INVISIBLE);

                getProposalsData(view, categoryId);

                setListTopListeners(view);


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
        URL link;
        try {
            link = new URL(MainActivity.SERVER + "/politicalParty");
            GetPoliticalParties task = new GetPoliticalParties(getActivity(), v.findViewById(R.id.partiesLayout));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void getSectionsData(View v, int id_category) {
        int limit = 10;
        URL link;

        try {
            link = new URL(MainActivity.SERVER + "/category/" + id_category + "/section/" + limit);
            GetTopSections task = new GetTopSections(getActivity(), v.findViewById(R.id.layoutTabTop));
            task.execute(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void getProposalsData(View v, int id_category) {
        int limit = 10;
        URL link;

        try {
            link = new URL(MainActivity.SERVER + "/category/" + id_category + "/proposal/" + limit);
            GetTopProposals task = new GetTopProposals(getActivity(), v.findViewById(R.id.layoutTabTop));
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
                b.putInt(ARG_ID_CATEGORY, 1);
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
                b.putInt(ARG_ID_CATEGORY, 2);
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
                b.putInt(ARG_ID_CATEGORY, 3);
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
                b.putInt(ARG_ID_CATEGORY, 4);
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
                b.putInt(ARG_ID_CATEGORY, 5);
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
                b.putInt(ARG_ID_CATEGORY, 6);
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
                b.putInt(ARG_ID_CATEGORY, 7);
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
                b.putInt(ARG_ID_CATEGORY, 1);
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
                b.putInt(ARG_ID_CATEGORY, 2);
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
                b.putInt(ARG_ID_CATEGORY, 3);
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
                b.putInt(ARG_ID_CATEGORY, 4);
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
                b.putInt(ARG_ID_CATEGORY, 5);
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
                b.putInt(ARG_ID_CATEGORY, 6);
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
                b.putInt(ARG_ID_CATEGORY, 7);
                in.putExtras(b);
                startActivity(in);
            }
        });



    }

    private void setListTopListeners(final View v) {

        //set more Views header

        ListView topListView = (ListView) v.findViewById(R.id.topListView);

        topListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TopItem it = (TopItem) parent.getItemAtPosition(position);

                if (it != null) {

                    if (it.isSection()) {

                        // TODO: launch the SectionViewerActivity

                        Section sec = (Section) it;

                        PoliticalParty pol = PoliticalGroups.getInstance().getPoliticalParty(sec.getmPoliticalParty());

                        Intent in = new Intent(getActivity(), SectionViewerActivity.class);

                        Bundle b = new Bundle();
                        b.putInt("PoliticalPartyId", pol.getmId());
                        b.putInt("SectionId", sec.getmSection());

                        in.putExtras(b);

                        startActivity(in);

                    } else if (it.isProposal()) {

                        Proposal prop = (Proposal) it;

                        Intent in = new Intent(getActivity(), ProposalViewerActivity.class);

                        Bundle b = new Bundle();

                        b.putInt("ProposalId", prop.getPropId());
                        in.putExtras(b);

                        startActivity(in);

                    }
                }
            }
        });

    }

}