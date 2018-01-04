package com.ef;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogFileLine {
	private String date;
	private String ip;
	private String request;
	private String status;
	private String userAgent;
	
	public String getDateAsString() {
		return date;
	}
	public Calendar getDate() {
		try {
			String toConvert = this.date.substring(0, this.date.indexOf("."));
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(fmt.parse(toConvert).getTime());
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	@Override
	public String toString() {
		return "LogFileLine [date=" + date + ", ip=" + ip + ", request=" + request + ", status=" + status
				+ ", userAgent=" + userAgent + "]";
	}
	
}
