package jdbc.step4;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

/*
 * 이번 예제에서는 디비서버의 정보와 sql구문의 정보를 가지고 있는 
 * properties파일을 이용해서 비즈니스 로직을 작성해보겠다.
 * 앞으로 우리가 배우게 될 프레임워크 기술에서는 이런 메타데이터에 정보를 저장하고
 * 이 저장된 정보를 끌어다 쓰는 기술들이 모두 라이브러리화 되어져서 제공된다.
 * 여기서는 그러한 메커니즘을 이해할 수 있는 시간을 가져보겠다.
 */

public class DBConnectionTest4 {

	public static void main(String[] args) throws Exception {
		Properties p = new Properties();
		
		//파일시스템일 경우 반드시 src를 경로에 포함시켜줘야함.
		p.load(new FileInputStream("src/config/db.properties"));
		
		String driver = p.getProperty("jdbc.mysql.driver");
		String url = p.getProperty("jdbc.mysql.url");
		String user = p.getProperty("jdbc.mysql.user");
		String pass = p.getProperty("jdbc.mysql.pass");
		
		String insertQuery = p.getProperty("jdbc.sql.insert");
		String deleteQuery = p.getProperty("jdbc.sql.delete");
		String updateQuery = p.getProperty("jdbc.sql.update");
		
		Class.forName(driver);
		System.out.println("드라이버 로딩...");
		
		Connection conn = DriverManager.getConnection(url, user, pass);
		System.out.println("서버 연결...");
		
		PreparedStatement ps = conn.prepareStatement(insertQuery);
		ps.setString(1, "1234");
		ps.setString(2, "일일일");
		ps.setString(3, "이이이");
		
		int row = ps.executeUpdate();		
		System.out.println(row + " row");
	}

}
