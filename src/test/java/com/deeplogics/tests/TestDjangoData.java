package com.deeplogics.tests;

import java.util.Random;

import com.deeplogics.models.Projects;
import com.deeplogics.models.Users;


public class TestDjangoData {
	private static Random r = new Random();
	private static String setOfChars = "abcdefghijklmnopqrstuvwxyz";
	//private static String setOfNums = "0123456789";
	
	public static String[] cities = {"Bayamon", "Guayama", "Toa Alta", "Guaynabo"};
	public static String[] categories = {"vehicles", "Electronics"};
	
	public static Users randomUser() {
		// Information about the User
		// Construct a random identifier using Java's UUID class
		String email = generateString(r ,setOfChars, 8) + "@example.com";
		//String firstName = generateString(r, setOfChars, 5);
		String password = "yourpass";

		return new Users(email, password);
	}
	
	public static Projects randomProject(){
		
		String title = generateString(10);
		String description = generateString(40);
		double desiredCost = r.nextInt(100);
		double latitude = r.nextInt(100) * r.nextDouble();
		double longitude = r.nextInt(100) * r.nextDouble();
		String city = cities[r.nextInt(4)];
		String country = "Puerto Rico";
		String category = categories[r.nextInt(2)];
		
		return new Projects(title, description, desiredCost, 
				latitude, longitude, city, country, category);
	}
	
	public static String generateString(Random rng, String characters, int length)
	{
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
	
	public static String generateString(int length) {
		Random rng = new Random();
		String characters = "abcdefghijklmnopqrstuvwxyz";
		char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
}
