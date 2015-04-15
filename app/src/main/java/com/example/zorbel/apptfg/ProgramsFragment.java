package com.example.zorbel.apptfg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.zorbel.service.GetSectionContent;
import com.example.zorbel.service.GetTopIndex;

import java.net.MalformedURLException;
import java.net.URL;


public class ProgramsFragment extends Fragment {

    private ListView moreViewsListView;
    private ListView moreLikesListView;
    private ListView moreNotUnderstoodListView;
    private ListView moreDislikesListView;
    private ListView moreCommentsListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_programs, container, false);

        getTop3Ranking(rootView);

        return rootView;

    }

    private void getTop3Ranking(View rootView) {
        URL link = null;
        try {
            link = new URL("http://10.0.2.2/ServiceRest/public/top");

            GetTopIndex task = new GetTopIndex(this.getActivity(), rootView);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
