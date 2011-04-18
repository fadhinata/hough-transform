package circledetection.gui.edit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import circledetection.gui.ImagePanel;
import circledetection.gui.frame.ApplicationFrame;
import circledetection.util.Operators;

public class ThresholdPanel extends JPanel implements ActionListener {
	private JButton threshold;
	protected ApplicationFrame appFrame;

	public ThresholdPanel(){
		appFrame = ApplicationFrame.getInstance();
		this.setLayout(new GridLayout(3,1));
		initComponents();
	}
	private void initComponents()
	{
		threshold = new JButton("Threshold");
		threshold.addActionListener(this);
		this.add(threshold);


	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ImagePanel workImage = appFrame.getImageFrame().getWorkImage();
		workImage.display(Operators.threshold(workImage.getSource()));

	}
}
