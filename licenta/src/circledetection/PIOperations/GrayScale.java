package circledetection.PIOperations;

import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.ImageLayout;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import com.sun.media.jai.codecimpl.util.RasterFactory;

public class GrayScale extends Operation {

	@Override
	public void processCommand() {
		ColorSpace colorSpaceInput = initialState.getColorModel().getColorSpace();
		ColorModel colorModelInput = RasterFactory.createComponentColorModel(
				initialState.getSampleModel().getDataType(), colorSpaceInput, false,
				false, Transparency.OPAQUE);

		ImageLayout imageLayoutInput = new ImageLayout();
		imageLayoutInput.setColorModel(colorModelInput);
		RenderingHints RenderingHintsInput = new RenderingHints(
				JAI.KEY_IMAGE_LAYOUT, imageLayoutInput);
		ParameterBlock parameterBlockInput = new ParameterBlock();
		parameterBlockInput.addSource(initialState);
		parameterBlockInput.add(initialState.getSampleModel().getDataType());
		PlanarImage sourceWithProfile = JAI.create("format",
				parameterBlockInput, RenderingHintsInput);

		ColorSpace colorSpaceOutput = ColorSpace
				.getInstance(ColorSpace.CS_GRAY);
		ColorModel colorModelOutput = RasterFactory.createComponentColorModel(
				sourceWithProfile.getSampleModel().getDataType(),
				colorSpaceOutput, false, false, Transparency.OPAQUE);

		ImageLayout imageLayoutOutput = new ImageLayout();
		imageLayoutOutput.setSampleModel(colorModelOutput
				.createCompatibleSampleModel(sourceWithProfile.getWidth(),
						sourceWithProfile.getHeight()));
		RenderingHints renderingHintsOutput = new RenderingHints(
				JAI.KEY_IMAGE_LAYOUT, imageLayoutOutput);
		ParameterBlock parameterBlockOutput = new ParameterBlock();
		parameterBlockOutput.addSource(sourceWithProfile);
		parameterBlockOutput.add(colorModelOutput);

		workImage.display(JAI.create("ColorConvert", parameterBlockOutput,
				renderingHintsOutput));


	}

}
