package com.neopragma.apimock;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class ApiMockTest extends ApiMockTestBase {
	
	@Test
	public void can_connect_to_the_server() throws IOException {
		startServerWithMockedResponse("GET", "/", "", "200");
		assertEquals(200, sendRequest(EMPTY_STRING));
		stopServer();
	}
	
	@Test
	public void it_supplies_a_mocked_response() throws IOException {
		startServerWithMockedResponse("GET", "/reverse/text+to+reverse", "esrever ot txet", "200");
	    sendRequest("reverse", "text to reverse");
		assertEquals("esrever ot txet", readResponse());
		stopServer();
	}

}
