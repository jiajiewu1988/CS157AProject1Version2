/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoproject;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Carita
 */
public class Data 
{
    public Data()
    {
        model = new Query();
    }
         
    /**
     * retrieve the column names for data
     * @param columnType
     * @return 
     */
    public String[] getColumnsNames(String columnType)
    {
        String[] data = null;
        if(columnType.equalsIgnoreCase("auto makers"))
        {
            String[] name = {"Auto Makers"};
            data = name;
        }
        else if(columnType.equalsIgnoreCase("model"))
        {
            String[] name = {"Models"};
            data = name;
        }
        else if(columnType.equalsIgnoreCase("year"))
        {
            String[] name = {"Year"};
            data = name;
        }
        else if(columnType.equalsIgnoreCase("vendor"))
        {
            String[] vendors = {"Vendor"};
            data = vendors;
        }
        else if(columnType.equalsIgnoreCase("description"))
        {
            String[] columnModel = {"Description", "Liter", "Engine", "Cubic", "RLink"};
            data = columnModel;
        }
        else if(columnType.equalsIgnoreCase("carForm"))
        {
            String[] columnModel = {"AutoMaker: ", "Model: ", "Year: ", "Description: ", "Liter: ", "Engine: ", "Cubic: "};
            data = columnModel;
        }
        else if(columnType.equalsIgnoreCase("partInfo"))
        {
            String[] column = {"Core", "Inhead", "Outhead", "Incon", "Oucon", "TMount", "Oilcool", "Price",  "Amount"};
            data = column;
        }
        else if(columnType.equalsIgnoreCase("vendors"))
        {
            String[] vendors = {"ARS1", "ARS2", "ARS3", "ARS4", "MOD1", "MOD2", "MOD3", "MOD4", "BEH1", "BEH2", "BEH3", "BEH4", "DAN1", "DAN2", "DAN3", "DAN4"};
            data = vendors;
        }
        else if(columnType.equalsIgnoreCase("vendorNames"))
        {
            String[] vendorNames = {"ARS", "Modine", "Behr", "Daniel"};
            data = vendorNames;
        }
        return data;
    }
    
    /**
     * select Distinct maker from cars;
     * @return 
     */
    public String[][] getMakers() throws SQLException
    {
        ArrayList<String> makers = model.getAllMakers();
        String[][] array = new String[makers.size()][1];
        for(int i = 0; i < makers.size(); i++)
        {
            array[i][0] = makers.get(i);
        }
        return array;
    }


    /**
     * select Distinct model from cars where maker='MAKER';
     * @param maker
     * @return 
     */
    public String[] getModelsFromMakers(String maker) throws SQLException
    {
        ArrayList<String> models = model.getAllModelsFromMaker(maker);
        String[] data = new String[models.size()];
        for(int i = 0; i < models.size(); i++)
        {
            data[i] = models.get(i);
        }
        return data;
    }
    
    /**
     * select distinct year from cars where maker='MAKER and model='MODEL';
     * @param maker
     * @param model
     * @return 
     */
    public String[] getYearsFromModel(String maker, String model) throws SQLException
    {
        ArrayList<String> years = this.model.getAllYearsFromModels(maker, model);
        String[] data = new String[years.size()];
        for(int i = 0; i < years.size(); i++)
        {
            data[i] = years.get(i);
        }
        return data;
    }
    
    /**
     * select description, litres, engine_type, cubic_inches, rlink from cars where maker='MAKER' and model='MODEL' and year=YEAR;
     * @param maker
     * @param model
     * @param year
     * @return 
     */
    public String[][] getYearData(String maker, String model, String year) throws SQLException
    {
        ArrayList<String[]> desc = this.model.getAllYearDescription(maker, model, year);
        String[][] data = new String[desc.size()][5];
        for(int row = 0; row < desc.size(); row++)
        {
            for(int column = 0; column < desc.get(row).length; column++)
            {
                data[row][column] = desc.get(row)[column];
            }
        }
        return data;
    }

    
    /**
     * select distinct P_Number from VENDOR
     * @param vendor
     * @return 
     */
    public String[] getPartNumFromVendor(String vendor) throws SQLException
    {
        ArrayList<String> partNum = model.getAllPartNumFromVendors(vendor);
        String[] data  = new String[partNum.size()];
        for(int i = 0; i < partNum.size(); i++)
        {
            data[i] = partNum.get(i);
        }
        return data;
    }
    
    public String[] getVendorParts(String rlink) throws SQLException
    {
        return model.getAllPartNumFromRLink(rlink);
    }
   
    /**
     * select * from rdimmod where P_Number in (select MOD1 from radcrx where rlink=7785);
     * @return 
     */
    public String[] getPartData(String supplier, String pnum) throws SQLException
    {
        ArrayList<String> desc = model.getDescriptionFromPNum(supplier, pnum);
        String[] data = new String[desc.size()];
        for(int i = 0; i < desc.size(); i++)
        {
            data[i] = desc.get(i); //dont get the pnum
        }
        return data;
    }
    
    //8260 distinct rlinks from radcrx
    public String[] getRLinks() throws SQLException
    {
        ArrayList<String> links = model.getAllRlinks();
        String[] data = new String[links.size()];
        for(int i = 0; i < links.size(); i++)
        {
            data[i] = links.get(i);
        }
        return data;
    }    
    public int getCurrentPNum() throws SQLException
    {
        return model.getMaxPNum();
    }
    
    public int getCurrentRLink() throws SQLException
    {
        return model.getMaxRlink();
    }
    /*
     * create new car with null rlink
     */
    public int insertNewCar(String[] car, int rLink) throws SQLException
    {
        return model.addNewCar(car, rLink);
    }
    /*
     * assign a new rlink for part
     * returns the rlink for that car
     */
    public int insertNewPart(String selectedSupplier, String[] part) throws SQLException
    {
        return this.model.addNewPart(selectedSupplier, part);
    }
    
    public int addNewPartToCar(String selectedSupplier, String[] part, String rlink) throws SQLException
    {
        return model.addNewPartToCar(selectedSupplier, part, rlink);
    }
    
    /*
     * update car, use the current rlink? or give it a new rlink? or null?
     */
    public int updateCar(String[] car, String[] oldCar, int rlink) throws SQLException
    {
        return model.updateCar(car, oldCar, rlink);
    }
    
    /*
     * returns new rlink for that car
     */
    public int updatePart(String[] part, int partNum, String supplier) throws SQLException
    {
        model.updatePart(part, partNum, supplier);
        return model.getMaxRlink();
    }
    
    /*
     * PNum should be in an array of suppliers in correct order
     */
    public void connectPartToCar(int RLink, int Pnum, String supplier) throws SQLException
    {
        model.connectRLinkPartNum(RLink, Pnum, supplier);
    }
    
    
    public void deleteCar(String[] car) throws SQLException
    {
        this.model.deleteCar(car);
    }
    
    public void deleteMaker(String maker) throws SQLException
    {
        this.model.deleteMaker(maker);
    }
    
    public void deleteMakerModel(String maker, String model) throws SQLException
    {
        this.model.deleteMakerModel(maker, model);
    }
    
    public void deleteMakerModelYear(String maker, String model, String year) throws SQLException
    {
        this.model.deleteMakerModelYear(maker, model, year);
    }
    
    public void deletePart(String supplier, String pNum) throws SQLException
    {
        model.deletePart(supplier, pNum);
    }
    
    private Query model;
}