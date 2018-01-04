package com.ef;

import com.ef.data.WalletHubJdbc;
import com.ef.model.BlockedIp;

public class DataAppender {
	
	private static int prints = 0;
	
	public static void append(LogFileLine line, String comment) {
		prints++;
		if(prints == 10) {
			System.out.print("'");
			prints = 0;
		}
		
		BlockedIp blockedIp = new BlockedIp();
		blockedIp.setLine(line);
		blockedIp.setComment(comment);
		
		WalletHubJdbc.insert(blockedIp);
	}
}
