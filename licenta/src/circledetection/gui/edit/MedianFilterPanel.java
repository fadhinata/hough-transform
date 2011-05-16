package circledetection.gui.edit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import circledetection.gui.ImagePanel;
import circledetection.gui.frame.ApplicationFrame;
import circledetection.util.Operators;

public class MedianFilterPanel extends JPanel{
	private JRadioButton xMask,squareMask,plusMask,squareSeparableMask;
	private ButtonGroup group;
	private JButton apply;
	private ApplicationFrame appFrame;
	
	public MedianFilterPanel()
	{
		appFrame = ApplicationFrame.getInstance();
		this.setLayout(new GridLayout(5,1));
		initComponents();
	
	}

	private void initComponents() {
		xMask = new JRadioButton("X Mask");
		plusMask = new JRadioButton("Plus Mask");
		squareMask = new JRadioButton("Square Mask");
		squareSeparableMask = new JRadioButton("Square Separable Mask");
		
		group = new ButtonGroup();
		group.add(xMask);
		group.add(plusMask);
		group.add(squareMask);
		group.add(squareSeparableMask);
		group.setSelected(squareSeparableMask.getModel(), true);

		apply = new JButton("Apply median filter");
		apply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MedianFilterShape mask = MedianFilterDescriptor.MEDIAN_MASK_SQUARE_SEPARABLE;
				if(group.getSelection().equals(xMask.getModel()))
				{
					mask = MedianFilterDescriptor.MEDIAN_MASK_X;
				}
				if(group.getSelection().equals(plusMask.getModel()))
				{
					mask = MedianFilterDescriptor.MEDIAN_MASK_PLUS;
				}
				if(group.getSelection().equals(squareMask.getModel()))
				{
					mask = MedianFilterDescriptor.MEDIAN_MASK_SQUARE_SEPARABLE;
				}
				ImagePanel workImage = appFrame.getImageFrame().getWorkImage();
				workImage.display(Operators.medianFilter(workImage.getSource(),mask));
				
			}
		});
		
		this.add(xMask);
		this.add(plusMask);
		this.add(squareMask);
		this.add(squareSeparableMask);
		this.add(apply);
		
	}
	

}
