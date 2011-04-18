package circledetection.gui.edit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.media.jai.PlanarImage;
import javax.swing.JButton;
import javax.swing.JPanel;

import circledetection.gui.HistogramChartPanel;
import circledetection.gui.frame.ApplicationFrame;

public class HistogramPanel extends JPanel implements ActionListener{

	private JButton histogram;
	protected ApplicationFrame appFrame;
	private JButton normalization;
	private JButton equalization;
	public HistogramPanel(){
		appFrame = ApplicationFrame.getInstance();
		this.setLayout(new GridLayout(3,1));
		initComponents();
	}
	private void initComponents()
	{
		histogram = new JButton("Histogram");
		histogram.addActionListener(this);
		this.add(histogram);

		equalization = new JButton("Histogram equalization");
		equalization.addActionListener(this);

		this.add(equalization);
		 normalization = new JButton("Histogram normalization");
		normalization.addActionListener(this);

		this.add(normalization);

	}
	public void actionPerformed(ActionEvent e) {
		appFrame.getHistogramFrame().setVisible(true);

		PlanarImage img = appFrame.getImageFrame().getWorkImage()
				.getSource();
		appFrame.getHistogramFrame().getContentPane().removeAll();
		HistogramChartPanel histPanel = new HistogramChartPanel(img);
		
		
		PlanarImage dest = null;
		if(e.getSource() == equalization){
			histPanel.histogramEqChart();
			dest = histPanel.getEq();
		}
		if(e.getSource() == normalization){
			histPanel.histogramNmChart();
			dest = histPanel.getNm();
		}
		if(e.getSource() == histogram){
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
