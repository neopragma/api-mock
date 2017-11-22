package com.neopragma.apimock;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public abstract class ApiMockTestBase {	
	protected RequestResponseData data;
	protected static final String EMPTY_STRING = "";
	private static HttpServer server;
	private HttpURLConnection conn;
	private static final String BASE_URL = "http://localhost:8000";
	private static final String SLASH = "/";
	private static final String MOCK_DATA_FILENAME = "test.yml";
	
	@BeforeClass
	public static void startServer() throws IOException {
		server = HttpServer.create(new InetSocketAddress(8000), 0);
		   server.createContext("/", new ApiMockRestHandler());
		   server.setExecutor(null); 
		   server.start();		
	}
	
	@AfterClass
	public static void stopServer() throws IOException {
		server.stop(1);
	}
	
	@Before
	public void beforeEach() {
		data = new RequestResponseData();
	}
	
	@After
	public void disconnectFromServer() {
		conn.disconnect();
	}

	protected int sendRequest(String... pathComponents) throws IOException {
		StringBuilder encodedPathComponents = new StringBuilder();
		for (String pathComponent : pathComponents) {
			encodedPathComponents.append(SLASH);
			encodedPathComponents.append(encode(pathComponent));
		}
		URL url = new URL(BASE_URL + encodedPathComponents.toString());
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		return conn.getResponseCode();
	}
	
	protected String readResponse() throws IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String output = "";
		String line;
		while ((line = br.readLine()) != null) {
			output += line;
		}
        return output;
	}	
	
	protected void loadMockData(String requestMethod,
			String requestURIPath,
			String responseBody,
			String responseCode) throws IOException {	
  	    Map<String, RequestResponseData> mockData = new HashMap<String, RequestResponseData>();
  	    mockData.put(requestMethod + "|" + requestURIPath,
		    buildMockData(requestMethod, requestURIPath, responseBody, responseCode));
        YamlWriter yamlWriter = new YamlWriter(new FileWriter(MOCK_DATA_FILENAME));
        yamlWriter.write(mockData);
        yamlWriter.close();
	}
	
	private RequestResponseData buildMockData(
			String requestMethod, 
			String requestURIPath,
			String responseBody,
			String responseCode) {
		RequestResponseData data = new RequestResponseData();
		data.requestMethod = requestMethod;
		data.requestURIPath = requestURIPath;
		data.responseBody = responseBody;
		data.responseCode = responseCode;
		return data;
	}
	
	private String encode(String unencodedString) {
		try {
			return URLEncoder.encode(unencodedString, "UTF-8");
		} catch (Exception e) {
			return unencodedString;
		}
	}

}
