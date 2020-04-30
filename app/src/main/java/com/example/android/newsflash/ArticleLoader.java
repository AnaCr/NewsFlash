package com.example.android.newsflash;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.ArrayList;

public class ArticleLoader extends AsyncTaskLoader<ArrayList<Article>> {

    /** Query URL */
    private String mUrl;

    /** Create a new ArticleLoader */
    public ArticleLoader (Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of articles.
        return QueryUtils.getArticleData(mUrl);
    }
}
