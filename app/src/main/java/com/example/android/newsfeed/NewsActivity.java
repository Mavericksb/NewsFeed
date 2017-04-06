package com.example.android.newsfeed;


import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager.LoaderCallbacks;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    private static NewsAdapter mNewsAdapter;
    private static final int NEWS_LOADER = 1;
    private static final String SOURCE_URL = "https://newsapi.org/v1/articles?source=the-guardian-uk&sortBy=latest&apiKey=49516c84a31946f69bbf3b5cd7b579ba";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER, null, this);


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();


        if(ImageLoader.getInstance()!=null) {
            ImageLoader.getInstance().init(config);
        }

        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();


        TextView emptyView = (TextView)findViewById(R.id.empty_view);

        //create and apply List View object in news_activity.xml
        ListView newsList = (ListView)findViewById(R.id.list_view);

        newsList.setEmptyView(emptyView);

        mNewsAdapter = new NewsAdapter(this, 0,  new ArrayList<News>());

        newsList.setAdapter(mNewsAdapter);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //select clicked news


                News currentNews = (News)adapterView.getItemAtPosition(i);
                String url = currentNews.getUrl();


                Intent webLoad = new Intent(NewsActivity.this, NewsBrowse.class );
                webLoad.putExtra("url", url);
                startActivity(webLoad);

            }
        });
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, SOURCE_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mNewsAdapter.clear();

        Log.e("News Activity", "onLoadFinished just cleared the Adapter");

        if(news!=null && !news.isEmpty()){
            mNewsAdapter.addAll(news);
            Log.e("News Activity", "onLoadFinished trying to popoluate Adapter");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNewsAdapter.clear();
    }
}
