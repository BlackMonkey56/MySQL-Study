package jdbc.step1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnectionTest1 {
	
	DBConnectionTest1(){
		try {
			//1. 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loading....");
			
			//2. 디비 서버 연결(jdbc프로토콜 사용)
			String url = "jdbc:mysql://127.0.0.1:3306/scott?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
			Connection conn = DriverManager.getConnection(url, "root", "1234");
			System.out.println("DBServer Connection....");
			
			//3. PreparedStatement생성
			//String query = "INSERT INTO member VALUES('ddd', 'Sam','진평동')";
			//PreparedStatement ps = conn.prepareStatement(query); //쿼리문이 컴파일되어질 뿐 실행되진 않는다.
			//String query = "DELETE FROM member WHERE id='ccc'";
			//PreparedStatement ps = conn.prepareStatement(query);
			String query = "UPDATE member SET name='아이유', address='여의도' WHERE id='aaa1'"; //update 이은주2-->아이유로 수정, 진평동-->여의도
			PreparedStatement ps = conn.prepareStatement(query);
			System.out.println("PreparedStatement creating....");
			
			//4. Query문 수행 : DML은 executeUpdate()
			int row = ps.executeUpdate(query);
			System.out.println(row);
			
		}catch(ClassNotFoundException e) {
			System.out.println("Driver Loading Fail....");
		}catch(SQLException e) {
			System.out.println("DBServer Connection Fail....");
			System.out.println("PreparedStatement creating....실패");
			System.out.println("Query 실패....");
		}
	}

	public static void main(String[] args) {
		new DBConnectionTest1();
	}

}
