package com.deeplogics.api;


import com.deeplogics.models.EditPassword;
import com.deeplogics.models.PageClient;
import com.deeplogics.models.Users;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Streaming;
import retrofit.mime.TypedFile;

/**
 * This interface defines an API for a User Django Api. The
 * interface is used to provide a contract for client/server
 * interactions. The interface is annotated with Retrofit
 * annotations so that clients can automatically convert the 
 * Json String returned by the Server to a POJO.
 * 
 * @author Gabriel
 *
 */
public interface UsersDjangoApi {
	
	public static final String HOST_URL = "http://localhost:8000";
									
	public static final String OAUTH_PATH = "/oauth/token";
	
	public static final String USERS_PATH = "/users";
	
	public static final String USERS_REGISTER = USERS_PATH + "/register";
	
	public static final String USERS_GETMYINFO_PATH = "/users/me";
	
	public static final String USERS_EDIT_PATH = USERS_PATH + "/edit/me";
	
	public static final String USERS_EDIT_PASS_PATH = USERS_EDIT_PATH + "/pass";
	
	@POST(USERS_REGISTER)
	public Users addUser(@Body Users user);
	
	@GET(USERS_GETMYINFO_PATH)
	public Users getMyUserInfo();
	
	@GET(USERS_PATH)
	public PageClient<Users> getUsers();
	
	@GET(USERS_PATH + "/{id}")
	public Users getSpecificUser(@Path("id") Long id);
	
	@Multipart
	@Streaming
	@PUT(USERS_EDIT_PATH)
	public Response editUser(@Part("first_name") String firstName,
			@Part("last_name") String lastName,
			@Part("email") String email,
			@Part("about_me") String aboutMe,
			@Part("street_1") String street1,
			@Part("street_2") String street2,
			@Part("city") String city,
			@Part("country") String country,
			@Part("zip_code") String zip,
			@Part("profile_pic") TypedFile photo);
	
	@Multipart
	@Streaming
	@PATCH(USERS_EDIT_PATH)
	public Response editUserPublicInfo(@Part("first_name") String firstName,
			@Part("last_name") String lastName,
			@Part("about_me") String aboutMe,
			@Part("street_1") String street1,
			@Part("street_2") String street2,
			@Part("city") String city,
			@Part("country") String country,
			@Part("zip_code") String zip);

	@PUT(USERS_EDIT_PASS_PATH)
	public Response editPassword(@Body EditPassword newPass);
	
}
