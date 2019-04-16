package jdbc.broker.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.transaction.InvalidTransactionException;

import config.ServerInfo;
import jdbc.broker.exception.DuplicateSSNException;
import jdbc.broker.exception.RecordNotFoundException;
import jdbc.broker.vo.CustomerRec;
import jdbc.broker.vo.SharesRec;
import jdbc.broker.vo.StockRec;

public class Database {
	 public Database(String server)throws ClassNotFoundException{
		 Class.forName(ServerInfo.DRIVER_NAME);
		 System.out.println("Driver Loading Success...");		
	 }
	 
	 //// 공통적인 기능  //////////////////
	 public Connection getConnect() throws SQLException{
		Connection conn = DriverManager.getConnection(ServerInfo.Server_URL, 
				ServerInfo.USER, ServerInfo.PASS);
		System.out.println("getConnect....Connection return");		
		 return conn;
	 }	 
	 public void closeAll(PreparedStatement ps, Connection conn)throws SQLException{
		 if(ps != null) ps.close();
		 if(conn != null) conn.close();
	 }
	 public void closeAll(ResultSet rs,PreparedStatement ps, Connection conn)throws SQLException{
		 if(rs != null) rs.close();
			closeAll(ps, conn);
	 }
	 
	 ////////////////  비지니스 로직 ////////////////////////////////////
	 private boolean isExist(String ssn, Connection conn) throws SQLException{
		String query = "SELECT ssn FROM customer WHERE ssn=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, ssn);
		ResultSet rs = ps.executeQuery();	

		return rs.next();
	 }
	 
