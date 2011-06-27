package circledetection.PIOperations;

import java.awt.image.renderable.ParameterBlock;
import java.util.concurrent.ExecutionException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Hough extends Operation {

	private ParameterBlock pb;
	private JProgressBar progessBar;
	 public Hough(ParameterBlock pb,JProgressBar progressBar)
	 {
		 this.pb = pb;
		 this.progessBar = progressBar;
		 
	 }

	@Override
	public void processCommand() {		
		
		class EllipseFinder extends SwingWorker<PlanarImage, Object> {


			
			
			@Override
			protected PlanarImage doInBackground() throws Exception {
				System.out.println("do in background");
				System.out.println(SwingUtilities.isEventDispatchThread());
				pb.addSource(initialState);
				return (PlanarImage)JAI.create("HoughEllipses",pb);
//				return  Operators.houghEllipse(workImg.getSource(), getParameters());
			}
			
			protected void done() {
				try {
					PlanarImage img = get();
					workImage.display(img);
					workImage.revalidate();
					System.out.println("done");
					progessBar.setIndeterminate(false);
//					workImg.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		};
		(new EllipseFinder()).execute();

    }

	

}
