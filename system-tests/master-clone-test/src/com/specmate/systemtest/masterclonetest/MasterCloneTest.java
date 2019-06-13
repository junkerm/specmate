package com.specmate.systemtest.masterclonetest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.security.sasl.AuthenticationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

public class MasterCloneTest {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	private static final String LOGIN_JSON = "{\"___nsuri\":\"http://specmate.com/20190125/model/user\",\"className\":\"User\",\"userName\":\"user\",\"passWord\":\"password\",\"projectName\":\"artificial\"}";
	private static final String CEG_JSON = "{\"___nsuri\":\"http://specmate.com/20190125/model/requirements\",\"name\":\"TestCeg1\",\"className\":\"CEGModel\",\"id\":\"TestCeg1\"}";
	private static final String CEG_NODE1_JSON = "{\"condition\":\"Condition3\",\"___nsuri\":\"http://specmate.com/20190125/model/requirements\",\"name\":\"TestCegNode4\",\"variable\":\"Variable2\",\"className\":\"CEGNode\",\"id\":\"TestCegNode4\",\"type\":\"OR\"}";
	private static final String CEG_NODE2_JSON = "{\"condition\":\"Condition6\",\"___nsuri\":\"http://specmate.com/20190125/model/requirements\",\"name\":\"TestCegNode7\",\"variable\":\"Variable5\",\"className\":\"CEGNode\",\"id\":\"TestCegNode7\",\"type\":\"OR\"}";
	private static final String CEG_CONNECTION_JSON = "{\"negate\":false,\"___nsuri\":\"http://specmate.com/20190125/model/requirements\",\"name\":\"TestConnection8\",\"className\":\"CEGConnection\",\"id\":\"TestConnection8\",\"source\":{\"___proxy\":\"true\",\"url\":\"artificial/default/requirement1/TestCeg1/TestCegNode4\"},\"target\":{\"___proxy\":\"true\",\"url\":\"artificial/default/requirement1/TestCeg1/TestCegNode7\"}}";
	private static final String CEG_TESTSPEC_JSON = "{\"___nsuri\":\"http://specmate.com/20190125/model/testspecification\",\"name\":\"TestSpecification9\",\"className\":\"TestSpecification\",\"id\":\"TestSpecification9\"}";
	
//	private static final String LOGIN_JSON_2 = "{\"___nsuri\":\"http://specmate.com/20190125/model/user\",\"className\":\"User\",\"userName\":\"user\",\"passWord\":\"password\",\"projectName\":\"test-data\"}";
//	private static final String TEST_JSON = "[{\"condition\":\"gedrückt\",\"parameter\":{\"___proxy\":true,\"className\":\"TestParameter\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestParameter-1\"},\"___nsuri\":\"http://specmate.com/20190125/model/testspecification\",\"name\":\"ParameterAssignment-1\",\"className\":\"ParameterAssignment\",\"id\":\"ParameterAssignment-1\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestCase-3/ParameterAssignment-1\"},{\"condition\":\"not gedrückt\",\"parameter\":{\"___proxy\":true,\"className\":\"TestParameter\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestParameter-2\"},\"___nsuri\":\"http://specmate.com/20190125/model/testspecification\",\"name\":\"ParameterAssignment-2\",\"className\":\"ParameterAssignment\",\"id\":\"ParameterAssignment-2\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestCase-3/ParameterAssignment-2\"},{\"condition\":\"gedrückt\",\"parameter\":{\"___proxy\":true,\"className\":\"TestParameter\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestParameter-3\"},\"___nsuri\":\"http://specmate.com/20190125/model/testspecification\",\"name\":\"ParameterAssignment-3\",\"className\":\"ParameterAssignment\",\"id\":\"ParameterAssignment-3\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestCase-3/ParameterAssignment-3\"},{\"condition\":\"markiert\",\"parameter\":{\"___proxy\":true,\"className\":\"TestParameter\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestParameter-4\"},\"___nsuri\":\"http://specmate.com/20190125/model/testspecification\",\"name\":\"ParameterAssignment-4\",\"className\":\"ParameterAssignment\",\"id\":\"ParameterAssignment-4\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestCase-3/ParameterAssignment-4\"},{\"condition\":\"not gelöscht\",\"parameter\":{\"___proxy\":true,\"className\":\"TestParameter\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestParameter-5\"},\"___nsuri\":\"http://specmate.com/20190125/model/testspecification\",\"name\":\"ParameterAssignment-5\",\"className\":\"ParameterAssignment\",\"id\":\"ParameterAssignment-5\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestCase-3/ParameterAssignment-5\"},{\"condition\":\"markiert\",\"parameter\":{\"___proxy\":true,\"className\":\"TestParameter\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestParameter-6\"},\"___nsuri\":\"http://specmate.com/20190125/model/testspecification\",\"name\":\"ParameterAssignment-6\",\"className\":\"ParameterAssignment\",\"id\":\"ParameterAssignment-6\",\"url\":\"test-data/evalFolder/EvalRequirement-1/EvalModel-1/947a55cf-7bd7-21cf-5a36-b5003bdbdccc/TestCase-3/ParameterAssignment-6\"}]";
//	private static final String CEG_JSON = "{\"entries\":[{\"deletedObjects\":[],\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"changes\":[{\"newValue\":\"New Node 2019-05-24 11:39:55\",\"feature\":\"name\",\"isCreate\":true,\"isDelete\":false,\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"objectName\":\"New Node 2019-05-24 11:39:55\",\"className\":\"Change\",\"objectType\":\"CEGNode\"}],\"className\":\"HistoryEntry\",\"user\":\"user\",\"timestamp\":\"1558690812479\"},{\"deletedObjects\":[],\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"changes\":[{\"newValue\":\"New Node 2019-05-24 11:39:56\",\"feature\":\"name\",\"isCreate\":true,\"isDelete\":false,\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"objectName\":\"New Node 2019-05-24 11:39:56\",\"className\":\"Change\",\"objectType\":\"CEGNode\"}],\"className\":\"HistoryEntry\",\"user\":\"user\",\"timestamp\":\"1558690812479\"},{\"deletedObjects\":[],\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"changes\":[{\"newValue\":\"New Node 2019-05-24 11:39:58\",\"feature\":\"name\",\"isCreate\":true,\"isDelete\":false,\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"objectName\":\"New Node 2019-05-24 11:39:58\",\"className\":\"Change\",\"objectType\":\"CEGNode\"}],\"className\":\"HistoryEntry\",\"user\":\"user\",\"timestamp\":\"1558690812479\"},{\"deletedObjects\":[],\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"changes\":[{\"newValue\":\"New Connection 2019-05-24 11:40:01\",\"feature\":\"name\",\"isCreate\":true,\"isDelete\":false,\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"objectName\":\"New Connection 2019-05-24 11:40:01\",\"className\":\"Change\",\"objectType\":\"CEGConnection\"}],\"className\":\"HistoryEntry\",\"user\":\"user\",\"timestamp\":\"1558690812479\"},{\"deletedObjects\":[],\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"changes\":[{\"newValue\":\"New Connection 2019-05-24 11:40:02\",\"feature\":\"name\",\"isCreate\":true,\"isDelete\":false,\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"objectName\":\"New Connection 2019-05-24 11:40:02\",\"className\":\"Change\",\"objectType\":\"CEGConnection\"}],\"className\":\"HistoryEntry\",\"user\":\"user\",\"timestamp\":\"1558690812479\"},{\"deletedObjects\":[],\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"changes\":[{\"newValue\":\"CEG\",\"feature\":\"name\",\"isCreate\":true,\"isDelete\":false,\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"objectName\":\"CEG\",\"className\":\"Change\",\"objectType\":\"CEGModel\"}],\"className\":\"HistoryEntry\",\"user\":\"user\",\"timestamp\":\"1558690786401\"}],\"___nsuri\":\"http://specmate.com/20190125/model/history\",\"className\":\"History\"}";
	
