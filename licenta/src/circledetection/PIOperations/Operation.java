package circledetection.PIOperations;

import javax.media.jai.PlanarImage;

import circledetection.gui.frame.ApplicationFrame;
import circledetection.gui.frame.ImagePanel;
import circledetection.util.Cache;

public abstract class Operation {
	protected PlanarImage initialState;
	private Cache cache = Cache.getInstance();
	protected ImagePanel workImage = ApplicationFrame.getInstance().getImageFrame().getWorkImage();

	public PlanarImage getInitialState() {
		return initialState;
	}
	public Operation()
	{
		this.initialState = workImage.getSource();
		cache.add(this);
	}
	public abstract void processCommand();
	
	public void undo(){
		workImage.display(initialState);
	}
	public void redo()
	{
		PlanarImage img;
		if((img=cache.getNextOpInitialState())!=null){
			workImage.display(img);
		}
	}
}
