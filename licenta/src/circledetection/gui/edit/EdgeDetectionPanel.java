package circledetection.gui.edit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.media.jai.PlanarImage;
import javax.swing.JButton;
import javax.swing.JPanel;

import circledetection.gui.HistogramChartPanel;
import circledetection.gui.ImagePanel;
import circledetection.gui.frame.ApplicationFrame;
import circledetection.util.Operators;

public class EdgeDetectionPanel extends JPanel implements ActionListener{

	private JButton prewitt;
	protected ApplicationFrame appFrame;
	private JButton sobel;
	private JButton canny;
	public EdgeDetectionPanel(){
		appFrame = ApplicationFrame.getInstance();
		this.setLayout(new GridLayout(3,1));
		initComponents();
	}
	private void initComponents()
	{

//		canny = new JButton("Histogram equalization");
//		canny.addActionListener(this);
//
//		this.add(canny);
		 sobel = new JButton("Sobel");
		sobel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ImagePanel workImage = appFrame.getImageFrame().getWorkImage();
				workImage.display(Operators.sobel(workImage.getSource()));
				
			}
		});

		this.add(sobel);

		prewitt = new JButton("Prewitt");
		prewitt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ImagePanel workImage = appFrame.getImageFrame().getWorkImage();
				workImage.display(Operators.prewitt(workImage.getSource()));
				
			}
		});
		this.add(prewitt);
	}
	public void actionPerformed(ActionEvent e) {
		appFrame.getHistogramFrame().setVisible(true);

		PlanarImage img = appFrame.getImageFrame().getWorkImage()
				.getSource();
		appFrame.getHistogramFrame().getContentPane().removeAll();
		HistogramChartPanel histPanel = new HistogramChartPanel(img);
		
		
		PlanarImage dest = null;
		if(e.getSource() == canny){
			histPanel.histogramEqChart();
			dest = histPanel.getEq();
		}
		if(e.getSource() == sobel){
			histPanel.histogramNmChart();
			dest = histPanel.getNm();
		}
		if(e.getSource() == prewitt){
			histPanel.histogramChart();
			dest = img;
		}
		
		
		appFrame.getHistogramFrame().add(histPanel);
		appFrame.getHistogramFrame().setLocation(0, 0);
		appFrame.getHistogramFrame().pack();
		appFrame.getHistogramFrame().revalidate();
		appFrame.getHistogramFrame().repaint();
		appFrame.getHistogramFrame().moveToFront();
		appFrame.getImageFrame().getWorkImage()
				.display(dest);
		try {
			appFrame.getHistogramFrame().setSelected(true);

		} catch (PropertyVetoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