	public static void main(String[] args) throws Exception {
		new MasterCloneTest(args);
	}

	private RestClient masterClient;
	private RestClient cloneClient;
	private String currentSessionId;
	private String lastActive;
	private String projectName = "artificial";
	private Process masterProc;
	private Process cloneProc;
	private String master;
	private String clone;
	private String masterArgs;
	private String cloneArgs;
	private String specmate;
	private CommandLine cmd;
	private String urlBeginn = "/services/rest/";
	private String topFolder = "default";

	public MasterCloneTest(String[] args) throws ParseException, IOException, InterruptedException {
		masterClient = new RestClient("http://localhost:8080");
		cloneClient = new RestClient("http://localhost:8081");

		cmd = parseCommandLine(args);
		if (cmd == null) {
			return;
		}

		master = cmd.getOptionValue("m");
		clone = cmd.getOptionValue("c");
		masterArgs = cmd.getOptionValue("am");
		cloneArgs = cmd.getOptionValue("ac");
		specmate = cmd.getOptionValue("s");

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

		masterProc = startSpecmate("master", ANSI_YELLOW, specmate, master, masterArgs);
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
		CommandLine cmdl = parser.parse(options, args);

		if (!cmdl.hasOption("m") || !cmdl.hasOption("c") || !cmdl.hasOption("s")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar masterclonetest.jar", options);
			return null;
		}

		return cmdl;
	}

