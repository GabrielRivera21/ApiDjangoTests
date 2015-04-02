package com.deeplogics.api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.apache.commons.io.IOUtils;

import retrofit.Endpoint;
import retrofit.ErrorHandler;
import retrofit.Profiler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Log;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.Client;
import retrofit.client.Client.Provider;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.converter.Converter;
import retrofit.mime.TypedOutput;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * A Builder class for a Retrofit REST Adapter. Extends the default implementation by providing logic to
 * handle an OAuth 2.0 password grant login flow. The RestAdapter that it produces uses an interceptor
 * to automatically obtain a bearer token from the authorization server and insert it into all client
 * requests.
 * 
 * You can use it like this:
 * 
  	private UsersDjangoApi usersService = new SecuredRestDjangoBuilder()
		.setEndpoint(UsersDjangoApi.HOST_URL)
		.setUsername(USERNAME)
		.setPassword(PASSWORD)
		.setLoginEndpoint(UsersDjangoApi.HOST_URL + UsersDjangoApi.OAUTH_PATH)
		.setLogLevel(LogLevel.FULL)
		.build().create(UsersDjangoApi.class);
 * 
 * @author Jules, Mitchell, Gabriel
 *
 */
public class SecuredRestDjangoBuilder extends RestAdapter.Builder {

	private class OAuthHandler implements RequestInterceptor {

		private boolean loggedIn;
		private Client client;
		private String tokenIssuingEndpoint;
		private String username;
		private String password;
		private String accessToken;

		public OAuthHandler(Client client, String tokenIssuingEndpoint, String username,
				String password, String clientId, String clientSecret) {
			super();
			this.client = client;
			this.tokenIssuingEndpoint = tokenIssuingEndpoint;
			this.username = username;
			this.password = password;
		}

		/**
		 * Every time a method on the client interface is invoked, this method is
		 * going to get called. The method checks if the client has previously obtained
		 * an OAuth 2.0 bearer token. If not, the method obtains the bearer token by
		 * sending a password grant request to the server. 
		 * 
		 * Once this method has obtained a bearer token, all future invocations will
		 * automatically insert the bearer token as the "Authorization" header in 
		 * outgoing HTTP requests.
		 * 
		 */
		@Override
		public void intercept(RequestFacade request) {
			// If we're not logged in, login and store the authentication token.
			if (!loggedIn) {
				try {
					// This code below programmatically builds a json and
					// sends it to the server
					
					JsonObject json = new JsonObject();
                 
					// Add the email and password property into the json for the body of the request.
					json.addProperty("email", username);
					json.addProperty("password", password);
					
					String jsonString = json.toString();
					String charset = "UTF-8";
					
					JsonTypedOutput to = new JsonTypedOutput(jsonString.getBytes(charset));
					
					// Add the Content-Type JSON header
					List<Header> headers = new ArrayList<Header>();
					headers.add(new Header("Content-Type", "application/json"));
					
					// Create the actual request using the data above
					Request req = new Request("POST", tokenIssuingEndpoint, headers, to);
					
					// Send the Request.
					Response resp = client.execute(req);
					
					// Make sure the server responded with 200 OK
					if (resp.getStatus() < 200 || resp.getStatus() > 299) {
						// If not, we probably have bad credentials
						throw new SecuredRestException("Login failure: "
								+ resp.getStatus() + " - " + resp.getReason());
					} else {
						// Extract the string body from the response
				        String body = IOUtils.toString(resp.getBody().in());
						
						// Extract the access_token (bearer token) from the response
						// so that we can add it to future requests.
						accessToken = new Gson().fromJson(body, JsonObject.class).get("token").getAsString();
						

						// Add the access_token to this request as the "Authorization"
						// header.
						request.addHeader("Authorization", "Bearer " + accessToken);	
						
						// Let future calls know we've already fetched the access token
						loggedIn = true;
					}
				} catch (Exception e) {
					throw new SecuredRestException(e);
				}
			}
			else {
				// Add the access_token that we previously obtained to this request as 
				// the "Authorization" header.
				request.addHeader("Authorization", "Bearer " + accessToken );
			}
		}

	}

