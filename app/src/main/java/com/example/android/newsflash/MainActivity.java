package com.example.android.newsflash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_item_list);

        // placeholder data for articles
        ArrayList<Article> Articles= QueryUtils.extractArticles();

        // placeholder data for news articles
        /*ArrayList<String> newsArticles = new ArrayList<>();
        newsArticles.add("News 1");
        newsArticles.add("News 2");
        newsArticles.add("News3");
        newsArticles.add("News 4");
        newsArticles.add("News 5");*/

        // list the articles
        //list the songs
        ArticleAdapter adapter = new ArticleAdapter(this, Articles);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

    }
}
