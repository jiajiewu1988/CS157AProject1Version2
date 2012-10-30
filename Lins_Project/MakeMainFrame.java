/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Carita
 */
public class MakeMainFrame
{

    public MakeMainFrame() throws SQLException
    {
        controller = new Data();
        currentMaxRLink = controller.getCurrentRLink();
//        currentMaxPNum = controller.getCurrentPNum();
//        System.out.println(currentMaxRLink + " " + currentMaxPNum);
    }
    /**
     * @param args the command line arguments
     */
    public void createMainFrame()
    {
        final JFrame mainFrame = new JFrame("Auto Parts Retrieval System");
        final JPanel panel = new JPanel();
        final JPanel panel2 = new JPanel();
        final JPanel panel3 = new JPanel();
        final JPanel panel4 = new JPanel();
        final JPanel empty = new JPanel();
        final JPanel empty2 = new JPanel();
        final Data data = new Data();

        JButton lookUpMaker = new JButton("Auto information");
        lookUpMaker.addActionListener(new
            ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    try
                    {
                        table = new MakeAutoTable();
                    }
                    catch (SQLException ex)
                    {
                        Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    final JButton back = new JButton("Back");
                    edit = new JButton("Edit Car");
                    parts = new JButton("See Parts");
                    delete = new JButton("Delete Car");
                    final JPanel panel = new JPanel();
                    final JLabel label = new JLabel();

                    edit.setEnabled(false);
                    parts.setEnabled(false);
                    delete.setEnabled(false);

                    delete.addActionListener(new
                        ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent event)
                            {
                                try
                                {
                                    if(table.getRLink() != null)
                                    {
                                        System.out.println("--rlink--");
                                        String[] carInfo = new String[8];
                                        carInfo[0] = table.getMaker();
                                        carInfo[1] = table.getModel();
                                        carInfo[2] = table.getYear();
                                        String[] temp = table.getDescription();
                                        System.arraycopy(temp, 0, carInfo, 3, temp.length -1);
                                        carInfo[7] = table.getRLink();
                                        controller.deleteCar(carInfo);
                                        table.repaint();
                                        label.setText("          " + carInfo[0] + " " + carInfo[1] + " " + carInfo[2] + " " + carInfo[3] + " " + carInfo[4] + " " + carInfo[5] + " " + carInfo[6] + " " + carInfo[7] + " has been deleted");
                                        
                                        table.repaintDesc();
                                    }
                                    else
                                    {
                                        if(table.getYear() != null)
                                        {
                                            System.out.println("--year--");
                                            String maker = table.getMaker();
                                            String model = table.getModel();
                                            String year = table.getYear();

                                            controller.deleteMakerModelYear(maker, model, year);
                                            table.repaint();
                                            label.setText("          " + maker + " " + model + " " + year + " has been deleted");
                                            
                                            table.repaintYear();
                                        }
                                        else
                                        {
                                            if(table.getModel() != null)
                                            {
                                                System.out.println("--model--");
                                                String maker = table.getMaker();
                                                String model = table.getModel();

                                                controller.deleteMakerModel(maker, model);
                                                table.repaint();
                                                label.setText("          " + maker + " " + model + " " + " has been deleted");
                                                
                                                table.repaintModel();
                                            }
                                            else
                                            {
                                                System.out.println("--maker--");
                                                String maker = table.getMaker();

                                                controller.deleteMaker(maker);
                                                table.repaint();
                                                label.setText("          " + maker + " has been deleted");
                                                
                                                table.repaintMaker();
                                            }
                                        }
                                    }
                                }
                                catch (SQLException ex)
                                {
                                    Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    );

                    back.addActionListener(new
                        ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent event)
                            {
                                mainFrame.remove(table);
                                mainFrame.remove(panel);
                                mainFrame.remove(back);
                                mainFrame.remove(label);
                                mainFrame.setTitle("Auto Parts Retrieval System");
                                mainFrame.setLayout(new GridLayout(1,3));
                                mainFrame.add(empty);
                                mainFrame.add(panel4);
                                mainFrame.add(empty2);
                                mainFrame.repaint();
                            }
                        }
                    );

                    edit.addActionListener(new
                        ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent event)
                            {
                                final String[] carInfo = new String[8];
                                carInfo[0] = table.getMaker();
                                carInfo[1] = table.getModel();
                                carInfo[2] = table.getYear();
                                String[] desc = table.getDescription();
                                System.arraycopy(desc, 0, carInfo, 3, desc.length);

                                final JFrame frame = new JFrame("Edit Car Information");
                                final JLabel label = new JLabel();
                                final Form f = new Form("carForm");
                                JPanel form = Form.createForm();
                                final String[] temp = new String[carInfo.length-1];
                                System.arraycopy(carInfo, 0, temp, 0, temp.length);
                                f.setText(temp);

                                Border title = BorderFactory.createTitledBorder("Edit Car Information");
                                form.setBorder(title);

                                JPanel buttons = new JPanel();
                                JButton submit = new JButton("Submit");
                                JButton back = new JButton("Back");

                                submit.addActionListener(new
                                    ActionListener()
                                    {
                                        @Override
                                        public void actionPerformed(ActionEvent event)
                                        {
                                            boolean changed = f.modified(temp);

                                            if(changed == true)
                                            {
                                                try
                                                {
/*send updated car  to controller*/                 currentMaxRLink = controller.updateCar(f.getInfo(), carInfo, ++currentMaxRLink);
                                                    table.repaintMaker();
                                                    table.repaintModel();
                                                    table.repaintYear();
                                                    table.repaintDesc();
                                                }
                                                catch (SQLException ex)
                                                {
                                                    Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                label.setText(padding + "Update Complete");
                                            }
                                            else
                                            {
                                                label.setText(padding + "No information was changed");
                                            }
                                        }
                                    }
                                );

                                back.addActionListener(new
                                    ActionListener()
                                    {
                                        @Override
                                        public void actionPerformed(ActionEvent event)
                                        {
                                            frame.dispose();
                                        }
                                    }
                                );

                                buttons.add(submit);
                                buttons.add(back);

                                frame.setLayout(new BorderLayout());
                                frame.add(form, BorderLayout.NORTH);
                                frame.add(label, BorderLayout.CENTER);
                                frame.add(buttons, BorderLayout.SOUTH);
                                frame.setSize(600,500);
                                frame.setResizable(false);
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame.setVisible(true);
                            }
                        }
                    );

                    parts.addActionListener(new
                        ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent event)
                            {
                                final JFrame partFrame = new JFrame("Part Information");
                                JPanel panel = new JPanel();
                                final JLabel label = new JLabel();

                                try
                                {
                                    partTable = new MakePartsTable(table.getRLink());
                                }
                                catch (SQLException ex)
                                {
                                    Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                update = new JButton("Update");
                                edit = new JButton("Edit Parts");
                                add = new JButton("Add Part");
                                submit = new JButton("Submit New Part");
                                existingPart = new JButton("Add ext part");
                                JButton back = new JButton("Back");

                                edit.setEnabled(false);
                                update.setEnabled(false);
                                add.setEnabled(false);
                                submit.setEnabled(false);
                                existingPart.setEnabled(false);

                                edit.addActionListener(new
                                    ActionListener()
                                    {
                                        @Override
                                        public void actionPerformed(ActionEvent event)
                                        {
                                            update.setEnabled(true);
                                            partTable.edit();
                                        }
                                    }
                                );

                                existingPart.addActionListener(new
                                    ActionListener()
                                    {
                                        @Override
                                        public void actionPerformed(ActionEvent event)
                                        {
                                            try
                                            {
                                                controller.connectPartToCar(Integer.parseInt(table.getRLink()), Integer.parseInt(partTable.getPartNumber()), partTable.getSupplier());
                                                label.setText(padding + "Part " + partTable.getPartNumber() + " is now added to Car");
                                                partTable.repaint(table.getRLink());
                                            }
                                            catch (SQLException ex)
                                            {
                                                Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    }
                                );

                                add.addActionListener(new
                                    ActionListener()
                                    {
                                        @Override
                                        public void actionPerformed(ActionEvent event)
                                        {
                                            update.setEnabled(false);
                                            submit.setEnabled(true);
                                            partTable.edit();
                                        }
                                    }
                                );

                                submit.addActionListener(new
                                    ActionListener()
                                    {
                                        @Override
                                        public void actionPerformed(ActionEvent event)
                                        {
                                            try
                                            {
///*Pass updated part to controller*/             controller.insertNewPart(partTable.getSupplier(), partTable.getUpdatedInfo());
                                                controller.addNewPartToCar(partTable.getSupplier(), partTable.getUpdatedInfo(), table.getRLink());
                                            }
                                            catch (SQLException ex)
                                            {
                                                Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            label.setText(padding + "Information has updated");
                                        }
                                    }
                                );

                                update.addActionListener(new
                                    ActionListener()
                                    {
                                        @Override
                                        public void actionPerformed(ActionEvent event)
                                        {
                                            add.setEnabled(false);
                                            boolean updated = false;
                                            try
                                            {
                                                updated = partTable.update();
                                            }
                                            catch (SQLException ex)
                                            {
                                                Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                            if(updated == true)
                                            {
                                                try
                                                {
/*Pass updated part to controller*/                 controller.updatePart(partTable.getUpdatedInfo(), Integer.parseInt(partTable.getPartNumber()), partTable.getSupplier().substring(0, 3));
                                                }
                                                catch (SQLException ex)
                                                {
                                                    Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                label.setText(padding + "Information has updated");
                                                try 
                                                {
                                                    partTable.repaint(table.getRLink());
                                        //return the new rlink for updated part?
                                        //controller should get the new info and pass it onto the model
                                        //refresh
                                                } 
                                                catch (SQLException ex) 
                                                {
                                                    Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                            else
                                            {
                                                label.setText(padding + "No information was changed");
                                            }
                                        }
                                    }
                                );

                                back.addActionListener(new
                                    ActionListener()
                                    {
                                        @Override
                                        public void actionPerformed(ActionEvent event)
                                        {
                                            partFrame.dispose();
                                        }
                                    }
                                );

                                panel.add(edit);
                                panel.add(update);
                                panel.add(add);
                                panel.add(submit);
                                panel.add(existingPart);
                                panel.add(back);

                                partFrame.add(partTable, BorderLayout.NORTH);
                                partFrame.add(label, BorderLayout.CENTER);
                                partFrame.add(panel, BorderLayout.SOUTH);
                                partFrame.setSize(600,500);
                                partFrame.setResizable(false);
                                partFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                partFrame.setVisible(true);
                            }
                        }
                    );

                    panel.add(parts);
                    panel.add(edit);
                    panel.add(delete);
                    panel.add(back);

                    mainFrame.setTitle("AutoMaker");
                    mainFrame.remove(empty);
                    mainFrame.remove(empty2);
                    mainFrame.remove(panel4);

                    mainFrame.setLayout(new FlowLayout());
                    mainFrame.add(table);
                    mainFrame.add(panel);
                    mainFrame.add(label);
                    mainFrame.repaint();
                    mainFrame.setVisible(true);
                }
            }
        );

        JButton lookUpVendor = new JButton("Vendor and parts");
        lookUpVendor.addActionListener(new
            ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent event)
                {
                    try
                    {
                        final JFrame vendorFrame = new JFrame("Select Vendor and Part Number");
                        JPanel panel = new JPanel();

                        String[] vendors = data.getColumnsNames("vendorNames");
                        final MakeVendorTable vendorTable = new MakeVendorTable(vendors);

                        JButton back = new JButton("Back");
                        select = new JButton("Select");

                        select.setEnabled(false);

                        back.addActionListener(new
                            ActionListener()
                            {
                                @Override
                                public void actionPerformed(ActionEvent event)
                                {
                                    vendorFrame.dispose();
                                }
                            }
                        );

                        select.addActionListener(new
                            ActionListener()
                            {
                                @Override
                                public void actionPerformed(ActionEvent event)
                                {
                                    try {
                                        final JFrame partFrame = new JFrame("Part Information");
                                        final JLabel label = new JLabel();
                                        JPanel panel = new JPanel();

                                        final MakePartsTable partTable = new MakePartsTable(vendorTable.getVendor(), vendorTable.getPartNumber());

                                        JButton edit = new JButton("Edit Parts");
                                        JButton delete = new JButton("Delete Part");
                                        final JButton update = new JButton("Update");
                                        JButton back = new JButton("Back");

                                        update.setEnabled(false);

                                        edit.addActionListener(new
                                            ActionListener()
                                            {
                                                @Override
                                                public void actionPerformed(ActionEvent event)
                                                {
                                                    partTable.edit();
                                                    update.setEnabled(true);
                                                }
                                            }
                                        );

                                        delete.addActionListener(new
                                            ActionListener()
                                            {
                                                @Override
                                                public void actionPerformed(ActionEvent event)
                                                {
                                                    String vendor = vendorTable.getVendor();
                                                    String pNum = vendorTable.getPartNumber();

                                                    try
                                                    {
                                                        controller.deletePart(vendor, pNum);
                                                    }
                                                    catch (SQLException ex)
                                                    {
                                                        Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                    label.setText(padding + "Part Has been deleted");
                                                    vendorTable.repaint();
                                                }
                                            }
                                        );

                                        update.addActionListener(new
                                            ActionListener()
                                            {
                                                @Override
                                                public void actionPerformed(ActionEvent event)
                                                {
                                                    boolean updated = false;
                                                    try
                                                    {
                                                        updated = partTable.update();
                                                    }
                                                    catch (SQLException ex)
                                                    {
                                                        Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                                    }

                                                    if(updated == true)
                                                    {
                                                        try
                                                        {
    /*send updated part to controller*/                     controller.updatePart(partTable.getUpdatedInfo(), Integer.parseInt(partTable.getPartNumber()), vendorTable.getVendor().substring(0, 3));
                                                            vendorTable.repaint();
                                                        }
                                                        catch (SQLException ex)
                                                        {
                                                            Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                                        }
                                                        label.setText(padding + "Information has updated");
                                                        //return the new rlink for updated part?
                                                        //controller should get the new info and pass it onto the model
                                                        //refresh
                                                    }
                                                    else
                                                    {
                                                        label.setText(padding + "No information was changed");
                                                    }
                                                }
                                            }
                                        );

                                        back.addActionListener(new
                                            ActionListener()
                                            {
                                                @Override
                                                public void actionPerformed(ActionEvent event)
                                                {
                                                    partFrame.dispose();
                                                }
                                            }
                                        );

                                        panel.add(edit);
                                        panel.add(update);
                                        panel.add(delete);
                                        panel.add(back);

                                        partFrame.add(partTable, BorderLayout.NORTH);
                                        partFrame.add(label, BorderLayout.CENTER);
                                        partFrame.add(panel, BorderLayout.SOUTH);
                                        partFrame.setSize(600,500);
                                        partFrame.setResizable(false);
                                        partFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                        partFrame.setVisible(true);
                                    }
                                    catch (SQLException ex)
                                    {
                                        Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        );

                        panel.add(select);
                        panel.add(back);

                        vendorFrame.add(vendorTable, BorderLayout.NORTH);
                        vendorFrame.add(panel, BorderLayout.SOUTH);
                        vendorFrame.setSize(300,300);
                        vendorFrame.setResizable(false);
                        vendorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        vendorFrame.setVisible(true);
                    }
                    catch (SQLException ex)
                    {
                        Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        );

        JButton addCar = new JButton("Car");
        addCar.addActionListener(new
            ActionListener()
            {
            @Override
                public void actionPerformed(ActionEvent event)
                {
                    final Form car = new Form("carForm");
                    final JPanel form = Form.createForm();
                    final JPanel design = new JPanel(new GridLayout(1, 3));
                    final JLabel label = new JLabel();

                    design.add(new JPanel());
                    design.add(label);
                    design.add(new JPanel());

                    Border title = BorderFactory.createTitledBorder("Enter New Car Information");
                    form.setBorder(title);

                    final JButton back = new JButton("Back");
                    final JButton addCar = new JButton("Submit New Car");
                    final JButton addNewPart = new JButton("Add New Parts to Car");
                    final JButton addExistingPart = new JButton("Add Existing Parts to Car");
                    final JPanel buttons = new JPanel();

                    addNewPart.setEnabled(false);
                    addExistingPart.setEnabled(false);

                    back.addActionListener(new
                        ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent event)
                            {
                                mainFrame.remove(form);
                                mainFrame.remove(buttons);
                                mainFrame.remove(panel);
                                mainFrame.remove(design);
                                mainFrame.remove(back);
                                mainFrame.setTitle("Auto Parts Retrieval System");
                                mainFrame.setLayout(new GridLayout(1,3));
                                mainFrame.add(empty);
                                mainFrame.add(panel4);
                                mainFrame.add(empty2);
                                mainFrame.repaint();
                            }
                        }
                    );

                    addCar.addActionListener(new
                        ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent event)
                            {
                                boolean flag = car.filled();

                                if(flag == true)
                                {
                                    newCar = car.getInfo();
                                    try
                                    {
/*send new car info to controller*/     currentMaxRLink = controller.insertNewCar(newCar, ++currentMaxRLink);
                                    }
                                    catch (SQLException ex)
                                    {
                                        Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    label.setText("Car added to database");
                                    addCar.setEnabled(false);

                                    addNewPart.addActionListener(new
                                        ActionListener()
                                        {
                                            @Override
                                            public void actionPerformed(ActionEvent event)
                                            {
                                                final JFrame newCarPartsFrame = new JFrame("Create New Part for Car");
                                                final JLabel newLabel = new JLabel();
                                                final JPanel buttons = new JPanel();

                                                final Form part = new Form("partInfo");
                                                final JPanel form = Form.createForm();
                                                Border title = BorderFactory.createTitledBorder("Enter New Part Information");
                                                form.setBorder(title);

                                                final JComboBox tab = new JComboBox(data.getColumnsNames("vendors"));
                                                final JPanel supplier = new JPanel();
                                                supplier.add(new JLabel("Please Select the Supplier for the New Part: "));
                                                supplier.add(tab);

                                                //add a new partsTable with empty cells as buttons

                                                final JPanel design = new JPanel(new GridLayout(2, 3));
                                                design.add(new JPanel());
                                                design.add(buttons);
                                                design.add(new JPanel());
                                                design.add(new JPanel());
                                                design.add(newLabel);
                                                design.add(new JPanel());

                                                JButton submit = new JButton("Submit");
                                                JButton cancel = new JButton("Cancel");

                                                submit.addActionListener(new
                                                    ActionListener()
                                                    {
                                                        @Override
                                                        public void actionPerformed(ActionEvent event)
                                                        {
                                                            boolean flag = part.filled();

                                                            if(flag == true)
                                                            {
                                                                try
                                                                {
///*send new part to controller, connect new rlink to car*/           int pnum = controller.insertNewPart((String) tab.getSelectedItem(), part.getInfo());
                                                                    controller.addNewPartToCar((String) tab.getSelectedItem(), part.getInfo(), Integer.toString(currentMaxRLink));
//                                                                    controller.connectPartToCar(currentMaxRLink, pnum, (String) tab.getSelectedItem());
                                                                    newLabel.setText("New Part is added to the car");
                                                                }
                                                                catch (SQLException ex)
                                                                {
                                                                    newLabel.setText("A part already exists for that supplier");
                                                                    Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                                                }
                                                            }
                                                            else
                                                            {
                                                                newLabel.setText("Please fill in all fields");
                                                            }
                                                        }
                                                    }
                                                );

                                                cancel.addActionListener(new
                                                    ActionListener()
                                                    {
                                                        @Override
                                                        public void actionPerformed(ActionEvent event)
                                                        {
                                                            newCarPartsFrame.remove(supplier);
                                                            newCarPartsFrame.remove(form);
                                                            newCarPartsFrame.remove(design);
                                                            newCarPartsFrame.dispose();
                                                        }
                                                    }
                                                );

                                                buttons.add(submit);
                                                buttons.add(cancel);

                                                newCarPartsFrame.setLayout(new FlowLayout());
                                                newCarPartsFrame.add(supplier);
                                                newCarPartsFrame.add(form);
                                                newCarPartsFrame.add(design);

                                                newCarPartsFrame.setResizable(false);
                                                newCarPartsFrame.setSize(600,500);
                                                newCarPartsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                newCarPartsFrame.setVisible(true);
                                            }
                                        }
                                    );

                                    addExistingPart.addActionListener(new
                                        ActionListener()
                                        {
                                            @Override
                                            public void actionPerformed(ActionEvent event)
                                            {
                                                try
                                                {
                                                    final JFrame newCarExistingPartsFrame = new JFrame("Choose Existing Part for Car");
                                                    final MakePartsTable table = new MakePartsTable(currentMaxRLink);
                                                    final JLabel newLabel = new JLabel();
                                                    final JPanel buttons = new JPanel();

                                                    final JPanel design = new JPanel(new GridLayout(1,3));
                                                    design.add(new JPanel());
                                                    design.add(newLabel);
                                                    design.add(new JPanel());

                                                    final JButton cancel = new JButton("Cancel");
                                                    final JButton submit = new JButton("Submit");

                                                    cancel.addActionListener(new
                                                        ActionListener()
                                                        {
                                                            @Override
                                                            public void actionPerformed(ActionEvent event)
                                                            {
                                                                newCarExistingPartsFrame.remove(table);
                                                                newCarExistingPartsFrame.remove(design);
                                                                newCarExistingPartsFrame.remove(buttons);
                                                                newCarExistingPartsFrame.dispose();
                                                            }
                                                        }
                                                    );

                                                    /*
                                                     * choose existing part for the new car
                                                     * need to select part too? or just rlink?
                                                     */
                                                    submit.addActionListener(new
                                                        ActionListener()
                                                        {
                                                            @Override
                                                            public void actionPerformed(ActionEvent event)
                                                            {
                                                                try
                                                                {
                                                                    controller.connectPartToCar(currentMaxRLink, Integer.parseInt(table.getPartNumber()), table.getSupplier());
                                                                    label.setText(padding.substring(0, 20) + "Part has been added to Car");
                                                                    table.repaint(Integer.toString(currentMaxRLink));
                                                                }
                                                                catch (SQLException ex)
                                                                {
                                                                    Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                                                }
                                                            }
                                                        }
                                                    );

                                                    buttons.add(submit);
                                                    buttons.add(cancel);

                                                    newCarExistingPartsFrame.add(table, BorderLayout.NORTH);
                                                    newCarExistingPartsFrame.add(design, BorderLayout.CENTER);
                                                    newCarExistingPartsFrame.add(buttons, BorderLayout.SOUTH);

                                                    newCarExistingPartsFrame.setResizable(false);
                                                    newCarExistingPartsFrame.setSize(600,500);
                                                    newCarExistingPartsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                    newCarExistingPartsFrame.setVisible(true);
                                                }
                                                catch (SQLException ex)
                                                {
                                                    Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                        }
                                    );

                                    addNewPart.setEnabled(true);
                                    addExistingPart.setEnabled(true);
                                }
                                else
                                {
                                    label.setText("Please fill in all fields");
                                }
                            }
                        }
                    );

                    buttons.add(addCar);
                    buttons.add(addNewPart);
                    buttons.add(addExistingPart);
                    buttons.add(back);

                    mainFrame.setTitle("New Car");
                    mainFrame.remove(empty);
                    mainFrame.remove(empty2);
                    mainFrame.remove(panel4);

                    mainFrame.setLayout(new FlowLayout());
                    mainFrame.add(form, BorderLayout.NORTH);
                    mainFrame.add(buttons, BorderLayout.CENTER);
                    mainFrame.add(design, BorderLayout.SOUTH);
                    mainFrame.repaint();
                    mainFrame.setVisible(true);
                }
            }
        );

        JButton addPart = new JButton("Part");
        addPart.addActionListener(new
            ActionListener()
            {
            @Override
                public void actionPerformed(ActionEvent event)
                {
                    final Form part = new Form("partInfo");
                    final JLabel label = new JLabel();
                    final JPanel form = Form.createForm();
                    final JComboBox tab = new JComboBox(data.getColumnsNames("vendorNames"));

                    final JPanel supplier = new JPanel();
                    supplier.add(new JLabel("Please Select the Supplier for the New Part: "));
                    supplier.add(tab);

                    final JPanel design = new JPanel(new GridLayout(1,3));
                    design.add(new JPanel());
                    design.add(label);
                    design.add(new JPanel());

                    Border title = BorderFactory.createTitledBorder("Enter New Part Information");
                    form.setBorder(title);

                    final JButton back = new JButton("Back");
                    JButton submitPart = new JButton("Submit New Part");
                    final JPanel buttons = new JPanel(new FlowLayout());

                    part.readText();
                    final String[] old = part.getInfo();

                    back.addActionListener(new
                        ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent event)
                            {
                                mainFrame.remove(supplier);
                                mainFrame.remove(form);
                                mainFrame.remove(buttons);
                                mainFrame.remove(design);
                                mainFrame.remove(back);
                                mainFrame.setTitle("Auto Parts Retrieval System");
                                mainFrame.setLayout(new GridLayout(1,3));
                                mainFrame.add(empty);
                                mainFrame.add(panel4);
                                mainFrame.add(empty2);
                                mainFrame.repaint();
                            }
                        }
                    );

                    submitPart.addActionListener(new
                        ActionListener()
                        {
                            @Override
                            public void actionPerformed(ActionEvent event)
                            {
                                part.readText();
                                if(tab.getSelectedItem() != null)
                                {
                                    String[] info = part.getInfo();
                                    if(info[7].equals("")){info[7] = null;}
                                    if(info[8].equals("")){info[8] = null;}
                                    try
                                    {
/*send new part to controller*/         controller.insertNewPart((String) tab.getSelectedItem(), part.getInfo());
                                    }
                                    catch (SQLException ex)
                                    {
                                        Logger.getLogger(MakeMainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    label.setText("           Part added to database");
                                }
                                else
                                {
                                    label.setText("           Please fill in all fields");
                                }
                            }
                        }
                    );
                    buttons.add(submitPart);
                    buttons.add(back);

                    mainFrame.setTitle("New Part");
                    mainFrame.remove(empty);
                    mainFrame.remove(empty2);
                    mainFrame.remove(panel4);

                    mainFrame.setLayout(new FlowLayout());
                    mainFrame.add(supplier);
                    mainFrame.add(form);
                    mainFrame.add(buttons);
                    mainFrame.add(design);
                    mainFrame.repaint();
                    mainFrame.setVisible(true);
                }
            }
        );

        JButton exit = new JButton("Exit");
        exit.addActionListener(new
            ActionListener()
            {
            @Override
                public void actionPerformed(ActionEvent event)
                {
                    mainFrame.dispose();
                }
            }
        );

        panel.setSize(400, 180);
        panel.setLayout(new GridLayout(2,1,10,10));
        panel.add(lookUpMaker);
        panel.add(lookUpVendor);

        panel2.setSize(400, 180);
        panel2.setLayout(new GridLayout(2,1,10,10));
        panel2.add(addCar);
        panel2.add(addPart);

        panel3.add(exit);

        Border title = BorderFactory.createTitledBorder("Look up auto information by:");
        panel.setBorder(title);

        Border title2 = BorderFactory.createTitledBorder("Create new:");
        panel2.setBorder(title2);

        Border title3 = BorderFactory.createTitledBorder("Exit Program");
        panel3.setBorder(title3);

        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
        panel4.add(Box.createVerticalStrut(75));
        panel4.add(panel);
        panel4.add(Box.createVerticalStrut(20));
        panel4.add(panel2);
        panel4.add(Box.createVerticalStrut(20));
        panel4.add(panel3);
        panel4.add(Box.createVerticalStrut(75));

        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(1,3));
        mainFrame.add(empty);
        mainFrame.add(panel4);
        mainFrame.add(empty2);
        mainFrame.setSize(600,500);
        mainFrame.setVisible(true);
    }

    public void endableDeleteButton()
    {
        delete.setEnabled(true);
    }
    public void disableDeleteButton()
    {
        delete.setEnabled(false);
    }
    public void enablePartsButton()
    {
        parts.setEnabled(true);
    }
    public void disablePartsButton()
    {
        parts.setEnabled(false);
    }
    public void enableEditButton()
    {
        edit.setEnabled(true);
    }
    public void disableEditButton()
    {
        edit.setEnabled(false);
    }
    public void enableSelectButton()
    {
        select.setEnabled(true);
    }
    public void disableSelectButton()
    {
        select.setEnabled(false);
    }
    public void enableAddButton()
    {
        add.setEnabled(true);
    }
    public void disableAddButton()
    {
        add.setEnabled(false);
    }
    public void enableUpdateButton()
    {
        update.setEnabled(true);
    }
    public void disableUpdateButton()
    {
        update.setEnabled(false);
    }
    public void enableSubmitButton()
    {
        submit.setEnabled(true);
    }
    public void disableSubmitButton()
    {
        submit.setEnabled(false);
    }
    public void enableExistingPartButton()
    {
        existingPart.setEnabled(true);
    }
    public void disableExistingPartButton()
    {
        existingPart.setEnabled(false);
    }
    public int getNewRLink()
    {
        return currentMaxRLink;
    }

    private String[] newCar;
    private static MakeAutoTable table;
    private static MakePartsTable partTable;
    private static JButton delete;
    private static JButton parts;
    private static JButton edit;
    private static JButton select;
    private static JButton add;
    private static JButton update;
    private static JButton submit;
    private static JButton existingPart;
    private int currentMaxRLink;
//    private int currentMaxPNum;
    private Data controller;
    private final static String padding = "                                                                        ";
}