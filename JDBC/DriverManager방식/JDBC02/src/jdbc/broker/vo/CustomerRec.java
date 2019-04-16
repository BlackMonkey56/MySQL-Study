package jdbc.broker.vo;
/*
 * 고객에 대한 정보를 담고 있는 클래스
 */

import java.util.Vector;

public class CustomerRec {
	private String ssn;
	private String name; //cust_name
	private String address;
	
	private Vector<SharesRec> portfolio;

	public CustomerRec(String ssn, String name, String address, Vector<SharesRec> portfolio) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.address = address;
		this.portfolio = portfolio;
	}

	public CustomerRec(String ssn, String name, String address) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.address = address;
	}
	
	public CustomerRec() {	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Vector<SharesRec> getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Vector<SharesRec> portfolio) {
		this.portfolio = portfolio;
	}

	@Override
	public String toString() {
		return "CustomerRec [ssn=" + ssn + ", name=" + name + ", address=" + address + ", portfolio=" + portfolio + "]";
	}	
}














