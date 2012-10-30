/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoproject;

import java.sql.*;
import java.util.ArrayList;

public class Query {
    private static Connection connection;

    public Query() {
	DatabaseConnection con = new DatabaseConnection();
	connection = con.getDBConnection();
    }

    /**
     * Gets every maker from the cars table
     * 
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getAllMakers() throws SQLException {
	ArrayList<String> makers = new ArrayList<String>();
	String stmntStr = "select distinct maker from cars order by maker";
	System.out.println(stmntStr);

	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);

	while (rs.next()) {
	    makers.add(rs.getString(1));
	}
	rs.close();
	stmnt.close();
	return makers;
    }

    /**
     * Gets every model by a particular maker
     * 
     * @param maker
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getAllModelsFromMaker(String maker)
	    throws SQLException {
	ArrayList<String> models = new ArrayList<String>();
	String stmntStr = "select distinct model from cars where maker='"
		+ maker.toUpperCase() + "' order by model";
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);

	while (rs.next()) {
	    models.add(rs.getString(1));
	}
	rs.close();
	stmnt.close();
	return models;
    }

    /**
     * Gets every year of car that exists under a particular make and model
     * 
     * @param maker
     * @param model
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getAllYearsFromModels(String maker, String model)
	    throws SQLException {
	ArrayList<String> years = new ArrayList<String>();
	String stmntStr = "select distinct year from cars where maker='"
		+ maker.toUpperCase() + "' and model='" + model.toUpperCase()
		+ "' order by year asc";
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);

	while (rs.next()) {
	    years.add(rs.getString(1));
	}
	rs.close();
	stmnt.close();
	return years;
    }

    /**
     * Gets every car description for cars of a specific make/model/year
     * 
     * @param maker
     * @param model
     * @param year
     * @return
     * @throws SQLException
     */
    public ArrayList<String[]> getAllYearDescription(String maker,
	    String model, String year) throws SQLException {
	ArrayList<String[]> desc = new ArrayList<String[]>();
	String stmntStr = "select description, litres, engine_type, cubic_inches, rlink from cars where maker='"
		+ maker.toUpperCase()
		+ "' and model='"
		+ model.toUpperCase()
		+ "' and year='" + year + "'";
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);

