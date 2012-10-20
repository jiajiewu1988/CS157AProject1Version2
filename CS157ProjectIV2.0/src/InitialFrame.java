
import java.awt.event.*;

import javax.swing.*;

public class InitialFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame = this; 
	
	public InitialFrame() {
		
		/*set title*/
		setTitle("Auto Selection");
		setSize(400,300);
		
		JPanel panel = new JPanel();
		/*create button for auto maker selection*/
		JButton makerButton = new JButton ("1.Choose from auto maker");
		panel.add(makerButton);
		/*create button for vender part selection*/
		JButton partButton = new JButton ("2.Choose from vender part");
		panel.add(partButton);
		
		/*set panel layout */
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		this.getContentPane().add(panel);	

//		final VenderPartFrame venderFrame = new VenderPartFrame(); 
		
		/* buttons click actions*/
		makerButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				AutoFrame autoFrame = new AutoFrame();
				autoFrame.setVisible(true);
				frame.setVisible(false);
			}
			
		});
		
		partButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				PartVendorFrame vendorFrame = new PartVendorFrame(frame);
				vendorFrame.setVisible(true);
				frame.setVisible(false);
			}
		});
		
	}// end of InitialFrame Constructor
} // class InitialFrame