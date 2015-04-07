package com.deeplogics.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.junit.Test;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedFile;

import com.deeplogics.api.SecuredRestDjangoBuilder;
import com.deeplogics.api.DjangoApi;
import com.deeplogics.models.PageClient;
import com.deeplogics.models.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UsersDjangoClientApiTest {

	private String USERNAME = "youremail@email.com"; //"admin";
	private String PASSWORD = "yourpass";
	
	
	Gson gson = new GsonBuilder()
		.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
	
	private DjangoApi usersNoTokenService = new RestAdapter.Builder()
		.setConverter(new GsonConverter(gson))
		.setEndpoint(DjangoApi.HOST_URL)
		.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
		.setLogLevel(LogLevel.FULL).build()
		.create(DjangoApi.class);
	
	private DjangoApi usersService = new SecuredRestDjangoBuilder()
		.setEndpoint(DjangoApi.HOST_URL)
		.setUsername(USERNAME)
		.setPassword(PASSWORD)
		.setConverter(new GsonConverter(gson))
		.setLoginEndpoint(DjangoApi.HOST_URL + DjangoApi.OAUTH_PATH)
		.setLogLevel(LogLevel.FULL)
		.build().create(DjangoApi.class);

	Users user = TestDjangoData.randomUser();

	@Test
	public void testAddUser() {
		Users u = usersNoTokenService.addUser(user);

		assertTrue(u != null);

		assertTrue(u.getEmail().equals(user.getEmail()));
	}

	@Test
	public void testGetUserInfo() {
		Users u = usersService.getMyUserInfo();
		assertTrue(u != null);

		assertTrue(u.getEmail().equals(USERNAME));
	}

	@Test
	public void testEditUserAndList() throws UnsupportedEncodingException {
		Users u = usersService.getMyUserInfo();
		assertTrue(u != null);

		String firstName = TestDjangoData.generateString(8);
		String lastName = TestDjangoData.generateString(6);

		File file = new File("src/test/resources/profile_pic.jpg");

		String mimeType = "image/jpeg";
		
		TypedFile photo = new TypedFile(mimeType, file);

		String fullName = firstName + " " + lastName;
		
		Response resp = usersService.editUserPublicInfo(
				firstName,
				lastName,
				"I rox",
				"Calle What",
				"Por ahi",
				"My City",
				"PR",
				"00932");
		assertTrue(resp.getStatus() >= 200 || resp.getStatus() < 300);
		
		Users uc = usersService.getMyUserInfo();
		String retrievedName = uc.getFirstName() + " " + uc.getLastName();
		assertTrue(retrievedName.equals(fullName));
		
		String newCity = TestDjangoData.generateString(6);
		resp = usersService.editUser(
				firstName,
				lastName,
				u.getEmail(),
				"Awesome",
				"Calle What",
				"Por ahi",
				newCity,
				"PR",
				"00931",
				photo);
		
		assertTrue(resp.getStatus() >= 200 || resp.getStatus() < 300);
		
		uc = usersService.getMyUserInfo();
		String retrievedCity = uc.getCity();
		retrievedName = uc.getFirstName() + " " + uc.getLastName();
		assertTrue(retrievedName.equals(fullName) && retrievedCity.equals(newCity));
	}
	
	@Test
	public void testGetAllUsers() {
		Users u = usersService.getMyUserInfo();
		assertTrue(u != null);
		
		PageClient<Users> userPage = usersService.getUsers();
		assertTrue(userPage != null);
		
		Collection<Users> content = userPage.getResults();
		assertTrue(content.contains(u));
	}
	
	@Test
	public void testRetrieveSpecificUser() {
		Users myUser = usersService.getMyUserInfo();
		assertTrue(myUser != null);
		
		long userId = myUser.getId();
		Users myUserRetrievedWithId = usersService.getSpecificUser(userId);
		assertTrue(myUser.equals(myUserRetrievedWithId));
	}
	
//	@Test
//	public void testEditPassword() {
//		Response resp = usersService.editPassword(new EditPassword(PASSWORD, "apass", "apass"));
//		assertTrue(resp.getStatus() >= 200 && resp.getStatus() < 300);
//	}

}
