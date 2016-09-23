package entities;

import java.util.Date;

import com.google.api.client.util.DateTime;

public class YouTubeVideo extends Record
{
	private String videoId;
	private String title;
	
	public YouTubeVideo()
	{
		type = "youtube";
	}
	
	public YouTubeVideo(String id, String userScreenName, String text, DateTime createdAt)
	{
		this();
		this.videoId = id;
		this.creator = userScreenName;
		this.dateCreated = createdAt;
		this.text = text;
		title = text;
	}
	
	public String getId()
	{
		return videoId;
	}
}
