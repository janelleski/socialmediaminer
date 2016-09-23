package extractor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.ActivityFeed;
import com.google.api.services.youtube.YouTube.Search;

import entities.GPlusActivity;
import entities.Record;

public class GooglePlusExtractor 
{
	private static String PROPERTIES_FILENAME = "gplus.properties";
	
	public static ArrayList<Record> search(String keyword) throws IOException, ParseException
	{
		ArrayList<Record> records = new ArrayList<Record>();
		// Read the developer key from the properties file.
		Properties properties = new Properties();
		try {
			String googleProperties ="gplus.apikey=AIzaSyAbJidH_pJb_QznRu8-33_WQyDYOIcFTrw";
			InputStream in = new ByteArrayInputStream(googleProperties.getBytes(StandardCharsets.UTF_8));
			properties.load(in);
		} catch (IOException e) {
			System.err.println("There was an error reading " + PROPERTIES_FILENAME + " : " + e.getMessage());
			System.exit(1);
		}
		
		String apiKey = properties.getProperty("gplus.apikey");

		// Set up the main Google+ class
		Plus plus = new Plus.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
			public void initialize(HttpRequest request) throws IOException {
				
			}
		})
		    .setApplicationName("GooglePlus Extractor")
		    .build();

		
		Plus.Activities.Search searchActivities = plus.activities().search(keyword).setOrderBy("recent").setKey(apiKey);
		searchActivities.setMaxResults(20L);

		ActivityFeed activityFeed = searchActivities.execute();
		List<Activity> activities = activityFeed.getItems();

		// Loop through until we arrive at an empty page
		int pageNumber = 1;
		while (activities != null && !activities.isEmpty()) {
			System.out.println("Searching googleplus page " + pageNumber);
		  pageNumber++;

		  boolean isBefore = false;
		  for (Activity activity : activities) {
			  
			  
			  GPlusActivity ga = new GPlusActivity(activity.getId(), activity.getActor().getDisplayName(), activity.getObject().getContent(), activity.getPublished());
//		    System.out.println("ID " + activity.getId() + "\n"
//		    		+ " Content: " + activity.getObject().getContent());
//		    System.out.println("\n=======================================================");
			  
			  if (activity.getLocation() != null)
			  {
				  ga.setPostLocation(activity.getLocation().getDisplayName());
			  }
			  
			  //System.out.println("Actor: \n" + activity.getActor().toPrettyString() + "\n================================");
			  
			  Calendar cal = Calendar.getInstance();
			  cal.setTimeInMillis(0);
			  cal.set(2016, 1, 1);
			  Date date = cal.getTime();
			  
			 //Date today = Calendar.getInstance().getTime();
			  
			  if (!ga.isBefore(date))
			  {
				  isBefore = true;
				  break;
			  }
			  
			  if (ga.hasKeyword(keyword))
			  {
				  records.add(ga);
				 // System.out.println(activity.toString());
				 
			  }
			  else
			  {
				  //ga.prettyPrint();
			  }
			  
		  }

		  // We will know we are on the last page when the next page token is null.
		  // If this is the case, break.
		  if (activityFeed.getNextPageToken() == null || isBefore) {
		    break;
		  }

		  // Prepare to request the next page of activities
		  searchActivities.setPageToken(activityFeed.getNextPageToken());

		  // Execute and process the next page request
		  activityFeed = searchActivities.execute();
		  activities = activityFeed.getItems();
		}
		
		return records;
	}
}
