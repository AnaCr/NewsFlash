package com.example.android.newsflash;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Activity context, ArrayList<Article> Articles) {
        super(context, 0, Articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_article, parent, false);
        }

        Article currentArticle = getItem(position);

        TextView articleTitleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        articleTitleTextView.setText(currentArticle.getArticleTitle());

        TextView articleSectionTextView = (TextView) listItemView.findViewById(R.id.section_text_view);
        articleSectionTextView.setText(currentArticle.getArticleSection());

        TextView articleDateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        articleDateTextView.setText(currentArticle.getArticleDate());

        return listItemView;
    }
}
