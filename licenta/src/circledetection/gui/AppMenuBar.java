package circledetection.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileFilter;

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
					mainFrame.setViewMode(ApplicationFrame.SINGLE_IMAGE_VIEW);
					mainFrame.showImage();
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
					mainFrame.setViewMode(ApplicationFrame.DUAL_VIEW);
					mainFrame.showImage();
					
			}
		});
		
		viewMenu.addSeparator();
		
		JCheckBoxMenuItem editPanel = new JCheckBoxMenuItem();
		viewMenu.add(editPanel);
		editPanel.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
					mainFrame.setShowEditPanel(viewMenu.isSelected());
				
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
				JFileChooser imageChooser = new JFileChooser();
				imageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				imageChooser.setMultiSelectionEnabled(false);
				FileFilter imageFilter = new FileFilter() {

					@Override
					public String getDescription() {
						return "Images (*.jpg, *.jpeg, *.bmp)";
					}

					@Override
					public boolean accept(File pathname) {
						if (pathname == null)
							return false;
						if (pathname.isDirectory())
							return true;
						return pathname.getName().toLowerCase().endsWith("jpg")
								|| pathname.getName().toLowerCase()
										.endsWith("jpeg")
								|| pathname.getName().toLowerCase()
										.endsWith("bmp");
					}
				};
				imageChooser.setFileFilter(imageFilter);
				imageChooser.setAcceptAllFileFilterUsed(false);
				if (imageChooser.showOpenDialog((Component) mainFrame) == JFileChooser.APPROVE_OPTION) {
					File path = imageChooser.getSelectedFile();
					mainFrame.setFilePath(path.getPath());
					mainFrame.showImage();
				}

			}

		});
		JMenuItem save = new JMenuItem("Save");
		JMenuItem close = new JMenuItem("Close");
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.close();
				
			}
		});
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(close);
		add(fileMenu);

		
	}
	
	
}
