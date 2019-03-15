package run.test_objects;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class ApiTests
{
	final static org.apache.log4j.Logger logger = Logger.getLogger(ApiTests.class);
	public String apiUrlListOfUsers = "https://reqres.in/api/users?page=2";
	public String apiUrlPostRegister = "https://reqres.in/api/register";
	public String apiUrlPostLogin = "https://reqres.in/api/login";
	public int statusCodeListUsers = 200;
	public int statusCodeSuccesfullRegister = 201;
	public int statusCodeSuccesfullRLogin = 200;

	String[] first_name = {"Eve", "Charles", "Tracey"};
	String[] last_name = {"Holt", "Morris", "Ramos"};
	String[] avatar = {
			"https://s3.amazonaws.com/uifaces/faces/twitter/marcoramires/128.jpg",
			"https://s3.amazonaws.com/uifaces/faces/twitter/stephenmoon/128.jpg",
			"https://s3.amazonaws.com/uifaces/faces/twitter/bigmancho/128.jpg"};
	Integer[] id = {6,5,6};
	Integer total_pages = 4;
	Integer total = 12;
	Integer per_page = 3;
	Integer page = 2;

	public void getListOfUsers() {

		when().
				get(apiUrlListOfUsers).
				then().
				statusCode(statusCodeListUsers).
				body("page", equalTo(page),
						"per_page", equalTo(per_page),
						"total", equalTo(total),
						"total_pages", equalTo(total_pages),
						"data.id", hasItems(id),
						"data.first_name", hasItems(first_name),
						"data.last_name", hasItems(last_name),
						"data.avatar", hasItems(avatar)
				);

	}

	public void postRegisterSuccessful()
	{
		JSONObject requestBody = new JSONObject();
		requestBody.put("email", "sydney@fife");
		requestBody.put("password", "pistol");

		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(requestBody.toString());
		Response response = request.post(apiUrlPostRegister);

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, statusCodeSuccesfullRegister);
		String token = response.jsonPath().get("token");
		Assert.assertEquals(token, "QpwL5tke4Pnpja7X");
		System.out.println(response.getBody().asString());
	}

	public void postLoginSuccessful()
	{
		JSONObject requestBody = new JSONObject();
		requestBody.put("email", "peter@klaven");
		requestBody.put("password", "cityslicka");

		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(requestBody.toString());
		Response response = request.post(apiUrlPostLogin);

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, statusCodeSuccesfullRLogin);
		String token = response.jsonPath().get("token");
		Assert.assertEquals(token, "QpwL5tke4Pnpja7X");
		System.out.println(response.getBody().asString());
	}


}
