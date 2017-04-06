package com.example.android.newsfeed;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by ROBERTO on 04/04/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String LOG_TAG = NewsLoader.class.getName();
    private String mUrl;

    public NewsLoader(Context context, String url){
        super(context);
        mUrl= url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if(mUrl==null){
            return null;
        }
        Log.e(LOG_TAG, "Loading in background args to Query utils");
        List<News> news = QueryUtils.extractNews(mUrl);

        return news;

    }
}
