package circledetection.gui.frame;

import java.awt.Dimension;

import javax.media.jai.PlanarImage;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;

import circledetection.util.Operators;


public class SingleViewFrame extends ImageFrame {
	
	private static SingleViewFrame INSTANCE;
	private SingleViewFrame(JDesktopPane contentPane, String filePath){
		super(contentPane,filePath,"Single View Image"); 
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
			new Thread(){
				public void run(){
					workImage.createImage(filePath);
					workImage.display(Operators.convertToGrayScale(workImage.getSource()));
					
				}
			}.start();
			
			
		}	
		else 
			workImage.display();
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
	
	
	public static ImageFrame getInstance(JDesktopPane contentPane,
			String filePath) {
		
		return INSTANCE == null ? new SingleViewFrame(contentPane, filePath) : INSTANCE;
	}
	@Override
	public void setSize(Dimension size) {
		imageFrame.setSize(size);
		
	}
	
	@Override
	public void scale(float scaleFactor) {
		PlanarImage img = Operators.scale(workImage.getSourceForZoom(),scaleFactor,scaleFactor);
		workImage.setSource(img);
		workImage.display();
	}
}
