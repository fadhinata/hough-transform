/*
 * Copyright 2005, 2009 Cosmin Basca.
 * e-mail: cosmin.basca@gmail.com
 * 
 * This is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * Please see COPYING for the complete licence.
 */
package circledetection.gui.edit;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import circledetection.PIOperations.Hough;


public class HoughEllipseConfigurationPanel extends EditPanelAtom implements ChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7471348945175906049L;
	private JSlider		maxMajor;
	private JSlider		minMajor;
	private JSlider		maxMinor;
	private JSlider		minMinor;
	private JTextField	stopCnt;
	private JTextField	quality;
	private JLabel		canvas;
	
	private JLabel		maxA;
	private JLabel		minA;
	private JLabel		maxB;
	private JLabel		minB;	
	
	private Dimension	canvasDim;
	
	private Color 		foreGround		=	 new Color(157,172,189,200);
	private Color 		backGround		=	 new Color(198,210,162,255);
	private JButton detectCircles;
	private JProgressBar progressBar;
	
	public HoughEllipseConfigurationPanel()
	{		
		canvasDim	=	new Dimension(180,170);
		buildGui();
		repaintCanvas();
	}
	
	public void buildGui()
	{
		this.setLayout(new BorderLayout() );			
		this.setBackground(Color.LIGHT_GRAY);
		
		JPanel	panel	=	new JPanel(new BorderLayout() );
		
		panel.add("North",createCanvas());			// canvas;		
		
		JComponent maj	= createMajorAxisPanel();
		maj.setPreferredSize(new Dimension(this.canvasDim.width,100));
		JPanel pmaj		=	new JPanel(new FlowLayout(FlowLayout.LEFT) );
		pmaj.setBackground(Color.LIGHT_GRAY);
		pmaj.add(maj);
		panel.add("Center",pmaj);	// major axis;		
		
		JComponent min	= createMinorAxisPanel();
		min.setPreferredSize(new Dimension(this.canvasDim.width,100));
		JPanel pmin		=	new JPanel();
		pmin.setBackground(Color.LIGHT_GRAY);
		pmin.setLayout(new BoxLayout(pmin,BoxLayout.Y_AXIS));
		pmin.add(min);
		panel.add("South",pmin);				
		
		add("North",panel);							// canvas;
		add("South",createStatusPanel());			// minor axis;
		
		this.setBorder(BorderFactory.createTitledBorder("Ellipse Detector Settings"));
	}
	
	public JComponent createCanvas()
	{
		canvas = new JLabel();
		canvas.setPreferredSize(this.canvasDim);
		canvas.setDoubleBuffered(true);
		canvas.setBorder(BorderFactory.createLineBorder(Color.darkGray,2));
		
		return canvas;
	}
	
	public JComponent createMajorAxisPanel()
	{		
		GridBagLayout gridLayout = new GridBagLayout();
	
		JPanel 	panel	=	new JPanel(gridLayout);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createTitledBorder("Large Ellipse"));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 90;      
//		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;

		maxMajor	=	createSlider(JSlider.HORIZONTAL,"W",this.canvasDim.width / 2);	
		panel.add(maxMajor,c);

		c.ipadx = 0; 
		c.gridx = 1;
		c.gridy = 0;
		maxA = new JLabel();
		maxA.setBorder(BorderFactory.createTitledBorder(""));
		panel.add(maxA,c);	
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 80;           
//		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		
		maxMinor	=	createSlider(JSlider.HORIZONTAL,"H",this.canvasDim.width / 2);	
		panel.add(maxMinor,c);
		
		c.ipadx = 0;      
		c.gridx = 1;
		c.gridy = 1;
	
		maxB = new JLabel();
		maxB.setBorder(BorderFactory.createTitledBorder(""));
		panel.add(maxB,c);	
		
		maxA.setText(String.valueOf(this.maxMajor.getValue()));
		maxB.setText(String.valueOf(this.maxMajor.getValue()));
	
		
		
		return panel;
	}
	
	public JComponent createMinorAxisPanel()
	{
		GridBagLayout gridLayout = new GridBagLayout();
		
		JPanel 	panel	=	new JPanel(gridLayout);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createTitledBorder("Small Ellipse"));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 90;      
