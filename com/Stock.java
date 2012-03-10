package com;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Stock {

	private String name;
	private String stockID;
	private double price;
	private int basePrice;
	private int maxPrice;
	private int minPrice;
	private int volatility;
	
	private boolean exists;
	
	public Stock (String name) {
		this.stockID = name;
		
		exists = getInfo();
	}
	
	public boolean getInfo () {
		// FIND THIS STOCK IN THE DB IF IT EXISTS
		MySQL mysql = new MySQL();
		
		PreparedStatement stmt = mysql.prepareStatement("SELECT * FROM stocks WHERE stockID LIKE ? ");
		try {
			stmt.setString(1, stockID);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet result = mysql.query(stmt);
		
		try {
			while (result.next()) {
				// WE FOUND IT, STORE SOME INFO
				name = result.getString("name");
				price = result.getInt("price");
				basePrice = result.getInt("basePrice");
				maxPrice = result.getInt("maxPrice");
				minPrice = result.getInt("minPrice");
				volatility = result.getInt("volatility");
				mysql.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		mysql.close();
		
		return false;
	}
	
	public boolean add (String name, String stockID, int baseprice, int maxprice, int minprice, int volatility) {
		MySQL mysql = new MySQL();
		mysql.execute("ALTER TABLE players ADD COLUMN " + stockID + " INT DEFAULT 0");
		
		PreparedStatement stmt = mysql.prepareStatement("INSERT INTO stocks (name, stockID, price, basePrice, maxPrice, minPrice, volatility) VALUES (?, ?, ?, ?, ?, ?, ?)");
		try {
			stmt.setString(1, name);
			stmt.setString(2, stockID);
			stmt.setInt(3, baseprice);
			stmt.setInt(4, baseprice);
			stmt.setInt(5, maxprice);
			stmt.setInt(6, minprice);
			stmt.setInt(7, volatility);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		mysql.execute(stmt);
		mysql.close();
		
		return true;
	}
	
	public boolean remove () {
		MySQL mysql = new MySQL();
		
		mysql.execute("ALTER TABLE players DROP COLUMN " + stockID);
		
		PreparedStatement stmt = mysql.prepareStatement("DELETE FROM stocks WHERE StockID LIKE ?");
		try {
			stmt.setString(1, stockID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		mysql.execute(stmt);
		
		mysql.close();
		
		return true;
	}
	
	public boolean exists() {
		return this.exists;
	}
	
	public int getMinPrice() {
		return this.minPrice;
	}
	
	public int getMaxPrice() {
		return this.maxPrice;
	}
	
	public int getBasePrice() {
		return this.basePrice;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public int getVolatility() {
		return this.volatility;
	}
	
	public String getID() {
		return this.stockID.toUpperCase();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toID() {
		return this.stockID.toUpperCase();
	}
	
	public String toString() {
		return this.name;
	}
	
}