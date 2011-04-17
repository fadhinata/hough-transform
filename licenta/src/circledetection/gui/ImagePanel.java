package circledetection.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JPanel;

import com.sun.media.jai.widget.DisplayJAI;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements MouseListener {

	private PlanarImage sourceForZoom = null;
	private PlanarImage source = null;
	// private RenderableImage renderableSource;
	// private Vector<PlanarImage> sources;
	private DisplayJAI display;
	private int width;
	private int height;
	private float scaleFactor = 1.0f;

	// private PlanarImage dst;
	// private JScrollPane scrollPane;

	public ImagePanel() {

		this.setLayout(new BorderLayout());
		setBackground(Color.black);
		this.addMouseListener(this);

	}

	public void createImage(String filename) {

		source = JAI.create("fileload", filename);
		sourceForZoom = JAI.create("fileload", filename);
		width = source.getWidth();
		height = source.getHeight();
		this.setSize(width, height);
		display = new DisplayJAI();
		add(display, BorderLayout.CENTER);

	}

	public void display() {

		display.set(source);

	}

	public void display(PlanarImage img) {
		source = img;
		sourceForZoom = img;
		display();

	}

	private void scale(float width, float height) {

		PlanarImage rendering;
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(sourceForZoom); // The source image
		pb.add(width); // The xScale
		pb.add(height); // The yScale
		pb.add(0.0F); // The x translation
		pb.add(0.0F); // The y translation
		pb.add(Interpolation.getInstance(Interpolation.INTERP_BICUBIC_2)); // The
																			// interpolation

		// Create the scale operation
		rendering = JAI.create("scale", pb, null);
		source = rendering;
		display.set(source);
	}

	

	
	public boolean hasContent() {
		return source != null;
	}

	public PlanarImage getSource() {
		// TODO Auto-generated method stub
		return source;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (e.getButton() == MouseEvent.BUTTON1)
			scaleFactor += 0.2;
		if (e.getButton() == MouseEvent.BUTTON3)
			scaleFactor -= 0.2;
		scale(scaleFactor, scaleFactor);

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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
