package negative_tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestEndpoint1 extends NegativeTest {
	
	@Test(
			description = "Send a POST request to endpoint /posts and verify it rejects with 404"
			)
	@Parameters("endpoint")
	public void push_document_with_post_query(String endpoint) 
	{
		
		Response response = 
			given()
				.contentType(ContentType.JSON)
				.body("{\"userId\": 0, \"title\": \"some title\", \"body\": \"some body\"}")
			.when()
				.post(endpoint);
		
		response_is_empty_http_not_found(response);
		
	}
	
}
