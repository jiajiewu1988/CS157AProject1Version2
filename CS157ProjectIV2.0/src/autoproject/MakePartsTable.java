/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoproject;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carita
 */
public class MakePartsTable extends Container
{
    public MakePartsTable(int rlink) throws SQLException
    {
        con = this;
        data = new Data();
        panel = new JPanel(new FlowLayout());
        ui = new MakeMainFrame();
        selectedSupplier = null;
        
        initializeVendorsTable();
        initializeAllPartNumTable();
        initializePartInfoTable();
        
        vendorsModel.addColumn("Part #", data.getVendorParts(Integer.toString(rlink)));
        partInfoModel.addColumn("");
        
        setUpAddExistingPartListener();
        
        Border title = BorderFactory.createTitledBorder("Select Supplier");
        panel.setBorder(title);
        
        con.setLayout(new BorderLayout());
        con.add(panel, BorderLayout.NORTH);
    }
    
    public MakePartsTable(String rLink) throws SQLException
    {
        con = this;
        data = new Data();
        panel = new JPanel(new FlowLayout());
        ui = new MakeMainFrame();
        selectedSupplier = null;
        
        initializeVendorsTable();
        initializePartInfoTable();
        initializeAllPartNumTable();
        
        vendorsModel.addColumn("Part #", data.getVendorParts(rLink));
        partInfoModel.addColumn("");
        
        setUpExistingPartListener();
        
        Border title = BorderFactory.createTitledBorder("Select Supplier");
        panel.setBorder(title);
        
        con.setLayout(new BorderLayout());
        con.add(panel, BorderLayout.NORTH);
    }
    
    public MakePartsTable(String vendor, String pnum) throws SQLException
    {
        con = this;
        data = new Data();
        panel = new JPanel(new FlowLayout());
        selectedPNum = pnum;
        ui = new MakeMainFrame();
        selectedSupplier = null;
        
        initializeVendorsTable();
        initializePartInfoTable();
        
        vendorsModel.addColumn("Part #");
        vendorsTable.setEnabled(false);
        
        partInfo = data.getPartData(vendor, pnum);
        partInfoModel.addColumn(pnum, partInfo);
        partInfoTable.setEnabled(false);
        
        Border title = BorderFactory.createTitledBorder("Select Vendor");
        panel.setBorder(title);
        
        con.setLayout(new BorderLayout());
        con.add(panel, BorderLayout.NORTH);
    }
    
