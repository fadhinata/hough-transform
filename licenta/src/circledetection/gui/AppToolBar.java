package circledetection.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import circledetection.gui.frame.ApplicationFrame;

public class AppToolBar extends JToolBar {
//	private ApplicationFrame mainFrame = ApplicationFrame.getInstance();
	private final String IMG_FOLDER = "img/" ;
	private final String OPEN = "open1.png", SAVE = "save1.png", EDIT = "edit1.png",CLOSE = "close.png", UNDO = "undo1.png",REDO = "redo1.png", 
					ZOOM_IN = "zoomin1.png",ZOOM_OUT= "zoomout1.png", DOUBLE_VIEW = "doubleView.jpg", SINGLE_VIEW = "singleView.gif";
	
	private static AppToolBar INSTANCE = null;
	
	private JButton open;
	private JButton save;
	private JButton close;
	private JButton edit;
	private JButton undo;
	private JButton redo;
	private JButton zoomIn;
	private JButton zoomOut;
	private JButton singleView;
	private JButton dualView;
	private AppToolBar() {
		setFloatable(false);
		setVisible(true);
		setBorder(BorderFactory.createCompoundBorder());
		setBackground(Color.LIGHT_GRAY);
		setAutoscrolls(true);
		buttons();
		disableButtons();
	}

	public static AppToolBar getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new AppToolBar();
		return INSTANCE;
	}
	private void buttons() {
		
		open = new JButton(new ImageIcon(IMG_FOLDER+ OPEN));
		open.setText("Open");
        open.setToolTipText("Open Image");
        open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppActions.openAction();
				
			}
		});
        this.add(open);
        
        
        save = new JButton(new ImageIcon(IMG_FOLDER + SAVE));
        save.setText("Save");
        save.setToolTipText("Save image");
        save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppActions.saveAction();
			}
		});
        
        this.add(save);
        
        close = new JButton(new ImageIcon(IMG_FOLDER + CLOSE));
        close.setText("Close");
        close.setToolTipText("Close image");
        close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppActions.closeAction();
			}
		});
        this.add(close);

        this.addSeparator();
        
        edit = new JButton(new ImageIcon(IMG_FOLDER + EDIT));
        edit.setText("Edit");
        edit.setToolTipText("Open edit frame");
        edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppActions.showEditPanelAction(!ApplicationFrame.getInstance().isShowedEditPanel());
			}
		});
        this.add(edit);
        
        this.addSeparator();
        
        undo = new JButton(new ImageIcon(IMG_FOLDER + UNDO));
        undo.setToolTipText("Undo");
        
        this.add(undo);
       
        redo = new JButton(new ImageIcon(IMG_FOLDER + REDO)); 
        redo.setToolTipText("Redo");
        
        this.addSeparator();
        
        zoomIn = new JButton(new ImageIcon(IMG_FOLDER + ZOOM_IN));
        zoomIn.setToolTipText("Zoom in");
        
        this.add(zoomIn);
        
        zoomOut = new JButton(new ImageIcon(IMG_FOLDER + ZOOM_OUT));
        zoomOut.setToolTipText("Zoom out");
        this.add(zoomOut);
        
        this.addSeparator();
        
        singleView = new JButton(new ImageIcon(IMG_FOLDER + SINGLE_VIEW));
        singleView.setToolTipText("Single View");
        singleView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppActions.singleViewAction();
				
			}
		});
        this.add(singleView);
        
        dualView = new JButton(new ImageIcon(IMG_FOLDER + DOUBLE_VIEW));
        dualView.setToolTipText("Dual View");
        dualView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AppActions.dualViewAction();
			}
		});
        
        this.add(dualView);
        
      
      
	}
	
	public void disableButtons()
	{
		setEnableButtons(false);
		
	}
	public void enableButtons()
	{
		setEnableButtons(true);
	}
	
	private void setEnableButtons(boolean flag){
		save.setEnabled(flag);
		close.setEnabled(flag);
		zoomIn.setEnabled(flag);
		zoomOut.setEnabled(flag);
		undo.setEnabled(flag);
		redo.setEnabled(flag);
	}

}
