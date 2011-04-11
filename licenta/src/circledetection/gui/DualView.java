package circledetection.gui;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class DualView extends ImageFrame {

	
	private static DualView INSTANCE;
	private ImagePanel sourceImage;
	private JInternalFrame imageFrame;

	private DualView(JDesktopPane contentPane, String filePath)
	{
		super(contentPane, filePath);
		imageFrame = new JInternalFrame("Dual View Image");
		contentPane.add(imageFrame);
		sourceImage = new ImagePanel();
	}
	
	public void show() {
		if(filePath==null)
			return;
			
		JScrollPane sourceScrollPane = null;
		if (!sourceImage.hasContent()) {
			sourceImage.createImage(filePath);
			sourceImage.display();
			sourceScrollPane  = new JScrollPane(sourceImage,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		}
		JScrollPane workScrollPane = null;
		if (!workImage.hasContent())
		{
			workImage.createImage(filePath);
			workImage.convertToGrayScale();
			workImage.display();
			workScrollPane = new JScrollPane(workImage,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			
		}	
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					sourceScrollPane, workScrollPane);
//		contentPane.getSize();
		workImage.setVisible(true);
		workImage.revalidate();
		sourceImage.setVisible(true);
		sourceImage.revalidate();
//        splitPane.setPreferredSize(imagePrefferedSize);
//        splitPane.setMinimumSize(imagePrefferedSize);
		
		imageFrame.add(splitPane);
//		imageFrame.repaint();
//		imageFrame.revalidate();
		imageFrame.setVisible(true);
		splitPane.setDividerLocation(imageFrame.getWidth()/2);
		contentPane.repaint();

	}

	@Override
	public void dispose() {
		imageFrame.setVisible(false);
		
	}
	
	public static ImageFrame getInstance(JDesktopPane contentPane,
			String filePath) {
		
		return INSTANCE == null ? new DualView(contentPane, filePath) : INSTANCE;
	}

	@Override
	public void setSize(Dimension size) {
		imageFrame.setSize(size);
		
	}

}
