package com.ef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LogFile{

	private File logFile = null;

	public LogFile(String pathname) {
		this.logFile = new File(pathname);
	}

	public ArrayList<LogFileLine> read() {
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			FileReader reader = new FileReader(logFile);
			BufferedReader br = new BufferedReader(reader);
			
			String line = br.readLine();
			
			while(line != null) {
				lines.add(line);
				line = br.readLine();
			}
			
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("Warning: file was not closed correctely.");
		}
		
		ArrayList<LogFileLine> logFileLines = new ArrayList<LogFileLine>();
		
		if(lines != null && lines.size() > 0) {
			for(String gotLine : lines) {
				String[] lineParts = gotLine.split("\\|");
				LogFileLine logFileLine = new LogFileLine();
				logFileLine.setDate(lineParts[0]);
				logFileLine.setIp(lineParts[1]);
				logFileLine.setRequest(lineParts[2]);
				logFileLine.setStatus(lineParts[3]);
				logFileLine.setUserAgent(lineParts[4]);
				
				logFileLines.add(logFileLine);
			}
		}
		
		return logFileLines;
	}
	
}
