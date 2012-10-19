import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class PartVendorFrame extends JFrame {
	
	private JFrame frame = this;
	
	public PartVendorFrame(final JFrame initialFrame) {
		
		JPanel vendorPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JButton selectButton = new JButton("Select");
		JButton backButton = new JButton("Back");
		
		String[] vendors = {"Hot Rod", "Motor Trend", "High Speed Unlimited"};
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
		buttonPanel.add(selectButton);
		buttonPanel.add(backButton);
		
		this.getContentPane().add(vendorPanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.setTitle("Vendor Selection");
		this.setSize(600,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
