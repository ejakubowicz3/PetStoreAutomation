package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	static Faker faker;
	static User userPayload;
	public Logger logger;
	@BeforeClass
	public void setup() {
		faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().phoneNumber());
		
		//logs
		logger = LogManager.getLogger(this.getClass());
		
	}
	
	@Test(priority=1)
	public void testCreateUser(){
		logger.info("********** Creating User **********");
		Response response = UserEndpoints.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("********** User Created **********");
	}
	
	@Test(priority=2)
	public void testGetUserByName(){
		logger.info("********** Getting User **********");
		Response response = UserEndpoints.readUser(userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("firstName"),userPayload.getFirstName());
		Assert.assertEquals(response.jsonPath().getString("lastName"),userPayload.getLastName());
	//	Assert.assertEquals(response.body().jsonPath("id"),userPayload.getId());
		logger.info("**********  User info has been displayed *********");

	}
	
	@Test(priority=3)
	public void testUpdateUserByName(){
		//update data
		logger.info("********** Updating User **********");

		String oldFirstName = userPayload.getFirstName();
		logger.info("=====Before========== " + oldFirstName);
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().phoneNumber());
		
		Response response = UserEndpoints.updateUser(userPayload.getUsername(), userPayload);
		response.then().log().body().statusCode(200);
		Assert.assertEquals(response.getStatusCode(),200);
		//Checking data after update
	
		
		Response responseAfterUpdate = UserEndpoints.readUser(userPayload.getUsername());
		responseAfterUpdate.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		String newFirstName = responseAfterUpdate.jsonPath().getString("firstName");
		logger.info("=====After========== " + newFirstName);


		Assert.assertNotEquals( oldFirstName, newFirstName);
		logger.info("**********  User Updated **********");

	}
	@Test(priority=4)
	public void testDeleteUserByName(){
		logger.info("********** Deleting User**********");
		Response response = UserEndpoints.deleteUser(userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);	
		logger.info("**********  User Deleted **********");

	}
}
