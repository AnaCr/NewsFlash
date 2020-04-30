package com.example.android.newsflash;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /** Private constructor so new object is never created*/
    private QueryUtils() {
    }

    /**
     * Query the Guardian API and return an ArrayList of {@link Article} objects.
     */
    public static ArrayList<Article> getArticleData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response
        // Return data as an ArrayList
        return extractArticles(jsonResponse);
    }

    /**
     * create a URL from String
     * @param stringUrl the url in string form
     * @return URL
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Problem building the URL ", exception);
        }
        return url;
    }

    /**
     * set up an HTTP connection to read the JSON response
     * @param url to the data needed
     * @return jsonResponse String
     * throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Article} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<Article> extractArticles(String articleJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding articles to
        ArrayList<Article> Articles = new ArrayList<>();

        // Try to parse the JSON response
        try {

            // convert string to JSONObject
            JSONObject baseResponse = new JSONObject(articleJSON);
            JSONObject response = baseResponse.getJSONObject("response");
            // extract results array
            JSONArray resultsArray = response.getJSONArray("results");

            // extract each article along with the title, date, section, web url, and author if any
            for (int i = 0; i < resultsArray.length(); i++){
                // get the article
                JSONObject article = resultsArray.getJSONObject(i);
                //get article title
                String title = article.getString("webTitle");
                // get article date and display in MMM dd, yyyy format
                String date = article.getString("webPublicationDate");
                SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // format it is given in
                Date dtIn = inFormat.parse(date); // parse String to Date
                SimpleDateFormat outFormat = new SimpleDateFormat("MMM dd, yyyy"); // format I want it in
                String dtOut = outFormat.format(dtIn); // format Date to String
                // get article section
                String section = article.getString("sectionName");
                // get sectionID
                String sectionID = article.getString("sectionId");
                // get article url
                String url = article.getString("webUrl");
                String authorName = "";

                // get the tags array, where you will find author
                JSONArray tags = article.getJSONArray("tags");

                // if the tags array is not empty, get the author name
                if(tags.length() > 0){
                    JSONObject author = tags.getJSONObject(0);
                    authorName = author.getString("webTitle");
                }

                // create and add an Article Object using title, date, section, url, and author
                Articles.add(new Article(title, dtOut, section, sectionID, url, authorName));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the article JSON results", e);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return Articles;
    }

    private ArrayList<Article> narrowByPreferences (ArrayList nonSortedArticles, Context context){

        // Create an empty ArrayList that we can start adding articles to
        ArrayList<Article> sortedArticles = new ArrayList<>();

        // Check what preferences are selected
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        Map prefMap = sharedPrefs.getAll();
        Set<String> keyset = prefMap.keySet();
        ArrayList<String> checkedTopics = new ArrayList<>();
        String s = Integer.toString(R.string.settings_interests_label);

        for ( String key : keyset) {
            boolean isChecked = (boolean) prefMap.get(key);
            if (!key.equals(s) && isChecked){
                checkedTopics.add(key);
            }
        }

        // Compare section in nonSortedArticles to checkedTopics
        for (int i = 0; i < nonSortedArticles.size(); i++){
            Article currentArticle = (Article) nonSortedArticles.get(i);

            String currentSection = currentArticle.getArticleSectionID();

            if(currentSection.equals(Integer.toString(R.string.settings_all_key))){

            }

        }



        return sortedArticles;
    }
}
