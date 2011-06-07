package circledetection.gui;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import circledetection.gui.frame.ApplicationFrame;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class AppActions {
	private static final class ImageFileFilter extends FileFilter {
		@Override
		public String getDescription() {
			return "Images (*.jpg, *.jpeg, *.bmp,*png)";
		}

		@Override
		public boolean accept(File pathname) {
			if (pathname == null)
				return false;
			if (pathname.isDirectory())
				return true;
			return pathname.getName().toLowerCase()
					.endsWith("jpg")
					|| pathname.getName().toLowerCase()
							.endsWith("jpeg")
					|| pathname.getName().toLowerCase()
							.endsWith("bmp")
					|| pathname.getName().toLowerCase()
							.endsWith("png");
		}
	}
	private static final ApplicationFrame mainFrame = ApplicationFrame.getInstance();
	public static void singleViewAction()
	{
		mainFrame.setViewMode(ApplicationFrame.SINGLE_IMAGE_VIEW);
		mainFrame.showImage();
	}
	public static void dualViewAction() {
		mainFrame.setViewMode(ApplicationFrame.DUAL_VIEW);
		mainFrame.showImage();
		
	}
	public static void showEditPanelAction(boolean flag)
	{
		mainFrame.setShowEditPanel(flag);
	}
	
	public static void openAction()
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				JFileChooser imageChooser = new JFileChooser();
				imageChooser
						.setFileSelectionMode(JFileChooser.FILES_ONLY);
				imageChooser.setMultiSelectionEnabled(false);
				ImageFileFilter imageFilter = new ImageFileFilter();
				imageChooser.setFileFilter(imageFilter);
				imageChooser.setAcceptAllFileFilterUsed(false);
				if (imageChooser.showOpenDialog((Component) mainFrame) == JFileChooser.APPROVE_OPTION) {
					File path = imageChooser.getSelectedFile();
					mainFrame.setFilePath(path.getPath());
					mainFrame.showImage();
				}
				AppToolBar.getInstance().enableButtons();
			}

		});
	}
	public static void saveAction()
	{ 		
		 	BufferedImage saveImage = mainFrame.getImageFrame().getWorkImage().getSource().getAsBufferedImage();
//			int imageWidth = bi.getWidth(null);
//		    int imageHeight = bi.getHeight(null);
//		    // draw original image to thumbnail image object and
//		    // scale it to the new size on-the-fly
//		    BufferedImage saveImage = new BufferedImage(imageWidth,
//		      imageHeight, BufferedImage.TYPE_INT_RGB);
//
//		    Graphics2D graphics2D = saveImage.createGraphics();
//		    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//
//		    graphics2D.drawImage(bi, 0, 0, imageWidth, imageHeight, null);

		    // save thumbnail image to OUTFILE
		    JFileChooser imageChooser = new JFileChooser();
			imageChooser
					.setFileSelectionMode(JFileChooser.FILES_ONLY);
			imageChooser.setMultiSelectionEnabled(false);
			ImageFileFilter imageFilter = new ImageFileFilter();
			imageChooser.setFileFilter(imageFilter);
			imageChooser.setAcceptAllFileFilterUsed(false);
			File path = null;
			if (imageChooser.showSaveDialog((Component) mainFrame) == JFileChooser.APPROVE_OPTION) {
				 path = imageChooser.getSelectedFile();
				
			}
			else return;
			
			String pathName = null;
			if(!path.getName().endsWith(".jpg"))
				pathName = path.getAbsolutePath() + ".jpg";
			
		    BufferedOutputStream out = null;
		        try {
					out = new BufferedOutputStream(new FileOutputStream(pathName));
		        } catch (FileNotFoundException ex) {
		           
		        }
		    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		    JPEGEncodeParam param = encoder.
		      getDefaultJPEGEncodeParam(saveImage);
		    int quality = 100;
		    quality = Math.max(0, Math.min(quality, 100));
		    param.setQuality((float)quality / 100.0f, false);
		    encoder.setJPEGEncodeParam(param);
		    try {
				encoder.encode(saveImage);
				out.close();
			} catch (ImageFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void closeAction() {
		mainFrame.getImageFrame().close();
		
	}
}
