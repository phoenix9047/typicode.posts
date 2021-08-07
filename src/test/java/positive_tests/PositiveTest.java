package positive_tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.apache.http.HttpStatus;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

abstract class PositiveTest {
	
	/**
	 * Header verification is used several times by different positive tests.
	 * Verifies status code is 200, content is JSON and is not empty
	 * @param response Entire response object, including body, headers and all.
	 */
	protected static void response_header_is_correct(Response response) {
		
		assertEquals(response.statusCode(), HttpStatus.SC_OK);
		
		// Content is application/json:
		assertTrue(response.contentType().contains(ContentType.JSON.toString()));
		
		// Content is not empty:
		assertFalse(response.getBody().asString().isEmpty());
		
	}
	
}
