package photoeditor;

import photoeditor.*;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.Raster;
import java.awt.image.ReplicateScaleFilter;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Feli & Lacri
 */
public class ImageProcessingPanel extends JPanel {
    public int w,h,x,y,Width,Height;
   
     // red band Matrix
  public static final float RED_BAND_MATRIX[][] = { { 1.0f, 0.0f, 0.0f },
      { 0.0f, 0.0f, 0.0f }, { 0.0f, 0.0f, 0.0f } };

  // green band Matrix
  static final float GREEN_BAND_MATRIX[][] = { { 0.0f, 0.0f, 0.0f },
      { 0.0f, 1.0f, 0.0f }, { 0.0f, 0.0f, 0.0f } };

  // blue band Matrix
  static final float BLUE_BAND_MATRIX[][] = { { 0.0f, 0.0f, 0.0f },
      { 0.0f, 0.0f, 0.0f }, { 0.0f, 0.0f, 1.0f } };

  // Matrix that inverts all the bands
  // the nagative of the image.
  static final float INVERSE_BAND_MATRIX[][] = { { -1.0f, 0.0f, 0.0f },
      { 0.0f, -1.0f, 0.0f }, { 0.0f, 0.0f, -1.0f } };

  // Matrix that reduces the intensities of all bands
  static final float AVERAGE_BAND_MATRIX[][] = { { 0.5f, 0.0f, 0.0f },
      { 0.0f, 0.5f, 0.0f }, { 0.0f, 0.0f, 0.5f } };


     Image image, resizedImage;
     Insets insets;
     BufferedImage biSrc,biDest;
     Graphics2D g2;
      // The source and destination rasters
  Raster srcRaster;
  WritableRaster dstRaster;

  BufferedImage bi;

  //Rectangle clip;

 public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;

    //g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    w = getWidth();
    h = getHeight();

    x = (w - bi.getWidth(this))/2;
    y = (h - bi.getHeight(this))/2;

    if (insets == null) {
      insets = getInsets();
    }
    //g2D.drawImage(bi, insets.left, insets.top, this);
    //g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5));
    g2D.drawImage(bi, x, y, this);
    //g2D.setPaint(Color.red);
    //g2D.draw(clip);
  }
