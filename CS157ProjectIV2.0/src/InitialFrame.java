
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

/**
 * Display initial screen for the program
 * @author YongXiang Tang, Sean Peng
 *
 */
public class InitialFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JFrame frame = this; 
	
	/**
	 * Constructor for InitialFrame
	 */
	public InitialFrame() {
		
		JPanel buttonPanel = new JPanel();
		JButton makerButton = new JButton ("1.Choose from auto maker");
		JButton partButton = new JButton ("2.Choose from vender part");
		
		/* buttons click actions*/
		makerButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				AutoFrame autoFrame = new AutoFrame();
				autoFrame.setVisible(true);
				frame.setVisible(false);
			}
			
		});
		
		/* button click actions */
		partButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//PartVendorFrame vendorFrame = new PartVendorFrame(frame);
				VendorFrame vendorFrame = new VendorFrame(frame);
				frame.setVisible(false);
			}
		});
		
		/* set panel layout */
		buttonPanel.setLayout(new GridLayout(2,1));
		buttonPanel.add(makerButton);
		buttonPanel.add(partButton);
		this.setLayout(new GridBagLayout());
		this.getContentPane().add(buttonPanel, new GridBagConstraints());
		
		/* set window's properties */
		this.setTitle("Auto Selection");
		this.setSize(400,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}// end of InitialFrame Constructor
	
} // class InitialFrame