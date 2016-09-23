package net.codejava.framework.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import entities.FacebookPost;
import entities.Record;
import extractor.FacebookExtractor;
import extractor.FacebookThirdPartyExtractor;
import extractor.GooglePlusExtractor;
import extractor.TwitterExtractor;
import extractor.YouTubeExtractor;
import facebook4j.FacebookException;
import service.Utilities;
import twitter4j.TwitterException;


public class SearchKeywordAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keywordString;
	private String keywordOption;
	private HashMap<String,Record> allRecords;
	private final String USER_AGENT = "Mozilla/5.0";
	
	public HashMap<String, Record> getAllRecords() {
		return allRecords;
	}

	public void setAllRecords(HashMap<String, Record> allRecords) {
		this.allRecords = allRecords;
	}

	public String execute() throws Exception {
		ArrayList<String> keywordList = new ArrayList<String>();
		// Set keyword to search
		String keyword = "";
		String hashtag = "#" + keyword;
		
		keywordList.add(getKeywordString());

		allRecords = new HashMap<String,Record>();
		
		
		for (String word: keywordList)
		{
			keyword = word;
			hashtag = "#" + keyword;
			System.out.println(keyword + " " + hashtag);

			// Search from facebook, twitter, google+, and youtube
			ArrayList<Record> records = new ArrayList<Record>();
			TwitterExtractor te = new TwitterExtractor();
			YouTubeExtractor ye = new YouTubeExtractor();
			
			FacebookThirdPartyExtractor fbt = new FacebookThirdPartyExtractor();
			
			if(keywordOption.equalsIgnoreCase("Facebook"))
			{	
				records.addAll(fbt.search(keyword));
				
			}else if(keywordOption.equalsIgnoreCase("Twitter"))
			{
				records.addAll(te.search(hashtag));
			
			}else if(keywordOption.equalsIgnoreCase("Google+"))
			{
				records.addAll(GooglePlusExtractor.search(hashtag));
				
			}else if(keywordOption.equalsIgnoreCase("Youtube"))
			{
				records.addAll(ye.search(hashtag));
				
			}else if(keywordOption.equalsIgnoreCase("Wordpress"))
			{
				searchWordpress(keyword);
			//	records.addAll(searchWordpress(keyword));
			}else
			{
				records.addAll(fbt.search(keyword));
				records.addAll(te.search(hashtag));
				records.addAll(GooglePlusExtractor.search(hashtag));
				records.addAll(ye.search(hashtag));
			}

			// Fix some record content
			for (int i = 0, len = records.size(); i < len; i++)
			{
				Record record = records.get(i);
				record.setHashtag(hashtag);

				// Get full content of facebook post when prematurely ended with ellipsis (...)
				if (record.getType().equals("facebook") && record.getText().endsWith("..."))
				{
					FacebookPost fbPost = (FacebookPost) record;
					FacebookExtractor fe = new FacebookExtractor();

					fe.getPostByUserAndPostId(fbPost);

					records.set(i, fbPost);
				}	
			}

			System.out.println("Done extracting");
			allRecords.putAll(Utilities.convertToHashMap(records));
		}
		

		return SUCCESS;
	}
	

	private void searchWordpress(String keyword) throws Exception
	{
		ArrayList<Record> recordList=new ArrayList<Record>();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject = sendGet(keyword);
		
		//return recordList;
		
	}
	
	private JSONObject sendGet(String keyword) throws Exception {
		
		JSONObject jsonObject = new JSONObject();
		String url = "https://public-api.wordpress.com/rest/v1.1/read/tags/"+keyword+"/posts";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

	//	int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
	//	System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		
		return jsonObject;
	}

	public String getKeywordString() {
		return keywordString;
	}

	public void setKeywordString(String keywordString) {
		this.keywordString = keywordString;
	}

	public String getKeywordOption() {
		return keywordOption;
	}

	public void setKeywordOption(String keywordOption) {
		this.keywordOption = keywordOption;
	}
}
