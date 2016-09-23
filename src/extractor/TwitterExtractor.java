package extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import entities.Record;
import entities.Tweet;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;



public class TwitterExtractor 
{
	private final static String CONSUMER_KEY = "dCMY3cQykUZ2sv2MAZKQrQAlv";
	private final static String CONSUMER_SECRET_KEY = "Nmq72bMKqeFdtjXhgp7l3RnHL9A42hsbWgaBLpTQIx2cBdsTtA";
	private final static String ACCESS_TOKEN = "94301440-QcHcvahJ2cCB2tqeoAGktMlHfwdNHUz6JBPYHFdoQ";
	private final static String ACCESS_TOKEN_SECRET = "ZLJYpGRXY3AoAHAab5p1DvUKfCD683y1bey9Bn6ev5xEh";
	
	private Twitter twitter;
	public TwitterExtractor() throws TwitterException
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(CONSUMER_KEY)
		  .setOAuthConsumerSecret(CONSUMER_SECRET_KEY)
		  .setOAuthAccessToken(ACCESS_TOKEN)
		  .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	public ArrayList<Record> search(String keyword) throws FileNotFoundException
	{
		Query query = new Query(keyword);
		//query.setSince("2016-01-01");
		//query.setSinceId(456867899965L);
		//query.setMaxId(477652685003L);
		
		
		int numberOfTweets = 5000;
		long lastID = Long.MAX_VALUE;
		ArrayList<Status> tweets = new ArrayList<Status>();
		int numOfIteration = 0;
		int oldSize = -1;
		int newSize = 0;
		//tweets.size() < numberOfTweets && 
		while ((oldSize != newSize || numOfIteration < 5)) {
			oldSize = tweets.size();
			numOfIteration++;
			if (numberOfTweets - tweets.size() > 100) 
			{
				query.setCount(100);
			}
			else
			{
				query.setCount(numberOfTweets - tweets.size());
			}
			
			QueryResult result;
			try {
				result = twitter.search(query);
				
				tweets.addAll(result.getTweets());
				//System.out.println("Gathered " + tweets.size() + " tweets");
				System.out.println("Searching twitter page " + numOfIteration);
				for (Status t : tweets)
				{
					if (t.getId() < lastID) lastID = t.getId();
				}
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			query.setMaxId(lastID-1);
			newSize = tweets.size();
			
			System.out.println("old: " + oldSize + ", new: " + newSize);
		}
		
		ArrayList<Record> tws = new ArrayList<Record>();
		
		for (Status status : tweets) 
		{
			Tweet tweet = new Tweet(status.getId(), status.getUser().getScreenName(), status.getText(), status.getCreatedAt());
	        
	        
	        // Add if country is in PH
			Place postPlace = status.getPlace();
	        if (postPlace != null)
	        {
	        	tweet.setPostLocation(postPlace.getCountryCode());
	        }
	        
	        String language = status.getLang();
	        if (language != null)
	        {
	        	tweet.setLanguage(language);
	        }
	        
	        String userLocation = status.getUser().getLocation();
	        if (userLocation != null)
	        {
	        	tweet.setUserLocation(userLocation);
	        }
	        
	        
	        tws.add(tweet);
		}
		
		return tws;	   
	}
	
	/*
	public void searchWithJsoup(String keyword) throws IOException
	{
		String url = "https://twitter.com/search?q=%23BDOUnibank%20since%3A2016-01-01%20until%3A2016-07-12&src=typd";
		
		Document tweetsDoc = Jsoup.connect(url).timeout(300000).get();
		
		Elements ps = tweetsDoc.select("p");
		
		for (int i = 0; i < ps.size(); i++)
		{
			System.out.println(ps.get(i).text());
		}
	}
	*/
}
