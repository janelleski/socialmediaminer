package entities;

import java.util.Date;

import com.google.api.client.util.DateTime;

public class Tweet extends Record
{
	private long tweetId;
	private String creator;
	
	public Tweet()
	{
		super();
		type = "twitter";
	}
	
	public Tweet(long id, String userScreenName, String text, Date createdAt)
	{
		this();
		this.tweetId = id;
		this.creator = userScreenName;
		this.dateCreated = new DateTime(createdAt);
		this.text = text;
	}
	
	public String getId()
	{
		return tweetId + "";
	}
	public long getTweetId() {
		return tweetId;
	}
	public void setTweetId(long id) {
		this.tweetId = id;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String userScreenName) {
		this.creator = userScreenName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
