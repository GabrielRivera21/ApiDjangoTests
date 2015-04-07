package com.deeplogics.tests;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import retrofit.RestAdapter.LogLevel;
import retrofit.converter.GsonConverter;

import com.deeplogics.api.DjangoApi;
import com.deeplogics.api.SecuredRestDjangoBuilder;
import com.deeplogics.models.PageClient;
import com.deeplogics.models.Projects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProjectsSvcClientApiTest {
	
	private String USERNAME_FAIL = "gabrielwola@matefix.com"; //"admin";
	private String USERNAME = "gabriel.rivera2192@gmail.com";
	private String PASSWORD = "gabriel21";
	
	Gson gson = new GsonBuilder()
		.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
	
	private DjangoApi projectFailService = new SecuredRestDjangoBuilder()
		.setEndpoint(DjangoApi.HOST_URL)
		.setUsername(USERNAME_FAIL)
		.setPassword(PASSWORD)
		.setConverter(new GsonConverter(gson))
		.setLoginEndpoint(DjangoApi.HOST_URL + DjangoApi.OAUTH_PATH)
		.setLogLevel(LogLevel.FULL)
		.build().create(DjangoApi.class);
	
	private DjangoApi projectService = new SecuredRestDjangoBuilder()
		.setEndpoint(DjangoApi.HOST_URL)
		.setUsername(USERNAME)
		.setPassword(PASSWORD)
		.setConverter(new GsonConverter(gson))
		.setLoginEndpoint(DjangoApi.HOST_URL + DjangoApi.OAUTH_PATH)
		.setLogLevel(LogLevel.FULL)
		.build().create(DjangoApi.class);
	
	Projects project = TestDjangoData.randomProject();
	
	@Test
	public void addProject() {
		Projects proj = projectService.addProject(project);
		assertTrue(proj != null);
	}
	
	@Test
	public void getAllProjects() {
		PageClient<Projects> listProjects = projectService.getAllProjects();
		assertTrue(listProjects != null);
		Collection<Projects> results = listProjects.getResults();
		assertTrue(results != null);
	}
	
	@Test
	public void testFailAddProject() {
		try{
			Projects proj = projectFailService.addProject(project);
			assertFalse(proj != null);
		}catch (Exception e) {
			assertTrue(e.getMessage().equals("403 FORBIDDEN"));
		}
	}
	
	@Test
	public void testGetSpecificProject() {
		Projects proj = projectService.addProject(project);
		assertTrue(proj != null);
		
		Long projId = proj.getId();
		Projects retrievedProj = projectService.getProject(projId);
		assertTrue(retrievedProj.equals(proj));
	}
}
