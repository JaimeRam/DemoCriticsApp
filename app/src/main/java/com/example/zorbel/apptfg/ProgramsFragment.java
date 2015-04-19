package com.example.zorbel.apptfg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

        setHeaders(rootView);

        getTop3Ranking(rootView);

        return rootView;

    }

    private void setHeaders(View rootView) {

        //set more Views header

        moreViewsListView = (ListView) rootView.findViewById(R.id.moreViewsListView);

        View headerViews = getActivity().getLayoutInflater().inflate(R.layout.top_ranking_header, null);

        ImageView imV = (ImageView) headerViews.findViewById(R.id.headerTopImage);
        TextView tvV = (TextView) headerViews.findViewById(R.id.headerTopText);

        imV.setImageResource(R.mipmap.ic_views_eye);
        tvV.setText(getString(R.string.name_headerMoreViews));

        moreViewsListView.addHeaderView(headerViews, null, true);


        /*//set more Likes header

        moreLikesListView = (ListView) rootView.findViewById(R.id.moreLikesListView);

        View headerLikes = getActivity().getLayoutInflater().inflate(R.layout.top_ranking_header, null);

        ImageView imL = (ImageView) headerViews.findViewById(R.id.headerTopImage);
        TextView tvL = (TextView) headerViews.findViewById(R.id.headerTopText);

        imL.setImageResource(R.mipmap.ic_greenlike);
        tvL.setText(getString(R.string.name_headerMoreLikes));

        moreLikesListView.addHeaderView(headerLikes, null, true);
*/

       /* //set more Comments header

        moreCommentsListView = (ListView) rootView.findViewById(R.id.moreCommentsListView);

        View headerComments = getActivity().getLayoutInflater().inflate(R.layout.top_ranking_header, null);

        ImageView imC = (ImageView) headerViews.findViewById(R.id.headerTopImage);
        TextView tvC = (TextView) headerViews.findViewById(R.id.headerTopText);

        imC.setImageResource(R.mipmap.ic_commentwhite);
        tvC.setText(getString(R.string.name_headerMoreComments));

        moreCommentsListView.addHeaderView(headerComments, null, true);*/

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
