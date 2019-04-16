package jdbc.step2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.ServerInfo;

public class DBConnectionTest2 {
	Connection conn;
	PreparedStatement ps;
	
	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이버 로딩 성공...");
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패...");
		}
	}
	
	public DBConnectionTest2() {
		try {
			conn = DriverManager.getConnection(ServerInfo.Server_URL, ServerInfo.USER, ServerInfo.PASSWORD);
			System.out.println("디비서버 연결 성공...");
			
			String query = "SELECT id, name, address FROM member";
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String address = rs.getString("addresss");
				
				System.out.println(id+"\t"+name+"\t"+address);
			}
			
		}catch(SQLException e) {
			System.out.println("디비서버 연결 실패...");
		}
	}

	public static void main(String[] args) {
		new DBConnectionTest2();
	}

}
