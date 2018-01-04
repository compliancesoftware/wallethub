package com.ef.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ef.model.BlockedIp;

public class WalletHubJdbc {
	public static void insert(BlockedIp blockedIp) {
		try {
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO blocked_ips(id,ip,request,status,date,user_agent,comment) VALUES(null,?,?,?,?,?,?)");
			stmt.setString(1, blockedIp.getLine().getIp());
			stmt.setString(2, blockedIp.getLine().getRequest());
			stmt.setString(3, blockedIp.getLine().getStatus());
			
			Timestamp timestamp = new Timestamp(blockedIp.getLine().getDate().getTimeInMillis());
			
			stmt.setTimestamp(4, timestamp);
			stmt.setString(5, blockedIp.getLine().getUserAgent());
			stmt.setString(6, blockedIp.getComment());
			
			stmt.execute();
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Fails trying to close database.");
		}
	}
}
