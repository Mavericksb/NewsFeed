package com.example.android.newsfeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by ROBERTO on 04/04/2017.
 */

public class NewsAdapter extends ArrayAdapter<News>{

    private ImageLoader imageLoader;

    public NewsAdapter(Context context, int resource, ArrayList<News> news){
        super(context, 0, news);

        //imageLoader = ImageLoader.getInstance();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        //get the current News object to extract the data
        News currentNews = getItem(position);

        //Exctracts title from News object and set it to news_item.xml correct view
        String title = currentNews.getTitle();
        TextView titleView = (TextView)listItemView.findViewById(R.id.title_view);

        titleView.setText(title);
  //      int linesLeft = 4 - titleView.getLineHeight();
        Log.e("Adapter", "lines left " + titleView.getLineHeight());
        //Exctracts title from News object and set it to news_item.xml correct view
        String subTitle = currentNews.getSubTitle();
        TextView subTitleView = (TextView)listItemView.findViewById(R.id.sub_title_view);
//        subTitleView.setMaxLines(linesLeft);
        subTitleView.setText(subTitle);


        String publishedDate = currentNews.getPublishedDate();
        TextView publishedAt = (TextView)listItemView.findViewById(R.id.author_view_view);
        publishedAt.setText(publishedDate);

        //Exctracts image from News object and set it to news_item.xml correct Imageview
        //Bitmap newsImage = currentNews.getImage();
        String newsImage = currentNews.getImage();
        ImageView imageView = (ImageView)listItemView.findViewById(R.id.image_view);
        //imageView.setImageBitmap(newsImage);

        imageLoader.clearMemoryCache();
        DisplayImageOptions options = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(15))
                .build();

        imageLoader.getInstance().displayImage(newsImage, imageView, options);

        return listItemView;
    }

}
