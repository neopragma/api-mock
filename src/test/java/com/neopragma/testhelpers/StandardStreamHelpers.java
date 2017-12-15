package com.neopragma.testhelpers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StandardStreamHelpers {
	
	private PrintStream origStdout;
	private PrintStream tempStdout;
	private ByteArrayOutputStream data;
	
	public void recordStdout() {
		origStdout = System.out;
		data = new ByteArrayOutputStream();
		tempStdout = new PrintStream(data);
		System.setOut(tempStdout);
	}
	
	public String playbackStdout() {
		System.out.flush();
		return data.toString();
	}
	
	public void resetStdout() {
		System.setOut(origStdout);
		tempStdout = null;
		data = null;
	}
	
	public String playbackAndResetStdout() {
		String output = playbackStdout();
		resetStdout();
		return output;
	}

}
