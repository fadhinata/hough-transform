package circledetection.gui.edit;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.renderable.ParameterBlock;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JInternalFrame;

@SuppressWarnings("serial")
public class EditPanel extends JInternalFrame implements Observer{
//	private final ApplicationFrame appFrame;
	private ArrayList<ExtandableAtom> list;

	public EditPanel() {
//		this.appFrame = ApplicationFrame.getInstance();
		setTitle("Edit");
		setClosable(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBackground(Color.lightGray);
		setLayout(new GridBagLayout());
		list = new ArrayList<ExtandableAtom>();
	
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(1, 3, 0, 3);
	        gbc.weightx = 1.0;
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.fill = GridBagConstraints.NONE;
	        gbc.gridwidth = GridBagConstraints.REMAINDER;
	       
	        ContrastPanel contrastPanel = new ContrastPanel();
	        ExtandableAtom contrastAtom = new ExtandableAtom("Contrast", contrastPanel);
	        list.add(contrastAtom);
	        contrastAtom.addObserver(this);
	              
	        contrastPanel.setVisible(true);
	        contrastAtom.toggleSelection();
	        this.add(contrastAtom, gbc);
	        gbc.gridx = GridBagConstraints.RELATIVE;
	        gbc.gridy = GridBagConstraints.RELATIVE;

	        this.add(contrastPanel, gbc);
	        
	        FiltersPanel medianFilterPanel = new FiltersPanel();
	        ExtandableAtom medianFilterAtom = new ExtandableAtom("Median&Gaussian Filter", medianFilterPanel);
	        list.add(medianFilterAtom); 
	        medianFilterAtom.addObserver(this);
	        
	        this.add(medianFilterAtom, gbc);
	        this.add(medianFilterPanel, gbc);
	        medianFilterPanel.setVisible(false);
	       
	        HistogramPanel histogramPanel = new HistogramPanel();
	        ExtandableAtom histogramAtom = new ExtandableAtom("Histogram", histogramPanel);
	        list.add(histogramAtom);
	        histogramAtom.addObserver(this);
	        
	        this.add(histogramAtom, gbc);
	        this.add(histogramPanel, gbc);
	        histogramPanel.setVisible(false);

	        EdgeDetectionPanel edgePanel = new EdgeDetectionPanel();
	        ExtandableAtom edgeAtom  = new ExtandableAtom("Edge detection", edgePanel);
	        list.add(edgeAtom);
	        edgeAtom.addObserver(this);
	        
	        this.add(edgeAtom,gbc);
	        this.add(edgePanel,gbc);
	        edgePanel.setVisible(false);
	        
	        ThresholdPanel thresholdPanel = new ThresholdPanel();
	        ExtandableAtom thresholdAtom = new ExtandableAtom("Threshold", thresholdPanel);
	        list.add(thresholdAtom);
	        thresholdAtom.addObserver(this);
	        
	        this.add(thresholdAtom, gbc);
	        this.add(thresholdPanel, gbc);
	        thresholdPanel.setVisible(false);
	        
	        
	        HoughEllipseConfigurationPanel houghPanel = new HoughEllipseConfigurationPanel();
			ParameterBlock pb = new ParameterBlock();
			pb.add(59); // minA - big radius range
			pb.add(71); // maxA
			pb.add(59); // minB - small radius range
			pb.add(71); // maxB
			pb.add(100); // the quality of the detected ellipse
			pb.add(5000); // threshold
		
			houghPanel.setParameters(pb);
	        ExtandableAtom houghAtom = new ExtandableAtom("Hough Transform", houghPanel);
	        list.add(houghAtom);
	        houghAtom.addObserver(this);
	        
	        this.add(houghAtom,gbc);
	        this.add(houghPanel,gbc);
	        houghPanel.setVisible(false);


	}

	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		for(ExtandableAtom e : list)
		{
			if(!((ExtandableAtom)arg1).equals(e) && e.isSelected())
			{
				e.close();
			}
		}
	}

}