/*
 public Rectangle getClip()
    {
        return clip;
    }

    public void setClip(Point p1, Point p2)
    {
        clip.setFrameFromDiagonal(p1, p2);
        repaint();
    }
*/
public void loadImage(Image loadedImage) throws FileNotFoundException, IOException {
    //loadedImage = Toolkit.getDefaultToolkit().getImage(name);
    MediaTracker tracker = new MediaTracker(this);
    tracker.addImage(loadedImage, 0);
    try {
      tracker.waitForID(0);
    } catch (InterruptedException e) {
    }
    if (loadedImage.getWidth(this) == -1) {
      System.out.println("No jpg) file");
      System.exit(0);
    }

   biSrc = new BufferedImage(loadedImage.getWidth(this), loadedImage
        .getHeight(this), BufferedImage.TYPE_INT_RGB);

    g2 = biSrc.createGraphics();
    g2.drawImage(loadedImage, 0, 0, this);

    

    biDest = new BufferedImage(image.getWidth(this), image
        .getHeight(this), BufferedImage.TYPE_INT_RGB);

    dstRaster = (WritableRaster) biDest.getRaster();
    srcRaster = biSrc.getRaster();

    bi = biSrc;
    
    
    int thumbWidth = 100;
    int thumbHeight = 100;

    double thumbRatio = (double)thumbWidth / (double)thumbHeight;

    int imageWidth = loadedImage.getWidth(null);
    int imageHeight = loadedImage.getHeight(null);

    double imageRatio = (double)imageWidth / (double)imageHeight;

    if (thumbRatio < imageRatio) {
      thumbHeight = (int)(thumbWidth / imageRatio);
    } else {
      thumbWidth = (int)(thumbHeight * imageRatio);
    }


     // draw original image to thumbnail image object and
    // scale it to the new size on-the-fly
    BufferedImage thumbImage = new BufferedImage(thumbWidth,
      thumbHeight, BufferedImage.TYPE_INT_RGB);

    Graphics2D graphics2D = thumbImage.createGraphics();
    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    graphics2D.drawImage(loadedImage, 0, 0, thumbWidth, thumbHeight, null);

    // save thumbnail image to OUTFILE

    BufferedOutputStream out = new BufferedOutputStream(new
      FileOutputStream("iunie29_img.jpg"));
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    JPEGEncodeParam param = encoder.
      getDefaultJPEGEncodeParam(thumbImage);
    int quality = 90;
    quality = Math.max(0, Math.min(quality, 100));
    param.setQuality((float)quality / 100.0f, false);
    encoder.setJPEGEncodeParam(param);
    encoder.encode(thumbImage);
    out.close();

    System.out.println("Done.");

    repaint();
  }
  public void loadImage(String name,String th) throws FileNotFoundException, IOException {
 
    image = Toolkit.getDefaultToolkit().getImage(name);
    MediaTracker tracker = new MediaTracker(this);
    tracker.addImage(image, 0);
    try {
      tracker.waitForID(0);
    } catch (InterruptedException e) {
    }
  

    //clip = new Rectangle();
    ImageProcessingFrame.img_width = image.getWidth(this);
    ImageProcessingFrame.img_height = image.getHeight(this);
    ImageProcessingFrame.nume = name;
    ImageProcessingFrame.numeleImaginiiCurente = name;
    ImageProcessingFrame.mesaj = "";
    /*#  // Load the image
#         BufferedImage loaded = loadImage(url);
#         // Create the image using the
#         BufferedImage aimg = new BufferedImage(loaded.getWidth(), loaded.getHeight(), BufferedImage.TRANSLUCENT);
#         // Get the images graphics
#         Graphics2D g = aimg.createGraphics();
#         // Set the Graphics composite to Alpha
#         g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transperancy));
#         // Draw the LOADED img into the prepared reciver image
#         g.drawImage(loaded, null, 0, 0);
#         // let go of all system resources in this Graphics
#         g.dispose();
     */

    //biSrc = new BufferedImage(image.getWidth(this), image.getHeight(this), BufferedImage.TYPE_INT_RGB);
    biSrc = new BufferedImage(image.getWidth(this), image.getHeight(this), BufferedImage.TYPE_INT_RGB);
    bi = biSrc;
    Width = bi.getWidth();
    Height = bi.getHeight();   
    x = w /2;
    y = h /2;

    g2 = biSrc.createGraphics();
    g2.drawImage(biSrc, x, x, this);

    srcRaster = biSrc.getRaster();

    biDest = new BufferedImage(image.getWidth(this), image
        .getHeight(this), BufferedImage.TYPE_INT_RGB);

    dstRaster = (WritableRaster) biDest.getRaster();
    //createThumbnail("th.jpg");
    repaint();
  }
  /*
  void createThumbnail(String th) throws IOException{
      int thumbWidth = 70;
    int thumbHeight = 70;

    double thumbRatio = (double)thumbWidth / (double)thumbHeight;
    int imageWidth = biSrc.getWidth(null);
    int imageHeight = biSrc.getHeight(null);
    double imageRatio = (double)imageWidth / (double)imageHeight;

    if (thumbRatio < imageRatio) {
      thumbHeight = (int)(thumbWidth / imageRatio);
    } else {
      thumbWidth = (int)(thumbHeight * imageRatio);
    }


     // draw original image to thumbnail image object and
    // scale it to the new size on-the-fly
    BufferedImage thumbImage = new BufferedImage(thumbWidth,
      thumbHeight, BufferedImage.TYPE_INT_RGB);

    Graphics2D graphics2D = thumbImage.createGraphics();
    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    graphics2D.drawImage(bi, 0, 0, thumbWidth, thumbHeight, null);

    // save thumbnail image to OUTFILE

    BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(th));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageProcessingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    JPEGEncodeParam param = encoder.
      getDefaultJPEGEncodeParam(thumbImage);
    int quality = 100;
    quality = Math.max(0, Math.min(quality, 100));
    param.setQuality((float)quality / 100.0f, false);
    encoder.setJPEGEncodeParam(param);
    encoder.encode(thumbImage);
    out.close();

    System.out.println("Done. Thumbnail    "+  th  +    "created!");
  }

*/
  void saveChanges(int q) throws IOException{

    int imageWidth = bi.getWidth(null);
    int imageHeight = bi.getHeight(null);
    // draw original image to thumbnail image object and
    // scale it to the new size on-the-fly
    BufferedImage saveImage = new BufferedImage(imageWidth,
      imageHeight, BufferedImage.TYPE_INT_RGB);

    Graphics2D graphics2D = saveImage.createGraphics();
    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    graphics2D.drawImage(bi, 0, 0, imageWidth, imageHeight, null);

    // save thumbnail image to OUTFILE
    BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(ImageProcessingFrame.nume));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageProcessingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    JPEGEncodeParam param = encoder.
      getDefaultJPEGEncodeParam(saveImage);
    int quality = 100;
    quality = Math.max(0, Math.min(quality, 100));
    param.setQuality((float)quality / 100.0f, false);
    encoder.setJPEGEncodeParam(param);
    encoder.encode(saveImage);
    out.close();

    System.out.println("Done. Image    "+  ImageProcessingFrame.nume  +    "saved!");
  }

  private void filter(BufferedImageOp op) {
    BufferedImage filteredImage = new BufferedImage(Width, Height, bi.getType());
    op.filter(bi, filteredImage);
    bi = filteredImage;
    //biSrc = bi;
    repaint();
  }

  private void convolve(float[] elements) {
    Kernel kernel = new Kernel(3, 3, elements);
    ConvolveOp op = new ConvolveOp(kernel);
    filter(op);
  }

  public void blur() throws IOException {
    float weight = 1.0f / 9.0f;
   float[] elements = new float[9];
   for (int i = 0; i < 9; i++)
         elements[i] = weight;
  
   // float[] elements = { weight*1.0f, weight*1.0f, weight*1.0f, weight*1.0f, weight*2.0f, weight*1.0f, weight*1.0f, weight*1.0f,
    //    weight*1.0f };
    convolve(elements);
    //createThumbnail("th1.jpg");
  }

  public void sharpen() {
    float[] elements = { 0.0f, -1.0f, 0.0f, -1.0f, 5.f, -1.0f, 0.0f, -1.0f,
        0.0f };
    convolve(elements);
      
  }

  void edgeDetect() {
   float[] elements = { 
       0.0f, -1.0f, 0.0f,
       -1.0f, 4.0f, -1.0f,
       0.0f, -1.0f, 0.0f
   };
   
    convolve(elements);
        
  }

  public void brighten() {
    float a = 1.5f;
    float b = -20.0f;
    RescaleOp op = new RescaleOp(a, b, null);
    filter(op);
        
  }

  void negative() {
    byte negative[] = new byte[256];
    for (int i = 0; i < 256; i++)
      negative[i] = (byte) (255 - i);
    ByteLookupTable table = new ByteLookupTable(0, negative);
    LookupOp op = new LookupOp(table, null);
    filter(op);
       
  }

  void rotate90DX()
	{
		int width = bi.getWidth();
		int height = bi.getHeight();

		BufferedImage biFlip = new BufferedImage(height, width, bi.getType());

		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++){
				//biFlip.setRGB(height-1-j, width-1-i, bi.getRGB(i, j));
                biFlip.setRGB(height-1-j, i, bi.getRGB(i, j));
            }

		bi = biFlip;
        repaint();
	}
  void rotate90SX()
	{
		int width = bi.getWidth();
		int height = bi.getHeight();

		BufferedImage biFlip = new BufferedImage( height,width,bi.getType());

		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++){
				//biFlip.setRGB(height-1-j, width-1-i, bi.getRGB(i, j));
                biFlip.setRGB(j, width-1-i, bi.getRGB(i, j));
            }

		bi = biFlip;
        repaint();
	}
  public void flipVer()
    {  
        int width = bi.getWidth();
		int height = bi.getHeight();

		BufferedImage biFlipHor = new BufferedImage( width,height, bi.getType());
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++) {
                //bi.setRGB(i, j, biFlipHor.getRGB(width-i-1,j));
                biFlipHor.setRGB( i,height-1-j, bi.getRGB(i, j));

            }
        }
        bi = biFlipHor;
        repaint();
  }
  public void flipHor()
    {
        int width = bi.getWidth();
		int height = bi.getHeight();

		BufferedImage biFlipHor = new BufferedImage( width,height, bi.getType());
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++) {
                //bi.setRGB(i, j, biFlipHor.getRGB(width-i-1,j));
                biFlipHor.setRGB( width-1-i,j, bi.getRGB(i, j));
                //biFlipHor.setRGB(i, j, bi.getRGB(width-1-i, j));


            }
        }
        bi = biFlipHor;
        repaint();
  }

  void rotate() {
      /*
    AffineTransform transform = AffineTransform.getRotateInstance(Math
        .toRadians(10), bi.getWidth() / 2, bi.getHeight() / 2);
    AffineTransformOp op = new AffineTransformOp(transform,
        AffineTransformOp.TYPE_BILINEAR);
       * */
      AffineTransform tx = new AffineTransform();
    //tx.scale(scalex, scaley);
    tx.shear(0.1, 0.3);
    //tx.translate(x, y);
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
    filter(op);
  }