//		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		minMajor	=	createSlider(JSlider.HORIZONTAL,"W",this.canvasDim.height / 2);	
		panel.add(minMajor,c);

	

		c.ipadx = 0; 
		c.gridx = 1;
		c.gridy = 0;
		minA = new JLabel();
		minA.setBorder(BorderFactory.createTitledBorder(""));
		panel.add(minA,c);
		
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 80;           
//		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		
		minMinor	=	createSlider(JSlider.HORIZONTAL,"H",this.canvasDim.height / 2);	
		panel.add(minMinor,c);

		c.ipadx = 0;      
		c.gridx = 1;
		c.gridy = 1;
		minB = new JLabel();
		minB.setBorder(BorderFactory.createTitledBorder(""));
		panel.add(minB,c);
		
		
		minA.setText(String.valueOf(this.minMajor.getValue()));
		minB.setText(String.valueOf(this.minMajor.getValue()));
	
		
		
		return panel;
		
	}
	
	private JSlider createSlider(int orient,String title,int max)
	{
		JSlider slider = new JSlider(orient,0,max,max/2);
		
		slider.addChangeListener(this);
		slider.setSnapToTicks(false);
		//slider.setPaintLabels(true);
		//slider.setPaintTicks(true);
		slider.setPaintTrack(true);
		slider.setBackground(Color.LIGHT_GRAY);
		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		// key , value
		for(int i=0;i<=max;i+=10)
			labels.put(new Integer(i),new JLabel(String.valueOf(i)));
		
		slider.setLabelTable(labels);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(10);
		
		slider.setBorder(BorderFactory.createTitledBorder(title));
		
		return slider;
	}
	
	public JComponent createSettingsPanel()
	{
		JPanel 	panel	=	new JPanel();
		return panel;
	}
	
	public JComponent createStatusPanel()
	{
		JPanel 	panel	=	new JPanel(new GridLayout(3,2));
		panel.setBackground(Color.LIGHT_GRAY);		
//		maxA.setPreferredSize(new Dimension(100,40));
	
		
		
		
//		stopCnt	=	new JTextField("5000");
////		stopCnt.setBackground(Color.white);
//		stopCnt.setBorder(BorderFactory.createTitledBorder("Detection threshold"));		
		quality	=	new JTextField("200");
		quality.setBorder(BorderFactory.createTitledBorder("Detection quality"));		
//		quality.setForeground(Color.LIGHT_GRAY);
		progressBar = new JProgressBar();
		detectCircles = new JButton("Detect Ellipses");
		detectCircles.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("action performed");
//				detectCircles.setEnabled(false);
				progressBar.setIndeterminate(true);
				op = new Hough(getParameters(),progressBar);
				op.processCommand();
			}
		});
		
		panel.add(quality);
		panel.add(progressBar);
		panel.add(detectCircles);
		return panel;
	}
	private void drawGrid(int size,Graphics2D graphics,Dimension dim)
	{
		graphics.setColor(this.backGround.darker());
		for(int x = 0; x<dim.width; x+=size)
			graphics.drawLine(x,0,x,dim.height);			
		for(int y = 0; y<dim.height; y+=size)
			graphics.drawLine(0,y,dim.width,y);
	}
	
	private void repaintCanvas()
	{
		BufferedImage	buffer	=	new BufferedImage(this.canvasDim.width,this.canvasDim.height,BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D)buffer.getGraphics();
		
		graphics.setColor(backGround);
		graphics.fill(new Rectangle2D.Double(0,0,this.canvasDim.width,this.canvasDim.height) );
		drawGrid(10,graphics,this.canvasDim);
		graphics.setColor(foreGround);
		
		double x	=	(this.canvasDim.width - this.maxMajor.getValue() * 2) / 2;
		double y	=	(this.canvasDim.height - this.maxMinor.getValue() * 2) / 2;
		double w	=	this.maxMajor.getValue() * 2;
		double h	=	this.maxMinor.getValue() * 2;
		
		Ellipse2D	maxEllipse 	= new Ellipse2D.Double(x,y,w,h);		
		
		x	=	(this.canvasDim.width - this.minMajor.getValue() * 2) / 2;
		y	=	(this.canvasDim.height - this.minMinor.getValue() * 2) / 2;
		w	=	this.minMajor.getValue() * 2;
		h	=	this.minMinor.getValue() * 2;
		
		Ellipse2D 	minEllipse 	= new Ellipse2D.Double(x,y,w,h);
		
		Area		domain	 	= new Area(maxEllipse);
		domain.subtract(new Area(minEllipse));
		
		graphics.fill(domain);
		BasicStroke	domainContour	=	new BasicStroke(3.0F);
		graphics.setColor(foreGround.darker().darker());
		graphics.fill(domainContour.createStrokedShape(domain));
		
		this.canvas.setIcon(new ImageIcon(buffer));
		this.canvas.repaint();
	}
	
	private void updateStatus()
	{
		maxA.setText(String.valueOf(this.maxMajor.getValue()));
		minA.setText(String.valueOf(this.minMajor.getValue()));
		maxB.setText(String.valueOf(this.maxMinor.getValue()));
		minB.setText(String.valueOf(this.minMinor.getValue()));
	}
	
	public void stateChanged(ChangeEvent e) 
	{
    	//if (!source.getValueIsAdjusting()) 
    	//{
    		repaintCanvas();
    		updateStatus();
    	//}
    }
    
    public void setParameters(ParameterBlock pb)
    {
    	Integer mnA = (Integer) pb.getObjectParameter(0); // minA
		Integer mxA = (Integer) pb.getObjectParameter(1); // maxA
		Integer mnB = (Integer) pb.getObjectParameter(2); // minB
		Integer mxB = (Integer) pb.getObjectParameter(3); // maxB
		Integer qEl = (Integer) pb.getObjectParameter(4); // qual
//		Integer sCt = (Integer) pb.getObjectParameter(5); // stop
		
		maxA.setText(mxA.toString());
		minA.setText(mnA.toString());
		maxB.setText(mxB.toString());
		minB.setText(mnB.toString());		
		quality.setText(qEl.toString());		
//		stopCnt.setText(sCt.toString());		
		
		this.maxMajor.setValue(mxA.intValue());
		this.minMajor.setValue(mnA.intValue());
		this.maxMinor.setValue(mxB.intValue());
		this.minMinor.setValue(mnB.intValue());
		
		repaintCanvas();
    	updateStatus();
    }
    
    public ParameterBlock getParameters()
    {
    	ParameterBlock pb = new ParameterBlock();
    	
    	pb.add(Integer.valueOf(minA.getText()));
    	pb.add(Integer.valueOf(maxA.getText()));
    	pb.add(Integer.valueOf(minB.getText()));
    	pb.add(Integer.valueOf(maxB.getText()));
    	pb.add(Integer.valueOf(quality.getText()));
//    	pb.add(Integer.valueOf(stopCnt.getText()));
    	    	
    	return pb;
    }
    
   
}