	while (rs.next()) {
	    desc.add(new String[] { rs.getString("description"),
		    rs.getString("litres"), rs.getString("engine_type"),
		    rs.getString("cubic_inches"), rs.getString("rlink") });
	}
	rs.close();
	stmnt.close();
	return desc;
    }

    /**
     * Gets every part number on a radcrx tuple for a specific rlink
     * 
     * @param rlink
     * @return
     * @throws SQLException
     */
    public String[] getAllPartNumFromRLink(String rlink) throws SQLException {

	String stmntStr = "select ars1, ars2, ars3, ars4, mod1, mod2, mod3, mod4, beh1, beh2, beh3, beh4, dan1, dan2, dan3, dan4 from radcrx where rlink="
		+ rlink;
	String[] partNum = new String[16];
	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);
	System.out.println(stmntStr);
	while (rs.next()) {
	    partNum[0] = rs.getString("ars1");
	    partNum[1] = rs.getString("ars2");
	    partNum[2] = rs.getString("ars3");
	    partNum[3] = rs.getString("ars4");
	    partNum[4] = rs.getString("mod1");
	    partNum[5] = rs.getString("mod2");
	    partNum[6] = rs.getString("mod3");
	    partNum[7] = rs.getString("mod4");
	    partNum[8] = rs.getString("beh1");
	    partNum[9] = rs.getString("beh2");
	    partNum[10] = rs.getString("beh3");
	    partNum[11] = rs.getString("beh4");
	    partNum[12] = rs.getString("dan1");
	    partNum[13] = rs.getString("dan2");
	    partNum[14] = rs.getString("dan3");
	    partNum[15] = rs.getString("dan4");
	    for (int i = 0; i < partNum.length; i++) {
		if (partNum[i] == null) {
		    partNum[i] = "";
		}
	    }
	}
	rs.close();
	stmnt.close();
	return partNum;
    }

    /**
     * Checks to see if an entry exists for a given rlink on radcrx
     * 
     * @param rlink
     * @return true if it exists, false if it does not
     * @throws SQLException
     */
    public boolean rlinkExistsOnRADCRX(String rlink) throws SQLException {
	String stmntStr = "select rlink from radcrx where rlink = '" + rlink
		+ "'";
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);
	return rs.next();
    }

    /**
     * Inserts a new, empty row into RADCRX with a given RLINK number
     * 
     * @param rlink
     * @throws SQLException
     */
    public void insertNewRlink(String rlink) throws SQLException {
	String stmntStr = "insert into radcrx values("
		+ rlink
		+ ", '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '')";
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	stmnt.execute(stmntStr);
	stmnt.close();
    }

    /**
     * Gets a part description for a given part from a given supplier
     * 
     * @param supplier
     * @param pnum
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getDescriptionFromPNum(String supplier, String pnum)
	    throws SQLException {
	ArrayList<String> desc = new ArrayList<String>();
	String stmntStr = "select core, inhead, outhead, incon, oucon, tmount, oilcool, price, amount"
		+ " from "
		+ "rdim"
		+ supplier.substring(0, 3)
		+ " where P_Number='" + pnum + "'";
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);

	while (rs.next()) {
	    desc.add(rs.getString("core"));
	    desc.add(rs.getString("inhead"));
	    desc.add(rs.getString("outhead"));
	    desc.add(rs.getString("incon"));
	    desc.add(rs.getString("oucon"));
	    desc.add(rs.getString("tmount"));
	    desc.add(rs.getString("oilcool"));
	    desc.add(rs.getString("price"));
	    desc.add(rs.getString("amount"));
	}
	rs.close();
	stmnt.close();
	return desc;
    }

    /**
     * Gets every distinct rlink from the radcrx table
     * 
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getAllRlinks() throws SQLException {
	ArrayList<String> links = new ArrayList<String>();
	String stmntStr = "select distinct rlink from radcrx";
	System.out.println(stmntStr);

	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);

	while (rs.next()) {
	    links.add(rs.getString(1));
	}
	rs.close();
	stmnt.close();
	return links;
    }

    /**
     * Gets every part number from a vendor table
     * 
     * @param vendor
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getAllPartNumFromVendors(String vendor)
	    throws SQLException {
	ArrayList<String> partNum = new ArrayList<String>();
	String stmntStr = "select distinct P_Number from rdim"
		+ vendor.substring(0, 3) + " order by P_Number";
	System.out.println(stmntStr);

	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);

	while (rs.next()) {
	    partNum.add(rs.getString(1));
	}
	rs.close();
	stmnt.close();
	return partNum;
    }

    /**
     * Gets the largest rlink
     * 
     * @return
     * @throws SQLException
     */
    public int getMaxRlink() throws SQLException {
	String stmntStr = "select max(rlink) from radcrx";
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);
	int link = 0;
	while (rs.next()) {
	    link = rs.getInt(1);
	}
	rs.close();
	stmnt.close();
	return link;
    }

    /**
     * Gets the largest part number of all of the parts of all of the suppliers
     * 
     * @return
     * @throws SQLException
     */
    public int getMaxPNum() throws SQLException {
	String stmntStr = "select max(P_NUMBER) from rdimars";
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);
	int ars = 0;
	while (rs.next()) {
	    ars = rs.getInt(1);
	}

	int mod = 0;
	String stmntStr1 = "select max(P_NUMBER) from rdimmod";
	System.out.println(stmntStr1);
	Statement stmnt1 = connection.createStatement();
	ResultSet rs1 = stmnt1.executeQuery(stmntStr1);
	while (rs1.next()) {
	    mod = rs1.getInt(1);
	}

	int beh = 0;
	String stmntStr2 = "select max(P_NUMBER) from rdimbeh";
	System.out.println(stmntStr2);
	Statement stmnt2 = connection.createStatement();
	ResultSet rs2 = stmnt2.executeQuery(stmntStr2);
	while (rs2.next()) {
	    beh = rs2.getInt(1);
	}
	int dan = 0;
	String stmntStr3 = "select max(P_NUMBER) from rdimdan";
	System.out.println(stmntStr3);
	Statement stmnt3 = connection.createStatement();
	ResultSet rs3 = stmnt3.executeQuery(stmntStr3);
	while (rs3.next()) {
	    dan = rs3.getInt(1);
	}

	System.out.println(ars + " " + beh + " " + mod + " " + dan);
	int a = Math.max(ars, beh);
	int b = Math.max(mod, dan);
	rs.close();
	rs1.close();
	rs2.close();
	rs3.close();
	stmnt.close();
	stmnt1.close();
	stmnt2.close();
	stmnt3.close();

	return Math.max(a, b);
    }

    /**
     * Adds a new car to the cars table
     * 
     * @param car
     * @param rlink
     * @return
     * @throws SQLException
     */
    public int addNewCar(String[] car, int rlink) throws SQLException {
	String stmntStr = "insert into cars values ('" + car[0].toUpperCase()
		+ "','" + car[1].toUpperCase() + "','" + car[2] + "','"
		+ car[3] + "','" + car[4] + "','" + car[5] + "','" + car[6]
		+ "'," + rlink + ")";
	String addRlinkToRadcrx = "insert into radcrx values("
		+ rlink
		+ ", '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '')";
	Statement stmnt = null;
	try {
	    connection.setAutoCommit(false);
	    stmnt = connection.createStatement();
	    System.out.println(stmntStr);
	    stmnt.executeQuery(stmntStr);
	    stmnt.executeQuery(addRlinkToRadcrx);
	    connection.commit();
	} catch (SQLException e) {
	    if (connection != null) {
		try {
		    System.err
			    .println("Error adding new car. Transaction being rolled back.");
		    connection.rollback();
		} catch (SQLException excep) {
		    System.out
			    .println("Couldn't roll back changes. Nothing was committed. Unknown error.");
		}
	    }
	} finally {
	    if (stmnt != null) {
		stmnt.close();
	    }
	    connection.setAutoCommit(true);

	}

	return rlink++;
    }

    /**
     * Adds a new part to a supplier table
     * 
     * @param selectedSupplier
     * @param part
     * @return
     * @throws SQLException
     */
    public int addNewPart(String selectedSupplier, String[] part)
	    throws SQLException {
	int newPartNum = 1 + getLargestPartNumberByVendor("rdim"
		+ selectedSupplier.substring(0, 3));
	if(part[7] == "") {
	    part[7] = "0";
	}
	if(part[8] == "") {
	    part[8] = "0";
	}
	String stmntStr = "insert into rdim" + selectedSupplier.substring(0, 3)
		+ " values ('" + Integer.toString(newPartNum) + "','"
		+ part[0].toUpperCase() + "','" + part[1].toUpperCase() + "','"
		+ part[2] + "','" + part[3] + "','" + part[4] + "','" + part[5]
		+ "','" + part[6] + "', " + part[7] + ", " + part[8] + ")";
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	stmnt.executeQuery(stmntStr);
	stmnt.close();
	return newPartNum;
    }
    
    /**
     * Adds a new part to a supplier table
     * 
     * @param selectedSupplier
     * @param part
     * @return
     * @throws SQLException
     */
    public int addNewPartToCar(String selectedSupplier, String[] part, String rlink)
	    throws SQLException {
	int newPartNum = 1 + getLargestPartNumberByVendor("rdim"
		+ selectedSupplier.substring(0, 3));
	if(part[7] == "") {
	    part[7] = "0";
	}
	if(part[8] == "") {
	    part[8] = "0";
	}
	String stmntStr = "insert into rdim" + selectedSupplier.substring(0, 3)
		+ " values ('" + Integer.toString(newPartNum) + "','"
		+ part[0].toUpperCase() + "','" + part[1].toUpperCase() + "','"
		+ part[2] + "','" + part[3] + "','" + part[4] + "','" + part[5]
		+ "','" + part[6] + "', " + part[7] + ", " + part[8] + ")";
	String stmntStr2 = "update radcrx set " + selectedSupplier + "=" + Integer.toString(newPartNum) + " where rlink ='" + rlink + "'";
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	stmnt.executeQuery(stmntStr);
	stmnt.executeQuery(stmntStr2);
	stmnt.close();
	return newPartNum;
    }

    /**
     * Updates a cars information
     * 
     * @param car
     * @param oldCar
     * @param rLink
     * @return
     * @throws SQLException
     */
    public int updateCar(String[] car, String[] oldCar, int rLink)
	    throws SQLException {
	String stmntStr = "update cars set maker = '" + car[0].toUpperCase()
		+ "', model = '" + car[1].toUpperCase() + "', year = '"
		+ car[2] + "', description = '" + car[3] + "', litres = '"
		+ car[4] + "', engine_type = '" + car[5]
		+ "', cubic_inches = '" + car[6] + "' where " + "maker = '"
		+ oldCar[0].toUpperCase() + "' AND model = '"
		+ oldCar[1].toUpperCase() + "' AND year = '" + oldCar[2]
		+ "' AND description= '" + oldCar[3] + "' AND litres ='"
		+ oldCar[4] + "' AND engine_type='" + oldCar[5]
		+ "' AND cubic_inches='" + oldCar[6] + "' AND rlink="
		+ oldCar[7];

	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	stmnt.executeQuery(stmntStr);
	stmnt.close();
	return rLink++;
    }

    /**
     * Updates a part with new information, creating a new part number, and
     * updating the old part number to the new part number across radcrx
     * 
     * @param newPartData
     * @param oldPartNum
     * @param supplier
     * @return
     * @throws SQLException
     */
    public int updatePart(String[] newPartData, int oldPartNum, String supplier)
	    throws SQLException {
	supplier = supplier.toUpperCase();
	String supplier_table = "RDIM" + supplier;
	int newPartNum = 1 + getLargestPartNumberByVendor(supplier_table);
	String stmntStr = "update " + supplier_table + " set P_NUMBER = '"
		+ newPartNum + "', " + "CORE = '" + newPartData[0] + "', "
		+ "INHEAD = '" + newPartData[1] + "', " + "OUTHEAD = '"
		+ newPartData[2] + "', " + "INCON = '" + newPartData[3] + "', "
		+ "OUCON = '" + newPartData[4] + "', " + "TMOUNT = '"
		+ newPartData[5] + "', " + "OILCOOL = '" + newPartData[6]
		+ "', " + "PRICE = " + newPartData[7] + ", " + "AMOUNT = "
		+ newPartData[8] + " " + "where P_NUMBER = '" + oldPartNum
		+ "'";

	// Update every car that uses this part with the new part
	String stmntStr1 = "update radcrx set " + supplier + "1 = '"
		+ newPartNum + "' where " + supplier + "1 = '" + oldPartNum
		+ "'";
	String stmntStr2 = "update radcrx set " + supplier + "2 = '"
		+ newPartNum + "' where " + supplier + "2 = '" + oldPartNum
		+ "'";
	String stmntStr3 = "update radcrx set " + supplier + "3 = '"
		+ newPartNum + "' where " + supplier + "3 = '" + oldPartNum
		+ "'";
	String stmntStr4 = "update radcrx set " + supplier + "4 = '"
		+ newPartNum + "' where " + supplier + "4 = '" + oldPartNum
		+ "'";

	Statement stmnt = null;
	try {
	    connection.setAutoCommit(false);
	    stmnt = connection.createStatement();
	    System.out.println(stmntStr);
	    stmnt.executeQuery(stmntStr);
	    System.out.println(stmntStr1);
	    stmnt.executeQuery(stmntStr1);
	    System.out.println(stmntStr2);
	    stmnt.executeQuery(stmntStr2);
	    System.out.println(stmntStr3);
	    stmnt.executeQuery(stmntStr3);
	    System.out.println(stmntStr4);
	    stmnt.executeQuery(stmntStr4);
	    connection.commit();
	} catch (SQLException e) {
	    if (connection != null) {
		try {
		    System.err
			    .println("Error updating part. Transaction being rolled back.");
		    connection.rollback();
		} catch (SQLException excep) {
		    System.out
			    .println("Couldn't roll back changes. Nothing was committed. Unknown error.");
		}
	    }
	} finally {
	    if (stmnt != null) {
		stmnt.close();
	    }
	    connection.setAutoCommit(true);
	}

	return newPartNum;
    }

    /**
     * Retrieves the largest part number from a given vendor
     * 
     * @param vendor_table
     * @return
     * @throws SQLException
     */
    public int getLargestPartNumberByVendor(String vendor_table)
	    throws SQLException {
	String stmntStr = "select max(P_NUMBER) from " + vendor_table;
	System.out.println(stmntStr);
	Statement stmnt = connection.createStatement();
	ResultSet rs = stmnt.executeQuery(stmntStr);
	if (rs.next()) {
	    int num = Integer.parseInt(rs.getString(1));
	    rs.close();
	    stmnt.close();
	    return num;
	} else {
	    rs.close();
	    stmnt.close();
	    return 0;
	}
    }

    /**
     * Sets a part number under a supplier field on radcrx for a given rlink
     * 
     * @param RLink
     * @param PNum
     * @param supplier_field
     * @throws SQLException
     */
    public void connectRLinkPartNum(int RLink, int PNum, String supplier_field)
	    throws SQLException {
	// Check to see if an entry for rlink exists
	if (!rlinkExistsOnRADCRX(Integer.toString(RLink))) {
	    insertNewRlink(Integer.toString(RLink));
	}
	String stmntStr = "update radcrx set " + supplier_field + " = '" + PNum
		+ "' where rlink = " + RLink;
	Statement stmnt = connection.createStatement();
	System.out.println(stmntStr);
	stmnt.executeUpdate(stmntStr);
	stmnt.close();
    }

    /**
     * delete a tuple from CARS, nothing else is modified
     * 
     * @param car
     */
    public void deleteCar(String[] car) throws SQLException {
	String stmntStr = "delete from cars where maker = '" + car[0]
		+ "' and model = '" + car[1] + "'" + " and year = '" + car[2]
		+ "' and description = '" + car[3] + "' and litres = '"
		+ car[4] + "' and engine_type = '" + car[5]
		+ "' and cubic_inches = '" + car[6] + "'" + " and rlink = '"
		+ car[7] + "'";

	Statement stmnt = null;
	try {
	    connection.setAutoCommit(false);
	    stmnt = connection.createStatement();
	    System.out.println(stmntStr);
	    stmnt.execute(stmntStr);
	    connection.commit();
	} catch (SQLException e) {
	    if (connection != null) {
		try {
		    System.err
			    .println("Error deleting car. Transaction being rolled back.");
		    connection.rollback();
		} catch (SQLException excep) {
		    System.out
			    .println("Couldn't roll back changes. Nothing was committed. Unknown error.");
		}
	    }
	} finally {
	    if (stmnt != null) {
		stmnt.close();
	    }
	    connection.setAutoCommit(true);
	}
    }

    /**
     * Deletes all cars that are made by a particular maker
     * 
     * @param maker
     * @throws SQLException
     */
    public void deleteMaker(String maker) throws SQLException {
	String stmntStr = "delete from cars where maker = '" + maker + "'";

	Statement stmnt = null;
	try {
	    connection.setAutoCommit(false);
	    stmnt = connection.createStatement();
	    System.out.println(stmntStr);
	    stmnt.execute(stmntStr);
	    connection.commit();
	} catch (SQLException e) {
	    if (connection != null) {
		try {
		    System.err
			    .println("Error deleting all cars by this maker. Transaction being rolled back.");
		    connection.rollback();
		} catch (SQLException excep) {
		    System.out
			    .println("Couldn't roll back changes. Nothing was committed. Unknown error.");
		}
	    }
	} finally {
	    if (stmnt != null) {
		stmnt.close();
	    }
	    connection.setAutoCommit(true);
	}
    }

    /**
     * Delete all cars that are of a particular make and model
     * 
     * @param maker
     * @param model
     * @throws SQLException
     */
    public void deleteMakerModel(String maker, String model)
	    throws SQLException {
	String stmntStr = "delete from cars where maker = '" + maker + "'"
		+ " and model = '" + model + "'";

	Statement stmnt = null;
	try {
	    connection.setAutoCommit(false);
	    stmnt = connection.createStatement();
	    System.out.println(stmntStr);
	    stmnt.execute(stmntStr);
	    connection.commit();
	} catch (SQLException e) {
	    if (connection != null) {
		try {
		    System.err
			    .println("Error deleting all cars by this maker and of this model. Transaction being rolled back.");
		    connection.rollback();
		} catch (SQLException excep) {
		    System.out
			    .println("Couldn't roll back changes. Nothing was committed. Unknown error.");
		}
	    }
	} finally {
	    if (stmnt != null) {
		stmnt.close();
	    }
	    connection.setAutoCommit(true);
	}
    }
    
    public void deleteMakerModelYear(String maker, String model, String year) throws SQLException {
	String stmntStr = "delete from cars where maker = '" + maker + "'"
		+ " and model = '" + model + "'" + " and year = '" + year +"'";

	Statement stmnt = null;
	try {
	    connection.setAutoCommit(false);
	    stmnt = connection.createStatement();
	    System.out.println(stmntStr);
	    stmnt.execute(stmntStr);
	    connection.commit();
	} catch (SQLException e) {
	    if (connection != null) {
		try {
		    System.err
			    .println("Error deleting all cars by this maker and of this model and year. Transaction being rolled back.");
		    connection.rollback();
		} catch (SQLException excep) {
		    System.out
			    .println("Couldn't roll back changes. Nothing was committed. Unknown error.");
		}
	    }
	} finally {
	    if (stmnt != null) {
		stmnt.close();
	    }
	    connection.setAutoCommit(true);
	}
    }

    /**
     * Delete a part from the supplier table and any mention of it in the radcrx
     * table under the 4 columns of the specific supplier
     * 
     * @param supplier
     * @param pNum
     * @throws SQLException
     */
    public void deletePart(String supplier, String pNum) throws SQLException {
	String deleteFromRdim = "delete from rdim" + supplier.substring(0, 3)
		+ " where p_Number = '" + pNum + "'";
	// Delete any mention of this part from RADCRX
	String fieldname = supplier.substring(0, 3);
	String deletePartFromRadcrx1 = "update radcrx set " + fieldname
		+ "1 = null where " + fieldname + "1 = " + "'" + pNum + "'";
	String deletePartFromRadcrx2 = "update radcrx set " + fieldname
		+ "2 = null where " + fieldname + "2 = " + "'" + pNum + "'";
	String deletePartFromRadcrx3 = "update radcrx set " + fieldname
		+ "3 = null where " + fieldname + "3 = " + "'" + pNum + "'";
	String deletePartFromRadcrx4 = "update radcrx set " + fieldname
		+ "4 = null where " + fieldname + "4 = " + "'" + pNum + "'";
	Statement stmnt = null;
	try {
	    connection.setAutoCommit(false);
	    stmnt = connection.createStatement();
	    System.out.println(deleteFromRdim);
	    stmnt.execute(deleteFromRdim);
	    System.out.println(deletePartFromRadcrx1);
	    stmnt.execute(deletePartFromRadcrx1);
	    System.out.println(deletePartFromRadcrx2);
	    stmnt.execute(deletePartFromRadcrx2);
	    System.out.println(deletePartFromRadcrx3);
	    stmnt.execute(deletePartFromRadcrx3);
	    System.out.println(deletePartFromRadcrx4);
	    stmnt.execute(deletePartFromRadcrx4);
	    connection.commit();
	} catch (SQLException e) {
	    if (connection != null) {
		try {
		    System.err
			    .println("Error deleting part. Transaction being rolled back.");
		    connection.rollback();
		} catch (SQLException excep) {
		    System.out
			    .println("Couldn't roll back changes. Nothing was committed. Unknown error.");
		}
	    }
	} finally {
	    if (stmnt != null) {
		stmnt.close();
	    }
	    connection.setAutoCommit(true);
	}

    }

}