void scaleDown(double scale) {
/*
    //image = biSrc.getScaledInstance(350, -1, Image.SCALE_AREA_AVERAGING);
    //g2.drawImage(bi, AffineTransform.getScaleInstance(0.7, 0.7), null);
    AffineTransform at = AffineTransform.getScaleInstance(0.9, 0.9);
    AffineTransformOp aop = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
    //g2.drawImage(bi, aop, 0, 0);
 */
    Width = (int)(scale * bi.getWidth());
	Height = (int)(scale * bi.getHeight());
    //image = biSrc.getScaledInstance(350, -1, Image.SCALE_AREA_AVERAGING);
    //g2.drawImage(bi, AffineTransform.getScaleInstance(0.7, 0.7), null);
    AffineTransform at = AffineTransform.getScaleInstance(scale,scale);
    AffineTransformOp aop = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
    //g2.drawImage(bi, aop, 0, 0);

    filter(aop);
}
void scaleUp(double scale) {
    Width = (int)(scale * bi.getWidth());
	Height = (int)(scale * bi.getHeight());
    //image = biSrc.getScaledInstance(350, -1, Image.SCALE_AREA_AVERAGING);
    //g2.drawImage(bi, AffineTransform.getScaleInstance(0.7, 0.7), null);
    AffineTransform at = AffineTransform.getScaleInstance(scale,scale);
    AffineTransformOp aop = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
    //g2.drawImage(bi, aop, 0, 0);
    filter(aop);
}

