package extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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

public class FacebookHtmlExtractor 
{
	private String htmlFilename;
	private String html_str = "";
	
	public FacebookHtmlExtractor()
	{
		htmlFilename = "";
	}
	
	public FacebookHtmlExtractor(String htmlFilename)
	{
		this.htmlFilename = htmlFilename;
		readHtml();
	}
	
	public void readHtml()
	{
		File file = new File(htmlFilename);
		try {
			Scanner input = new Scanner(file);
			while(input.hasNextLine())
			{
				html_str += input.nextLine();
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(html_str);
	}
	
	public void parse()
	{
		Document docFb = Jsoup.parse(html_str);
		//System.out.println(docFb.toString());
		Elements contents = docFb.select("div.userContent");
		
		for (int i = 0; i < contents.size(); i++)
		{
			System.out.println((i+1) + ") " + contents.get(i).text());
		}
	}
	
	public void setDemographic (FacebookPost fbPost) throws IOException
	{
		//String postId = "706304686174157";
		String postId = fbPost.getId();
		String url_str = "https://www.facebook.com/" + postId;
		System.out.println(url_str);
		
		Document docFb = Jsoup.parse(new URL(url_str).openStream(), "utf-8", url_str);
		Elements as = docFb.select("a[href^='https://www.facebook.com/pages']");
		String userLocation = "";
		String postLocation = "";
		for (int i = 0; i < as.size(); i++)
		{
			
//			if (as.get(i).toString().toLowerCase().contains("profileLink".toLowerCase()))
//			{
//				userLocation += as.get(i).text() + ";";
//			}
//			else
//			{
//				postLocation += as.get(i).text() + ";";
//			}
			System.out.println(as.get(i).toString());
		}
		
		fbPost.setUserLocation(userLocation);
		fbPost.setPostLocation(postLocation);
	}
	
	public String getContentByPostId(String postId) throws IOException
	{
		//postId="981067338606164";
		String url_str = "https://www.facebook.com/" + postId;
		//Document docFb = Jsoup.connect(url_str).get();
		Document docFb = Jsoup.parse(new URL(url_str).openStream(), "utf-8", url_str);
		Elements ps = docFb.select(".userContent p");
		String text = "";
		for (int i = 0; i < ps.size(); i++)
		{
			text += Record.removeRtLUnicode(ps.get(i).text().trim());
		}
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(text);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return text;
	}
}
