package com.example.android.newsfeed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by ROBERTO on 04/04/2017.
 */

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static String author = "";
    private static String title = "";
    private static String description = "";
    private static String articleUrl = "";
    private static String articleImage = "";
    private static String publishDate = "";


    public static List<News> extractNews(String stringUrl){



        //try creating an URL object from the parameter strinUrl
        URL url = createUrl(stringUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpConnection(url);
        } catch (IOException e){
            Log.e(LOG_TAG, "Error in jsonResponse " + e);
        }

        List<News> news = extractFromJson(jsonResponse);
        return news;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpConnection(URL stringUrl) throws IOException{
        String jsonResponse = null;

        if(stringUrl==null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) stringUrl.openConnection();
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error connecting " + urlConnection.getResponseCode());
            }
        }
        catch(IOException e){
                Log.e(LOG_TAG, e.getMessage());
            }

            return jsonResponse;

        }

        private static String readFromStream(InputStream inputStream) throws IOException{
            StringBuilder output = new StringBuilder();
            if(inputStream!=null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            }
            return output.toString();
        }

        private static List<News> extractFromJson(String jsonResponse){
            if (TextUtils.isEmpty(jsonResponse)) {
                return null;
            }

            List<News> news = new ArrayList<News>();
            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {


                // Parsing the jsonResponse and build up a list of News objects with the corresponding data.
                JSONObject query = new JSONObject(jsonResponse);
                JSONArray articles = query.getJSONArray("articles");
                for(int i = 0; i < articles.length(); i++ )
                {
                    JSONObject article = articles.getJSONObject(i);

                    author = article.getString("author");
                    title = article.getString("title");
                    description = article.getString("description");
                    articleUrl = article.getString("url");




                    articleImage = article.getString("urlToImage");

                    /*URL url = createUrl(articleImage);
                    Bitmap bmp = null;
                    try {
                        InputStream in = url.openStream();
                        bmp = BitmapFactory.decodeStream(in);
                        Log.e("QueryUtils", "Retrieving image");
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }*/
                    publishDate = article.getString("publishedAt");


                    news.add(new News(title, description, articleImage, publishDate ));


                }

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.

                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the list of earthquakes
            return news;
        }

}


