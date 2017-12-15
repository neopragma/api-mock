package com.neopragma.apimock;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class ApiMockRestHandler implements HttpHandler {
	Map<String, RequestResponseData> mockData;

	public ApiMockRestHandler(Map<String, RequestResponseData> mockData) {
		if (mockData == null) {
			throw new IllegalArgumentException("ApiRestHandler requires a non-null Map<String, RequestResponseData> argument");
		}
		this.mockData = mockData;
	}

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
	
	private RequestResponseData getMockData(String requestMethod, String requestURIPath) {
		return mockData.get(requestMethod + "|" + requestURIPath);
	}

}
