package positive_tests;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class TestEndpoint2 extends PositiveTest {
	
	private Response response = null;
	private JSONObject document = null;
	
	@Test(
			description = "Gets only one document by predefined ID and verifies status code and content-type"
			)
	@Parameters( { "endpoint", "correct_user_id" })
	public void check_query_for_single_document_is_responded_correctly(String endpoint, int correct_user_id) 
	{
		response = get(endpoint + "/" + correct_user_id);
		
		response_header_is_correct(response);
		
	}
	
	@Test(
			description = "Verifies a format for the returned document."
					+ "Content should be a JSON Object",
			dependsOnMethods = { "check_query_for_single_document_is_responded_correctly" }
			)
	public void check_response_contains_only_one_document() 
	{
		if (response == null) 
			fail("Document object is unavailable for this test. "
					+ "Make sure all previous tests have been executed");
		
		try {
			document = (JSONObject) (new JSONParser()).parse(response.getBody().asString());
		} catch (ParseException | ClassCastException e) {
			e.printStackTrace();
			fail("Single document: Response parsing failed");
		}
		
	}
	
	@Test(
			description = "Verifies fields types in the captured document",
			dependsOnMethods = { "check_response_contains_only_one_document" }
			)
	public void check_document_keys_and_values_are_correct() {
		if (document == null) 
			fail("Document object is unavailable for this test. "
					+ "Make sure all previous tests have been executed");
		
		// In case of a field is not found in the document, 'get' method will return null pointer,
		// and assertTrue will fail as null is not instance of Integer or String
		assertTrue(document.get("userId") instanceof Long);
		assertTrue(document.get("id") instanceof Long);
		assertTrue(document.get("title") instanceof String);
		assertTrue(document.get("body") instanceof String);
		
		// No more fields are expected in the document but those listed above
		assertTrue(document.keySet().size() == 4);
		
	}
	
}
