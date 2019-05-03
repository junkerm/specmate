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

public class MasterCloneTest {
	public static void main(String[] args) throws Exception {
		new MasterCloneTest(args);
	}

	public MasterCloneTest(String[] args) throws ParseException, IOException, InterruptedException {
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

		Process masterProc = startSpecmate("master", specmate, master, masterArgs);
		Process cloneProc = startSpecmate("clone", specmate, clone, cloneArgs);

		Thread.sleep(20000);
	}

	private Process startSpecmate(String name, String specmate, String config, String args) throws IOException {
		ProcessBuilder procBuilder = new ProcessBuilder("java", args, "-jar", specmate, "--configurationFile", config);
		Process process = procBuilder.start();
		System.out.println(name + " started");
		startStreamReader(name, process.getInputStream(), System.out);
		startStreamReader(name, process.getErrorStream(), System.err);
		return process;
	}

	private void startStreamReader(String name, InputStream inputStream, PrintStream outputStream) {
		new Thread() {
			@Override
			public void run() {
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String line;
				try {
					while ((line = reader.readLine()) != null) {
						outputStream.print(name + ": ");
						outputStream.println(line);
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
}
