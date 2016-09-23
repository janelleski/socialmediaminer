package extractor;

import java.util.ArrayList;
import java.util.List;

import com.sola.instagram.InstagramSession;
import com.sola.instagram.auth.AccessToken;
import com.sola.instagram.auth.InstagramAuthentication;
import com.sola.instagram.exception.InstagramException;
import com.sola.instagram.model.Tag;
import com.sola.instagram.model.User;


public class InstagramExtractor
{
	String clientId = "96f10299c46e4765911e5815bf9eef45";
	String clientSecret = "2fc9c4ca0f384decbd0a3a5b7dd085f7";
	String callbackUrl = "www.first-cpc.com";
	String redirectUri = "http://www.first-cpc.com";
	String code = "abd83f2aad574fa98482739254579020";
	String accessToken = "3543107938.96f1029.a3e5a830c50c4779a8d79006e2b38daf";
	
	// https://www.instagram.com/oauth/authorize/?client_id=96f10299c46e4765911e5815bf9eef45&redirect_uri=http://www.first-cpc.com&response_type=token&scope=public_content
	
	private InstagramSession instagram;
	
	public InstagramExtractor() throws Exception 
	{
		InstagramAuthentication auth =  new InstagramAuthentication();
//		String authUri = auth.setRedirectUri(redirectUri)
//		                     .setClientSecret(clientSecret)
//		                     .setClientId(callbackUrl)
//		                     .getAuthorizationUri();
//		                     //.setScope("public_content")
//		System.out.println(authUri);
		AccessToken token = new AccessToken(accessToken);
		
		instagram = new InstagramSession(token);
    }

    
    public void search(String keyword) throws Exception 
    {
    	instagram.searchTags(keyword);
    	List<Tag> tags = instagram.searchTags(keyword);
    	for (Tag tag : tags)
    	{
    		System.out.println(tag.toString());
    	}
    	
    	//instagram.getRecentMediaForTag(keyword);
    	
    	
    	
    	//User rihanna = instagram.searchUsersByName("badgalriri").get(0);
    	//instagram.searchUsersByName("badgalriri");
    	
    	/*
    	 * !! Again, no API to search for media by keyword
    	 */
    }
}
