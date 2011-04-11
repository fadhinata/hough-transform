package circledetection.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
@SuppressWarnings("serial")
public class ApplicationFrame extends JFrame {
	public static final int SINGLE_IMAGE_VIEW = 0;
	public static final int DUAL_VIEW = 1;

	private JMenuBar menuBar;
	private EditPanel editPanel;
	private Container contentPane;
//	private Toolkit tk;

	private String filePath;
	private ImageFrame imageFrame;
	private JInternalFrame histogramFrame;

	private int viewMode = SINGLE_IMAGE_VIEW;
	private static Dimension imagePrefferedSize;




	private ApplicationFrame() {

		super("Circle Detection");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = new JDesktopPane();
		this.setContentPane(contentPane);
		contentPane.setBackground(Color.black);
		
//		tk = Toolkit.getDefaultToolkit();

		setExtendedState(MAXIMIZED_BOTH);
		contentPane.setLayout(new BorderLayout());

		initComponents();
		
		setVisible(true);
		calculateImagePanelPrefferedSize();

	}


	public ImageFrame getImageFrame() {
		return imageFrame;
	}


	public void setImageFrame(ImageFrame imageFrame) {
		this.imageFrame = imageFrame;
	}


	public JInternalFrame getHistogramFrame() {
		return histogramFrame;
	}

	public static Dimension getPrefferedSize(){
		return imagePrefferedSize;
		
	}
	public int getViewMode() {
		return viewMode;
	}
	
	public void setViewMode(int viewMode) {
		this.viewMode = viewMode;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void close() {
		System.exit(0);
	}

	private void initComponents() {
		editPanel = new EditPanel(this);
		contentPane.add(editPanel, BorderLayout.LINE_END);
		editPanel.setVisible(true);
		
	
		
		histogramFrame = new JInternalFrame("Histogram");
		histogramFrame.setClosable(true);
		histogramFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
		histogramFrame.setResizable(true);
		contentPane.add(histogramFrame);
		histogramFrame.setSize(200, 200);
//		histogramFrame.setMaximumSize(new Dimension(200, 200));

		
		menuBar = new AppMenuBar(this);
		this.setJMenuBar(menuBar);

	}

	public void showImage() {
		
		if(imageFrame!= null)
			imageFrame.dispose();
		if (viewMode == SINGLE_IMAGE_VIEW)
			imageFrame =  SingleView.getInstance((JDesktopPane)contentPane,filePath);
		else
			imageFrame = DualView.getInstance((JDesktopPane)contentPane, filePath);

		imageFrame.setSize(imagePrefferedSize);
		imageFrame.show();
	}
	
	public void calculateImagePanelPrefferedSize() {

		int width = (int) (0.75 * getWidth());
		int height = (int) (0.9 * getHeight());

//		if (viewMode == DUAL_VIEW) {
//			width = width / 2 - 10;
//			height = height / 2 - 10;
//			
//		}
	  imagePrefferedSize= new Dimension(width,height);
	}

	public static void main(String[] args) {
		ApplicationFrame ap = new ApplicationFrame();
		System.out.println(ap.getContentPane().getWidth() + ", "
				+ ap.getHeight());
	}

	public void setShowEditPanel(boolean selected) {

	}

}
