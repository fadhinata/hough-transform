package circledetection.gui.frame;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;


public abstract class ImageFrame {

	protected String filePath;
	protected ImagePanel workImage;
	//	protected JInternalFrame imageFrame;
	protected JDesktopPane contentPane;
	protected JInternalFrame imageFrame;

	public ImageFrame(JDesktopPane contentPane, String filePath,String imageFrameName) {
		this.contentPane = contentPane;
		this.filePath = filePath;
		workImage = new ImagePanel();
		imageFrame = new JInternalFrame(imageFrameName);
		imageFrame.setClosable(true);
		imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	public ImagePanel getWorkImage() {
		return workImage;
	}

	public void dispose(){
		imageFrame.setVisible(false);
	};
	public abstract void show();
	public abstract void scale(float scaleFactor);
	public abstract void setSize(Dimension size);
	
	public void close() {
		imageFrame.dispose();
		workImage.clear();
		
	}

}