void  zoomIn(double scale)
	{
		int width = (int)(scale * bi.getWidth());
		int height = (int)(scale * bi.getHeight());

		BufferedImage biScale = new BufferedImage(width, height, bi.getType());

                // Cicla dando un valore medio al pixel corrispondente
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				biScale.setRGB(i, j, bi.getRGB((int)(i/scale),(int) (j/scale)));

		bi = biScale;
        repaint();
	}
void  zoomOut(double scale)
	{
		int width = (int)(scale * bi.getWidth());
		int height = (int)(scale * bi.getHeight());

		BufferedImage biScale = new BufferedImage(width, height, bi.getType());

                // Cicla dando un valore medio al pixel corrispondente
		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				biScale.setRGB(i, j, bi.getRGB((int)(i/scale),(int) (j/scale)));

		bi = biScale;
        repaint();
	}



  void grayOut() throws IOException {

    ColorConvertOp colorConvert = new ColorConvertOp(ColorSpace
        .getInstance(ColorSpace.CS_GRAY), null);
    //colorConvert.filter(image, image);
    filter(colorConvert);
  
  }

  void bandCombine(float[][] bandCombineMatrix) throws IOException {

    BandCombineOp bandCombineOp = new BandCombineOp(bandCombineMatrix, null);
    bandCombineOp.filter(srcRaster, dstRaster);
    bi = biDest;
    repaint();
 
  }
