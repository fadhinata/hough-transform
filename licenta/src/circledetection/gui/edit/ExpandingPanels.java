package circledetection.gui.edit;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ExpandingPanels extends MouseAdapter {

    private ExtandableAtom[] aps;
    private JPanel[] panels;

    public ExpandingPanels() {
    	assemblePanels();
        assembleActionPanels();
    }

   

    private void assembleActionPanels() {
        String[] ids = {"level 1", "level 2", "level 3", "level 4"};
        aps = new ExtandableAtom[ids.length];
        for (int j = 0; j < aps.length; j++) {
            aps[j] = new ExtandableAtom(ids[j], panels[j]);
        }
    }

    private void assemblePanels() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 1, 2, 1);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JPanel p1 = new JPanel(new GridBagLayout());
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        p1.add(new JButton("button 1"), gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        p1.add(new JButton("button 2"), gbc);
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        p1.add(new JButton("button 3"), gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        p1.add(new JButton("button 4"), gbc);
        
        JPanel p2 = new JPanel(new GridBagLayout());
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        p2.add(new JLabel("enter"), gbc);
        gbc.anchor = GridBagConstraints.WEST;
        p2.add(new JTextField(8), gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        p2.add(new JButton("button 1"), gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        p2.add(new JButton("button 2"), gbc);
        
        JPanel p3 = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(8, 12);
        textArea.setLineWrap(true);
        p3.add(new JScrollPane(textArea));
        JPanel p4 = new JPanel(new GridBagLayout());
        addComponents(new JLabel("label 1"), new JTextField(12), p4, gbc);
        addComponents(new JLabel("label 2"), new JTextField(16), p4, gbc);
        gbc.gridwidth = 2;
        gbc.gridy = 2;
        p4.add(new JSlider(), gbc);
        gbc.gridy++;
        JPanel p5 = new JPanel(new GridBagLayout());
        p5.add(new JButton("button 1"), gbc);
        p5.add(new JButton("button 2"), gbc);
        p5.add(new JButton("button 3"), gbc);
        p5.add(new JButton("button 4"), gbc);
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        p4.add(p5, gbc);
        
        panels = new JPanel[]{p1, p2, p3, p4};
    }

    private void addComponents(Component c1, Component c2, Container c,
            GridBagConstraints gbc) {
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        c.add(c1, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        c.add(c2, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
    }

    private JPanel getComponent() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 3, 0, 3);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        for (int j = 0; j < aps.length; j++) {
            panel.add(aps[j], gbc);
            panel.add(panels[j], gbc);
            panels[j].setVisible(false);
        }
        JLabel padding = new JLabel();
        gbc.weighty = 1.0;
        panel.add(padding, gbc);
        return panel;
    }

    public static void main(String[] args) {
        ExpandingPanels test = new ExpandingPanels();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new JScrollPane(test.getComponent()));
        f.setSize(360, 500);
        f.setLocation(200, 100);
        f.setVisible(true);
    }
}

