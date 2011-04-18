package circledetection.gui.edit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.jai.Histogram;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import circledetection.gui.ImagePanel;
import circledetection.gui.frame.ApplicationFrame;
import circledetection.util.Operators;

public class ThresholdPanel extends JPanel {
	private JButton threshold;
	private JRadioButton maxEntropy,maxVariance,minError,minFuzziness,mode,pTile,iterative;
	private JTextField value;
	private JTextField parameter;
	private ButtonGroup group;
	protected ApplicationFrame appFrame;
	private ImagePanel workImage;
	private Histogram hist;
	private JLabel parameterLabel;
	private JButton computeThreshold;
	protected double[] thresholdValue;

	public ThresholdPanel(){
		appFrame = ApplicationFrame.getInstance();
		this.setLayout(new GridLayout(13,1));
		initComponents();
	}
	private void initComponents()
	{
		
		initRadioButtons();
		
		parameterLabel = new JLabel();
		parameterLabel.setVisible(false);
		this.add(parameterLabel);
		parameter = new JTextField();
		parameter.setVisible(false);
		this.add(parameter);
		
		
		computeThreshold = new JButton("Compute Threshold");
		this.add(computeThreshold);
		computeThreshold.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				workImage = appFrame.getImageFrame().getWorkImage();
				hist = Operators.createHistogram(workImage.getSource());

				ButtonModel buttonModel = group.getSelection();
				
				if(buttonModel.equals(iterative.getModel()))
				{
					thresholdValue = hist.getIterativeThreshold();
				}
				if(buttonModel.equals(maxVariance.getModel()))
				{
					thresholdValue = hist.getMaxVarianceThreshold();
				}
				if(buttonModel.equals(maxEntropy.getModel()))
				{
					thresholdValue = hist.getMaxEntropyThreshold();
				}
				if(buttonModel.equals(minError.getModel()))
				{
					thresholdValue = hist.getMinErrorThreshold();
				}
				
				if(buttonModel.equals(minFuzziness.getModel()))
				{
					thresholdValue = hist.getMinFuzzinessThreshold();
				}
				
				if(buttonModel.equals(mode.getModel()))
				{
					Double parameterValue = Double.valueOf(parameter.getText());
					thresholdValue = hist.getModeThreshold(parameterValue);
				}
				if(buttonModel.equals(pTile.getModel()))
				{
					Double parameterValue = Double.valueOf(parameter.getText());
					thresholdValue = hist.getPTileThreshold(parameterValue);
				}
				value.setText(String.valueOf(thresholdValue[0]));
				
			}
		});
		JLabel valueLabel = new JLabel("Threshold value: ");
		this.add(valueLabel);
		value = new JTextField();
		value.setEditable(false);
		this.add(value);


		threshold = new JButton("Apply Threshold");
		threshold.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				
				workImage.display(Operators.threshold(workImage.getSource(),thresholdValue));

			}		});
		this.add(threshold);
	}
	private void initRadioButtons()
	{
		group = new ButtonGroup();
		
		iterative= new JRadioButton("Iterative Threshold");
		group.add(iterative);
		this.add(iterative);
		
		maxEntropy = new JRadioButton("Max Entropy Threshold");
		group.add(maxEntropy);
		this.add(maxEntropy);
		
		maxVariance = new JRadioButton("Max Variance Threshold");
		group.add(maxVariance);
		this.add(maxVariance);
		
		minError = new JRadioButton("Min Error Threshold");
		group.add(minError);
		this.add(minError);
		
		minFuzziness = new JRadioButton("Min Fuzziness Threshold");
		group.add(minFuzziness);
		this.add(minFuzziness);
		
		mode = new JRadioButton("Mode threshold");
		mode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mode.isSelected()){
					parameterLabel.setText("Power");
					parameterLabel.setVisible(true);
					parameter.setVisible(true);
				}
				else {
					parameterLabel.setVisible(false);
					parameter.setVisible(false);
				}
				
			}
	
		});
		group.add(mode);
		this.add(mode);
		
		pTile = new JRadioButton("P-Tile Threshold");
		pTile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(pTile.isSelected()){
					parameterLabel.setText("Portion");
					parameterLabel.setVisible(true);
					parameter.setVisible(true);
				}
				else {
					parameterLabel.setVisible(false);
					parameter.setVisible(false);
				}
				
			}
		
		});
		group.add(pTile);
		this.add(pTile);
	}

	
}
