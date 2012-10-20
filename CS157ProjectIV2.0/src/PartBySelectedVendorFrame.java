import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class PartBySelectedVendorFrame extends JFrame {

	private JFrame frame = this;
	
	public PartBySelectedVendorFrame(final JFrame partVendorFrame, final Object selectedVendor) {
		
		JPanel partPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JButton selectButton = new JButton("Select");
		JButton backButton = new JButton("Back");
		
		String[] parts = Result.getPart(selectedVendor.toString());
		//String[] parts = {"5.6L HEMI V8", "2.4L i-VTEC I4", "2.0 Boxer Turbo H4"};
		final JList<String> partList = new JList<String>(parts);
		partList.setSelectedIndex(0);
		
		selectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object selectedPart = partList.getSelectedValue();
				
				PartSpecFrame specFrame = new PartSpecFrame(frame, selectedVendor, selectedPart);
				specFrame.setVisible(true);
				frame.setVisible(false);
			}
			
		});
		
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				partVendorFrame.setVisible(true);
				frame.setVisible(false);
			}
			
		});
		
		partPanel.add(new JScrollPane(partList));
		buttonPanel.add(selectButton);
		buttonPanel.add(backButton);
		
		this.getContentPane().add(partPanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.setTitle("Part Selection");
		this.setSize(600,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}
