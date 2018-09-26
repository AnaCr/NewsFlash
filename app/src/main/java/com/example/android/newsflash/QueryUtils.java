package com.example.android.newsflash;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving data from Guardian.
 */
public final class QueryUtils {

    /** Sample JSON response for a Guardian query */
    private static final String SAMPLE_JSON_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":151,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":16,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"australia-news/2017/sep/19/qa-panellists-spar-over-coal-as-energy-debate-dominates\",\"type\":\"article\",\"sectionId\":\"australia-news\",\"sectionName\":\"Australia news\",\"webPublicationDate\":\"2017-09-18T21:38:03Z\",\"webTitle\":\"Q&A: panellists spar over coal as energy debate dominates\",\"webUrl\":\"https://www.theguardian.com/australia-news/2017/sep/19/qa-panellists-spar-over-coal-as-energy-debate-dominates\",\"apiUrl\":\"https://content.guardianapis.com/australia-news/2017/sep/19/qa-panellists-spar-over-coal-as-energy-debate-dominates\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"environment/2018/aug/29/all-praise-our-coastal-walking-heroes\",\"type\":\"article\",\"sectionId\":\"environment\",\"sectionName\":\"Environment\",\"webPublicationDate\":\"2018-08-29T16:07:20Z\",\"webTitle\":\"All praise our coastal walking heroes | Letters\",\"webUrl\":\"https://www.theguardian.com/environment/2018/aug/29/all-praise-our-coastal-walking-heroes\",\"apiUrl\":\"https://content.guardianapis.com/environment/2018/aug/29/all-praise-our-coastal-walking-heroes\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"business/2018/may/30/closure-of-vedanta-copper-plant-in-india-must-be-followed-by-a-cleanup\",\"type\":\"article\",\"sectionId\":\"business\",\"sectionName\":\"Business\",\"webPublicationDate\":\"2018-05-30T17:07:44Z\",\"webTitle\":\"Closure of Vedanta copper plant in India must be followed by a cleanup | Letters\",\"webUrl\":\"https://www.theguardian.com/business/2018/may/30/closure-of-vedanta-copper-plant-in-india-must-be-followed-by-a-cleanup\",\"apiUrl\":\"https://content.guardianapis.com/business/2018/may/30/closure-of-vedanta-copper-plant-in-india-must-be-followed-by-a-cleanup\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"environment/2018/may/11/linc-energy-fined-45m-for-pollution-amounting-to-ecological-vandalism\",\"type\":\"article\",\"sectionId\":\"environment\",\"sectionName\":\"Environment\",\"webPublicationDate\":\"2018-05-11T04:10:59Z\",\"webTitle\":\"Linc Energy fined $4.5m for pollution amounting to 'ecological vandalism'\",\"webUrl\":\"https://www.theguardian.com/environment/2018/may/11/linc-energy-fined-45m-for-pollution-amounting-to-ecological-vandalism\",\"apiUrl\":\"https://content.guardianapis.com/environment/2018/may/11/linc-energy-fined-45m-for-pollution-amounting-to-ecological-vandalism\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"uk-news/2018/jun/11/lets-replace-the-hs2-project-with-local-transport-investment\",\"type\":\"article\",\"sectionId\":\"uk-news\",\"sectionName\":\"UK news\",\"webPublicationDate\":\"2018-06-11T17:01:56Z\",\"webTitle\":\"Let’s replace the HS2 project with local transport investment | Letters\",\"webUrl\":\"https://www.theguardian.com/uk-news/2018/jun/11/lets-replace-the-hs2-project-with-local-transport-investment\",\"apiUrl\":\"https://content.guardianapis.com/uk-news/2018/jun/11/lets-replace-the-hs2-project-with-local-transport-investment\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"environment/2018/jan/03/no-cause-for-rejoicing-in-the-countryside\",\"type\":\"article\",\"sectionId\":\"environment\",\"sectionName\":\"Environment\",\"webPublicationDate\":\"2018-01-03T18:04:56Z\",\"webTitle\":\"No cause for rejoicing in the countryside | Letters\",\"webUrl\":\"https://www.theguardian.com/environment/2018/jan/03/no-cause-for-rejoicing-in-the-countryside\",\"apiUrl\":\"https://content.guardianapis.com/environment/2018/jan/03/no-cause-for-rejoicing-in-the-countryside\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"environment/2018/sep/20/air-pollution-sickens-us-in-a-car-addicted-society\",\"type\":\"article\",\"sectionId\":\"environment\",\"sectionName\":\"Environment\",\"webPublicationDate\":\"2018-09-20T16:58:52Z\",\"webTitle\":\"Air pollution sickens us in a car-addicted society | Letters\",\"webUrl\":\"https://www.theguardian.com/environment/2018/sep/20/air-pollution-sickens-us-in-a-car-addicted-society\",\"apiUrl\":\"https://content.guardianapis.com/environment/2018/sep/20/air-pollution-sickens-us-in-a-car-addicted-society\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2018/jul/09/disabled-person-plastic-straws-baby-wipes\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-07-09T10:52:27Z\",\"webTitle\":\"I rely on plastic straws and baby wipes. I’m disabled – I have no choice | Penny Pepper\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/jul/09/disabled-person-plastic-straws-baby-wipes\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/jul/09/disabled-person-plastic-straws-baby-wipes\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"environment/2018/mar/16/inaction-over-clean-air-zones-and-bottled-water-cannot-continue\",\"type\":\"article\",\"sectionId\":\"environment\",\"sectionName\":\"Environment\",\"webPublicationDate\":\"2018-03-16T16:36:52Z\",\"webTitle\":\"Inaction over clean air zones and bottled water cannot continue | Letters\",\"webUrl\":\"https://www.theguardian.com/environment/2018/mar/16/inaction-over-clean-air-zones-and-bottled-water-cannot-continue\",\"apiUrl\":\"https://content.guardianapis.com/environment/2018/mar/16/inaction-over-clean-air-zones-and-bottled-water-cannot-continue\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"environment/2018/mar/08/rising-threat-of-transport-emissions\",\"type\":\"article\",\"sectionId\":\"environment\",\"sectionName\":\"Environment\",\"webPublicationDate\":\"2018-03-08T18:30:18Z\",\"webTitle\":\"Rising threat of transport emissions | Letters\",\"webUrl\":\"https://www.theguardian.com/environment/2018/mar/08/rising-threat-of-transport-emissions\",\"apiUrl\":\"https://content.guardianapis.com/environment/2018/mar/08/rising-threat-of-transport-emissions\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}}";
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Article} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Article> extractArticles() {

        // Create an empty ArrayList that we can start adding articles to
        ArrayList<Article> Articles = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Article objects with the corresponding data.

            // convert string to JSONObject
            JSONObject sampleResponse = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONObject response = sampleResponse.getJSONObject("response");
            // extract results array
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); i++){
                JSONObject article = results.getJSONObject(i);
                String title = article.getString("webTitle");
                String date = article.getString("webPublicationDate");
                String section = article.getString("sectionName");

                // create and add an Article Object using title, date, section
                Articles.add(new Article(title, date, section));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of articles
        return Articles;
    }

}