import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class PartSpecFrame extends JFrame{

	private JFrame frame = this;
	
	public PartSpecFrame(final JFrame partBySelectedVendorFrame, Object selectedVendor, Object selectedPart) {
		
		JPanel buttonPanel = new JPanel();
		JButton orderButton = new JButton("Order");
		JButton backButton = new JButton("Back");
		JTextArea descriptionArea = new JTextArea();
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				partBySelectedVendorFrame.setVisible(true);
				frame.setVisible(false);
			}
			
		});
		
		String newLine = System.getProperty("line.separator");
		descriptionArea.setText("Vendor: " + selectedVendor + newLine + "Part: " + selectedPart);
		
		buttonPanel.add(orderButton);
		buttonPanel.add(backButton);
		
		this.getContentPane().add(descriptionArea, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.setTitle("Part Specification");
		this.setSize(600,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
