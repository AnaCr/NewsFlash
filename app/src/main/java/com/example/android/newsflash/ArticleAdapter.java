package com.example.android.newsflash;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import static android.view.View.GONE;

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

        // get the current article
        Article currentArticle = getItem(position);

        // set the article's title, section, date, and author
        TextView articleTitleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        articleTitleTextView.setText(currentArticle.getArticleTitle());

        TextView articleSectionTextView = (TextView) listItemView.findViewById(R.id.section_text_view);
        articleSectionTextView.setText(currentArticle.getArticleSection());

        TextView articleDateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        articleDateTextView.setText(currentArticle.getArticleDate());

        TextView articleAuthorTextView = (TextView) listItemView.findViewById(R.id.author_text_view);

        // handle articles that do not have an author
        // hide the author and bullet view
        if(currentArticle.getArticleAuthor().isEmpty()){
            articleAuthorTextView.setVisibility(GONE);
            listItemView.findViewById(R.id.bullet_view).setVisibility(GONE);
        }else{
            articleAuthorTextView.setText(currentArticle.getArticleAuthor());
        }

        // Get the appropriate color based on the article section
        int sectionColor = getSectionColor(currentArticle.getArticleSection());

        // Set the text color
        articleSectionTextView.setTextColor(sectionColor);

        return listItemView;
    }

    private int getSectionColor(String section) {
        int sectionColorResId;
        switch (section) {
            case "Environment":
                sectionColorResId = R.color.Environment;
                break;
            case "Politics":
                sectionColorResId = R.color.Politics;
                break;
            case "Opinion":
                sectionColorResId = R.color.Opinion;
                break;
            case "Society":
                sectionColorResId = R.color.Society;
                break;
            case "Art and design":
                sectionColorResId = R.color.artAndDesign;
                break;
            case "Business":
                sectionColorResId = R.color.Business;
                break;
            default:
                sectionColorResId = R.color.colorPrimary;
                break;
        }
        return ContextCompat.getColor(getContext(), sectionColorResId);
    }
}
