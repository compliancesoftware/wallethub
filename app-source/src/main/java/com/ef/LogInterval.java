package com.ef;

public enum LogInterval {
	HOURLY("hourly"),
	DAILY("daily");
	
	private String interval = "";
	
	LogInterval(String interval) {
		this.interval = interval;
	}
	
	@Override
	public String toString() {
		return this.interval;
	}
	
}
