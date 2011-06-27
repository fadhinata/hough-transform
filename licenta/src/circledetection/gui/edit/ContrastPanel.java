package circledetection.gui.edit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import circledetection.PIOperations.AdjustContrast;


public class ContrastPanel extends EditPanelAtom {
	private JSpinner min;
	private JSpinner max;
	private JButton adjustContrast;

	public ContrastPanel() {

		GridLayout layout = new GridLayout(5, 1);
		layout.setVgap(2);
		this.setLayout(layout);
		initComponents();

	}

	private void initComponents() {
		SpinnerModel minModel = new SpinnerNumberModel(10, 0.0, 255.0, 1);
		min = new JSpinner(minModel);

		min.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				double maxValue = (Double) max.getValue();
				double minValue = (Double) min.getValue();
				if (minValue > maxValue)
					max.setValue(minValue);

			}
		});

		this.add(new JLabel("Min value:"));
		this.add(min);

		SpinnerModel maxModel = new SpinnerNumberModel(200.0, 0.0, 255.0, 1);
		max = new JSpinner(maxModel);
		max.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				double maxValue = (Double) max.getValue();
				double minValue = (Double) min.getValue();
				if (minValue > maxValue)
					min.setValue(maxValue);

			}
		});
		this.add(new JLabel("Max value:"));
		this.add(max);

		adjustContrast = new JButton("Adjust contrast");
		adjustContrast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double maxValue = (Double) max.getValue();
				double minValue = (Double) min.getValue();

				op = new AdjustContrast(minValue, maxValue);
				op.processCommand();

			}
		});

		this.add(adjustContrast);
	}

}
