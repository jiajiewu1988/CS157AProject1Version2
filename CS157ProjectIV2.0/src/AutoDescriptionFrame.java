import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class AutoDescriptionFrame extends JFrame {
	
	private JFrame desFrame;
	private JTextArea description;
	
	/*Constructor of AutoDescriptionFrame */
	public AutoDescriptionFrame(String rLink){
		
		/*set Title and Size*/
		this.setTitle("Auto Selection");
		this.setSize(800,600);
		
		JPanel desPanel = new JPanel();
		String[] desResult 
			= Result.getPartNumberByRlink(Integer.parseInt(rLink));
		
		/* Create TextArea for description */
		JButton backButton = new JButton(" Back ");
		
		description = new JTextArea();
		description.setBorder(BorderFactory.createTitledBorder("Auto Description: "));
		description.setEditable(false);
		description.setPreferredSize(new Dimension(600,250));
		for(String des : desResult)
			description.append(des);
		
		/* back button function */
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				AutoFrame autoFrame = new AutoFrame();
				autoFrame.setVisible(true);
				desFrame.setVisible(false);
			}
			
		});

		desPanel.add(description);	
		desPanel.add(backButton);
		desPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
		this.getContentPane().add(desPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
