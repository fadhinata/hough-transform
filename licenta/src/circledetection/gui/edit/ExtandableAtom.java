package circledetection.gui.edit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.util.Observer;

import javax.swing.JPanel;

public class ExtandableAtom extends  JPanel implements MouseListener {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private String text;
    private Font font;
    private boolean selected;
    private BufferedImage open, closed;
    public Rectangle target;
    final int OFFSET = 30, PAD = 5;
    private MyObservable o;
    ExtandableAtom(String text, JPanel panel) {
        o = new MyObservable();
    	this.text = text;
        this.panel = panel;
        this.addMouseListener(this);
        font = new Font("sans-serif", Font.PLAIN, 12);
        selected = false;
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(200, 20));
      //  setBorder(BorderFactory.createRaisedBevelBorder());
        setPreferredSize(new Dimension(200, 20));
        createImages();
        setRequestFocusEnabled(true);
    }

    public void addObserver(Observer observer)
    {
    	o.addObserver(observer);
    }
    @Override
    public void mousePressed(MouseEvent e) {
       
        if (target.contains(e.getPoint())) {
            toggleSelection();
            togglePanelVisibility();
            System.out.println(o.countObservers());
            o.setChanged();
            o.notifyObservers(this);
        }
    }
    
    
    private void togglePanelVisibility() {
      
       
		if (panel.isShowing()) {
            panel.setVisible(false);
        } else {
            panel.setVisible(true);
        }
        this.getParent().validate();
    }
    
    public void toggleSelection() {
        selected = !selected;
        repaint();
    }
    public boolean isSelected()
    {
    	return selected;
    }
    public void close()
    {
    	selected = false;
    	panel.setVisible(false);
	    this.getParent().validate();
    	repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        if (selected) {
            g2.drawImage(open, PAD, 0, this);
        } else {
            g2.drawImage(closed, PAD, 0, this);
        }
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics(text, frc);
        float height = lm.getAscent() + lm.getDescent();
        float x = OFFSET;
        float y = (h + height) / 2 - lm.getDescent();
        g2.drawString(text, x, y);
    }

    private void createImages() {
        int w = 20;
        int h = getPreferredSize().height;
        target = new Rectangle(2, 0, 20, 18);
        open = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = open.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(getBackground());
        g2.fillRect(0, 0, w, h);
        int[] x = {2, w / 2, 18};
        int[] y = {4, 15, 4};
        Polygon p = new Polygon(x, y, 3);
        g2.setPaint(Color.green.brighter());
        g2.fill(p);
        g2.setPaint(Color.blue.brighter());
        g2.draw(p);
        g2.dispose();
        closed = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        g2 = closed.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(getBackground());
        g2.fillRect(0, 0, w, h);
        x = new int[]{3, 13, 3};
        y = new int[]{4, h / 2, 16};
        p = new Polygon(x, y, 3);
        g2.setPaint(Color.WHITE);
        g2.fill(p);
        g2.setPaint(Color.blue.brighter());
        g2.draw(p);
        g2.dispose();
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}