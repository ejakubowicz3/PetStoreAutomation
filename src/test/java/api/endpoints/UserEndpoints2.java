package api.endpoints;

import api.payload.User;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

// Created to perform CRUD  operations
// create, update, update, delete
public class UserEndpoints2 {
	
	//load property file
	private static ResourceBundle getURL(){
		ResourceBundle routes = ResourceBundle.getBundle("routes"); //Loads the property file
		return routes;
	}
	
	public static Response createUser(User payload) {
		
		String postUrl = getURL().getString("post_url");
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(postUrl);
		
		return response;		
	}
	
	public static Response readUser(String userName) {
		String getUrl = getURL().getString("get_url");
		
		Response response = given()
				.pathParam("username", userName)
		.when()
			.get(getUrl);
		
		return response;		
	}
	
	public static Response updateUser(String userName, User payload) {
		String updateUrl = getURL().getString("update_url");

		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", userName)
			.body(payload)
		.when()
			.put(updateUrl);
		return response;		
	}
	
	public static Response deleteUser(String userName) {
		
		String deleteUrl = getURL().getString("delete_url");
		
		Response response = given()
				.pathParam("username", userName)
		.when()
			.delete(deleteUrl);
		
		return response;		
	}
	
}
