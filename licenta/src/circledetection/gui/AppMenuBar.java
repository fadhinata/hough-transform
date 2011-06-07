package circledetection.gui;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import circledetection.gui.frame.ApplicationFrame;



@SuppressWarnings("serial")
public class AppMenuBar  extends JMenuBar{
	private ApplicationFrame mainFrame ;
	private Insets insets; 
	
	public AppMenuBar(){
	
	this.mainFrame = ApplicationFrame.getInstance();	
	this.setBackground(Color.LIGHT_GRAY);
	insets = new Insets(2, 4, 2, 4);
    initComponents();
	
	}
	public void initComponents()
	{
		fileMenu();
		editMenu();
		viewMenu();
	}
	private void viewMenu() {
	
		//view menu
		final JMenu viewMenu = new JMenu("View");
		viewMenu.setMargin(insets);
		final ButtonGroup group = new ButtonGroup();
		final JRadioButtonMenuItem singleView = new JRadioButtonMenuItem("Single view");
		group.add(singleView);
		singleView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(group.getSelection().equals(singleView.getModel()))
					AppActions.singleViewAction();
			}
		});
		group.setSelected(singleView.getModel(),true);
		viewMenu.add(singleView);
		
		final JRadioButtonMenuItem dualView = new JRadioButtonMenuItem("Dual view");
		group.add(dualView);
		viewMenu.add(dualView);
		dualView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(group.getSelection().equals(dualView.getModel()))
					AppActions.dualViewAction();
					
			}
		});
		
		viewMenu.addSeparator();
		
		JCheckBoxMenuItem editPanel = new JCheckBoxMenuItem();
		editPanel.setText("EditPanel");
		viewMenu.add(editPanel);
		editPanel.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
					AppActions.showEditPanelAction(viewMenu.isSelected());
				
			}
		});
		
		add(viewMenu);
		
	}
	private void editMenu() {
		//editmenu
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMargin(insets);
		JMenu zoom = new JMenu("Zoom");
		JMenuItem zoomIn = new JMenuItem("Zoom in");
		JMenuItem zoomOut = new JMenuItem("Zoom out");
		zoom.add(zoomIn);
		zoom.add(zoomOut);
		editMenu.add(zoom);
		add(editMenu);
		
	}
	public void fileMenu()
	{
		//file menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMargin(insets);
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AppActions.openAction();
			}
		});
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppActions.saveAction();
				
			}
		});
		
		JMenuItem close = new JMenuItem("Close");
		
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppActions.closeAction();
				
			}
		});
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(close);
		fileMenu.add(exit);
		add(fileMenu);

		
	}
	
	
}
