package entities;

import java.text.ParseException;
import java.util.Date;

import com.google.api.client.util.DateTime;

import service.Utilities;

public class Record 
{
	protected DateTime dateCreated;
	protected String text;
	protected String creator;
	protected String type;
	protected String id;
	protected String postLocation;
	protected String userLocation;
	protected String language;
	protected String hashtag;
	
	public Record() {}
	
	public Record(String[] args)
	{
		this(args[0],args[1],args[2],DateTime.parseRfc3339(args[3]),args[4],args[5],args[6],args[7],args[8]);
	}
	
	public Record(String Id,String Author,String Content,DateTime Date,String Type,String PostLocation,String UserLocation,String Language,String Hashtag)
	{
		this.dateCreated = Date;
		this.text = Content;
		this.creator = Author;
		this.type = Type;
		this.id = Id;
		this.postLocation = PostLocation;
		this.userLocation = UserLocation;
		this.language = Language;
		this.hashtag = Hashtag;
	}
	
	public String getHashtag() {
		return hashtag == null ? "" : hashtag;
	}


	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}


	public String getId()
	{
		return id;
	}
	
	
	public String getLanguage() {
		return language == null ? "" : language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getUserLocation() {
		return userLocation == null ? "" : userLocation;
	}


	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}


	public String getPostLocation() {
		return postLocation == null ? "" : postLocation;
	}


	public void setPostLocation(String postLocation) {
		this.postLocation = postLocation;
	}


	public DateTime getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(DateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getText() {
		return text == null ? "" : text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getType()
	{
		return type;
	}
	
	public boolean isBefore(Date after) throws ParseException
	{
		Date created = Utilities.getDate(dateCreated);
		if (created.before(after))
		{
			return false;
		}
		
		return true;
	}
	
	public boolean hasKeyword(String keyword)
	{
		if (text.toLowerCase().contains(keyword.toLowerCase()))
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isValid(Date after, Date before, String keyword) throws ParseException
	{
		Date created = Utilities.getDate(dateCreated);
		if (created.before(after))
		{
			return false;
		}
		if (created.after(before))
		{
			return false;
		}
		
		if (!text.contains(keyword))
		{
			return false;
		}
		
		return true;
	}
	
	public void prettyPrint()
	{
			System.out.println("Id: " + getId());
			System.out.println("Creator: " + getCreator());
			System.out.println("Type: " + getType());
			System.out.println("Date created: " + getDateCreated());
			System.out.println("Content: " + getText());
			System.out.println("===================================================");
		
	}
	
	public static String removeRtLUnicode(String str)
	{
		return str.replace("\\u202a", "").replace("\\u200e", "").replace("\\u202c", "");
	}
	
	public static String removeMarksAroundHashtag(String str)
	{
		return str.replace("?#?", "#");
	}
}
