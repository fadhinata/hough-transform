package circledetection.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
@SuppressWarnings("serial")
public class ApplicationFrame extends JFrame {
	public static final int SINGLE_IMAGE_VIEW = 0;
	public static final int DUAL_VIEW = 1;

	private ImagePanel sourceImage = null;
	private ImagePanel workImage = null;

	private JMenuBar menuBar;
	private EditPanel editPanel;
	private Container contentPane;
//	private Toolkit tk;

	private String filePath;
	private JInternalFrame imageFrame;
	private JInternalFrame histogramFrame;

	private int viewMode = SINGLE_IMAGE_VIEW;
	private static Dimension imagePrefferedSize;
	private JSplitPane splitPane;



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

	public ImagePanel getWorkImage() {
		return workImage;
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
		
		workImage = new ImagePanel();
		sourceImage = new ImagePanel();
		
		imageFrame = new JInternalFrame("Image");
		histogramFrame = new JInternalFrame("Histogram");
		histogramFrame.setClosable(true);
		contentPane.add(histogramFrame);
		histogramFrame.setSize(300, 300);
		histogramFrame.setMaximumSize(new Dimension(300, 300));

//		imageFrame.setPreferredSize(imagePrefferedSize);
		imageFrame.setVisible(true);
		
		contentPane.add(imageFrame);

//		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		imageFrame.setContentPane(scrollPane);
//	    scrollPane.setViewportView(workImage);
//	    scrollPane.setPreferredSize(imagePrefferedSize);
	  
//		contentPane.add(imageFrame);
		
		menuBar = new AppMenuBar(this);
		this.setJMenuBar(menuBar);

	}

	public void showImage() {
		
		if (viewMode == SINGLE_IMAGE_VIEW)
			showSingleView();
		else
			showDualView();

	}

	private void showDualView() {
		if(filePath==null)
			return;
		
		try {
			imageFrame.remove(workImage);
//			imageFrame.repaint();
	
		} catch (Exception e) {
		}
		
		if (!sourceImage.hasContent()) {
			sourceImage.createImage(filePath);
			sourceImage.display();

		}
		if (!workImage.hasContent())
		{
			workImage.createImage(filePath);
			workImage.convertToGrayScale();
			workImage.display();
			
		}	
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					sourceImage, workImage);
//		contentPane.getSize();
		workImage.setVisible(true);
		workImage.revalidate();
		sourceImage.setVisible(true);
		sourceImage.revalidate();
//        splitPane.setPreferredSize(imagePrefferedSize);
//        splitPane.setMinimumSize(imagePrefferedSize);
		splitPane.setDividerLocation(imageFrame.getHeight()/2);
		
		imageFrame.add(splitPane);
		imageFrame.repaint();
		imageFrame.revalidate();
		contentPane.repaint();

	}

	private void showSingleView() {
		if(filePath==null)
			return;

		try {
			imageFrame.remove(splitPane);
			imageFrame.repaint();
		} catch (Exception e) {

		}
		if(!workImage.hasContent())
		{
			workImage.createImage(filePath);
			workImage.convertToGrayScale();
			workImage.display();
			
		}	
		JScrollPane scrollPane = new JScrollPane(workImage,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scrollPane.setPreferredSize(new Dimension(work.width, height));
	
		imageFrame.add(scrollPane);
		imageFrame.setSize(imagePrefferedSize);
		System.out.println(imageFrame.getSize());
		imageFrame.revalidate();
		contentPane.repaint();

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
