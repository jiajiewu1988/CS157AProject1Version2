import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class AutoModelFrame extends JFrame {
	
	private final static String title = 
			"Please select one of the following auto models:";
	private String model; 
	private JFrame modelFrame = this; 
	private final static int height = 20;
	private final static int width = 400;
	
	public AutoModelFrame(){
		
		/*set Title*/
		this.setTitle("Auto Selection");
		this.setSize(800,600);
		
		JPanel autoPanel = new JPanel(); 
		JLabel makerLabel = new JLabel(title);
		JButton nextButton = new JButton("Next>");
		JButton backButton = new JButton("<Back");
		
		/*Create JList and Set up JList property*/
		String[] makerModel =  {"A", "B", "C"};
		JList<String> modelList = new JList<String>(makerModel);
		modelList.setVisibleRowCount(5);
		modelList.setFixedCellHeight(height);
		modelList.setFixedCellWidth(width);
		modelList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				//getValueIsAdjusting becomes false
				if(!e.getValueIsAdjusting()){
					
					JList<String> list = (JList<String>)e.getSource();
					model = (String) list.getSelectedValue();
				}
			}
		});
		
		/* Add button clicking actions */
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				AutoMakerFrame makerFrame = new AutoMakerFrame();
				makerFrame.setVisible(true);
				modelFrame.setVisible(false);
			}
			
		});
		
		nextButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				AutoYearFrame yearFrame = new AutoYearFrame();
				yearFrame.setVisible(true);
				modelFrame.setVisible(false);
			}
		});
		
		/*Constructing autoPanel's property*/
		autoPanel.add(makerLabel);
		autoPanel.add(new JScrollPane(modelList));
		autoPanel.add(backButton);
		autoPanel.add(nextButton);
		autoPanel.setBorder(BorderFactory.createEmptyBorder(100, 200, 400, 200));
		this.getContentPane().add(autoPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/** 
	 * Get car model
	 * @return model
	 */
	public String getModel(){
		return model;
	}

}
