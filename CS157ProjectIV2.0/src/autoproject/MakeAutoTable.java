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
public class MakeAutoTable extends Container
{
    public MakeAutoTable() throws SQLException
    {
        con = this;
        ui = new MakeMainFrame();
        this.data = new Data();
        panel = new JPanel(new FlowLayout());
        panelBottom = new JPanel(new FlowLayout());
        selectedMaker = null;
        selectedModel = null;
        selectedYear = null;
        descrip = null;
        RLink = null;
        
        initializeTables();
        setUpListeners();
        
        Border title = BorderFactory.createTitledBorder("Select Maker, Model, Year");
        panel.setBorder(title);
        
        con.setLayout(new BorderLayout());
        con.add(panel, BorderLayout.NORTH);
        con.add(panelBottom, BorderLayout.SOUTH);
    }
    
    private void initializeTables() throws SQLException
    {
        makerTable = new JTable(data.getMakers(), data.getColumnsNames("auto makers"))
        {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) 
            {
                return false; //Disallow the editing of any cell
            }
        };
        JScrollPane scroll = new JScrollPane(makerTable);
        scroll.setPreferredSize(new Dimension(180, 200));
        panel.add(scroll);
        
        modelTable = new JTable()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) 
            {
                return false; //Disallow the editing of any cell
            }
        };
        DefaultTableModel model = (DefaultTableModel) modelTable.getModel();
        model.addColumn("Models");
        JScrollPane scroll2 = new JScrollPane(modelTable);
        scroll2.setPreferredSize(new Dimension(180, 200));
        panel.add(scroll2);
        
        yearTable = new JTable()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) 
            {
                return false; //Disallow the editing of any cell
            }
        };
        DefaultTableModel yearModel = (DefaultTableModel) yearTable.getModel();
        yearModel.addColumn("Years");
        JScrollPane scroll3 = new JScrollPane(yearTable);
        scroll3.setPreferredSize(new Dimension(180, 200));
        panel.add(scroll3);
        
        descriptions = new JTable()
        {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) 
            {
                return false; //Disallow the editing of any cell
            }
        };
        DefaultTableModel descModel = (DefaultTableModel) descriptions.getModel();
        String[] temp = this.data.getColumnsNames("description");
        for(int i = 0; i < temp.length; i++)
        {
            descModel.addColumn(temp[i]);
        }
        JScrollPane scroll4 = new JScrollPane(descriptions);
        scroll4.setPreferredSize(new Dimension(400, 130));
        Border title2 = BorderFactory.createTitledBorder("Description");
        scroll4.setBorder(title2);
        panelBottom.add(scroll4);
        
        image = new JPanel();
        image.setPreferredSize(new Dimension(170,130));
        Border title3 = BorderFactory.createTitledBorder("Car Photo");
        image.setBorder(title3);
        panelBottom.add(image);
    }
    
    private void setUpListeners()
    {
        makerTable.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = makerTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                setModelTable(null);
                setYearModel(null);
                setDescModel();
                image.removeAll();
                ui.disablePartsButton();
                ui.disableEditButton();
                ui.endableDeleteButton();
                
                selectedModel = null;
                selectedYear = null;
                descrip = null;
                RLink = null;
                
                int selectedRow = makerTable.getSelectedRow();
                selectedMaker = (String) makerTable.getValueAt(selectedRow, 0);
                String[] models;
                try 
                {
                    models = data.getModelsFromMakers(selectedMaker);
                    setModelTable(models);
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(MakeAutoTable.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                modelTable.setCellSelectionEnabled(true);
                ListSelectionModel cellSelectionModel = modelTable.getSelectionModel();
                cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e)
                    {
                        setYearModel(null);
                        setDescModel();
                        image.removeAll();
                        ui.disablePartsButton();
                        ui.disableEditButton();
                        
                        selectedYear = null;
                        descrip = null;
                        RLink = null;
                        
                        int selectedRow = modelTable.getSelectedRow();
                        if(selectedRow != -1)
                        {
                            image.removeAll();
                            selectedModel = (String) modelTable.getValueAt(selectedRow, 0);
                            String[] years = null;
                            try 
                            {
                                years = data.getYearsFromModel(selectedMaker, selectedModel);
                            } 
                            catch (SQLException ex) 
                            {
                                Logger.getLogger(MakeAutoTable.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            setYearModel(years);
                        
                            //set the image
                            PicturePanel pic = new PicturePanel(selectedMaker, selectedModel);
                            image.add(pic);
                            image.revalidate();
                            
                            yearTable.setCellSelectionEnabled(true);
                            ListSelectionModel cellSelectionModel = yearTable.getSelectionModel();
                            cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                            cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
                                @Override
                                public void valueChanged(ListSelectionEvent e)
                                {
                                    DefaultTableModel descModel = setDescModel();
                                    ui.disablePartsButton();
                                    ui.disableEditButton();
                                    ui.endableDeleteButton();
                                    
                                    descrip = null;
                                    RLink = null;

                                    int selectedRow = yearTable.getSelectedRow();
                                    if(selectedRow != -1)
                                    {
                                        selectedYear = (String) yearTable.getValueAt(selectedRow, 0);
                                        try 
                                        {
                                            desc = data.getYearData(selectedMaker, selectedModel, selectedYear);
                                        } 
                                        catch (SQLException ex) 
                                        {
                                            Logger.getLogger(MakeAutoTable.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                        descModel.setNumRows(desc.length);

                                        for(int i = 0; i < desc.length; i++)
                                        {
                                            for(int j = 0; j < desc[i].length; j++)
                                            {
                                                descModel.setValueAt(desc[i][j], i, j);
                                            }
                                        }

                                        ListSelectionModel cellSelectionModel = descriptions.getSelectionModel();

                                        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
                                            @Override
                                            public void valueChanged(ListSelectionEvent e)
                                            {
                                                int selectedRow = descriptions.getSelectedRow();
                                                if(selectedRow != -1)
                                                {
                                                    descrip = new String[desc[selectedRow].length];
                                                    RLink = (String) descriptions.getValueAt(selectedRow, descriptions.getColumnCount()-1);
                                                    System.arraycopy(desc[selectedRow], 0, descrip, 0, desc[selectedRow].length);
                                                    ui.enablePartsButton();
                                                    ui.enableEditButton();
                                                }
                                            }
                                        });con.repaint();
                                    }
                                }
                            });con.repaint();
                        }
                    }
                });
                con.repaint();
            }
        });
    }
    
    public void repaintMaker() throws SQLException
    {
        String[][] makers = data.getMakers();
//        String[] temp = new String[makers.length];
//        for(int i = 0; i < makers.length; i++)
//        {
//            temp[i] = makers[i][0];
//        }
        for(int i = 0; i < makerTable.getRowCount(); i++)
        {
            makerTable.setValueAt("", i, 0);
        }
        for(int i = 0; i < makerTable.getRowCount(); i++)
        {
            makerTable.setValueAt(makers[i][0], i, 0);
        }
        setModelTable(null);
    }
    
    public void repaintModel() throws SQLException
    {
        setModelTable(data.getModelsFromMakers(selectedMaker));
    }
    
    public void repaintYear() throws SQLException
    {
        setYearModel(data.getYearsFromModel(selectedMaker, selectedModel));
    }
    
    public void repaintDesc() throws SQLException
    {
        try 
        {
            desc = data.getYearData(selectedMaker, selectedModel, selectedYear);
            DefaultTableModel descModel = setDescModel();
            descModel.setNumRows(desc.length);

            for(int i = 0; i < desc.length; i++)
            {
                for(int j = 0; j < desc[i].length; j++)
                {
                    descModel.setValueAt(desc[i][j], i, j);
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MakeAutoTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void setMakerTable(Object[] data)
//    {
//        DefaultTableModel model = new DefaultTableModel();
//        makerTable.setModel(model);
//        model.addColumn("Auto Makers", data);
//    }
    
    public void setModelTable(Object[] data)
    {
        DefaultTableModel model = new DefaultTableModel();
        modelTable.setModel(model);
        model.addColumn("Models", data);
    }

    public void setYearModel(Object[] data)
    {
        DefaultTableModel model = new DefaultTableModel();
        yearTable.setModel(model);
        model.addColumn("Years", data);
    }
        
    public DefaultTableModel setDescModel()
    {
        DefaultTableModel model = new DefaultTableModel();
        descriptions.setModel(model);
        String[] temp = data.getColumnsNames("description");
        for(int i = 0; i < temp.length; i++)
        {
            model.addColumn(temp[i]);
        }
        return model;
    }
        
    public String getMaker()
    {
        return selectedMaker;
    }
    
    public String getModel()
    {
        return selectedModel;
    }
    
    public String getYear()
    {
        return selectedYear;
    }
    
    public String getRLink()
    {
        return RLink;
    }
    
    public String[] getDescription()
    {
        return descrip;
    }
    
    private JTable makerTable;
    private JTable modelTable;
    private JTable yearTable;
    private JTable descriptions;
    private String selectedMaker;
    private String selectedModel;
    private String selectedYear;
    private String RLink;
    private String[] descrip;
    private String[][] desc;
    private JPanel panel;
    private JPanel panelBottom;
    private JPanel image;
    final Data data;
    final Container con;
    final MakeMainFrame ui;
}