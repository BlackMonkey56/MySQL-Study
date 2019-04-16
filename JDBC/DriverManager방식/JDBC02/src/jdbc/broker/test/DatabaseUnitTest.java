package jdbc.broker.test;

import java.util.ArrayList;
import java.util.Vector;

import jdbc.broker.dao.Database;
import jdbc.broker.vo.CustomerRec;
import jdbc.broker.vo.SharesRec;
import jdbc.broker.vo.StockRec;

public class DatabaseUnitTest {

	public static void main(String[] args) throws Exception{
		Database db = new Database("127.0.0.1");
		
		//db.addCustomer(new CustomerRec("777-777", "아이유", "여의도"));		
		//db.deleteCustomer("777-777");
		//db.updateCustomer(new CustomerRec("777-777", "아이유", "봉천동"));
		//   ************ getPortfolio ********************
		Vector<SharesRec> portfolio = db.getPortfolio("111-111");
		for(SharesRec sr : portfolio) {
			System.out.println(sr);
		}
		//   ************ getCustomer ********************
		System.out.println(db.getCustomer("777-777"));
		
		//   ************ getAllCustomers ********************
		ArrayList<CustomerRec> custs = db.getAllCustomers();
		for(CustomerRec cr : custs) {
			System.out.println(cr);
		}
		
		// ************ getAllStocks ********************
		ArrayList<StockRec> stocks = db.getAllStocks();
		for(StockRec sr : stocks) {
			System.out.println(sr);
		}
		
		// ************ buyShares ********************
		/*db.buyShares("777-777", "DUKE", 250);
		Vector<SharesRec> portfolio2 = db.getPortfolio("777-777");
		for(SharesRec sr : portfolio2) {
			System.out.println(sr);
		}*/
		
		// ************ sellShares ********************
		db.sellShares("777-777", "DUKE", 250);
		Vector<SharesRec> portfolio3 = db.getPortfolio("777-777");
		for(SharesRec sr : portfolio3) {
			System.out.println(sr);
		}
	}
}





