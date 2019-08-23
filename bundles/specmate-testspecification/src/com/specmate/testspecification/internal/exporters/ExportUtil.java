package com.specmate.testspecification.internal.exporters;

import java.util.regex.Pattern;

/** Utility methods for exporting test specifications and procedures */
public class ExportUtil {

	/** Char to wrap text fields in */
	public static final String CSV_TEXT_WRAP = "\"";
	/** Char to separate columns */
	public static final String CSV_COL_SEP = ";";
	/** Char used for line end */
	public static final String CSV_LINE_SEP = "\n";

	/** pattern that detects a number at the beginning */
	private static Pattern startsNumerical = Pattern.compile("^[0-9]");

	/** invalid characters for test names */
	private static Pattern invalidChars = Pattern.compile("[^a-zA-Z_0-9\\_]");

	/** Replaces potentially invalid characters for names */
	public static String replaceInvalidChars(String name) {
		name = startsNumerical.matcher(name).replaceAll("");
		return invalidChars.matcher(name).replaceAll("_");
	}

}
