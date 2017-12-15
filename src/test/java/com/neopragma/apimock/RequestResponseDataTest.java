package com.neopragma.apimock;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

public class RequestResponseDataTest {

	@SuppressWarnings("unchecked")
	@Test
	public void it_serializes_and_deserializes_a_data_object() throws IOException {
		RequestResponseData data = new RequestResponseData();
		data.requestMethod = "POST";
		data.requestURIPath = "/check";
		data.responseCode = "403";
		data.requestHeaders.add("Content-Type: application/json");
		data.requestHeaders.add("Accept-Charset: utf-8");
		Map<String, RequestResponseData> mockData = new HashMap<String, RequestResponseData>();
		mockData.put(data.requestMethod + "|" + data.requestURIPath, data);
		StringWriter stringWriter = new StringWriter();
	    YamlWriter yamlWriter = new YamlWriter(stringWriter);
	    yamlWriter.write(mockData);
	    yamlWriter.close();
	    YamlReader yamlReader = new YamlReader(stringWriter.toString());
	    
	    Map<String, RequestResponseData> serializedData = (Map<String, RequestResponseData>) yamlReader.read();
	    RequestResponseData result = serializedData.get("POST|/check");
	    assertEquals(data, result);
	}
}
