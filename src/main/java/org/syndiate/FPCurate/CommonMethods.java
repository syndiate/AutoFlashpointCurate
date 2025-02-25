package org.syndiate.FPCurate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.syndiate.FPCurate.gui.common.dialog.ErrorDialog;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;



public class CommonMethods {
	
	
	
	
	public static String getResource(String filePath) {
		
		if (filePath == null) {
			return null;
		}
		
		InputStream is = I18N.class.getClassLoader().getResourceAsStream(filePath);
		if (is == null) {
			return null;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		
		String currentLine = "";
		StringBuilder resourceContents = new StringBuilder("");
		
		try {
			while ((currentLine = br.readLine()) != null) {
				resourceContents.append(currentLine);
			}
			is.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return resourceContents.toString();
		
	}
	
	
	
	public static byte[] getResourceByte(String filePath) {
		if (filePath == null) {
			return null;
		}
		
		InputStream is = I18N.class.getClassLoader().getResourceAsStream(filePath);
		if (is == null) {
			return null;
		}
		
		try {
			return IOUtils.toByteArray(is);
		} catch (IOException ex) {
			new ErrorDialog(ex);
		}
		
		return null;

	}
	
	
	
	
	
	
	
	
	
	
	public static void runExecutable(String filePath, String command, boolean commandLine, boolean wait) {
		
		InputStream inputStream = CommonMethods.class.getClassLoader().getResourceAsStream(filePath);
		String tempFileSuffix = "";
		switch (CommonMethods.getOperatingSystem()) {
			case "Windows":
				tempFileSuffix = ".exe";
				break;
			case "Linux":
			case "Mac":
				tempFileSuffix = ".bin";
				break;
		}
		
		
		
		File tempFile;
		try {
			tempFile = File.createTempFile("AutoFPCurator", tempFileSuffix);
			
			if (CommonMethods.getOperatingSystem().equals("Mac") || CommonMethods.getOperatingSystem().equals("Linux")) {
				Files.setPosixFilePermissions(tempFile.toPath(), PosixFilePermissions.fromString("rwxr-x---"));
			}
			
		} catch (IOException e) {
			new ErrorDialog(e);
			return;
		}
		
		
		
//		tempFile.deleteOnExit();

		
		try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
		    byte[] buffer = new byte[1024];
		    int length;
		    while ((length = inputStream.read(buffer)) > 0) {
		        outputStream.write(buffer, 0, length);
		    }
		    outputStream.close();
		} catch (IOException e) {
			new ErrorDialog(e);
			return;
		}
		
		
		
		
		String cmdProgram = "";
		String cFlag = "-c";
		switch (CommonMethods.getOperatingSystem()) {
			case "Windows": 
				cmdProgram = "cmd.exe";
				cFlag = "/c";
				break;
			case "Mac":
				cmdProgram = "sh";
				break;
			case "Linux":
				cmdProgram = "bash";
				break;
			default:
				return;
		}
		
		ProcessBuilder processBuilder = !commandLine ? new ProcessBuilder(tempFile.getAbsolutePath(), command)
								: new ProcessBuilder(cmdProgram, cFlag, tempFile.getAbsolutePath() + " " + command);
		processBuilder.redirectErrorStream(true);
		
		
		Process process;
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			new ErrorDialog(e);
			return;
		}
		if (!wait) {
			return;
		}
		
		
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			new ErrorDialog(e);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	public static Object parseJSONStr(String json) {
		
		Object jsonObj;
		
		try {
			jsonObj = new Gson().fromJson(json, Object.class);
		} catch (JsonSyntaxException e) {
			new ErrorDialog(e);
			return null;
		}
		
		return jsonObj;
		
	}
	
	
	
	
	
	
	public static String getFileExtension(File file) {
		
		if (file == null) {
			return "";
		}
		
		String fileName = file.getName();
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex <= 0) {
			return "";
		}
		

		String fileExtension = fileName.substring(lastDotIndex + 1);
		return fileExtension;
		
	}
	
	
	
	
	
	public static String correctURL(String url) {
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = "http://" + url;
		}
		return url;
	}
	
	
	
	
	
	// the string is split and joined to prevent any stray semicolons (separators) at the start or end of the value
	public static String correctSeparators(String input, String delimiter) {
		return String.join(delimiter + " ", new ArrayList<String>(Arrays.asList(input.split(delimiter))));
	}
	
	
	
	
	
	public static boolean isValidDate(String date) {
		
		if (date == null) {
			return false;
		}
		
	    DateTimeFormatter[] formatters = {
	            new DateTimeFormatterBuilder().appendPattern("yyyy")
	                    .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
	                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
	                    .toFormatter(),
	            new DateTimeFormatterBuilder().appendPattern("yyyy-MM")
	                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
	                    .toFormatter(),
	            new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
	                    .parseStrict().toFormatter() 
	    };
	    
	    for (DateTimeFormatter formatter : formatters) {
	        try {
	            LocalDate.parse(date, formatter);
	            return true;
	        } catch (DateTimeParseException e) {
	        }
	    }
	    return false;
	}
	
	
	
	
	
	
	// Returns true or false depending on whether or not the first parameter is a newer version than the second parameter
	public static boolean compareVersions(String version1, String version2) {
        String[] version1Parts = version1.replaceAll("v", "").split("\\.");
        String[] version2Parts = version2.replaceAll("v", "").split("\\.");

        int length = Math.max(version1Parts.length, version2Parts.length);
        for (int i = 0; i < length; i++) {
            int v1 = i < version1Parts.length ? Integer.parseInt(version1Parts[i]) : 0;
            int v2 = i < version2Parts.length ? Integer.parseInt(version2Parts[i]) : 0;

            if (v1 > v2) {
                return true;
            } else if (v1 < v2) {
                return false;
            }
        }

        return false;
    }
	
	
	
	
	
	
	
	public static String getOperatingSystem() {
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            return "Windows";
        } else if (os.contains("mac")) {
            return "Mac";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return "Linux";
        } else {
            return "Unknown";
        }
    }

	
	

}
