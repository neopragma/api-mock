package com.neopragma.apimock;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class ApiMockRestHandler implements HttpHandler {
	
	private static final String MOCK_DATA_FILENAME = "test.yml";

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {		
		String requestMethod = httpExchange.getRequestMethod();
		String requestURIPath = httpExchange.getRequestURI().getPath();
		RequestResponseData data = getMockData(requestMethod, requestURIPath);
	
		String responseBody = data.responseBody;
		int responseCode = Integer.parseInt(data.responseCode);

        httpExchange.sendResponseHeaders(responseCode, responseBody.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(responseBody.getBytes());
        os.close();	
    }
	
	@SuppressWarnings("unchecked")
	private RequestResponseData getMockData(String requestMethod, String requestURIPath) throws IOException {
		YamlReader yamlReader = new YamlReader(new FileReader(MOCK_DATA_FILENAME));
		Map<String, RequestResponseData> config = (Map<String, RequestResponseData>) yamlReader.read();
		return config.get(requestMethod + "|" + requestURIPath);
	}

}
