

import java.sql.*;

/**
 * To create a connection to oracle
 * @author jerry wu
 *
 */
public class DBOperation {
	private final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:oracle";
	private final String DB_USER = "system";
	private final String DB_PASS = "tiger";
	private static Connection con;
	private static Statement stmnt;
	
	/**
	 * Constructs a connection to oracle
	 */
	public DBOperation() {
		try {
			Class.forName(DB_DRIVER);
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			stmnt = con.createStatement();
			System.out.println("Connected to DB!");
			} catch (SQLException ex) {
			    System.out.println("Error "+ex);
			System.err.println(ex.getMessage());
			} catch (ClassNotFoundException ex) {
			System.err.println(ex.getMessage());
			}
	}
	
	/**
	 * Disconnect from DB
	 */
	public void disconnectFromDB() {
		try {
			stmnt.close();
			con.close();
			System.out.println("Disconnected from DB!");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Query the Auto Maker
	 * @return A Maker Set
	 */
	public ResultSet queryMaker() throws SQLException {
			String queryMaker = "SELECT table_name FROM all_tables WHERE table_name LIKE 'APL___'";
			ResultSet result = stmnt.executeQuery(queryMaker);
			return result;
	}
	
	/**
	 * Query car model by auto maker
	 * @return a model set
	 */
	public ResultSet queryModel(String maker) throws SQLException {
		String model = "SELECT model FROM " + maker;
		ResultSet rs = stmnt.executeQuery(model);
		return rs;
	}
	
	/**
	 * Query car year by maker, model
	 * @return a year set
	 */
	public ResultSet queryYear(String maker, String model) throws SQLException {
		String year = "SELECT YEAR FROM " + maker + " WHERE MODEL = '" + model + "'";
		ResultSet rs = stmnt.executeQuery(year);
		return rs;
	}
	
	/**
	 * Query engine_type by maker, model, year
	 * @return a engine_type set
	 */
	public ResultSet queryEngineType(String maker, String model, String year) throws SQLException {
		String engine_type = "SELECT ENGINE_TYPE FROM " + maker 
							+ " WHERE MODEL = '" + model + "'"
							+ " AND YEAR = '" + year + "'";
		ResultSet rs = stmnt.executeQuery(engine_type);
		return rs;
	}
}
