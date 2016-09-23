package entities;

import java.util.Date;

import com.google.api.client.util.DateTime;

public class FacebookPost extends Record
{
	private String userId;
	
	public FacebookPost()
	{
		super();
		type = "facebook";
	}
	
	public FacebookPost(String id, String userScreenName, String text, Date createdAt, String userId)
	{
		this();
		this.id = id;
		this.creator = userScreenName;
		this.dateCreated = new DateTime(createdAt);
		this.text = text;
		this.userId = userId;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
