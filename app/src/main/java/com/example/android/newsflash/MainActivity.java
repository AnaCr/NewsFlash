package com.example.android.newsflash;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class MainActivity extends AppCompatActivity {

    /** URL to query the Guardian for articles */
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?q=environment&from-date=2014-01-01&api-key=00eb36ac-685a-4a5e-9bde-56bd6807f09b&show-tags=contributor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_item_list);

        // placeholder data for articles
        new getArticlesArray().execute();
    }

    /**
     * Update the screen to display information from the given {@link Article}.
     */
    private void updateUi(ArrayList Articles) {

        // list the articles
        final ArticleAdapter adapter = new ArticleAdapter(this, Articles);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Find the article that was clicked
                Article currentArticle = adapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri articleUri = Uri.parse(currentArticle.getArticleURL());

                // Create intent and launch activity
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, articleUri);
                startActivity(browserIntent);
            }
        });
    }

    private class getArticlesArray extends AsyncTask<URL, Void, ArrayList>{
        @Override
        protected ArrayList<Article> doInBackground(URL... urls){
            String JSONResponse = "";
            try{
                JSONResponse = makeHttpRequest(createUrl(GUARDIAN_REQUEST_URL));
            }
            catch(IOException e){
                return null;
            }
            ArrayList<Article> ArticlesArray = extractArticles(JSONResponse);
            return ArticlesArray;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link getArticlesArray}).
         */
        @Override
        protected void onPostExecute(ArrayList articlesArray) {
            if (articlesArray == null) {
                return;
            }

            updateUi(articlesArray);
        }

    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            return null;
        }
        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
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
    public static ArrayList<Article> extractArticles(String articleJSON) {

        // Create an empty ArrayList that we can start adding articles to
        ArrayList<Article> Articles = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // convert string to JSONObject
            JSONObject baseResponse = new JSONObject(articleJSON);
            JSONObject response = baseResponse.getJSONObject("response");
            // extract results array
            JSONArray resultsArray = response.getJSONArray("results");
            Log.i("results Array length", Integer.toString(resultsArray.length())); //10

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
                    // get article url
                    String url = article.getString("webUrl");
                    String authorName = "";

                    // get the tags array, where you will find author
                    JSONArray tags = article.getJSONArray("tags");

                    if(tags.length() > 0){
                        JSONObject author = tags.getJSONObject(0);
                        authorName = author.getString("webTitle");
                    }

                    //return new Article(title, dtOut, section, url);

                    // create and add an Article Object using title, date, section
                    Articles.add(new Article(title, dtOut, section, url, authorName));
                }

                Log.i("Articles Array length", Integer.toString(Articles.size()));
                return Articles;

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("MainActivity", "Problem parsing the article JSON results", e);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Return the list of articles
        // return Articles;
        return null;
    }
}
