package com.example.android.newsflash;

public class Article {

    // article title
    private String mArticleTitle;

    // article date
    private String mArticleDate;

    // article section
    private String mArticleSection;

    // article section id
    private String mArticleSectionID;

    // article url
    private String mArticleURL;

    // article author
    private String mArticleAuthor;

    /**
     * Create a new Song object.
     *
     * @param articleTitle is the article title
     * @param articleDate is the date of publication
     * @param articleSection is the section
     * @param articleSectionID is the ID of the section
     * @param articleURL is the url to view article online
     * @param articleAuthor is the author
     */
    public Article (String articleTitle, String articleDate, String articleSection, String articleSectionID,
                    String articleURL, String articleAuthor) {
        mArticleTitle = articleTitle;
        mArticleDate = articleDate;
        mArticleSection = articleSection;
        mArticleSectionID = articleSectionID;
        mArticleURL = articleURL;
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

    // get the section of the article
    public String getArticleSection() {
        return mArticleSection;
    }

    // get the ID of the section
    public String getArticleSectionID() {
        return  mArticleSectionID;
    }

    // get article url
    public String getArticleURL(){
        return mArticleURL;
    }

    // get the author of the article
    public String getArticleAuthor() {
        return mArticleAuthor;
    }

}
