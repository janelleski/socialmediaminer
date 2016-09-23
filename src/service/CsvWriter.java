package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import entities.Record;
import entities.Tweet;

public class CsvWriter 
{
	public static void writeAllRecord(String filename, HashMap<String,Record> records) throws FileNotFoundException
	{
		File file = new File(filename);
		if (file.exists()) 
		{
			Scanner input = new Scanner(file);
			String line = input.nextLine();	// read header
			
			while (input.hasNextLine())
			{
				line = input.nextLine();
				String args[] = line.split(",");
				Record rec = new Record(args);
				records.put(rec.getId(),rec);
			}
			
			input.close();
		}
		
		{
			PrintWriter pw = new PrintWriter(file);
			
			StringBuilder sb = new StringBuilder();
			sb.append("Id");
			sb.append(',');
			sb.append("Author");
			sb.append(',');
			sb.append("Content");
			sb.append(',');
			sb.append("Date");
			sb.append(',');
			sb.append("Type");
			sb.append(',');
			sb.append("Post Location");
			sb.append(',');
			sb.append("User Location");
			sb.append(',');
			sb.append("Language");
			sb.append(',');
			sb.append("Hashtag");
			sb.append('\n');
			
			for (Record record: records.values())
			{
//		        System.out.println(i + " " + tweet.getTweetId() + " @" + tweet.getCreator() + ":" + tweet.getText() + 
//		        		" Date: " + tweet.getDateCreated());
		        
		        sb.append(record.getId());
		        sb.append(',');
		        sb.append(cleanText(record.getCreator()));
		        sb.append(',');
		        sb.append(cleanText(record.getText()));
		        sb.append(',');
		        sb.append(record.getDateCreated());
		        sb.append(',');
		        sb.append(cleanText(record.getType()));
		        sb.append(',');
		        sb.append(cleanText(record.getPostLocation()));
		        sb.append(',');
		        sb.append(cleanText(record.getUserLocation()));
		        sb.append(',');
		        sb.append(cleanText(record.getLanguage()));
		        sb.append(',');
		        sb.append(record.getHashtag());
		        sb.append('\n');
			}
		    
			pw.write(sb.toString());
			pw.close();
			System.out.println("Done writing to " + file.getName());
		}
	}
	
	public static void writeRecord(String filename, ArrayList<Record> records) throws FileNotFoundException
	{
		File file = new File(filename + ".csv");
		int index = 1;
		while (file.exists())
		{
			index++;
			file = new File(filename + "_" + index +".csv");
		}
		
		PrintWriter pw = new PrintWriter(file);
			
		StringBuilder sb = new StringBuilder();
		sb.append("Id");
		sb.append(',');
		sb.append("Author");
		sb.append(',');
		sb.append("Content");
		sb.append(',');
		sb.append("Date");
		sb.append(',');
		sb.append("Type");
		sb.append(',');
		sb.append("Post Location");
		sb.append(',');
		sb.append("User Location");
		sb.append(',');
		sb.append("Language");
		sb.append('\n');
		
		for (int i = 0, len = records.size(); i < len; i++)
		{
			Record record = records.get(i);
//	        System.out.println(i + " " + tweet.getTweetId() + " @" + tweet.getCreator() + ":" + tweet.getText() + 
//	        		" Date: " + tweet.getDateCreated());
	        
	        sb.append(record.getId());
	        sb.append(',');
	        sb.append(cleanText(record.getCreator()));
	        sb.append(',');
	        sb.append(cleanText(record.getText()));
	        sb.append(',');
	        sb.append(record.getDateCreated());
	        sb.append(',');
	        sb.append(cleanText(record.getType()));
	        sb.append(',');
	        sb.append(cleanText(record.getPostLocation()));
	        sb.append(',');
	        sb.append(cleanText(record.getUserLocation()));
	        sb.append(',');
	        sb.append(cleanText(record.getLanguage()));
	        sb.append('\n');
		}
	    
		pw.write(sb.toString());
		pw.close();
		System.out.println("Done writing to " + file.getName());
	}
	
	private static String cleanText(String str)
	{
		return str.replace(',', ' ').replace('\n', ' ');		// not sure what character to replace, so space for now
	}
}
