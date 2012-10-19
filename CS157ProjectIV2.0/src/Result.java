

import java.sql.*;
import java.util.*;

/**
 * Fetch the result by query statement, return ArrayLists
 * @author jerry wu
 *
 */
public class Result implements Comparator<String>{

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
}