package com.neopragma.apimock;

import java.util.ArrayList;
import java.util.List;

/**
 * Bag of data elements for mocking an API call.
 * Meant to be intepreted by YamlBeans
 * 
 * @author dave
 */
public class RequestResponseData {
	
	private static final String EMPTY_STRING = "";
	private static final String DEFAULT_METHOD = "GET";
	private static final String DEFAULT_PATH = "/";
	private static final String DEFAULT_RESPONSE_CODE = "200";
	
	public String requestURIPath = DEFAULT_PATH;
	public String requestMethod = DEFAULT_METHOD;
	public List<String> requestHeaders = new ArrayList<String>();
	
	public String responseCode = DEFAULT_RESPONSE_CODE;
	public List<String> responseHeaders = new ArrayList<String>();
	public String responseBody = EMPTY_STRING;

	@Override
	public boolean equals(Object that) {
		if (that == null) {
			return false;
		}
		if (that instanceof RequestResponseData == false) {
			return false;
		}
		if (this == that) {
			return true;
		}
		RequestResponseData other = (RequestResponseData) that;
		if (this.requestURIPath.equals(other.requestURIPath) &&
			this.requestMethod.equals(other.requestMethod) &&
			this.requestHeaders.equals(other.requestHeaders) &&
			this.responseBody.equals(other.responseBody) &&
			this.responseCode.equals(other.responseCode)) {
	            return true;	
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
}
