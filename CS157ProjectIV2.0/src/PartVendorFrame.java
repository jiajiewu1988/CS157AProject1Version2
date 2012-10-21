import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * Display available part vendors
 * @author Sean Peng
 *
 */
public class PartVendorFrame extends JFrame {
	
	private JFrame frame = this;
	
	/**
	 * Constructor for PartVendorFrame
	 * @param initialFrame JFrame object from previous screen
	 */
	public PartVendorFrame(final JFrame initialFrame) {
		
		JPanel vendorPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel comboPanel = new JPanel();
		
		JButton selectButton = new JButton("Select");
		JButton backButton = new JButton("Back");
		
		String[] vendors = Result.getVendor();
		final JList<String> vendorList = new JList<String>(vendors);
		vendorList.setSelectedIndex(0);
		
		selectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Object selectedVendor = vendorList.getSelectedValue();
				
				PartBySelectedVendorFrame partFrame = new PartBySelectedVendorFrame(frame, selectedVendor);
				partFrame.setVisible(true);
				frame.setVisible(false);
			}
			
		});
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				initialFrame.setVisible(true);
				frame.setVisible(false);
			}
			
		});
		
		vendorPanel.add(new JScrollPane(vendorList));
		buttonPanel.add(backButton);
		buttonPanel.add(selectButton);
		
		comboPanel.setLayout(new GridLayout(2,1));
		comboPanel.add(vendorPanel);
		comboPanel.add(buttonPanel);
		
		this.setSize(400,400);
		this.setTitle("Vendor Selection");
		this.setLayout(new GridBagLayout());
		this.getContentPane().add(comboPanel, new GridBagConstraints());	
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
