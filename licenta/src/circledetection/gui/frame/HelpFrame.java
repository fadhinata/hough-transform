package circledetection.gui.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.rtf.RTFEditorKit;

public class HelpFrame extends JFrame {

	private JList list ;
	private JTextPane textPane;
	public HelpFrame()
	{
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBackground(Color.yellow);
		this.setSize(new Dimension(400,400));
		String[] listData = {"Adjust Contrast", "Median Filter", "Gaussian Filter", "Histogram", "Histogram Equalization","Threshold","Sobel","Prewitt","Canny","Hough Transform"};
		list =new JList(listData);
		textPane = new JTextPane();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,list,textPane);
		this.add(splitPane);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(list.getSelectedValue().equals("Adjust Contrast"))
				{
					setTextToPane("helpFiles/AdjustContrast.txt");
				}
				if(list.getSelectedValue().equals("Median Filter"))
				{
					setTextToPane("helpFiles/MedianFilter.rtf");
				}
			}
		});
		
	}
	private void setTextToPane(String file)
	{



		RTFEditorKit kitRtf;
		StyleContext styleContext;
		DefaultStyledDocument document;


	


		kitRtf = new RTFEditorKit();
		textPane.setEditorKit(kitRtf);
		styleContext = new StyleContext();


		InputStream in;
		try {
			in = new FileInputStream(file);
			document = new DefaultStyledDocument(styleContext);
			kitRtf.read(in,document,0);
			textPane.setDocument(document);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
