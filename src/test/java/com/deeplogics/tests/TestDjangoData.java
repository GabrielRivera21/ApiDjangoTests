package com.deeplogics.tests;

import java.util.Random;

import com.deeplogics.models.Users;


public class TestDjangoData {
	private static Random r = new Random();
	private static String setOfChars = "abcdefghijklmnopqrstuvwxyz";
	//private static String setOfNums = "0123456789";
	
	public static Users randomUser() {
		// Information about the User
		// Construct a random identifier using Java's UUID class
		String email = generateString(r ,setOfChars, 8) + "@example.com";
		//String firstName = generateString(r, setOfChars, 5);
		String password = "yourpass";

		return new Users(email, password);
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
