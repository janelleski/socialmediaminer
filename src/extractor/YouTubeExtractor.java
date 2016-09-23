package extractor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.model.Caption;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import entities.Record;
import entities.YouTubeVideo;

public class YouTubeExtractor 
{
	private static final String PROPERTIES_FILENAME = "youtube.properties";
	private static final long NUMBER_OF_VIDEOS_RETURNED = 50;
	private static YouTube youtube;
	YouTube.Search.List search;
	
	public YouTubeExtractor()
	{
		// Read the developer key from the properties file.
		Properties properties = new Properties();
		try {
			//InputStream in = Search.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
			//InputStream in = Search.class.getResourceAsStream("C://youtube/youtube.properties");
			String youtubeProperties = "youtube.apikey=AIzaSyAbJidH_pJb_QznRu8-33_WQyDYOIcFTrw";
			InputStream in = new ByteArrayInputStream(youtubeProperties.getBytes(StandardCharsets.UTF_8));
			properties.load(in);
		} catch (IOException e) {
			System.err.println("There was an error reading " + PROPERTIES_FILENAME + " : " + e.getMessage());
			System.exit(1);
		}
		
		try {
			youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
				public void initialize(HttpRequest request) throws IOException {
					
				}
			}).setApplicationName("youtube-cmdline-search-sample").build();
			
			// Define the API request for retrieving search results.
			search = youtube.search().list("id,snippet");
			
			// Set your developer key from the Google Developers Console for
			// non-authenticated requests. See: https://console.developers.google.com/
			String apiKey = properties.getProperty("youtube.apikey");
			search.setKey(apiKey);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public ArrayList<Record> search(String keyword) throws IOException, ParseException
	{
		ArrayList<Record> records = new ArrayList<Record>();
		
		
		search.setQ(keyword);
		
		// Restrict the search results to only include videos.
		search.setType("video");
		
		// To increase efficiency, only retrieve the fields that the application uses.
		//search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
		search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
		
		// Call the API and print results.
		SearchListResponse searchResponse = search.execute();
		List<SearchResult> searchResultList = searchResponse.getItems();
		
		int pageNumber = 1;
		while (searchResultList != null && !searchResultList.isEmpty()) {
			System.out.println("Searching youtube page number " + pageNumber );
			pageNumber++;
			//Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();
			
			for (SearchResult result : searchResultList)
	        //while (iteratorSearchResults.hasNext()) 
	        {
	            //SearchResult singleVideo = iteratorSearchResults.next();
	            
	            ResourceId rId = result.getId();

	            // Confirm that the result represents a video. Otherwise, the
	            // item will not contain a video ID.
	            if (rId.getKind().equals("youtube#video")) {
	                Thumbnail thumbnail = result.getSnippet().getThumbnails().getDefault();
	                
	                YouTubeVideo record = new YouTubeVideo(rId.getVideoId(), "?", result.getSnippet().getTitle(), result.getSnippet().getPublishedAt());
	               
	                Calendar cal = Calendar.getInstance();
	  			  cal.setTimeInMillis(0);
	  			  cal.set(2016, 1, 1);
	  			  Date date = cal.getTime();
	  			  
	  			 Date today = Calendar.getInstance().getTime();
	                if (record.isValid(date, today, keyword))
	                {
	                records.add(record);
	               // System.out.println(result.toString());
	                }
//	                System.out.println(i);
//	                System.out.println(" Video Id" + rId.getVideoId());
//	                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
//	                System.out.println(" By: " );
//	                System.out.println(" Thumbnail: " + thumbnail.getUrl());
//	                //System.out.println(" Pretty string: " + singleVideo.toPrettyString());
//	                System.out.println("\n-------------------------------------------------------------\n");
	            }
	        }
	        
	        if (searchResponse.getNextPageToken() == null) {
	        	System.out.println("breaking");
			    break;
			  }

			  // Prepare to request the next page of activities
			  search.setPageToken(searchResponse.getNextPageToken());

			  // Execute and process the next page request
			  searchResponse = search.execute();
			  searchResultList = searchResponse.getItems();
		}
		
		return records;
	}
	
	/**
	 * Prompt the user to enter a query term and return the user-specified term.
	 */
	private static String getInputQuery() throws IOException {
		String inputQuery = "";
		
		System.out.print("Please enter a search term: ");
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		inputQuery = bReader.readLine();
		
		if (inputQuery.length() < 1)
		{
			// Use the string "YouTube Developers Live" as a default.
			inputQuery = "YouTube Developers Live";
		}
		
		return inputQuery;
	}
	
	/**
	 * Prints out all results in the Iterator. For each result, print the 
	 * title, video ID, and thumbnail.
	 * 
	 * @param iteratorSearchResults Iterator of SearchResults to print
	 * 
	 * @param query Search query (String)
	 * @throws IOException 
	 */
	private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) throws IOException
	{
		System.out.println("\n==========================================");
		
		 System.out.println(
	                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
	        System.out.println("=============================================================\n");

	        if (!iteratorSearchResults.hasNext()) {
	            System.out.println(" There aren't any results for your query.");
	        }
	        int i = 0;
	        while (iteratorSearchResults.hasNext()) {
	        	i++;
	            SearchResult singleVideo = iteratorSearchResults.next();
	            ResourceId rId = singleVideo.getId();

	            // Confirm that the result represents a video. Otherwise, the
	            // item will not contain a video ID.
	            if (rId.getKind().equals("youtube#video")) {
	                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
	                System.out.println(i);
	                System.out.println(" Video Id" + rId.getVideoId());
	                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
	                System.out.println(" By: " );
	                System.out.println(" Thumbnail: " + thumbnail.getUrl());
	                //System.out.println(" Pretty string: " + singleVideo.toPrettyString());
	                System.out.println("\n-------------------------------------------------------------\n");
	            }
	        }
	}
}
