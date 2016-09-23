package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.google.api.client.util.DateTime;

import entities.Record;

public class Utilities {
	 private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");      
	
	public static Date getDate(DateTime dt) throws ParseException
	{
		return sdf.parse(dt.toString());
	}
	
	public static DateTime getDateTime(Date d)
	{
		return new DateTime(d);
	}
	
	public static Date getDateToday()
	{
		return Calendar.getInstance().getTime();
	}
	
	public static String getDateFolderName() throws ParseException
	{
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
		
	    return format.format(calendar.getTime());
	}
	

	public static <T extends Record> HashMap<String,T> convertToHashMap(ArrayList<T> list)
	{
		HashMap<String,T> map = new HashMap<String,T>();
		for (T t: list)
		{
			map.put(t.getId(), t);
		}
		
		return map;
	}
}
