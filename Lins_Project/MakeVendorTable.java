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
public class MakeVendorTable extends Container
{
    public MakeVendorTable(String[] list) throws SQLException
    {
        con = this;
        data = new Data();
        panel = new JPanel(new FlowLayout());
        ui = new MakeMainFrame();
        
        initializeTables(list);
        setUpListeners();
        
        Border title = BorderFactory.createTitledBorder("Select Vendor");
        panel.setBorder(title);
        
        con.setLayout(new BorderLayout());
        con.add(panel, BorderLayout.NORTH);
    }
    private void initializeTables(String[] data)
    {
        vendorTable = new JTable()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) 
            {
                return false; //Disallow the editing of any cell
            }
        };
        DefaultTableModel vModel = (DefaultTableModel) vendorTable.getModel();
        vModel.addColumn("Vendors", data);
        JScrollPane scroll = new JScrollPane(vendorTable);
        scroll.setPreferredSize(new Dimension(100, 180));
        panel.add(scroll);
        
        partNumTable = new JTable()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) 
            {
                return false; //Disallow the editing of any cell
            }
        };
        DefaultTableModel model = (DefaultTableModel) partNumTable.getModel();
        model.addColumn("PartNumbers");
        JScrollPane scroll2 = new JScrollPane(partNumTable);
        scroll2.setPreferredSize(new Dimension(100, 180));
        panel.add(scroll2);
    }
    
    private void setUpListeners()
    {
        vendorTable.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = vendorTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                try 
                {
                    int selectedRow = vendorTable.getSelectedRow();
                    ui.disableSelectButton();
                    
                    selectedVendor = (String) vendorTable.getValueAt(selectedRow, 0);
                    
                    String[] partNum = data.getPartNumFromVendor(selectedVendor);
                    
                    DefaultTableModel model = new DefaultTableModel();
                    partNumTable.setModel(model);
                    model.addColumn("Part Numbers", partNum);
                    
                    partNumTable.setCellSelectionEnabled(true);
                    ListSelectionModel cellSelectionModel = partNumTable.getSelectionModel();
                    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e)
                        {
                            int selectedRow = partNumTable.getSelectedRow();
                            if(selectedRow != -1)
                            {
                                selectedPartNumber = (String) partNumTable.getValueAt(selectedRow, 0);
                                ui.enableSelectButton();
                            }
                        }
                    });
                    con.repaint();
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(MakeVendorTable.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    @Override
    public void repaint()
    {
        try 
        {
            DefaultTableModel model = new DefaultTableModel();
            partNumTable.setModel(model);
            model.addColumn("Part Numbers", data.getPartNumFromVendor(selectedVendor));
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MakeVendorTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getVendor()
    {
        return selectedVendor;
    }
    
    public String getPartNumber()
    {
        return selectedPartNumber;
    }
    final MakeMainFrame ui;
    private JTable vendorTable;
    private JTable partNumTable;
    private String selectedVendor;
    private String selectedPartNumber;
    private JPanel panel;
    final Data data;
    final Container con;
    
}