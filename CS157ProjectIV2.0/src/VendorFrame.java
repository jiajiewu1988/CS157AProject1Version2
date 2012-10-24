import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Display user interface for user to choose auto parts from vendor
 * @author Sean Peng
 *
 */
public class VendorFrame extends JFrame{
	
	private JFrame frame = this;
	
	private DefaultListModel<String> vendorListModel;
	private DefaultListModel<String> partListModel;
	private String vendor;
	private String part;
	

	/**
	 * Constructor for VendorFrame
	 * @param initialFrame JFrame object for initialFrame
	 */
	public VendorFrame(final JFrame initialFrame) {
		
		vendorListModel = new DefaultListModel<String>();
		partListModel = new DefaultListModel<String>();
		
		JList<String> vendorList = new JList<String>(vendorListModel);
		JList<String> partList = new JList<String>(partListModel);
		
		JPanel partSelection = new JPanel();
		JPanel description = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		JButton backButton = new JButton("Back");
		JButton orderButton = new JButton("Order");
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				initialFrame.setVisible(true);
				frame.setVisible(false);
			}
		
		});
		
		String[] vendors = Result.getVendor();
			
		for(String v : vendors)
			vendorListModel.addElement(v);
			
		vendorList.setBorder(BorderFactory.createTitledBorder("Vendor:"));
		vendorList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				//only fire up when mouse up
				if(!e.getValueIsAdjusting()) {
					
					JList<String> list = (JList<String>)e.getSource();
					vendor = list.getSelectedValue();
					partListModel.clear();
					
					String[] parts = Result.getPartNumber(vendor);					
					for(String part : parts)
						partListModel.addElement(part);
				}
			}
			
		});
		
		partList.setBorder(BorderFactory.createTitledBorder("Part:"));
		partList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				//only fire up when mouse up
				if(!e.getValueIsAdjusting()) {
					
					JList<String> list = (JList<String>)e.getSource();
					part = list.getSelectedValue();
					
					String[] spec = Result.getPartSpec(vendor, part);
					String[] colName = Result.getColumnLableFromLatestQueriedTable();
					
					JTable table = makeDisplayTable(spec, colName);
				}
			}
			
		});
		
		JScrollPane vendorScroll = new JScrollPane(vendorList);
		JScrollPane partScroll = new JScrollPane(partList);
		
		vendorScroll.setPreferredSize(new Dimension(100, 115));
		partScroll.setPreferredSize(new Dimension(100, 115));
		
		partSelection.add(vendorScroll, BorderLayout.WEST);
		partSelection.add(partScroll, BorderLayout.EAST);
		
		description.setBorder(BorderFactory.createTitledBorder("Part Description: "));
		
		buttonPanel.add(backButton);
		buttonPanel.add(orderButton);
		
		
		this.setSize(600,400);
		this.setTitle("Part Selection");
		this.setLayout(new GridLayout(3,1));
		this.add(partSelection);
		this.add(description);
		this.add(buttonPanel);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	/**
	 * Construct a table displaying sql data
	 * @param data data being display
	 * @param heading the heading for the data
	 * @return JTable
	 */
	private JTable makeDisplayTable(String[] data, String[] heading) {
		
		Vector<String> rowOne = new Vector<String>(Arrays.asList(data));
		Vector<String> columnNames = new Vector<String>(Arrays.asList(heading));		
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
		
		return table;
	}
	
}
