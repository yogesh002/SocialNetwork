package com.parishram.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.common.collect.Lists;

public class Recommendation {

	private static YouTube youtube;
	  private static String getInputQuery() throws IOException {

	        String inputQuery = "";

	        System.out.print("Please enter a search term: ");
	        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
	        inputQuery = bReader.readLine();

	        if (inputQuery.length() < 1) {
	            // Use the string "YouTube Developers Live" as a default.
	            inputQuery = "YouTube Developers Live";
	        }
	        return inputQuery;
	    }
	  private static final long NUMBER_OF_VIDEOS_RETURNED = 10;
	  
	public static void main(String[] args) {
		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.readonly", "https://www.googleapis.com/auth/youtube.force-ssl", "https://www.googleapis.com/auth/youtube");
		Credential credential;
		try {
			credential = Auth.authorize(scopes, "recommendation");
			// This object is used to make YouTube Data API requests.
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
					.setApplicationName("youtube-recommendation").build();
			/* YouTube.Activities.List activities = youtube.activities().list("id, snippet,contentDetails");
			 activities.setPart("contentDetails, id, snippet");
			 activities.setHome(true);
			ActivityListResponse response =  activities.execute();
			List<Activity> activityList = response.getItems();
			for (Activity activity : activityList) {
				ActivityContentDetails contentDetailst = activity.getContentDetails();
				ActivityContentDetailsRecommendation recommendations = contentDetailst.getRecommendation();
			}
			 // Print information from the API response.
			System.out.println("\n================== Returned Recommendations ==================\n");*/
			
			
			String queryTerm = getInputQuery();

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            Properties properties = new Properties();
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(queryTerm);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            for (SearchResult searchResult : searchResultList) {
				System.out.println(searchResult.getSnippet().getTitle());
			}

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
