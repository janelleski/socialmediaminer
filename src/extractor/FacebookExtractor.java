package extractor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import entities.FacebookPost;
import entities.Record;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.PostUpdate;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONObject;

public class FacebookExtractor 
{
	private Facebook facebook;
	private static String APP_ID = "1619730041689969";
	private static String APP_SECRET = "26e41ae7ec5edd1d0506021ec8d76bda";
	private static String CLIENT_TOKEN = "eba94664ae955a470c7b19ecbba6c220";
	private static String ACCESS_TOKEN = "1619730041689969|oCQQ1RJALlXTTcEKKcSS_8iZ-PQ";
	private static String APP_TOKEN = "1619730041689969|oCQQ1RJALlXTTcEKKcSS_8iZ-PQ";
	
	public FacebookExtractor()
	{
		facebook = new FacebookFactory().getInstance();
		//facebook.setOAuthAppId(APP_ID, APP_SECRET);
		facebook.setOAuthAppId(APP_ID, APP_SECRET);
		//facebook.setOAuthPermissions("publish_actions");
	
		AccessToken at = new AccessToken(ACCESS_TOKEN);
		facebook.setOAuthAccessToken(at);
	
		
		
	}
	
	public void updateStatus(String status) throws FacebookException
	{
		facebook.postStatusMessage("Hello World from Facebook4J.");
	}
	
	public void search_restFB()
	{
//		Connection<User> targetedSearch = fb_restFB.fetchConnection("search", User.class,
//			      Parameter.with("q", "Mark"), Parameter.with("type", "user"));
	}
	
	
	
	public Record getPostByUserAndPostId(FacebookPost fbPost) throws FacebookException
	{
		//System.out.println(fbPost.getText());
		String userId = fbPost.getUserId();
		//System.out.println(userId);
		ResponseList<Post> results = facebook.getPosts(userId, new Reading().limit(100));
		
		//ResponseList<Post> results = facebook.getFeed("BDOUnibank");
		//facebook.search
		//facebook.search("%23BDOUnibank");
		//facebook.search("BDOUnibank");
		
		//ResponseList<JSONObject> results = facebook.search("BDOUnibank", new Reading().limit(100));
		System.out.println("Searching for the fb post to get full content: ");
		int page = 0;
		do {
			page++;
			System.out.println("Page : " + page);
			for (int i = 0; i < results.size(); i++)
			{
				String str = results.get(i).toString();
				if (results.get(i).getId().equals(userId + "_" + fbPost.getId()))
				{
					//System.out.println("===============\n"+str+"\n========================");
					fbPost.setText(results.get(i).getMessage());
					fbPost.setText(fbPost.getText()+ "\nCaption: " + results.get(i).getStory());
					
					
					return fbPost;
				}
	//			if (str.contains("BDOUnibank"))
	//			{
	//				System.out.println(i + ") " + str);
	//			}
			}
			
			if (results.getPaging() != null)
			{
				results = facebook.fetchNext(results.getPaging());
			}
			else 
			{
				results = null;
			}
		} while (results != null && !results.isEmpty());
		
		return fbPost;
	}
	
	public void search(String keyword) throws FacebookException
	{
		//ResponseList<Post> results = facebook.searchPosts("watermelon");
		
		ResponseList<Post> results = facebook.getPosts("265779500134955", new Reading().limit(100));
		
		//ResponseList<Post> results = facebook.getFeed("BDOUnibank");
		//facebook.search
		//facebook.search("%23BDOUnibank");
		//facebook.search("BDOUnibank");
		
		//ResponseList<JSONObject> results = facebook.search("BDOUnibank", new Reading().limit(100));
		int page = 0;
		do {
			page++;
			System.out.println("Page : " + page);
		for (int i = 0; i < results.size(); i++)
		{
			String str = results.get(i).toString();
			if (results.get(i).getId().equals("265779500134955_981067338606164"))
			{
				System.out.println(page + ") " + results.get(i).toString());
				return;
			}
//			if (str.contains("BDOUnibank"))
//			{
//				System.out.println(i + ") " + str);
//			}
		}
		
		results = facebook.fetchNext(results.getPaging());
		} while (results != null && !results.isEmpty() && results.getPaging() != null);
	}
	
	public void publishLink() throws MalformedURLException, FacebookException
	{
		PostUpdate post = new PostUpdate(new URL("http://facebook4j.org"))
                .picture(new URL("http://facebook4j.org/images/hero.png"))
                .name("Facebook4J - A Java library for the Facebook Graph API")
                .caption("facebook4j.org")
                .description("Facebook4J is a Java library for the Facebook Graph API.");
		facebook.postFeed(post);
	}
}
