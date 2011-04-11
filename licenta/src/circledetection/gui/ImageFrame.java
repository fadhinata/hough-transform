package circledetection.gui;

import java.awt.Dimension;

import javax.swing.JDesktopPane;

public abstract class ImageFrame {

	protected String filePath;
	protected ImagePanel workImage;
	//	protected JInternalFrame imageFrame;
	protected JDesktopPane contentPane;

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

}
