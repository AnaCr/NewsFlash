package com.example.android.newsflash;

public class Article {

    // article title
    private String mArticleTitle;

    // article date
    private String mArticleDate;

    // article author
    private String mArticleSection;


    /**
     * Create a new Song object.
     *
     * @param articleTitle is the article title
     * @param articleDate is the date of publication
     * @param articleSection is the section
     */
    public Article (String articleTitle, String articleDate, String articleSection) {
        mArticleTitle = articleTitle;
        mArticleDate = articleDate;
        mArticleSection = articleSection;
    }

    // get the article title
    public String getArticleTitle() {
        return mArticleTitle;
    }

    // get the date of publication
    public String getArticleDate() {
        return mArticleDate;
    }

    // get the author of the article
    public String getArticleSection() {
        return mArticleSection;
    }

}
