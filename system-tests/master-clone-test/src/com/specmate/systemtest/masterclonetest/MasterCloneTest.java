package com.specmate.systemtest.masterclonetest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

public class MasterCloneTest {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	private static final String LOGIN_JSON = "{\"___nsuri\":\"http://specmate.com/20190125/model/user\",\"className\":\"User\",\"userName\":\"user\",\"passWord\":\"password\",\"projectName\":\"artificial\"}";;

	public static void main(String[] args) throws Exception {
		new MasterCloneTest(args);
	}

	private RestClient masterClient;
	private RestClient cloneClient;
	private String currentSessionId;
	private Process masterProc;
	private Process cloneProc;

	public MasterCloneTest(String[] args) throws ParseException, IOException, InterruptedException {
		masterClient = new RestClient("http://localhost:8080");
		cloneClient = new RestClient("http://localhost:8081");

		CommandLine cmd = parseCommandLine(args);
		if (cmd == null) {
			return;
		}

		String master = cmd.getOptionValue("m");
		String clone = cmd.getOptionValue("c");
		String masterArgs = cmd.getOptionValue("am");
		String cloneArgs = cmd.getOptionValue("ac");
		String specmate = cmd.getOptionValue("s");

		File masterFile = new File(master);
		if (!masterFile.exists()) {
			System.err.println(masterFile.getAbsolutePath() + " does not exist.");
			System.exit(1);
		}

		File cloneFile = new File(clone);
		if (!cloneFile.exists()) {
			System.err.println(cloneFile.getAbsolutePath() + " does not exist.");
			System.exit(1);
		}

		File specmateJar = new File(specmate);
		if (!specmateJar.exists()) {
			System.err.println(specmateJar.getAbsolutePath() + " does not exist.");
			System.exit(1);
		}

		masterProc = startSpecmate("master", ANSI_BLUE, specmate, master, masterArgs);
		Thread.sleep(20000);
		cloneProc = startSpecmate("clone", ANSI_GREEN, specmate, clone, cloneArgs);
		Thread.sleep(20000);

		performTests();

		System.in.read();
		masterProc.destroy();
		cloneProc.destroy();
	}

	private Process startSpecmate(String name, String color, String specmate, String config, String args)
			throws IOException {
		ProcessBuilder procBuilder = new ProcessBuilder("java", args, "-jar", specmate, "--configurationFile", config);
		Process process = procBuilder.start();
		System.out.println(name + " started");
		startStreamReader(name, color, process.getInputStream(), System.out);
		startStreamReader(name, color, process.getErrorStream(), System.err);
		return process;
	}

	private void startStreamReader(String name, String color, InputStream inputStream, PrintStream outputStream) {
		new Thread() {
			@Override
			public void run() {
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String line;
				try {
					while ((line = reader.readLine()) != null) {
						outputStream.println(color + name + ": " + line + ANSI_RESET);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	private CommandLine parseCommandLine(String[] args) throws ParseException {
		// create Options object
		Options options = new Options();

		options.addOption("m", "master", true, "Config file for the master");
		options.addOption("c", "clone", true, "Config file for the clone");
		options.addOption("s", "specmate", true, "Path to the specmate jar file");
		options.addOption("am", "argsMaster", true, "Java system arguments for the master");
		options.addOption("ac", "argsClone", true, "Java system arguments for the clone");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		if (!cmd.hasOption("m") || !cmd.hasOption("c") || !cmd.hasOption("s")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar masterclonetest.jar", options);
			return null;
		}

		return cmd;
	}

	private void performTests() {
		try {
			testLogin();
			// testCreateModel();
		} catch (Exception e) {
			System.exit(1);
		}
		System.exit(0);
	}

	private void testLogin() {
		loginOnMaster();
		verifyLoggedInOnClone();
		killMaster();
		// restartMaster();
		// verifyLoggedInOnMaster();
		// killMaster();
		// verifyLoggedInOnClone();
		// logoutOnClone();
		// restartMaster();
		// verifyLoggedOutOnMaster();
	}

	private void loginOnMaster() {
		RestResult<JSONObject> result = masterClient.post("/services/rest/login", new JSONObject(LOGIN_JSON));
		currentSessionId = result.getPayload().getString("id");

		// TODO:besser
		masterClient.setCookie("specmate-user-token", getSpecmateToken());
		cloneClient.setCookie("specmate-user-token", getSpecmateToken());
	}

	private String getSpecmateToken() {
		return "{\"session\":{\"lastActive\":\"1557920085291\",\"allowedPathPattern\":\".+services/rest/artificial/.*\",\"___nsuri\":\"http://specmate.com/20190125/model/user\",\"libraryFolders\":[],\"TargetSystem\":\"NONE\",\"className\":\"UserSession\",\"id\":\""
				+ currentSessionId
				+ "\",\"userName\":\"asdf\",\"SourceSystem\":\"ALL\",\"url\":\"kd7UcAQMnqsuFb9E3jySCAs3KqwxZTWu\"},\"project\":\"artificial\",\"libraryFolders\":[]}, value);";
	}

	private void killMaster() {
		masterProc.destroy();
	}

	private void verifyLoggedInOnClone() {
		RestResult<JSONObject> result = masterClient.get("services/rest/artificial/list");
		System.out.println(result);
	}

}
