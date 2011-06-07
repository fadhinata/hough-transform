package circledetection.gui.frame;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import circledetection.gui.ImagePanel;

public abstract class ImageFrame {

	protected String filePath;
	protected ImagePanel workImage;
	//	protected JInternalFrame imageFrame;
	protected JDesktopPane contentPane;
	protected JInternalFrame imageFrame;

	public ImageFrame(JDesktopPane contentPane, String filePath) {
		this.contentPane = contentPane;
		this.filePath = filePath;
		workImage = new ImagePanel();
	}
	public ImagePanel getWorkImage() {
		return workImage;
	}

	public abstract void dispose();
	public abstract void show();
	public abstract void setSize(Dimension size);
	
	public void close() {
		imageFrame.dispose();
		workImage.clear();
		
	}

}