/*
    void  resize(int newW, int newH) {
          int w = bi.getWidth();
           int h = bi.getHeight();
          BufferedImage dimg  = new BufferedImage(newW, newH, b.getType());
            Graphics2D g = dimg.createGraphics();
         g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
           g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
          g.dispose();

       }
 * */

  public void multRGB(double rn, double gn, double bn){

      ColorComponentScaler ccs = new ColorComponentScaler(rn,gn,bn);
      int width = bi.getWidth();
		int height = bi.getHeight();

		BufferedImage bimult = new BufferedImage( width,height, bi.getType());

      
      for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++) {
                //bi.setRGB(i, j, biFlipHor.getRGB(width-i-1,j));
                
                bimult.setRGB( i,j, ccs.filterRGB(i,j,bi.getRGB(i,j)));
                //System.out.println("Culoare "+bi.getRGB(i,j)+", noua culoare "+ccs.filterRGB(i,j,bi.getRGB(i,j)) );
            }
        }
        bi = bimult;
repaint();

  }
   public void sepia(int sepiaIntensity) throws Exception
{
// Play around with this. 20 works well and was recommended
// by another developer. 0 produces black/white image
int sepiaDepth = 25;

int we = bi.getWidth();
int he = bi.getHeight();

WritableRaster raster = bi.getRaster();

// We need 3 integers (for R,G,B color values) per pixel.
int[] pixels = new int[we*he*3];
raster.getPixels(0, 0, we, he, pixels);

// Process 3 ints at a time for each pixel. Each pixel has 3 RGB colors in array
for (int i=0;i<pixels.length; i+=3)
            {
int r = pixels[i];
int g = pixels[i+1];
int b = pixels[i+2];

int gry = (r + g + b) / 3;
r = g = b = gry;
r = r + (sepiaDepth * 2);
g = g + sepiaDepth;

if (r>255) r=255;
if (g>255) g=255;
if (b>255) b=255;

// Darken blue color to increase sepia effect
b-= sepiaIntensity;

// normalize if out of bounds
if (b<0) b=0;
if (b>255) b=255;

pixels[i] = r;
pixels[i+1]= g;
pixels[i+2] = b;
}
raster.setPixels(0, 0, we, he, pixels);
    repaint();
    
}
  public void reset() {

    g2.setColor(Color.black);
    g2.clearRect(0, 0, bi.getWidth(this), bi.getHeight(this));
    g2.drawImage(image, 0, 0, this);
    bi = biSrc;
     Width =  bi.getWidth();
	Height =  bi.getHeight();

  }
 

  public void update(Graphics g) {
    g.clearRect(0, 0, getWidth(), getHeight());
    paintComponent(g);
  }


/*
void histogram(){
           
        Raster raster = image.getRaster();
        for(int i=0; i < Width ; i++)
        {
            for(int j=0; j < Height ; j++)
            {
                if(gray)
                {
                    bins[0][ raster.getSample(i,j, 0) ] ++;
                }
                else
                {
                    bins[0][ raster.getSample(i,j, 0) ] ++;
                    bins[1][ raster.getSample(i,j, 1) ] ++;
                    bins[2][ raster.getSample(i,j, 2) ] ++;
                }
            }
        }

}
 * */
}