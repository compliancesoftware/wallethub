package com.ef;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Parser {
	
	private String accessLog = null;
	private Calendar startDate = null;
	private String duration = null;
	private int threshold = 0;
	
	public String getAccessLog() {
		return accessLog;
	}

	public void setAccessLog(String accessLog) {
		this.accessLog = accessLog;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public String getStartDateAsString() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(this.startDate.getTime());
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public String toString() {
		return "Parser [accessLog=" + accessLog + ", startDate=" + getStartDateAsString() + ", duration=" + duration + ", threshold="
				+ threshold + "]";
	}

	public static void main(String[] args) {
		System.out.println("\nLoading log file...");
		
		Parser parser = new Parser();
		
		for(String arg : args) {
			String argName = arg.split("=")[0];
			String argValue = arg.split("=")[1];
			
			if(argName.equals("--accesslog")) {
				parser.setAccessLog(argValue);
			}
			else if(argName.equals("--startDate")){
				try {
					SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date = argValue.replace(".", " ");
					Calendar startDate = Calendar.getInstance();
					startDate.setTimeInMillis(fmt.parse(date).getTime());
					parser.setStartDate(startDate);
				} catch (ParseException e) {
					System.out.println("Wrong date format.");
					System.exit(0);
				}
			}
			else if(argName.equals("--duration")) {
				if(!argValue.equals(LogInterval.HOURLY.toString()) && !argValue.equals(LogInterval.DAILY.toString())) {
					System.out.println("Wrong duration. [please type '"+LogInterval.HOURLY+"' or '"+LogInterval.DAILY+"' values]");
					System.exit(0);
				}
				else {
					parser.setDuration(argValue);
				}
			}
			else if(argName.equals("--threshold")) {
				try {
					parser.setThreshold(Integer.parseInt(argValue));
				} catch(Exception e) {
					System.out.println("Wrong value for threshold. Must be an Integer value.");
					System.exit(0);
				}
			}
			else {
				System.out.println("Unknow argument: "+argName);
				System.exit(0);
			}
		}
		
		LogFile logFile = new LogFile(parser.getAccessLog());
		ArrayList<LogFileLine> allLogs = logFile.read();
		ArrayList<LogFileLine> logsByInterval = new ArrayList<LogFileLine>();
		
		for(LogFileLine line : allLogs) {
			Calendar startDate = parser.getStartDate();
			Calendar endDate = Calendar.getInstance();
			
			if(parser.getDuration().equals(LogInterval.HOURLY.toString())) {
				endDate.setTimeInMillis(startDate.getTimeInMillis() + 3600000L);
			}
			else if(parser.getDuration().equals(LogInterval.DAILY.toString())) {
				endDate.setTimeInMillis(startDate.getTimeInMillis() + 86400000L);
			}
			
			if(line.getDate().before(endDate) && line.getDate().after(startDate)) {
				logsByInterval.add(line);
			}
		}
		
		ArrayList<String> ips = new ArrayList<String>();
		HashMap<String,Integer> blockMap = new HashMap<String,Integer>();
		
		for(LogFileLine line : logsByInterval) {
			String ip = line.getIp();
			int requests = 0;
			if(blockMap.get(ip) != null) {
				requests = blockMap.get(ip) + 1;
			}
			else {
				requests = 1;
			}
			blockMap.put(ip, requests);
			if(!ips.contains(ip)) {
				ips.add(ip);
			}
		}
		
		for(String ip : ips) {
			int requests = blockMap.get(ip);
			int threshold = parser.getThreshold();
			if(requests > threshold) {
				String comment = "IP Address blocked by made more than "+threshold+" requests in ";
				if(parser.getDuration().equals(LogInterval.HOURLY.toString())) {
					comment += "a hour.";
				}
				else if(parser.getDuration().equals(LogInterval.DAILY.toString())) {
					comment += "a day.";
				}
				comment += "["+requests+" requests]";
				
				System.out.println("\n\nBlocking IP "+ip+", reason: "+comment+"");
				
				System.out.print("Appending to database: ");
				
				for(LogFileLine line : logsByInterval) {
					if(line.getIp().equals(ip)) {
						DataAppender.append(line, comment);
					}
				}
			}
		}
		
		System.exit(0);
	}
}