	private void performTests() {
		try {
//			testLogin();
//			killBoth();
//			restartBoth();
			testCreateModel();
		} catch (Exception e) {
			System.exit(1);
		}
		System.exit(0);
	}

	private void testCreateModel() throws InterruptedException, ParseException, IOException {
		String ceg = "ceg";
		String cegNode1 = "cegNode1";
		String cegNode2 = "cegNode2";
		String connection = "connection";
		String testSpec = "testSpec";
		String testCase = "testCase";
		loginOnMaster();
		verifyLoggedInOnMaster();
		postToMaster(ceg);
		postToMaster(cegNode1);
		postToMaster(cegNode2);
		postToMaster(connection);
		postToMaster(testSpec);
		killMaster();
//		postToMaster(testCase);
		postToClone(testCase);
		restartMaster();
		retrieveNumberOFTestCasesMaster();
		deleteCEGModell();
	}

	private void retrieveNumberOFTestCasesMaster() {
		System.out.println("Retrieve Test Cases from Master");
		RestResult<JSONArray> retrieve = masterClient.getList(urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/TestSpecification9/list");
		
		JSONArray retrievedTestChilds = retrieve.getPayload();
		int numberTestChilds = retrievedTestChilds.length();
		System.out.println("Number of Test Cases: " + numberTestChilds);
		if (numberTestChilds != 4) {
			System.out.println("Not the right amount of Test Cases!");
			System.exit(1);
		}
	}
	
	private void postToMaster(String object) {
		String url = "";
		switch (object) {
		case "ceg":
			System.out.println("Post CEG To Master");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/list";
			masterClient.post(url, new JSONObject(CEG_JSON));
			break;
		case "cegNode1":
			System.out.println("Post CEG Node1 To Master");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/list";
			masterClient.post(url, new JSONObject(CEG_NODE1_JSON));
			break;
		case "cegNode2":
			System.out.println("Post CEG Node2 To Master");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/list";
			masterClient.post(url, new JSONObject(CEG_NODE2_JSON));
			break;
		case "connection":
			System.out.println("Post Connection To Master");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/list";
			masterClient.post(url, new JSONObject(CEG_CONNECTION_JSON));
			break;
		case "testSpec":
			System.out.println("Post Test Specification To Master");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/list";
			masterClient.post(url, new JSONObject(CEG_TESTSPEC_JSON));	
			break;
		case "testCase":
			System.out.println("Post Test Cases To Master");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/TestSpecification9/generateTests";
			masterClient.post(url, null);
			break;
		}
	}
	
	private void postToClone(String object) {
		String url = "";
		switch (object) {
		case "ceg":
			System.out.println("Post CEG to Clone");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/list";
			cloneClient.post(url, new JSONObject(CEG_JSON));
			break;
		case "cegNode1":
			System.out.println("Post CEG Node1 to Clone");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/list";
			cloneClient.post(url, new JSONObject(CEG_NODE1_JSON));
			break;
		case "cegNode2":
			System.out.println("Post CEG Node2 to Clone");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/list";
			cloneClient.post(url, new JSONObject(CEG_NODE2_JSON));
			break;
		case "connection":
			System.out.println("Post Connection to Clone");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/list";
			cloneClient.post(url, new JSONObject(CEG_CONNECTION_JSON));
			break;
		case "testSpec":
			System.out.println("Post Test Specification to Clone");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/list";
			cloneClient.post(url, new JSONObject(CEG_TESTSPEC_JSON));	
			break;
		case "testCase":
			System.out.println("Post Test Cases to Clone");
			url = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/TestSpecification9/generateTests";
			cloneClient.post(url, null);
			break;
		}
	}
	
	private void deleteCEGModell() {
		System.out.println("Delete CEG Model");
		String deleteUrl = urlBeginn + projectName + "/" + topFolder + "/requirement1/TestCeg1/delete";
		masterClient.delete(deleteUrl);
	}

	private void testLogin() throws ParseException, IOException, InterruptedException {
		loginOnMaster();
		verifyLoggedInOnMaster();
		killMaster();
//		killClone();
//		restartClone();
		verifyLoggedInOnClone();
//		restartMaster();
//		verifyLoggedInOnMaster();
//		killMaster();
//		verifyLoggedInOnClone();
		logoutOnClone();
		restartMaster();
//		logoutOnMaster();
		verifyLoggedOutOnMaster();
	}
	
	private void restartBoth() throws ParseException, IOException, InterruptedException {
		restartMaster();
		restartClone();
	}

	private void killBoth() throws InterruptedException {
		killMaster();
		killClone();
	}

	private void killMaster() throws InterruptedException {
		System.out.println("destroy master");
		masterProc.destroy();
		Thread.sleep(60000);
	}
	
	private void killClone() throws InterruptedException {
		System.out.println("destroy clone");
		cloneProc.destroy();
		Thread.sleep(60000);
	}
	
	private void restartMaster() throws ParseException, IOException, InterruptedException {
		System.out.println("restart master");
		masterProc = startSpecmate("master", ANSI_YELLOW, specmate, master, masterArgs);
		Thread.sleep(30000);
	}
	
	private void restartClone() throws ParseException, IOException, InterruptedException {
		System.out.println("restart clone");
		masterProc = startSpecmate("clone", ANSI_GREEN, specmate, clone, cloneArgs);
		Thread.sleep(30000);
	}

	private void loginOnMaster() {
		System.out.println("login master");
		RestResult<JSONObject> result = masterClient.post("/services/rest/login", new JSONObject(LOGIN_JSON));
		currentSessionId = result.getPayload().getString("id");
		lastActive = result.getPayload().getString("lastActive");
		
		String authorizationHeader = "Token " + currentSessionId;

		// TODO:besser
		masterClient.setCookie("specmate-user-token", getSpecmateToken());
		cloneClient.setCookie("specmate-user-token", getSpecmateToken());
		masterClient.setHeader("Authorization", authorizationHeader);
		cloneClient.setHeader("Authorization", authorizationHeader);
	}

	private String getSpecmateToken() {
		projectName = "artificial";
		//projectName = "test-data";
		return "{\"session\":{\"lastActive\":\""
				+ lastActive
				+ "\",\"allowedPathPattern\":\".+services/rest/"
				+ projectName
				+ "/.*\",\"___nsuri\":\"http://specmate.com/20190125/model/user\",\"libraryFolders\":[],\"TargetSystem\":\"NONE\",\"className\":\"UserSession\",\"id\":\""
				+ currentSessionId
				+ "\",\"userName\":\"user\",\"SourceSystem\":\"ALL\",\"url\":\""
				+ currentSessionId
				+ "\"},\"project\":\""
				+ projectName
				+ "\",\"libraryFolders\":[]}";
	}
	
	private void verifyLoggedInOnMaster() throws AuthenticationException {
		RestResult<JSONArray> result = masterClient.getList("services/rest/" + projectName + "/list");
		System.out.println("Status Code Master: " + result.getResponse().getStatus());
		if (result.getResponse().getStatus() != 200) {
			throw new AuthenticationException("Not logged in");
		}
	}
	
	private void verifyLoggedInOnClone() throws AuthenticationException {
		RestResult<JSONArray> result = cloneClient.getList("services/rest/" + projectName + "/list");
		System.out.println("Status Code Clone: " + result.getResponse().getStatus());
		if (result.getResponse().getStatus() != 200) {
			throw new AuthenticationException("Not logged in");
		}
	}
	
	private void logoutOnClone() {
		System.out.println("logout clone");
		RestResult<JSONObject> result = cloneClient.get("/services/rest/logout");
	}
	
	private void logoutOnMaster() {
		System.out.println("logout master");
		RestResult<JSONObject> result = masterClient.get("/services/rest/logout");
	}
	
	private void verifyLoggedOutOnMaster() throws AuthenticationException {
		RestResult<JSONArray> result = masterClient.getList("services/rest/" + projectName + "/list");
		System.out.println("Status Code Master: " + result.getResponse().getStatus());
		if (result.getResponse().getStatus() == 200) {
			throw new AuthenticationException("Still logged in");
		}
	}

}
