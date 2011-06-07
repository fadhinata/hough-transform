/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package photoeditor;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

/**
 *
 * @author Administrator
 */
public class ColorComponentScaler extends RGBImageFilter {
  private double redMultiplier, greenMultiplier, blueMultiplier,h,s,l;

  private int newRed, newGreen, newBlue;

  private Color color, newColor;

  /**
   * rm = red multiplier gm = green multiplier bm = blue multiplier
   */
  public ColorComponentScaler(double rm, double gm, double bm) {
    canFilterIndexColorModel = true;
    redMultiplier = rm;
    greenMultiplier = gm;
    blueMultiplier = bm;
    h = 0;
    s = 0;
    l = 0;
  }

  private int multColor(int colorComponent, double multiplier) {
    colorComponent = (int) (colorComponent * multiplier);
    if (colorComponent < 0)
      colorComponent = 0;
    else if (colorComponent > 255)
      colorComponent = 255;

    return colorComponent;
  }

  /**
   * split the argb value into its color components, multiply each color
   * component by its corresponding scaler factor and pack the components back
   * into a single pixel
   */
  public int filterRGB(int x, int y, int argb) {
    color = new Color(argb);
    newBlue = multColor(color.getBlue(), blueMultiplier);
    newGreen = multColor(color.getGreen(), greenMultiplier);
    newRed = multColor(color.getRed(), redMultiplier);
    newColor = new Color(newRed, newGreen, newBlue);
    return (newColor.getRGB());
  }
  public int filterHSL(int x, int y, int argb) {
    color = new Color(argb);
    newBlue = multColor(color.getBlue(), blueMultiplier);
    newGreen = multColor(color.getGreen(), greenMultiplier);
    newRed = multColor(color.getRed(), redMultiplier);
    newColor = new Color(newRed, newGreen, newBlue);
    return (newColor.getRGB());
  }
}
