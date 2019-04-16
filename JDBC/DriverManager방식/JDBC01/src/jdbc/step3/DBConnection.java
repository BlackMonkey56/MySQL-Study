package jdbc.step3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.ServerInfo;

/*
 * Drive manager방식(현업에서는 안쓰임)
 * DB Access하는 로직만 모아놓겠다.
 * Business Logic
 * 이후에는 MemberDAO로 될 클래스!
 * ::
 * 비즈니스로직 메소드마다
 * 1) 고정적인 부분 - Connection반환, close()
 * 2) 가변적인 부분 - 
 */
public class DBConnection {
	//필드 선언
	//Connection conn; //서비스 하나당 Connection 하나 만들어져야됨
	
	public DBConnection() throws Exception{
		Class.forName(ServerInfo.DRIVER_NAME);
		System.out.println("드라이버...로딩..");
	}
	
	//공통적인 기능1.
	public Connection getConn() throws Exception{
		//디비연결해서 Connection 하나 반환하는 로직을...
		Connection conn = DriverManager.getConnection(ServerInfo.Server_URL, ServerInfo.USER, ServerInfo.PASSWORD);
		System.out.println("Connection OK....");
		
		return conn;
	}
	
	//공통적인 기능2.
	public void closeAll(PreparedStatement ps, Connection conn) throws Exception{
		if(ps != null) ps.close();
		if(conn != null) conn.close();
	}
	
	public void closeAll(PreparedStatement ps, Connection conn, ResultSet rs) throws Exception{
		if(rs != null) rs.close();
		closeAll(ps, conn);
	}
	
	//Business Logic정의
	public void insertMember(String id, String name, String addr) throws Exception{
		Connection conn = getConn();
		String query = "INSERT INTO member VALUES(?,?,?)";
		PreparedStatement ps = conn.prepareStatement(query);
		
		//바인딩
		ps.setString(1, id);
		ps.setString(2, name);
		ps.setString(3, addr);
		
		int row = ps.executeUpdate();
		System.out.println(row+" row INSERT OK!!");
		
		closeAll(ps, conn);
	}
	
	public void deleteMember(String id) throws Exception{
		Connection conn = getConn();
		String query = "DELETE FROM member WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		
		//바인딩
		ps.setString(1, id);
		
		int row = ps.executeUpdate();
		System.out.println(row+" row DELETE OK!!");
		
		closeAll(ps, conn);
	}
	
	public void updateMember(String id, String name, String addr) throws Exception{
		Connection conn = getConn();
		String query = "UPDATE member SET name=?, address=? WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		
		//바인딩
		ps.setString(1, name);
		ps.setString(2, addr);
		ps.setString(3, id);
		
		int row = ps.executeUpdate();
		System.out.println(row+" row UPDATE OK!!");
		
		closeAll(ps, conn);
	}
	
	public void printAllMember() throws Exception{
		Connection conn = getConn();
		String query = "SELECT * FROM member";
		PreparedStatement ps = conn.prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		System.out.println("------------------------");
		System.out.println("id\tname\taddr");
		System.out.println("------------------------");
		while(rs.next()) {
			String id = rs.getString("id");
			String name = rs.getString("name");
			String addr = rs.getString("address");
			
			System.out.println(id+"\t"+name+"\t"+addr);
		}
		System.out.println("------------------------");
		
		closeAll(ps, conn, rs);
	}
}
