import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * Display available parts from selected vendor 
 * @author Sean Peng
 *
 */
public class PartBySelectedVendorFrame extends JFrame {

	private JFrame frame = this;
	
	/**
	 * Constructor for PartBySelectedVendorFrame
	 * @param partVendorFrame JFrame object from previous screen
	 * @param selectedVendor selected vendor's name from previous screen
	 */
	public PartBySelectedVendorFrame(final JFrame partVendorFrame, final Object selectedVendor) {
		
		JPanel partPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel comboPanel = new JPanel();
		
		JButton selectButton = new JButton("Select");
		JButton backButton = new JButton("Back");
		
		String[] parts = Result.getPartNumber(selectedVendor.toString());
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
		buttonPanel.add(backButton);
		buttonPanel.add(selectButton);
		
		comboPanel.setLayout(new GridLayout(2,1));
		comboPanel.add(partPanel);
		comboPanel.add(buttonPanel);
		
		this.setSize(400,400);
		this.setTitle("Part Selection");
		this.setLayout(new GridBagLayout());
		this.getContentPane().add(comboPanel, new GridBagConstraints());		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}
