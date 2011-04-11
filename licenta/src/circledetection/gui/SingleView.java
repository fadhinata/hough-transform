package circledetection.gui;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;


public class SingleView extends ImageFrame {
	
	private static SingleView INSTANCE;
	private JInternalFrame imageFrame;

	private SingleView(JDesktopPane contentPane, String filePath){
		super(contentPane,filePath); 
		imageFrame = new JInternalFrame("Single View Image");
		contentPane.add(imageFrame);
	}
	public void show() {
		if(filePath==null)
			return;

//		try {
//			imageFrame.remove(splitPane);
//			imageFrame.repaint();
//		} catch (Exception e) {

//		}
		if(!workImage.hasContent())
		{
			workImage.createImage(filePath);
			workImage.convertToGrayScale();
			workImage.display();
			
		}	
		JScrollPane scrollPane = new JScrollPane(workImage,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scrollPane.setPreferredSize(new Dimension(work.width, height));
	
		imageFrame.add(scrollPane);
//		imageFrame.setSize(imagePrefferedSize);
		System.out.println(imageFrame.getSize());
		imageFrame.revalidate();
		imageFrame.setVisible(true);
		imageFrame.moveToFront();
		contentPane.repaint();

	}
	
	public void dispose()
	{
		imageFrame.setVisible(false);
	}
	public static ImageFrame getInstance(JDesktopPane contentPane,
			String filePath) {
		
		return INSTANCE == null ? new SingleView(contentPane, filePath) : INSTANCE;
	}
	@Override
	public void setSize(Dimension size) {
		imageFrame.setSize(size);
		
	}
}
