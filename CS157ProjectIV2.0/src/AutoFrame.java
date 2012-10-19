import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AutoFrame extends JFrame {
	
	private String maker;
	private String model;
	private String year;
	private JList<String> makerList;
	private JList<String> modelList;
	private JList<String> yearList;
	DefaultListModel<String> makerListModel;
	DefaultListModel<String> modelListModel;
	DefaultListModel<String> yearListModel;
	
	private final static int height = 20;
	private final static int width = 200;
	
	public AutoFrame(){
		
		/*set Title and Size*/
		this.setTitle("Auto Selection");
		this.setSize(800,600);
		
		JPanel autoPanel = new JPanel();
		
		/*Create maker list and Set up maker list's property*/
		String[] makers = Result.getAutoMaker();
		makerListModel = new DefaultListModel<String>();
		for(String maker : makers)
			makerListModel.addElement(maker);
			
		makerList = new JList<String>(makerListModel);
		makerList.setVisibleRowCount(5);
		makerList.setAutoscrolls(true); 
		makerList.setFixedCellHeight(height);
		makerList.setFixedCellWidth(width);
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
		
		/*Create model list and Set up model list's property*/
			modelListModel = new DefaultListModel<String>();
			modelListModel.addElement("");
			modelList = new JList<String>(modelListModel);
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
						yearListModel.clear();
						String[] years = Result.getYear(maker, model);
						for( String year : years)
							yearListModel.addElement(year);
					}
				}
			});
			modelList.setBorder(BorderFactory.createTitledBorder("Model:"));
		
		/*Create year list and Set up year list's property*/
			yearListModel = new DefaultListModel<String>();
			yearListModel.addElement("");
			yearList = new JList<String>(yearListModel);
			yearList.setVisibleRowCount(5);
			yearList.setFixedCellHeight(height);
			yearList.setFixedCellWidth(width);
			yearList.addListSelectionListener(new ListSelectionListener(){

				@Override
				public void valueChanged(ListSelectionEvent e) {
					
					//getValueIsAdjusting becomes false
					if(!e.getValueIsAdjusting()){
						
						JList<String> list = (JList<String>)e.getSource();
						year = (String) list.getSelectedValue();
					}
				}
			});
			yearList.setBorder(BorderFactory.createTitledBorder("Year:"));
		
		autoPanel.add(makerList, BorderLayout.WEST);
		autoPanel.add(modelList, BorderLayout.CENTER);
		autoPanel.add(yearList, BorderLayout.EAST);
		this.getContentPane().add(autoPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		

	}
	
}
