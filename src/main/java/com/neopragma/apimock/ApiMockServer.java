package com.neopragma.apimock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class ApiMockServer {	
	private static final Logger LOGGER = Logger.getLogger("ApiMockServer");
	private HttpServer server;
	private Options options;
	private String dataFilename = "test.yml";
	private int port = 8000;
	private Level loglevel = Level.ALL;
	
	public void startServer(String[] args) throws IOException, ParseException {
		init(args);
		logConfig();
		server = HttpServer.create(new InetSocketAddress(port), 0);
		   server.createContext("/", new ApiMockRestHandler());
		   server.setExecutor(null); 
		   server.start();		
	}
	
	private void init(String[] args) throws ParseException {
		initOptions(args);
		initLogging();
	}
	
	private void initOptions(String[] args) throws ParseException {
		options = new Options();
		options.addOption(new Option("d", "data", true, "Filename of yaml file containing mock data"));
		options.addOption(new Option("l", "loglevel", true, "Logger level"));
		options.addOption(new Option("p", "port", true, "Port server will listen on"));
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse( options, args);
		if (cmd.hasOption("data")) {
			dataFilename = cmd.getOptionValue("data");
		}
		if (cmd.hasOption("loglevel")) {
			loglevel = Level.parse(cmd.getOptionValue("loglevel"));
		}
		if (cmd.hasOption("port")) {
			port = Integer.parseInt(cmd.getOptionValue("port"));
		}
	}
	
	private void initLogging() {
		Handler logHandler = new ConsoleHandler();
		logHandler.setLevel(Level.ALL);
		logHandler.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(logHandler);
		LOGGER.setLevel(Level.ALL);
	}
	
	private void logConfig() {
		LOGGER.log(Level.INFO, "ApiMockServer starting");
		StringBuilder configSettings = new StringBuilder();
		configSettings.append("data: " + dataFilename);
		configSettings.append(System.getProperty("line.separator") + "\t");
		configSettings.append("loglevel: " + loglevel);
		configSettings.append(System.getProperty("line.separator") + "\t");
		configSettings.append("port: " + String.valueOf(port));
		LOGGER.log(Level.CONFIG, configSettings.toString());
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		ApiMockServer app = new ApiMockServer();
		app.startServer(args);
	}

}
