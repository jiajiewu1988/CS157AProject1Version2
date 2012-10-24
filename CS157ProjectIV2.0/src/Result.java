

import java.sql.*;
import java.util.*;

/**
 * Fetch the result by query statement, return ArrayLists
 * @author jerry wu, Sean Peng
 *
 */
public class Result implements Comparator<String>{
	
	private static String[] columnLabel;
	
	
	/**
	 * Set a private constructor
	 */
	private Result() {
		
	}

	
	/**
	 * Compare 2 Strings
	 * @param o1 string to compare
	 * @param o2 String to compare
	 * @return positive number if o1 > o2, negative number if o1 < o2, 0 if o1 = o2
	 */
	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}
	
	
	/**
	 * Return the list of column name from the latest queried table
	 * @return list of column name
	 */
	public static String[] getColumnLableFromLatestQueriedTable() {
		return columnLabel;
	}

	
	/**
	 * Get an ArrayList of of AutoMaker
	 * @return AutoMaker List
	 */
	public static String[] getAutoMaker() {
		ArrayList<String> mList = new ArrayList<String>();
		try {
			DBOperation dbop = new DBOperation();
			ResultSet rs = dbop.queryMaker();
			mList = constructList(rs);
			Collections.sort(mList, new Result());
			dbop.disconnectFromDB();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return mList.toArray(new String[mList.size()]);
	}

	
	/**
	 * Get a list of model according to auto maker
	 * @param maker Auto Maker
	 * @return a list of model
	 */
	public static String[] getModel(String maker) {
		ArrayList<String> mList = new ArrayList<String>();
		try {
			DBOperation dbop = new DBOperation();
			ResultSet rs = dbop.queryModel(maker);
			mList = constructList(rs);
			Collections.sort(mList, new Result());
			dbop.disconnectFromDB();
		} catch (SQLException e) {
			System.err.println("Error in Result.getModel: " + e.getMessage());
		}
		return mList.toArray(new String[mList.size()]);
	}

	
	/**
	 * Get a list of year according to auto maker, model
	 * @param maker auto maker
	 * @param model auto model
	 * @return a list of year
	 */
	public static String[] getYear(String maker, String model) {
		ArrayList<String> yl = new ArrayList<String>();
		try {
			DBOperation dbop = new DBOperation();
			ResultSet rs = dbop.queryYear(maker, model);
			yl = constructList(rs);
			Collections.sort(yl, new Result());
			dbop.disconnectFromDB();
		} catch (SQLException e) {
			System.err.println("Error in Result.getYear: " + e.getMessage());
		}
		return yl.toArray(new String[yl.size()]);
	}

	
	/**
	 * Get a list of engine type according to auto maker, model, and year
	 * @param maker auto maker
	 * @param model car model
	 * @param year build year
	 * @return a list of engine
	 */
	public static String[] getEngineType(String maker, String model, String year) {
		ArrayList<String> et = new ArrayList<String>();
		try {
			DBOperation dbop = new DBOperation();
			ResultSet rs = dbop.queryEngineType(maker, model, year);
			et = constructList(rs);
			Collections.sort(et, new Result());
			dbop.disconnectFromDB();
		} catch (SQLException e) {
			System.err.println("Error in Result.getEngineType: " + e.getMessage());
		}
		return et.toArray(new String[et.size()]);
	}

	
	/**
	 * Get a list of vendor's name
	 * @return a list of vendor's name
	 */
	public static String[] getVendor() {
		
		ArrayList<String> vList = new ArrayList<String>();
		
		try {
			DBOperation dbop = new DBOperation();
			ResultSet rs = dbop.queryVendor();
			vList = constructList(rs);
			Collections.sort(vList, new Result());
			dbop.disconnectFromDB();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return vList.toArray(new String[vList.size()]);
	}	
	
	
	/**
	 * Get a list of part number according to part vendor's name
	 * @param vendor vendor's name
	 * @return a list of part number
	 */
	public static String[] getPartNumber(String vendor) {
		
		ArrayList<String> pl = new ArrayList<String>();
		
		try {
			DBOperation dbop = new DBOperation();
			ResultSet rs = dbop.queryPartNumber(vendor);
			pl = constructList(rs);
			Collections.sort(pl, new Result());
			dbop.disconnectFromDB();
		} catch (SQLException e) {
			System.err.println("Error in Result.getPartNumber: " + e.getMessage());
		}
		
		return pl.toArray(new String[pl.size()]);
	}
	
	
	/**
	 * Get a list of part specification according to part vendor's name, part number
	 * @param vendor vendor's name
	 * @param partNumber part number
	 * @return a list of part specification
	 */
	public static String[] getPartSpec(String vendor, String partNumber) {
		
		ArrayList<String> pl = new ArrayList<String>();
		ArrayList<String> colName = new ArrayList<String>();
		
		try {
			DBOperation dbop = new DBOperation();
			ResultSet rs = dbop.queryPartSpec(vendor, partNumber);			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			for(int i=1; i<=rsmd.getColumnCount(); i++) {
				colName.add(rsmd.getColumnLabel(i));
			}
			
			columnLabel = colName.toArray(new String[colName.size()]);
			pl = constructList(rs, rsmd.getColumnCount());
			dbop.disconnectFromDB();
		} catch (SQLException e) {
			System.err.println("Error in Result.getPartSpec: " + e.getMessage());
		}
		
		return pl.toArray(new String[pl.size()]);
	}
	
	/**
	 * Sample get Description - need to modify
	 * @param maker
	 * @param model
	 * @param year
	 * @return 2 dimensional array stores table with attributes: DESCRIPTION, LITRES, ENGINE_TYPE, CUBIC_INCHES, RLINK
	 * @throws SQLException
	 */
	public static String[][] getAllDesc(String maker, String model, String year) throws SQLException{
		DBOperation dbop = new DBOperation();
		ResultSet rs = dbop.queryDescription(maker, model, year);
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int row_size = 0;
		int col_size = rsmd.getColumnCount();
		if (rs != null) {
			rs.beforeFirst();
			rs.last();
			row_size = rs.getRow();
			rs.beforeFirst();
		}
		String[][] table = new String[row_size][col_size];
		
		int i = 0;
		while (rs.next()) {
			for (int j = 0; j < col_size; j++) {
				table[i][j] = rs.getString(j+1);
			}
			i++;
		}
		
		dbop.disconnectFromDB();
		
		return table;

	}
	
	/**
	 * Construct the ArrayList with no duplicate items by the result set from oracle
	 * @param rs contains list items fetched from oracle
	 * @return ArrayList with no duplicate items
	 */
	private static ArrayList<String> constructList(ResultSet rs) throws SQLException{
		ArrayList<String> list = new ArrayList<String>();
		while (rs.next()) {
			if (!list.contains(rs.getString(1)))
				list.add(rs.getString(1));
		}
		return list;
	}
	
	/**
	 * Construct the ArrayList with based on the result set and its column size from oracle
	 * @param rs contains list items fetched from oracle
	 * @param columnCount the size of the column in result set
	 * @return ArrayList based on the data from result set
	 */
	private static ArrayList<String> constructList(ResultSet rs, int columnCount) throws SQLException{
		
		ArrayList<String> list = new ArrayList<String>();
		
		while (rs.next()) {			
			for(int i=0; i<columnCount; i++) {			
				list.add(rs.getString(i+1));
			}		
		}
		
		return list;
	}
	
}