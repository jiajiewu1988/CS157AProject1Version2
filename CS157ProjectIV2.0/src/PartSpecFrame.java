import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


/**
 * Display selected part's specification
 * @author Sean Peng
 *
 */
public class PartSpecFrame extends JFrame{

	private JFrame frame = this;
	
	/**
	 * Constroctor for PartSpecFrame
	 * @param partBySelectedVendorFrame JFrame object from previous screen
	 * @param selectedVendor selected vendor's name from previous screen
	 * @param selectedPart selected part number from previous screen
	 */
	public PartSpecFrame(final JFrame partBySelectedVendorFrame, Object selectedVendor, Object selectedPart) {
		
		String[] spec = Result.getPartSpec(selectedVendor.toString(), selectedPart.toString());
		String[] colName = Result.getColumnLableFromCurrentTable();
		
		Vector<String> rowOne = new Vector<String>(Arrays.asList(spec));
		Vector<String> columnNames = new Vector<String>(Arrays.asList(colName));		
	    Vector<Vector> rowData = new Vector<Vector>();
	    
	    rowData.addElement(rowOne);
		
		JTable table = new  JTable(rowData, columnNames) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
			
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumnAdjuster tca = new TableColumnAdjuster(table);
		tca.adjustColumns();
		
		JScrollPane tablePanel = new JScrollPane(table);
		
		JPanel buttonPanel = new JPanel();
		JButton orderButton = new JButton("Order");
		JButton backButton = new JButton("Back");
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				partBySelectedVendorFrame.setVisible(true);
				frame.setVisible(false);
			}
			
		});
		
		buttonPanel.add(backButton);
		buttonPanel.add(orderButton);
		
		this.setSize(800,400);
		this.setTitle("Part Specification");
		this.getContentPane().add(tablePanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);	
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