    private void initializeAllPartNumTable()
    {
        allPartsTable = new JTable()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) 
            {
                return false; //Disallow the editing of any cell
            }
        };
        allPartsModel = (DefaultTableModel) allPartsTable.getModel();
        allPartsModel.addColumn("Part#s");
        JScrollPane scroll = new JScrollPane(allPartsTable);
        scroll.setPreferredSize(new Dimension(100, 275));
        panel.add(scroll);
    }
    
    /*
     * all 4 vendors with 4 parts
     */
    private void initializeVendorsTable()
    {
        vendorsTable = new JTable()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) 
            {
                return false; //Disallow the editing of any cell
            }
        };
        vendorsModel = (DefaultTableModel) vendorsTable.getModel();
        vendorsModel.addColumn("Vendors", data.getColumnsNames("vendors"));
        JScrollPane scroll = new JScrollPane(vendorsTable);
        scroll.setPreferredSize(new Dimension(100, 275));
        panel.add(scroll);
    }
    
    /**
     * table with part information
     */
    private void initializePartInfoTable()
    {
        partInfoTable = new JTable();
        partInfoModel = (DefaultTableModel) partInfoTable.getModel();
        partInfoModel.addColumn("Part #:", data.getColumnsNames("partInfo"));
        JScrollPane scroll2 = new JScrollPane(partInfoTable);
        scroll2.setPreferredSize(new Dimension(200, 275));
        panel.add(scroll2);
        modifiedInfo = new String[partInfoTable.getRowCount()];
    }
    
    private void setUpAddExistingPartListener()
    {
        ListSelectionModel cellSelectionModel = vendorsTable.getSelectionModel();
        
        cellSelectionModel.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                int selectedRow = vendorsTable.getSelectedRow();
                selectedSupplier = (String) vendorsTable.getValueAt(selectedRow, 0);
                if((String) vendorsTable.getValueAt(selectedRow, 1) != null)
                {
                    selectedPNum = (String) vendorsTable.getValueAt(selectedRow, 1);

                    boolean num = false;
                    try
                    {
                        Integer.parseInt(selectedPNum);
                        num = true;
                    }
                    catch(Exception ex){}

                    if(num)
                    {   
                        try 
                        {
                            setAllPartsTable(null);
                            partInfo = data.getPartData(selectedSupplier, selectedPNum); 
                            partInfoTable.getColumnModel().getColumn(1).setHeaderValue(selectedPNum);

                            if(partInfo.length != 0)
                            {
                                for(int i = 0; i < partInfo.length; i++)
                                {
                                    partInfoTable.setValueAt(partInfo[i], i, 1);
                                }
                            }
                            else
                            {
                                for(int j = 0; j < partInfoTable.getRowCount(); j++)
                                {
                                    partInfoTable.setValueAt("", j, 1);
                                }
                            }
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(MakePartsTable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else
                    {
                        partInfoTable.getColumnModel().getColumn(1).setHeaderValue("");
                        for(int j = 0; j < partInfoTable.getRowCount(); j++)
                        {
                            partInfoTable.setValueAt("", j, 1);
                        }

                        String[] parts = null;                
                        try 
                        {
                            parts = data.getPartNumFromVendor(selectedSupplier);
                            setAllPartsTable(parts);

                            for(int j = 0; j < partInfoTable.getRowCount(); j++)
                            {
                                partInfoTable.setValueAt("", j, 1);
                            }
                        }
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(MakePartsTable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                ListSelectionModel cellSelectionModel = allPartsTable.getSelectionModel();
                cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                cellSelectionModel.addListSelectionListener(new ListSelectionListener(){
                    @Override
                    public void valueChanged(ListSelectionEvent e)
                    {
                        try 
                        {
                            int selectedRow = allPartsTable.getSelectedRow();
                            if(selectedRow != -1)
                            {
                                selectedPNum = (String) allPartsTable.getValueAt(selectedRow, 0);

                                partInfo = data.getPartData(selectedSupplier, selectedPNum); 
                                partInfoTable.getColumnModel().getColumn(1).setHeaderValue(selectedPNum);

                                if(partInfo.length != 0)
                                {
                                    for(int i = 0; i < partInfo.length; i++)
                                    {
                                        partInfoTable.setValueAt(partInfo[i], i, 1);
                                    }
                                }
                                else
                                {
                                    for(int j = 0; j < partInfoTable.getRowCount(); j++)
                                    {
                                        partInfoTable.setValueAt("", j, 1);
                                    }
                                }
                            }
                            
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(MakePartsTable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                con.repaint();
            }
        });
    }
    
    private void setUpExistingPartListener()
    {
        ListSelectionModel cellSelectionModel = vendorsTable.getSelectionModel();
        
        cellSelectionModel.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                ui.disableAddButton();
                ui.disableEditButton();
                int selectedRow = vendorsTable.getSelectedRow();
                selectedSupplier = (String) vendorsTable.getValueAt(selectedRow, 0);
                selectedPNum = (String) vendorsTable.getValueAt(selectedRow, 1);
                
                boolean num = false;
                try
                {
                    Integer.parseInt(selectedPNum);
                    num = true;
                }
                catch(Exception ex){}
                
                if(num)
                {   
                    try 
                    {
                        ui.enableEditButton();
                        ui.enableUpdateButton();
                        ui.disableAddButton();
                        ui.disableSubmitButton();
                        ui.disableExistingPartButton();
                        setAllPartsTable(null);
                        partInfo = data.getPartData(selectedSupplier, selectedPNum); 
                        partInfoTable.getColumnModel().getColumn(1).setHeaderValue(selectedPNum);
                        
                        if(partInfo.length != 0)
                        {
                            for(int i = 0; i < partInfo.length; i++)
                            {
                                partInfoTable.setValueAt(partInfo[i], i, 1);
                            }
                        }
                        else
                        {
                            for(int j = 0; j < partInfoTable.getRowCount(); j++)
                            {
                                partInfoTable.setValueAt("", j, 1);
                            }
                        }
                    } 
                    catch (SQLException ex) 
                    {
                        Logger.getLogger(MakePartsTable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    ui.disableEditButton();
                    ui.disableUpdateButton();
                    partInfoTable.getColumnModel().getColumn(1).setHeaderValue("");
                    for(int j = 0; j < partInfoTable.getRowCount(); j++)
                    {
                        partInfoTable.setValueAt("", j, 1);
                    }
                    ui.enableAddButton();
                    ui.enableSubmitButton();
                    ui.enableExistingPartButton();
                    
                    String[] parts = null;                
                    try 
                    {
                        parts = data.getPartNumFromVendor(selectedSupplier);
                        setAllPartsTable(parts);

                        for(int j = 0; j < partInfoTable.getRowCount(); j++)
                        {
                            partInfoTable.setValueAt("", j, 1);
                        }
                    }
                    catch (SQLException ex) 
                    {
                        Logger.getLogger(MakePartsTable.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
                
                ListSelectionModel cellSelectionModel = allPartsTable.getSelectionModel();
                cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                cellSelectionModel.addListSelectionListener(new ListSelectionListener(){
                    @Override
                    public void valueChanged(ListSelectionEvent e)
                    {
                        try 
                        {
                            int selectedRow = allPartsTable.getSelectedRow();
                            if(selectedRow != -1)
                            {
                                ui.disableAddButton();
                                ui.disableSubmitButton();
                                selectedPNum = (String) allPartsTable.getValueAt(selectedRow, 0);

                                partInfo = data.getPartData(selectedSupplier, selectedPNum); 
                                partInfoTable.getColumnModel().getColumn(1).setHeaderValue(selectedPNum);

                                if(partInfo.length != 0)
                                {
                                    for(int i = 0; i < partInfo.length; i++)
                                    {
                                        partInfoTable.setValueAt(partInfo[i], i, 1);
                                    }
                                }
                                else
                                {
                                    for(int j = 0; j < partInfoTable.getRowCount(); j++)
                                    {
                                        partInfoTable.setValueAt("", j, 1);
                                    }
                                }
                            }
                            
                        } 
                        catch (SQLException ex) 
                        {
                            Logger.getLogger(MakePartsTable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                con.repaint();
            }
        });
    }
    
    public void repaint(String rLink) throws SQLException
    {
        String[] nums = data.getVendorParts(rLink);
        for(int i = 0; i < vendorsTable.getRowCount(); i++)
        {
            vendorsTable.setValueAt(nums[i], i, 1);
        }
    }

    public boolean update() throws SQLException
    {
        boolean flag = false; //no change
        String[] originalInfo = partInfo;
        modifiedInfo = new String[partInfoTable.getRowCount()];
        for(int i = 0; i < partInfoTable.getRowCount(); i++)
        {
            modifiedInfo[i] = (String) partInfoTable.getValueAt(i, 1);
            if((modifiedInfo[i] == null) && (originalInfo[i] == null))
            {
                flag = false; 
            }
            else if((modifiedInfo[i] != null) && (originalInfo[i] == null))
            {
                flag = true;
            }
            else if((modifiedInfo[i] == null) && (originalInfo[i] != null))
            {
                flag = true;
            }
            else if(!modifiedInfo[i].equalsIgnoreCase(originalInfo[i]))
            {
                flag = true; //info changed
            }
        }
        return flag;
    }
    
    public void setAllPartsTable(Object[] data)
    {
        DefaultTableModel model = new DefaultTableModel();
        allPartsTable.setModel(model);
        model.addColumn("Part#", data);
    }
    
    public void edit()
    {
        partInfoTable.setEnabled(true);
    }
    
    public String getPartNumber()
    {
        return selectedPNum;
    }
    
    public String getSupplier()
    {
        return selectedSupplier;
    }
    
    public String[] getUpdatedInfo()
    {
        for(int i = 0; i < partInfoTable.getRowCount(); i++)
        {
            modifiedInfo[i] = (String) partInfoTable.getValueAt(i, 1);
        }
        return modifiedInfo;
    }
    
    private JTable allPartsTable;
    private JTable vendorsTable;
    private JTable partInfoTable;
    private String selectedSupplier;
    private String selectedPNum;
    private String[] partInfo;
    private String[] modifiedInfo;
    private DefaultTableModel allPartsModel;
    private DefaultTableModel vendorsModel;
    private DefaultTableModel partInfoModel;
    private JPanel panel;
    final Data data;
    final Container con;
    final MakeMainFrame ui;
}