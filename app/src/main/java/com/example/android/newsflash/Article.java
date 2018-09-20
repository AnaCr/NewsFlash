package com.example.android.newsflash;

public class Article {

    // article title
    private String mArticleTitle;

    // article date
    private String mArticleDate;

    // article author
    private String mArticleAuthor;


    /**
     * Create a new Song object.
     *
     * @param articleTitle is the article title
     * @param articleDate is the date of publication
     * @param articleAuthor is the author
     */
    public Article (String articleTitle, String articleDate, String articleAuthor) {
        mArticleTitle = articleTitle;
        mArticleDate = articleDate;
        mArticleAuthor = articleAuthor;
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
    public String getArticleAuthor() {
        return mArticleAuthor;
    }

}
