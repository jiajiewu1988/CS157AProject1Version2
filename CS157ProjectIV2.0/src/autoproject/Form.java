/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoproject;

import java.sql.Connection;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author Carita
 */
public class Form
{
    public Form(String type)
    {
        data = new Data();
        columnNames = data.getColumnsNames(type);
        field = new JTextField[columnNames.length];
        info = new String[field.length];
    }
    /**
     * creates a form 
     * @return a JPanel of the form
     */
    public static JPanel createForm()
    {
        JPanel form = new JPanel(new SpringLayout());
        
        int numLabels = columnNames.length;
        int numCol = 2;
        int initialX = 5; //x-coord location to start grid
        int initialY = 5; //y-coord location to start grid
        int padX = 5; //padding between cells
        int padY = 5; //padding between cells
        
        for(int j = 0; j < field.length; j++)
        {
            JLabel make = new JLabel(columnNames[j], JLabel.TRAILING);
            form.add(make);
            field[j] = new JTextField(30);
            make.setLabelFor(field[j]);
            form.add(field[j]);
        }
        SpringUtilities.makeCompactGrid(form, numLabels, numCol, initialX, initialY, padX, padY);
        
        return form;
    }
    
    public void readText()
    {
        for(i = 0; i < field.length; i ++)
        {
            info[i] = field[i].getText();
        }
    }
    
    public void setText(String[] values)
    {
        for(int j = 0; j < values.length; j++)
        {
            field[j].setText(values[j]);
        }
    }
    
    public boolean modified(String[] old)
    {
        boolean changed = false; //nothing changed
        readText(); //reads the form
        String[] modified = getInfo();
        for(int i = 0; i < old.length; i++)
        {
            if(!old[i].equalsIgnoreCase(modified[i]))
            {
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean filled()
    {
        readText();
        boolean flag = true;
        String[] temp = getInfo(); //get info
        for(int i = 0; i < temp.length; i++)
        {
            if(temp[i].equals(""))
            {
                flag = false;
            }
        }
        return flag;
    }
    
    public String[] getInfo()
    {
        return info;
    }
    
    private static int i;
    private Data data;
    private static JTextField[] field;
    private static String[] info;
    private static String[] columnNames;
}