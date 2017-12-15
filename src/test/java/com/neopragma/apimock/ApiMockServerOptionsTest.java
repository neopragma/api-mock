package com.neopragma.apimock;

import static org.junit.Assert.assertEquals;

import java.util.logging.Level;

import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.neopragma.testhelpers.StandardStreamHelpers;

public class ApiMockServerOptionsTest {
	
	private ApiMockServer server;
	private StandardStreamHelpers stream;
	private String expectedUsageHelpText = 
		"Usage: java -jar apimock.jar\n" +
		"    -h|help - this usage help\n" +
		"    -d|--data filename\n" +
		"    -l|--loglevel SEVERE|WARNING|INFO|CONFIG|FINE|FINER|FINEST\n" +
		"    -p|--port - server listens on this port\n";
	
	@Before
	public void instantiateServer() {
		server = new ApiMockServer();
	}
	
	@Before
	public void instantiateTestHelpers() {
		stream = new StandardStreamHelpers();
	}

	@Test
	public void it_emits_usage_help_when_option_h_is_passed() throws Exception {
		stream.recordStdout();
		initServerWithOptions("-h");
		assertEquals(expectedUsageHelpText, stream.playbackAndResetStdout());
	}

	@Test
	public void it_emits_usage_help_when_option_help_is_passed() throws Exception {
		stream.recordStdout();
		initServerWithOptions("--help");
		assertEquals(expectedUsageHelpText, stream.playbackAndResetStdout());
	}
	
	@Test
	public void it_honors_the_default_value_of_option_data() throws Exception {
		initServerWithNoOptions();
		assertEquals("test.yml", server.dataFilename);
	}
	
	@Test
	public void it_honors_the_specified_value_of_option_d() throws Exception {
		initServerWithOptions("-d", "mock_data.yml");
		assertEquals("mock_data.yml", server.dataFilename);
	}
	
	@Test
	public void it_honors_the_specified_value_of_option_data() throws Exception {
		initServerWithOptions("--data", "mock_data.yml");
		assertEquals("mock_data.yml", server.dataFilename);
	}

	@Test
	public void it_honors_the_default_value_of_option_loglevel() throws Exception {
		initServerWithNoOptions();
		assertEquals(Level.ALL, server.loglevel);
	}
	
	@Test
	public void it_honors_the_specified_value_of_option_l() throws Exception {
		initServerWithOptions("-l", "INFO");
		assertEquals(Level.INFO, server.loglevel);
	}
	
	@Test
	public void it_honors_the_specified_value_of_option_loglevel() throws Exception {
		initServerWithOptions("--loglevel", "INFO");
		assertEquals(Level.INFO, server.loglevel);
	}

	@Test
	public void it_honors_the_default_value_of_option_port() throws Exception {
		initServerWithNoOptions();
		assertEquals(8000, server.port);
	}
	
	@Test
	public void it_honors_the_specified_value_of_option_p() throws Exception {
		initServerWithOptions("-p", "6500");
		assertEquals(6500, server.port);
	}
	
	@Test
	public void it_honors_the_specified_value_of_option_port() throws Exception {
		initServerWithOptions("--port", "6500");
		assertEquals(6500, server.port);
	}

	private void initServerWithNoOptions() throws ParseException {
		server.init(new String[] {});
	}
	
	private void initServerWithOptions(String...options) throws ParseException {
		server.init(options);
	}
}
