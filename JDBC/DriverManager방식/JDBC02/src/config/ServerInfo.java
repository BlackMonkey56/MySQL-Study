package config;
/*
 * Driver FQCN
 * DB Server URL
 * DB Server USER
 * DB Server PASSWORD
 */
public interface ServerInfo {
	String DRIVER_NAME = "com.mysql.cj.jdbc.Driver"; //public static final
	String Server_URL = "jdbc:mysql://127.0.0.1:3306/scott?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
	String USER = "root";
	String PASS = "1234";
}









