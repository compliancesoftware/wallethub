package com.ef.model;

import com.ef.LogFileLine;

public class BlockedIp {
	private LogFileLine line;
	
	private String comment;

	public LogFileLine getLine() {
		return line;
	}

	public void setLine(LogFileLine line) {
		this.line = line;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "BlockedIp [line=" + line.toString() + ", comment=" + comment + "]";
	}
	
}
