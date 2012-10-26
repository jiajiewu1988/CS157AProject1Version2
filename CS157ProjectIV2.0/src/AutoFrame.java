import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This class includes Graphic Design for first selection
 * (Choose from Auto Maker) of initial screen. The auto
 * selection window include three Jlist and 1 JTextArea 
 * to show all available auto maker, model, year and 
 * description.
 * @author Yongxiang
 *
 */
public class AutoFrame extends JFrame {
	
	private AutoFrame autoFrame = this;
	private String maker;
	private String model;
	private String year;
	private JList<String> makerList;
	private JList<String> modelList;
	private JList<String> yearList;
	private DefaultListModel<String> makerListModel;
	private DefaultListModel<String> modelListModel;
	private DefaultListModel<String> yearListModel;
	private JTextArea description;
	private String allDes = ""; 
	private int desLength;
	
	public AutoFrame(){
		
		/*set Title and Size*/
		this.setTitle("Auto Selection");
		this.setSize(800,600);
	
		JPanel autoSelection = new JPanel();
		
		/*Create maker list and Set up maker list's property*/
		String[] makers = Result.getAutoMaker();
		makerListModel = new DefaultListModel<String>();
		for(String maker : makers)
			makerListModel.addElement(maker);
			
		makerList = new JList<String>(makerListModel);
		makerList.setVisibleRowCount(5);
		makerList.setAutoscrolls(true); 
		makerList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
					
				//getValueIsAdjusting becomes false
				if(!e.getValueIsAdjusting()){
						
					JList<String> list = (JList<String>)e.getSource();
					maker = (String) list.getSelectedValue();
					modelListModel.clear();
					String[] models = Result.getModel(maker);
					for( String model : models)
							modelListModel.addElement(model);
					}
				}
			});
			makerList.setBorder(BorderFactory.createTitledBorder("Maker:"));
			makerList.setSize(200, 300);
			JScrollPane makerScroll = new JScrollPane(makerList);
			makerScroll.setPreferredSize(new Dimension(200,300));

		
		/*Create model list and Set up model list's property*/
		modelListModel = new DefaultListModel<String>();
		modelListModel.addElement("");
		modelList = new JList<String>(modelListModel);
		modelList.setVisibleRowCount(5);
		modelList.addListSelectionListener(new ListSelectionListener(){

				@Override
				public void valueChanged(ListSelectionEvent e) {
					
					//getValueIsAdjusting becomes false
					if(!e.getValueIsAdjusting()){
						
						JList<String> list = (JList<String>)e.getSource();
						model = (String) list.getSelectedValue();
						yearListModel.clear();
						String[] years = Result.getYear(maker, model);
						for( String year : years)
							yearListModel.addElement(year);
					}
				}
			});
			modelList.setBorder(BorderFactory.createTitledBorder("Model:"));
			JScrollPane modelScroll = new JScrollPane(modelList);
			modelScroll.setPreferredSize(new Dimension(200,300));
		
		/*Create year list and Set up year list's property*/
		yearListModel = new DefaultListModel<String>();
		yearListModel.addElement("");
		yearList = new JList<String>(yearListModel);
		yearList.setVisibleRowCount(5);
		yearList.addListSelectionListener(new ListSelectionListener(){

				@Override
				public void valueChanged(ListSelectionEvent e) {
					
					//getValueIsAdjusting becomes false
					if(!e.getValueIsAdjusting()){
						
						/*remove previous text in TextArea*/
						desLength = allDes.length();
						description.replaceRange("", 0, desLength);
						
						/*Append text to textArea */
						JList<String> list = (JList<String>)e.getSource();
						year = (String) list.getSelectedValue();
						try {
							allDes = Result.getAllDesc(maker, model, year);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						description.append(allDes);
					}
				}
			});
		yearList.setBorder(BorderFactory.createTitledBorder("Year:"));
		JScrollPane yearScroll = new JScrollPane(yearList);
		yearScroll.setPreferredSize(new Dimension(200,300));
		
		/* Create TextArea for description */
		JPanel autoDes = new JPanel();
		JButton backButton = new JButton(" Back ");
		
		description = new JTextArea();
		description.setBorder(BorderFactory.createTitledBorder("Auto Description: "));
		description.setEditable(false);
		description.setPreferredSize(new Dimension(600,250));
		
		/* back button function */
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				InitialFrame iniFrame = new InitialFrame();
				iniFrame.setVisible(true);
				autoFrame.setVisible(false);
			}
			
		});

		autoDes.add(description);	
		autoDes.add(backButton);
		
		
		autoSelection.add(makerScroll, BorderLayout.WEST);
		autoSelection.add(modelScroll, BorderLayout.CENTER);
		autoSelection.add(yearScroll, BorderLayout.EAST);
		autoSelection.setBorder(BorderFactory.createEmptyBorder(0, 50, 300, 50));
		autoDes.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
		this.add(autoSelection, BorderLayout.CENTER);
		this.add(autoDes, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		

	}
	
}
