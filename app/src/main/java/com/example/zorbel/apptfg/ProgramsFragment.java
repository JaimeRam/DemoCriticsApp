package com.example.zorbel.apptfg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zorbel.service.GetTopIndex;

import java.net.MalformedURLException;
import java.net.URL;


public class ProgramsFragment extends Fragment {

    private ListView topIndexListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_programs, container, false);

        setListeners(rootView);

        getTop3Ranking(rootView);

        return rootView;

    }

    private void setListeners(View rootView) {

        //set more Views header

        topIndexListView = (ListView) rootView.findViewById(R.id.topIndexListView);

        topIndexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TopItem it = (TopItem) parent.getItemAtPosition(position);

                if (it != null) {

                    if (it.isSection()) {

                        // TODO: launch the SectionViewerActivity

                    } else {

                        TopHeaderItem header = (TopHeaderItem) it;

                        Intent in = new Intent(ProgramsFragment.this.getActivity(), Top10Activity.class);

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
            link = new URL("http://10.0.2.2/ServiceRest/public/top");

            GetTopIndex task = new GetTopIndex(this.getActivity(), rootView);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
