package circledetection.gui.edit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import circledetection.PIOperations.GaussianFilter;
import circledetection.PIOperations.MedianFilter;


public class FiltersPanel extends EditPanelAtom{
	private JRadioButton xMask,squareMask,plusMask,squareSeparableMask;
	private ButtonGroup group;
	private JButton applyMedian;

	
	public FiltersPanel()
	{
	
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		initComponents();
	
	}

	private void initComponents() {
		JPanel medianFilterPanel = new JPanel();
		medianFilterPanel.setLayout(new GridLayout(5,1));
		JPanel gaussianFilterPanel = new JPanel();
		medianFilterPanel.setBorder(BorderFactory.createTitledBorder("Median Filter"));
		gaussianFilterPanel.setBorder(BorderFactory.createTitledBorder("Gaussian Filter"));
		xMask = new JRadioButton("X Mask");
		plusMask = new JRadioButton("Plus Mask");
		squareMask = new JRadioButton("Square Mask");
		squareSeparableMask = new JRadioButton("Square Separable Mask");
		
		group = new ButtonGroup();
		group.add(xMask);
		group.add(plusMask);
		group.add(squareMask);
		group.add(squareSeparableMask);
		group.setSelected(xMask.getModel(), true);

		applyMedian = new JButton("Apply ");
		applyMedian.addActionListener(new ActionListener() {
			
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
				op = new MedianFilter(mask);
				op.processCommand();
				
			}
		});
		
		medianFilterPanel.add(xMask);
		medianFilterPanel.add(plusMask);
		medianFilterPanel.add(squareMask);
		medianFilterPanel.add(squareSeparableMask);
		medianFilterPanel.add(applyMedian);
		
		
		JButton applyGaussian = new JButton("Apply");
		applyGaussian.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				op = new GaussianFilter();
				op.processCommand();
			}
		});
		
		gaussianFilterPanel.add(applyGaussian);
		
		this.add(medianFilterPanel);
		this.add(gaussianFilterPanel);
	}
	

}
