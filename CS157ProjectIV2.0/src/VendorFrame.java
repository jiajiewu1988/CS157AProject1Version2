import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Display user interface for user to choose auto parts from vendor
 * @author Sean Peng
 *
 */
public class VendorFrame extends JFrame{
	
	private JFrame frame = this;
	private JLabel description;
	
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
		JPanel buttonPanel = new JPanel();
		
		description = new JLabel();
		
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
					
					@SuppressWarnings("unchecked")
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
				if(!e.getValueIsAdjusting() && ((JList<String>)e.getSource()).getModel().getSize() != 0) {
					
					@SuppressWarnings("unchecked")
					JList<String> list = (JList<String>)e.getSource();
					part = list.getSelectedValue();
					
					String[] spec = Result.getPartSpec(vendor, part);
					String[] colName = Result.getColumnLableFromLatestQueriedTable();
					
					StringBuilder sb = new StringBuilder();
					sb.append("<html>");
					sb.append("<table>");
					
					for(int i=0; i<colName.length; i++) {
						
						sb.append("<tr>");
						sb.append("<td>" + colName[i] + ": </td><td>" + spec[i] + "</td>");
						sb.append("</tr>");
					}
					
					sb.append("</table>");
					sb.append("</html>");
								
					description.setText(sb.toString());
				}
			}
			
		});
		
		JScrollPane vendorScroll = new JScrollPane(vendorList);
		JScrollPane partScroll = new JScrollPane(partList);
		JScrollPane specScroll = new JScrollPane(description);
		
		vendorScroll.setPreferredSize(new Dimension(100, 115));
		partScroll.setPreferredSize(new Dimension(100, 115));
		specScroll.setPreferredSize(new Dimension(500, 115));
		
		specScroll.setBorder(BorderFactory.createTitledBorder("Part Description: "));
		
		partSelection.add(vendorScroll, BorderLayout.WEST);
		partSelection.add(partScroll, BorderLayout.EAST);
		
		buttonPanel.add(backButton);
		buttonPanel.add(orderButton);
		
		
		this.setSize(600,400);
		this.setTitle("Part Selection");
		this.setLayout(new GridLayout(3,1));
		this.add(partSelection);
		this.add(specScroll);
		this.add(buttonPanel);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
