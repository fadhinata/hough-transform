package circledetection.gui.frame;

import java.awt.Dimension;

import javax.media.jai.PlanarImage;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import circledetection.gui.ImagePanel;
import circledetection.util.Operators;

public class DualViewFrame extends ImageFrame {

	
	private static DualViewFrame INSTANCE;
	private ImagePanel sourceImage;
	private DualViewFrame(JDesktopPane contentPane, String filePath)
	{
		super(contentPane, filePath,"Dual View Image");
		contentPane.add(imageFrame);
		sourceImage = new ImagePanel();
	}
	
	public void show() {
		if(filePath==null)
			return;
			
		JScrollPane sourceScrollPane = null;
		if (!sourceImage.hasContent()) {
			new Thread() {
				public void run() {
					sourceImage.createImage(filePath);
					sourceImage.display();

				}
			}.start();
			sourceScrollPane = new JScrollPane(sourceImage,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		}
		JScrollPane workScrollPane = null;
		if (!workImage.hasContent())
		{
			new Thread() {
				public void run() {
					workImage.createImage(filePath);
					workImage.display(Operators.convertToGrayScale(workImage.getSource()));

				}
			}.start();
			
		}	
		else 
		{
			workImage.display();
		}
		workScrollPane = new JScrollPane(workImage,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
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
		splitPane.setDividerLocation(.5);
		contentPane.repaint();

	}


	
	public static ImageFrame getInstance(JDesktopPane contentPane,
			String filePath) {
		
		return INSTANCE == null ? new DualViewFrame(contentPane, filePath) : INSTANCE;
	}

	@Override
	public void setSize(Dimension size) {
		imageFrame.setSize(size);
		
	}

	@Override
	public void scale(float scaleFactor) {
		if(scaleFactor == 1.0f)
		{
			float sFactor;
			if(sourceImage.getSource().getWidth() != workImage.getSource().getWidth() )
				{
					sFactor = ApplicationFrame.getInstance().getScaleFactor();
					PlanarImage img = Operators.scale(sourceImage.getSourceForZoom(),sFactor,sFactor);
					sourceImage.setSource(img);
					sourceImage.display();
				}
		}
		else
		{
			PlanarImage img = Operators.scale(workImage.getSourceForZoom(),scaleFactor,scaleFactor);
			workImage.setSource(img);
			workImage.display();
			
			img = Operators.scale(sourceImage.getSourceForZoom(),scaleFactor,scaleFactor);
			sourceImage.setSource(img);
			sourceImage.display();
		}
	}

	

}
