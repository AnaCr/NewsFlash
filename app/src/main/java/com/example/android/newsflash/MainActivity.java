package com.example.android.newsflash;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Article>> {

    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?q=business&tag=environment/pollution&from-date=2014-01-01&show-tags=contributor&api-key="
                    + BuildConfig.GuardianAPIKEY;

    // Constant value for the loader ID.
    private static final int ARTICLE_LOADER_ID = 1;

    // Adapter for the list of articles
    private ArticleAdapter mAdapter;

    // empty text view
    TextView emptyTextView;

    // loading spinner
    ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_item_list);

        emptyTextView = findViewById(R.id.empty_textview);
        loadingSpinner = findViewById(R.id.loading_spinner);

        // Check network connectivity & show appropriate info
        if(getActiveNetworkInfo()){
            updateUi(new ArrayList<Article>());

            // Get a reference to the LoaderManager, in order to interact with loaders.
            getSupportLoaderManager().initLoader(ARTICLE_LOADER_ID, null, this);
        }else{
            // no internet
            emptyTextView.setText(R.string.noInternet);
            loadingSpinner.setVisibility(View.GONE);
        }

    }

    public boolean getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @NonNull
    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new ArticleLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Article>> loader, ArrayList<Article> articles) {
        // Clear the adapter of previous data
        mAdapter.clear();

        // If there is a valid list of articles, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }

        emptyTextView.setText(R.string.noArticles);
        loadingSpinner.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Article>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    /**
     * Update the screen to display information from the given {@link Article}.
     */
    private void updateUi(ArrayList Articles) {

        // list the articles
        mAdapter = new ArticleAdapter(this, Articles);
        ListView listView = findViewById(R.id.list);
        listView.setEmptyView(emptyTextView);

        listView.setAdapter(mAdapter);

        // start a browser intent to the article's web url when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Find the article that was clicked
                Article currentArticle = mAdapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri articleUri = Uri.parse(currentArticle.getArticleURL());

                // Create intent and launch activity
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, articleUri);
                startActivity(browserIntent);
            }
        });
    }

    @Override
    // This method initializes the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
