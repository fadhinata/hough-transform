package circledetection.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.media.jai.Histogram;
import javax.media.jai.RenderedOp;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.media.jai.*;
import com.sun.media.jai.widget.*;
@SuppressWarnings("serial")
public class EditPanel extends JPanel {
	private final ApplicationFrame appFrame;
	private JButton contrast;
	private JButton histogram;
	
	public EditPanel(final ApplicationFrame appFrame)
	{
		   this.appFrame = appFrame;
		   setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
		   setBorder(new TitledBorder("Edit "));
		   setBackground(Color.lightGray);
		   setLayout(new GridLayout(10,1));
		   contrast = new JButton("Adjust Contrast");
		   contrast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				appFrame.getWorkImage().adjustContrast();
			}
	
		   });
		   this.add(contrast);
		   
		   histogram = new JButton("Histogram");
		   histogram.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				appFrame.getHistogramFrame().setVisible(true);

				PlanarImage hist = HistogramPanel.getHistogram(appFrame.getWorkImage().getSource());
				DisplayJAI display = new DisplayJAI();
				display.set(hist);
//				
				appFrame.getHistogramFrame().add(display);
//				appFrame.getHistogramFrame().setSize(display.getSize());
				appFrame.getHistogramFrame().setLocation(0, 0);
				appFrame.getHistogramFrame().revalidate();
				appFrame.getHistogramFrame().repaint();
				appFrame.getHistogramFrame().moveToFront();
				try {
					appFrame.getHistogramFrame().setSelected(true);
					
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		   
		   this.add(histogram);
		
	}
	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		int height = getParent().getHeight();
		int parentWidth = getParent().getWidth();
		int width =(int) (parentWidth*0.25);
		Dimension preferredSize = new Dimension(width,height);
//		setPreferredSize(preferredSize);
//		setSize(preferredSize);
		setBounds(parentWidth-width, 0, width, height);
		setMinimumSize(preferredSize);
		//add(new JButton("button"));
		
	}

}
