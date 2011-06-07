package circledetection.util;

import java.util.List;

import javax.media.jai.PlanarImage;

public class EllipseDetectedData {

	private PlanarImage image;
	private List<EllipseDescriptor> list;
	public EllipseDetectedData() {

	}
	public EllipseDetectedData(PlanarImage image, List<EllipseDescriptor> list){
		this.image = image;
		this.list = list;
	}
	public PlanarImage getImage() {
		return image;
	}
	public void setImage(PlanarImage image) {
		this.image = image;
	}
	public List<EllipseDescriptor> getList() {
		return list;
	}
	public void setList(List<EllipseDescriptor> list) {
		this.list = list;
	}
	
}
