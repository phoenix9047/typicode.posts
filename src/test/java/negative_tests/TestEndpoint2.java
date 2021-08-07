package negative_tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class TestEndpoint2 extends NegativeTest {
	
	@Test(
			description = "A GET request to a non-existing ID should be rejected with 404"
			)
	@Parameters( { "endpoint", "non_existing_user_id" })
	public void request_to_non_existing_id_should_be_rejected(String endpoint, int non_existing_user_id) 
	{
		response_is_empty_http_not_found(get(endpoint + "/" + non_existing_user_id));
	}
	
	@Test(
			description = "A GET request to a non-numeric ID should be rejected with 404"
			)
	@Parameters( { "endpoint" })
	public void request_to_non_numeric_id_should_be_rejected(String endpoint) 
	{
		response_is_empty_http_not_found(get(endpoint + "/nonnumericvalue"));
	}
	
}
