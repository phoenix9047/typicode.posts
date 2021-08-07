package positive_tests;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class TestEndpoint1 extends PositiveTest {
	
	Response response = null;
	
	@Test(
			description = "Verify a response to a simple GET request to /posts"
			)
	@Parameters("endpoint")
	public void check_query_for_all_documents_is_responded_correctly(String endpoint) 
	{
		response = get(endpoint);
		
		response_header_is_correct(response);
		
	}
	
	@Test(
			description = "Content of the response should be a JSON Array and should contain at least 1 document",
			dependsOnMethods = { "check_query_for_all_documents_is_responded_correctly" }
			)
	public void check_array_of_documents_is_returned()
	{
		if (response == null) 
			fail("All_documents object is unavailable for this test. "
					+ "Make sure all previous tests have been executed");
		
		// is Array:
		JSONArray all_documents = null;
		try {
			all_documents = (JSONArray) (new JSONParser()).parse(response.getBody().asString());
		} catch (ParseException | ClassCastException e) {
			e.printStackTrace();
			fail("All documents: Response parsing failed");
		}
		
		// not empty:
		assertTrue(all_documents.size() > 0);
		
	}
	
}
