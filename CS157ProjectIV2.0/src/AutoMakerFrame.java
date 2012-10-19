import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;


public class AutoMakerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame = this; 
	private String maker;
	private final static String title = 
				"Please select one of the following auto makers:";
	private final static int height = 20;
	private final static int width = 400;
	
	public AutoMakerFrame(){
		
		/*set Title*/
		this.setTitle("Auto Selection");
		this.setSize(800,600);
		
		JPanel autoPanel = new JPanel(); 
		JLabel makerLabel = new JLabel(title);
		JButton nextButton = new JButton("Next>");
		JButton backButton = new JButton("<Back");
		
		
		/*Create Jlist and Set up JList property*/
		String[] makerModel =  {"BMW", "TOY", "HON"};
		JList<String> makerList = new JList<String>(makerModel);
		makerList.setVisibleRowCount(5);
		makerList.setFixedCellHeight(height);
		makerList.setFixedCellWidth(width);
		makerList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				//getValueIsAdjusting becomes false
				if(!e.getValueIsAdjusting()){
					
					JList<String> list = (JList<String>)e.getSource();
					maker = (String) list.getSelectedValue();
				}
			}
		});

		
		/* Add button clicking actions */
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				InitialFrame intFrame = new InitialFrame();
				intFrame.setVisible(true);
				frame.setVisible(false);
			}
			
		});
		
		nextButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				AutoModelFrame modelFrame = new AutoModelFrame(); 
				modelFrame.setVisible(true);
				frame.setVisible(false);
			}
		});
		
		/*Constructing autoPanel's property*/
		autoPanel.add(makerLabel);
		autoPanel.add(new JScrollPane(makerList));
		autoPanel.add(backButton);
		autoPanel.add(nextButton);
		autoPanel.setBorder(BorderFactory.createEmptyBorder(100, 200, 400, 200));
		this.getContentPane().add(autoPanel);	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	} // end of autoMakerFrame constructor

	/** 
	 * Get car maker
	 * @return maker
	 */
	public String getMaker(){
		return maker;
	}
}
