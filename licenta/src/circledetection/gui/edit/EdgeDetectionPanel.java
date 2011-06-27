package circledetection.gui.edit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import circledetection.PIOperations.Canny;
import circledetection.PIOperations.Prewitt;
import circledetection.PIOperations.Sobel;


public class EdgeDetectionPanel extends EditPanelAtom {

	private JButton prewitt;
	private JButton sobel;
	private JButton canny;

	public EdgeDetectionPanel() {
		this.setLayout(new GridLayout(3, 1));
		initComponents();
	}

	private void initComponents() {

		canny = new JButton("Canny Detector");
		canny.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				op = new Canny();
				op.processCommand();
			}
		});

		this.add(canny);
		sobel = new JButton("Sobel Detector");
		sobel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				op = new Sobel();
				op.processCommand();

			}
		});

		this.add(sobel);

		prewitt = new JButton("Prewitt Detector");
		prewitt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				op = new Prewitt();
				op.processCommand();

			}
		});
		this.add(prewitt);
	}

}