	private String username;
	private String password;
	private String loginUrl;
	private String clientId;
	private String clientSecret = "";
	private Client client;
	
	public SecuredRestDjangoBuilder setLoginEndpoint(String endpoint){
		loginUrl = endpoint;
		return this;
	}

	@Override
	public SecuredRestDjangoBuilder setEndpoint(String endpoint) {
		return (SecuredRestDjangoBuilder) super.setEndpoint(endpoint);
	}

	@Override
	public SecuredRestDjangoBuilder setEndpoint(Endpoint endpoint) {
		return (SecuredRestDjangoBuilder) super.setEndpoint(endpoint);
	}

	@Override
	public SecuredRestDjangoBuilder setClient(Client client) {
		this.client = client;
		return (SecuredRestDjangoBuilder) super.setClient(client);
	}

	@Override
	public SecuredRestDjangoBuilder setClient(Provider clientProvider) {
		client = clientProvider.get();
		return (SecuredRestDjangoBuilder) super.setClient(clientProvider);
	}

	@Override
	public SecuredRestDjangoBuilder setErrorHandler(ErrorHandler errorHandler) {

		return (SecuredRestDjangoBuilder) super.setErrorHandler(errorHandler);
	}

	@Override
	public SecuredRestDjangoBuilder setExecutors(Executor httpExecutor,
			Executor callbackExecutor) {

		return (SecuredRestDjangoBuilder) super.setExecutors(httpExecutor,
				callbackExecutor);
	}

	@Override
	public SecuredRestDjangoBuilder setRequestInterceptor(
			RequestInterceptor requestInterceptor) {

		return (SecuredRestDjangoBuilder) super
				.setRequestInterceptor(requestInterceptor);
	}

	@Override
	public SecuredRestDjangoBuilder setConverter(Converter converter) {

		return (SecuredRestDjangoBuilder) super.setConverter(converter);
	}

	@Override
	public SecuredRestDjangoBuilder setProfiler(@SuppressWarnings("rawtypes") Profiler profiler) {

		return (SecuredRestDjangoBuilder) super.setProfiler(profiler);
	}

	@Override
	public SecuredRestDjangoBuilder setLog(Log log) {

		return (SecuredRestDjangoBuilder) super.setLog(log);
	}

	@Override
	public SecuredRestDjangoBuilder setLogLevel(LogLevel logLevel) {

		return (SecuredRestDjangoBuilder) super.setLogLevel(logLevel);
	}

	public SecuredRestDjangoBuilder setUsername(String username) {
		this.username = username;
		return this;
	}

	public SecuredRestDjangoBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public SecuredRestDjangoBuilder setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}
	
	public SecuredRestDjangoBuilder setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}
	
		

	@Override
	public RestAdapter build() {
		if (username == null || password == null) {
			throw new SecuredRestException(
					"You must specify both a username and password for a "
							+ "SecuredRestBuilder before calling the build() method.");
		}

		if (client == null) {
			client = new OkClient();
		}
		OAuthHandler hdlr = new OAuthHandler(client, loginUrl, username, password, clientId, clientSecret);
		setRequestInterceptor(hdlr);

		return super.build();
	}
	
	private static class JsonTypedOutput implements TypedOutput {
		private final byte[] jsonBytes;

		JsonTypedOutput(byte[] jsonBytes) { this.jsonBytes = jsonBytes; }

		@Override public String fileName() { return null; }
		@Override public String mimeType() { return "application/json; charset=UTF-8"; }
		@Override public long length() { return jsonBytes.length; }
		@Override public void writeTo(OutputStream out) throws IOException { out.write(jsonBytes); }
	}
}