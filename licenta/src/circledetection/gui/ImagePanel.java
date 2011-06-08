package circledetection.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.media.jai.widget.DisplayJAI;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel  {

	private PlanarImage sourceForZoom = null;
	public PlanarImage getSourceForZoom() {
		return sourceForZoom;
	}

	private PlanarImage source = null;
	// private RenderableImage renderableSource;
	// private Vector<PlanarImage> sources;
	private DisplayJAI display;
	private int width;
	private int height;

	private JLabel img;

	// private PlanarImage dst;
	// private JScrollPane scrollPane;

	public ImagePanel() {

		this.setLayout(new BorderLayout());
		setBackground(Color.black);
		this.img = new JLabel();
		this.img.setDoubleBuffered(true);
		this.add("Center",img);
		
	
//		this.addMouseListener(this);

	}

	public void createImage(String filename) {

		source = JAI.create("fileload", filename);
		sourceForZoom = JAI.create("fileload", filename);
		width = source.getWidth();
		height = source.getHeight();
		this.setSize(width, height);

	}

	public void display() {
		BufferedImage image = source.getAsBufferedImage();
		BufferedImage buffer = new BufferedImage(image.getWidth(), image.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics	=	(Graphics2D)buffer.getGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
		this.img.setIcon(new ImageIcon(buffer) );		
		this.img.repaint();
		this.setSize(new Dimension(image.getWidth(),image.getHeight()));


	}

	public void display(PlanarImage img) {
		source = img;
		sourceForZoom = img;
		display();

	}

	

	  
	
	public boolean hasContent() {
		return source != null;
	}

	public PlanarImage getSource() {
		// TODO Auto-generated method stub
		return source;
	}

//	@Override
//	public void mouseClicked(MouseEvent e) {
//		// TODO Auto-generated method stub
//
//		if (e.getButton() == MouseEvent.BUTTON1)
//			scaleFactor += 0.2;
//		if (e.getButton() == MouseEvent.BUTTON3)
//			scaleFactor -=0.2;
////		scale(scaleFactor, scaleFactor);
//
//	}

	

	public void setSource(PlanarImage source) {
		this.source = source;
		
	}

	public void clear() {
		source = null;
		sourceForZoom = null;
		
	}
	

}
