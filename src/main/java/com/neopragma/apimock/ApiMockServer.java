package com.neopragma.apimock;

import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
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

import com.esotericsoftware.yamlbeans.YamlReader;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class ApiMockServer {		
	private static final String MOCK_DATA_FILENAME = "test.yml";
	private static final Logger LOGGER = Logger.getLogger("ApiMockServer");
	private HttpServer server;
	private Options options;
	String dataFilename = "test.yml";
	int port = 8000;
	Level loglevel = Level.ALL;
	boolean runServer = true;
	Map<String, RequestResponseData> mockData;
	
	public void startServer(String[] args) throws IOException, ParseException {
		init(args);
		if (runServer == false) {
			return;
		}
		logConfig();
		server = HttpServer.create(new InetSocketAddress(port), 0);
		   server.createContext("/", new ApiMockRestHandler(mockData));
		   server.setExecutor(null); 
		   server.start();		
	}
	
	void init(String[] args) throws ParseException, IOException {
		initOptions(args);
		if (runServer) {
 		    initLogging();
 		    mockData = getMockData();
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, RequestResponseData> getMockData() throws IOException {
		YamlReader yamlReader = new YamlReader(new FileReader(MOCK_DATA_FILENAME));
		return (Map<String, RequestResponseData>) yamlReader.read();
	}
	
	private void initOptions(String[] args) throws ParseException {
		options = new Options();
		options.addOption("h", "help", false, "Display usage help on stdout");
		options.addOption(new Option("d", "data", true, "Filename of yaml file containing mock data"));
		options.addOption(new Option("l", "loglevel", true, "Logger level"));
		options.addOption(new Option("p", "port", true, "Port server will listen on"));
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse( options, args);
		if (cmd.hasOption("help")) {
			outputUsageHelp();
			runServer = false;
	    }
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
	
	private void outputUsageHelp() {
		stdout("Usage: java -jar apimock.jar");
		stdout("    -h|help - this usage help");
		stdout("    -d|--data filename");
		stdout("    -l|--loglevel SEVERE|WARNING|INFO|CONFIG|FINE|FINER|FINEST");
		stdout("    -p|--port - server listens on this port");
	}
	
	private void stdout(String message) {
		System.out.println(message);
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		ApiMockServer app = new ApiMockServer();
		app.startServer(args);
	}

}