	 public void addCustomer(CustomerRec cust)throws SQLException, DuplicateSSNException{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnect();
			if(!isExist(cust.getSsn(),conn)) { //고객이 없다면...
				String query = "INSERT INTO customer VALUES(?,?,?)";
				ps=  conn.prepareStatement(query);
				
				ps.setString(1,cust.getSsn());
				ps.setString(2,cust.getName());
				ps.setString(3,cust.getAddress());
				
				System.out.println(ps.executeUpdate()+" 명 insert success...addCustomer");
			}else {
				throw new DuplicateSSNException("이미 그런 사람 있어여: addCustomer");
			}
		}finally{
			closeAll(ps, conn);
		}				 
	 }
	 public void deleteCustomer(String ssn)throws SQLException,RecordNotFoundException{
		 Connection conn = null;
		 PreparedStatement ps = null;		 
		 try{
			 conn = getConnect();
			 if(isExist(ssn,conn)){
				 String query = "DELETE FROM customer WHERE ssn=?";			
				 ps = conn.prepareStatement(query);
				 ps.setString(1,ssn);			
				 System.out.println(ps.executeUpdate()+"명 delete success...deleteCustomer()");
			 }else{
				 throw new RecordNotFoundException("삭제할 사람 없어여..");
			 }

		 }finally{
			 closeAll(ps, conn);			 
		 }
	 }	 
	 public void updateCustomer(CustomerRec cust)throws SQLException, RecordNotFoundException{
		 Connection conn = null;
		 PreparedStatement ps = null;		
		 try{
			 conn = getConnect();
			 String query ="UPDATE customer SET cust_name=?, address=? WHERE ssn=?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, cust.getName());
			 ps.setString(2, cust.getAddress());
			 ps.setString(3, cust.getSsn());
			 int row = ps.executeUpdate();
			 
			 if(row==1) System.out.println(row+" 명 update success...");
			 else throw new RecordNotFoundException("수정할 대상이 없어여..");
		 }finally{
			 closeAll(ps, conn);
		 }
	 }

	 public Vector<SharesRec> getPortfolio(String ssn)throws SQLException{
		 Connection conn = null;
		 PreparedStatement ps = null;	
		 ResultSet rs = null;
		 Vector<SharesRec> v = new Vector<SharesRec>();
		 try{
			 conn = getConnect();
			 String query = "SELECT * FROM shares WHERE ssn=?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1,ssn);
			 rs = ps.executeQuery();
			 while(rs.next()){
				 v.add(new SharesRec(ssn, 
						 			rs.getString("symbol"), 
						 			rs.getInt("quantity")));
			 }			 
		 }finally{
			 closeAll(rs, ps, conn);
		 }
		 return v; 
	 }
	
	 public CustomerRec getCustomer(String ssn)throws SQLException{
		 Connection conn = null;
		 PreparedStatement ps = null;	
		 ResultSet rs = null;
		 CustomerRec cust = null;
		 try{
			 conn = getConnect();
			 String query = "SELECT * FROM customer WHERE ssn=?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1,ssn);
			 rs = ps.executeQuery();
			 if(rs.next()){
				 cust = new CustomerRec(ssn, 
						 				rs.getString("cust_name"), 
						 				rs.getString("address"));
			 }//
			 cust.setPortfolio(getPortfolio(ssn));
		 }finally{
			 closeAll(rs, ps, conn);
		 }
		 return cust;
	 }
	 public ArrayList<CustomerRec> getAllCustomers()throws SQLException{
		 Connection conn = null;
		 PreparedStatement ps = null;	
		 ResultSet rs = null;
		 ArrayList<CustomerRec> list = new ArrayList<CustomerRec>();
		 try{
			 conn=  getConnect();
			 String query = "SELECT * FROM customer";
			 ps = conn.prepareStatement(query);
			 rs = ps.executeQuery();
			 while(rs.next()){
				 list.add(new CustomerRec(rs.getString(1), 
						 				  rs.getString(2), 
						 				  rs.getString(3), 
						 				  getPortfolio(rs.getString(1))));
			 }			 
		 }finally{
			 closeAll(rs, ps, conn);
		 }
		 return list;
	 }
	 
	 public ArrayList<StockRec> getAllStocks()throws SQLException{
		 Connection conn = null;
		 PreparedStatement ps = null;	
		 ResultSet rs = null;
		 ArrayList<StockRec> list = new ArrayList<StockRec>();
		 try{
			 conn = getConnect();
			 String query = "SELECT * FROM stock";
			 ps = conn.prepareStatement(query);
			 rs = ps.executeQuery();
			 while(rs.next()){
				 list.add(new StockRec(rs.getString(1), 
						 			   rs.getFloat(2)));
			 }
		 }finally{
			 closeAll(rs, ps, conn);
		 }
		 return list;
	 }
	 

	 public void buyShares(String ssn, String symbol, int quantity)
	 				throws SQLException{
		 Connection conn = null;
		 PreparedStatement ps = null;	
		 ResultSet rs = null;
		 try{
			 conn = getConnect();
			 String query = "SELECT quantity FROM shares WHERE ssn=? AND symbol=?";
			 ps = conn.prepareStatement(query);
			 ps.setString(1, ssn);
			 ps.setString(2, symbol);
			 rs = ps.executeQuery();
			 if(rs.next()){ //기존의 주식을 얼마정도 갖고 있따.
				 int q = rs.getInt(1); //가지고 있는 주식의 수량
				 int newQuantity = q+quantity; //update문
				 String query1 = "UPDATE shares SET quantity=? WHERE ssn=? AND symbol=?";
				 ps = conn.prepareStatement(query1);
				 ps.setInt(1, newQuantity);
				 ps.setString(2, ssn);
				 ps.setString(3, symbol);
				 System.out.println(ps.executeUpdate()+" row buyShares()....ok");
			 }else{ //주식이 없다
				 String query2 = "INSERT INTO shares VALUES(?,?,?)";
				 ps = conn.prepareStatement(query2);
				 ps.setString(1, ssn);
				 ps.setString(2, symbol);
				 ps.setInt(3, quantity);
				 System.out.println(ps.executeUpdate()+" row buyShares()...insert ok");
			 } 
		 }finally{
			 closeAll(rs, ps, conn);
		 }
				 
	 }
	 public void sellShares(String ssn, String symbol, int quantity)
			 throws SQLException,RecordNotFoundException,InvalidTransactionException{
		 Connection conn = null;
		 PreparedStatement ps = null;	
		 ResultSet rs = null;
		 try{
			 conn=  getConnect();
			 if(!isExist(ssn,conn)){
				 throw new RecordNotFoundException("주식을 팔려는 사람이 없어여..");
			 }else{
				 String query = "SELECT quantity FROM shares WHERE ssn=? AND symbol=?";
				 ps = conn.prepareStatement(query);
				 ps.setString(1, ssn);
				 ps.setString(2, symbol);
				 rs = ps.executeQuery();
				 rs.next(); //quantity 부분을 일단 가리키게 한다.
				 int q = rs.getInt(1); //100개 가지고 있다.
				 int newQuantity=q - quantity;//팔고 남은 주식의 수량..update
				 if(q==quantity){
					 //delete
					 String query1 = "DELETE FROM shares WHERE ssn=? AND symbol=?";
					 ps = conn.prepareStatement(query1);
					 ps.setString(1, ssn);
					 ps.setString(2, symbol);
					 System.out.println(ps.executeUpdate()+" row shares DELETE...1.");
				 }else if(q>quantity){
					 //update
					 String query2 = "UPDATE shares SET quantity=? WHERE ssn=? AND symbol=?";
					 ps = conn.prepareStatement(query2);
					 ps.setInt(1, newQuantity);
					 ps.setString(2, ssn);
					 ps.setString(3, symbol);
					 System.out.println(ps.executeUpdate()+" row shares UPDATE..2");
				 }else{ //q<quantity인 경우..
					 //예외
					 throw new InvalidTransactionException("팔려는 주식의 수량이 넘 많아여..");
				 }
			 }
		 }finally{
			 closeAll(ps, conn);			 
		 }
	 } 	
}//class


















