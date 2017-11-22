package com.neopragma.apimock;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;

public class ApiMockTest extends ApiMockTestBase {
	
	@Test
	public void it_connects_to_the_server() throws IOException {
		loadMockData("GET", "/", "", "200");
		assertEquals(200, sendRequest(EMPTY_STRING));
	}
	
	@Test
	public void it_supplies_a_mocked_response() throws IOException {
		loadMockData("GET", "/reverse/text+to+reverse", "esrever ot txet", "200");
	    sendRequest("reverse", "text to reverse");
		assertEquals("esrever ot txet", readResponse());
	}

}
