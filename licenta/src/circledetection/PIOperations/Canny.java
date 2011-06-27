package circledetection.PIOperations;

import circledetection.util.jai.CannyEdgeDetector;

public class Canny extends Operation {

	@Override
	public void processCommand() {
		CannyEdgeDetector ced = new CannyEdgeDetector();
		ced.setSourceImage(initialState);
		ced.process();
		workImage.display(ced.getEdgesImage());

	}

}
