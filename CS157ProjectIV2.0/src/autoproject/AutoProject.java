/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoproject;

import java.sql.SQLException;

/**
 *
 * @author Carita
 */
public class AutoProject 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException 
    {
        MakeMainFrame frame = new MakeMainFrame();
        frame.createMainFrame();
    }
}