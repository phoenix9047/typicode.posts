package negative_tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

abstract class NegativeTest {
	
	/**
	 * Header verification is used several times by different negative tests.
	 * Verifies status code is 404, content is JSON and is empty or an empty JSON document
	 * @param response Entire response object, including body, headers and all.
	 */
	protected static void response_is_empty_http_not_found(Response response) {
		
		// Status code is 404:
		assertEquals(response.statusCode(), HttpStatus.SC_NOT_FOUND);
		
		// Content is application/json:
		assertTrue(response.contentType().contains(ContentType.JSON.toString()));
		
		// Content should be or empty or an empty document:
		String body = response.body().asPrettyString();
		if ( ! body.isEmpty())
		{   JSONObject empty_document = null;
			try {
				 empty_document = (JSONObject) (new JSONParser()).parse(body);
			} catch (ParseException | ClassCastException e) {
				e.printStackTrace();
				fail("Empty document: Response parsing failed");
			}
			
			assertTrue(empty_document.isEmpty());
		}
		
	}
	
}
