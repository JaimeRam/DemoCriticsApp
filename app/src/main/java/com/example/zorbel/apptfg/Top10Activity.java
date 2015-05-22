package com.example.zorbel.apptfg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zorbel.service_connection.GetTop10;

import java.net.MalformedURLException;
import java.net.URL;


public class Top10Activity extends MenuActivity {

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

        super.setMenus(findViewById(R.id.drawer_layout), 0);

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

        getTop10();

    }

    public void getTop10() {

        URL link;
        try {
            link = new URL(MainActivity.SERVER + "/top/" + url);

            GetTop10 task = new GetTop10(this, findViewById(R.id.layoutTop10));
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
