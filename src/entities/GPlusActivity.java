package entities;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.api.client.util.DateTime;

public class GPlusActivity extends Record
{
	private String gplusId;
	private String creator;
	
	public GPlusActivity()
	{
		type = "gplus";
	}
	
	public GPlusActivity(String id, String userScreenName, String text, DateTime createdAt)
	{
		this();
		this.gplusId = id;
		this.creator = userScreenName;
		this.dateCreated = createdAt;
		
		
		this.text = parseTextFromHtml(text);
	}
	
	private String parseTextFromHtml(String html)
	{
		Document doc = Jsoup.parse(html);
		return doc.text();
	}
		
	public String getId()
	{
		return gplusId;
	}
	public String getGplusId() {
		return gplusId;
	}
	public void setGplusId(String gplusId) {
		this.gplusId = gplusId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String userScreenName) {
		this.creator = userScreenName;
	}
}
