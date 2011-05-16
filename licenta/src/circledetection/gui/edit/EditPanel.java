package circledetection.gui.edit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class EditPanel extends JPanel {
//	private final ApplicationFrame appFrame;
	private ExtandableAtom contrastAtom;

	public EditPanel() {
//		this.appFrame = ApplicationFrame.getInstance();
		setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		setBorder(new TitledBorder("Edit "));
		setBackground(Color.lightGray);
		setLayout(new GridBagLayout());
		    

	
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(1, 3, 0, 3);
	        gbc.weightx = 1.0;
	        gbc.gridx = 0;
	        gbc.fill = GridBagConstraints.NONE;
	        gbc.gridwidth = GridBagConstraints.REMAINDER;
	       
	        ContrastPanel contrastPanel = new ContrastPanel();
	        contrastAtom = new ExtandableAtom("Contrast", contrastPanel);
	              
	        contrastPanel.setVisible(true);
	        contrastAtom.toggleSelection();
	        this.add(contrastAtom, gbc);
	        this.add(contrastPanel, gbc);
	        
	        gbc.gridx = GridBagConstraints.RELATIVE;
	        MedianFilterPanel medianFilterPanel = new MedianFilterPanel();
	        ExtandableAtom medianFilterAtom = new ExtandableAtom("Median Filter", medianFilterPanel);
	              
	        this.add(medianFilterAtom, gbc);
	        this.add(medianFilterPanel, gbc);
	        medianFilterPanel.setVisible(false);
	       
	        HistogramPanel histogramPanel = new HistogramPanel();
	        ExtandableAtom histogramAtom = new ExtandableAtom("Histogram", histogramPanel);
	              
	        this.add(histogramAtom, gbc);
	        this.add(histogramPanel, gbc);
	        histogramPanel.setVisible(false);
	        
	        ThresholdPanel thresholdPanel = new ThresholdPanel();
	        ExtandableAtom thresholdAtom = new ExtandableAtom("Threshold", thresholdPanel);
	              
	        this.add(thresholdAtom, gbc);
	        this.add(thresholdPanel, gbc);
	        thresholdPanel.setVisible(false);
	       

	}

	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		int height = getParent().getHeight();
		int parentWidth = getParent().getWidth();
		int width = (int) (parentWidth * 0.25);
		Dimension preferredSize = new Dimension(width, height);
		// setPreferredSize(preferredSize);
		// setSize(preferredSize);
		setBounds(parentWidth - width, 0, width, height);
		setMinimumSize(preferredSize);
		// add(new JButton("button"));

	}

}
