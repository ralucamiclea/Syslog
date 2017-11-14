package dirtybits;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Auxiliary {

	//Creates log fileNames according to the config file
	public static String formatFileName(LogServerConfig config, String clientName, String level) throws IOException, UnsupportedEncodingException {
		String fileName = "file";

		if (config.isIncludeClientName()) {
			fileName += "-" + clientName;
		}
		if (config.isIncludeLevel()) {
			fileName += "-" + level;
		}

		return fileName;
	}

	public static String getFileName(LogServerConfig config, LogMessage log) throws UnsupportedEncodingException, IOException {
		String clientName = log.getClient();
		String level = log.getLevel().toString();
		return formatFileName(config, clientName, level);
	}

	public static boolean createDirectoryIfNotExists(String pathStr) {
		File file = new File(pathStr);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdirs();
		}
		return true;
	}

	public static boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static int getMatchedNumber(String fileName, Pattern pattern) {
		Matcher matcher = pattern.matcher(fileName);
		matcher.matches();
		String lastNr = matcher.group(1);
		if (Auxiliary.tryParseInt(lastNr)) {
			return Integer.parseInt(lastNr);
		}
		return 0;
	}
}
