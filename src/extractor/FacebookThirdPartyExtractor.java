package extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entities.FacebookPost;
import entities.Record;

/**
 * Extractor from http://www.facebook-hashtag.com/
 * @author Cheradee Camacho
 *
 */
public class FacebookThirdPartyExtractor 
{
	private static String baseUrl = "http://www.facebook-hashtag.com/search/hashtag/";

	public FacebookThirdPartyExtractor()
	{

	}

	public ArrayList<Record> search(String keyword) throws IOException, JSONException
	{
		System.out.println("Searching for facebook posts. This will take a while (about 3 mins)");

		// Open the page: www.facebook-hashtag.com, pass the keyword, and get result
		String url_str = baseUrl + keyword;
		StringBuilder result = new StringBuilder();
		URL url = new URL(url_str);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line.replace("\\u202a", "").replace("\\u200e", "").replace("\\u202c", "")); // remove unicode left-to-right embeddings
		}
		rd.close();

		// Parse the json result and store in records
		ArrayList<Record> records = new ArrayList<Record>();
		//System.out.println(result.toString());
	
		JSONObject jsonObj = new JSONObject(result.toString());
		JSONArray array = new JSONArray(jsonObj.get("result").toString());
		for (int i = 0; i < array.length(); i++) 
		{
			JSONObject object = array.getJSONObject(i);

			int timeStamp = object.getInt("created");
			FacebookPost post = new FacebookPost(object.getString("postID"), object.getString("user"), 
					object.getString("content"), new Date((long)timeStamp*1000), String.valueOf(object.get("userID")));
			records.add(post);
		}

		return records;
	}
}

