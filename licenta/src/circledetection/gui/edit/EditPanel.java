package circledetection.gui.edit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import circledetection.gui.frame.ApplicationFrame;

@SuppressWarnings("serial")
public class EditPanel extends JPanel {
	private final ApplicationFrame appFrame;
	private JButton contrast;
	private JButton histogram;
	private ExtandableAtom contrastAtom;

	public EditPanel() {
		this.appFrame = ApplicationFrame.getInstance();
		setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		setBorder(new TitledBorder("Edit "));
		setBackground(Color.lightGray);
		setLayout(new GridBagLayout());
		
//		contrast = new JButton("Adjust Contrast");
//		contrast.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				ImagePanel workImage = appFrame.getImageFrame().getWorkImage();
//				workImage.display(Operators.adjustContrast(workImage
//						.getSource()));
//			}
//
//		});
//		this.add(contrast);
		 JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(1, 3, 0, 3);
	        gbc.weightx = 1.0;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        gbc.gridwidth = GridBagConstraints.REMAINDER;
	        ContrastPanel contrastPanel = new ContrastPanel();
	        contrastAtom = new ExtandableAtom("Contrast", contrastPanel);
	       
	        
	        this.add(contrastAtom, gbc);
	        this.add(contrastPanel, gbc);
	        contrastPanel.setVisible(false);
	        	        JLabel padding = new JLabel();
	        gbc.weighty = 1.0;
	        panel.add(padding, gbc);

//		histogram = new JButton("Histogram");
//		histogram.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				appFrame.getHistogramFrame().setVisible(true);
//
//				PlanarImage img = appFrame.getImageFrame().getWorkImage()
//						.getSource();
//				appFrame.getHistogramFrame().getContentPane().removeAll();
//				HistogramPanel histPanel = new HistogramPanel(img);
//				histPanel.histogramChart();
//				appFrame.getHistogramFrame().add(histPanel);
//				appFrame.getHistogramFrame().setLocation(0, 0);
//				appFrame.getHistogramFrame().pack();
//				appFrame.getHistogramFrame().revalidate();
//				appFrame.getHistogramFrame().repaint();
//				appFrame.getHistogramFrame().moveToFront();
//				try {
//					appFrame.getHistogramFrame().setSelected(true);
//
//				} catch (PropertyVetoException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//			}
//		});
//
//		histogram = new JButton("Threshold");
//		histogram.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				ImagePanel workImage = appFrame.getImageFrame().getWorkImage();
//				workImage.display(Operators.threshold(workImage.getSource()));
//			}
//		});
//
//		this.add(histogram);
//
//		JButton histogram2 = new JButton("Histogram equalization");
//		histogram2.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				appFrame.getHistogramFrame().setVisible(true);
//
//				PlanarImage img = appFrame.getImageFrame().getWorkImage()
//						.getSource();
//				appFrame.getHistogramFrame().getContentPane().removeAll();
//				HistogramPanel histPanel = new HistogramPanel(img);
//				histPanel.histogramEqChart();
//				appFrame.getHistogramFrame().add(histPanel);
//
//				appFrame.getHistogramFrame().setLocation(0, 0);
//				appFrame.getHistogramFrame().pack();
//				appFrame.getHistogramFrame().revalidate();
//				appFrame.getHistogramFrame().repaint();
//				appFrame.getHistogramFrame().moveToFront();
//				appFrame.getImageFrame().getWorkImage()
//						.display(histPanel.getEq());
//				try {
//					appFrame.getHistogramFrame().setSelected(true);
//
//				} catch (PropertyVetoException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//			}
//		});
//
//		this.add(histogram2);
//		JButton histogram3 = new JButton("Histogram normalization");
//		histogram3.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				appFrame.getHistogramFrame().setVisible(true);
//
//				PlanarImage img = appFrame.getImageFrame().getWorkImage()
//						.getSource();
//				appFrame.getHistogramFrame().getContentPane().removeAll();
//				HistogramPanel histPanel = new HistogramPanel(img);
//				histPanel.histogramNmChart();
//				appFrame.getHistogramFrame().add(histPanel);
//
//				appFrame.getHistogramFrame().setLocation(0, 0);
//				appFrame.getHistogramFrame().pack();
//				appFrame.getHistogramFrame().revalidate();
//				appFrame.getHistogramFrame().repaint();
//				appFrame.getHistogramFrame().moveToFront();
//				appFrame.getImageFrame().getWorkImage()
//						.display(histPanel.getNm());
//				try {
//					appFrame.getHistogramFrame().setSelected(true);
//
//				} catch (PropertyVetoException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//			}
//		});
//
//		this.add(histogram3);

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
