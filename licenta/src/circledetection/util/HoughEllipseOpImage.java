/*
 * Copyright 2005, 2009 Cosmin Basca.
 * e-mail: cosmin.basca@gmail.com
 * 
 * This is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * Please see COPYING for the complete licence.
 */
package circledetection.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.media.jai.ImageLayout;
import javax.media.jai.PlanarImage;
import javax.media.jai.TileComputationListener;
import javax.media.jai.UntiledOpImage;
import javax.swing.SwingUtilities;

/** Find ellipses in an image with a Hough transform.
 */

@SuppressWarnings("unchecked")
class HoughEllipseOpImage  extends UntiledOpImage
{	
	
	private double  majAxisSmallEllipse;	// major axis
	private double  majAxisLargeEllipse;
	
	private double  minAxisSmallEllipse;	// minor axis
	private double  minAxisLargeEllipse;
	
	private boolean debug;
	
	private float maxPairs;
	
	private int 	MIN_VOTES_TO_DETECT_ELLIPSE;
	private int 	IDLE_STOP_SEARCH;
	
	private Integer[]	accumulator;
	
	private List<EllipseDescriptor> 	ellipses;
	//private int		quantizationBlockWidth;
	//private int		quantizationBlockHeigth;
	
	public HoughEllipseOpImage(RenderedImage source, ImageLayout layout, Integer minMajorAxis, Integer maxMajorAxis , Integer minMinorAxis ,Integer maxMinorAxis,Integer minVotes,Integer idleStop, Float maxPairs, Boolean debug)
    {
    	super(source, null, layout); 
    	System.out.println("constructor");
    	this.minAxisSmallEllipse = (double)minMinorAxis.intValue();
        this.majAxisLargeEllipse = (double)maxMajorAxis.intValue();
        this.minAxisLargeEllipse = (double)maxMinorAxis.intValue();
        this.maxPairs = maxPairs;
        this.debug = debug;
        this.MIN_VOTES_TO_DETECT_ELLIPSE = minVotes.intValue();
        this.IDLE_STOP_SEARCH = idleStop.intValue();
        this.ellipses 	= new ArrayList<EllipseDescriptor>();
    }


    protected void computeImage(Raster[] srcarr, WritableRaster dst, Rectangle destRect)
    {
    	System.out.println("compute image HoughOp");
    	System.out.println(Thread.currentThread().getName());
    	System.out.println(SwingUtilities.isEventDispatchThread());
    	Raster 			src  = srcarr[0];
    	double edgeCoordinates[][];
    	ArrayList<Point> edgeListPoints  = new ArrayList<Point>();
    	int width  		= src.getWidth();
    	int heigth 		= src.getHeight();
    	    	
    	for(int i=0;i<width;i++)
    	{
    		for(int j=0;j<heigth;j++)
    		{
    			int sample = src.getSample(i,j,0);
    			if(sample==Utils.WHITE)
    				edgeListPoints.add(new Point(i,j));
    		}
    	}
    	
    	if(edgeListPoints.isEmpty())
    		return;
    	
    	int edgePoints = edgeListPoints.size();   
    	edgeCoordinates = new double[edgeListPoints.size()][3];
    	for(int i=0;i<edgeListPoints.size();i++)
    	{
    		edgeCoordinates[i][0] = ((Point)edgeListPoints.get(i)).x;
    		edgeCoordinates[i][1] = ((Point)edgeListPoints.get(i)).y;
    		edgeCoordinates[i][2] = 1;									// punct activ
    	}

		if (minAxisLargeEllipse > majAxisLargeEllipse) {
			double temp = minAxisLargeEllipse;
			minAxisLargeEllipse = majAxisLargeEllipse;
			majAxisLargeEllipse = temp;
		}
        // RHT	-	Randomized Hough Transform
        // max nr of pairs about 50 % of img        
    	if(this.debug) {
    		System.out.println("Max Points	: "+this.maxPairs+" %");
    	}
        int step =0;
        if(edgeCoordinates.length <5000)
        	step = 10;
        if(edgeCoordinates.length>= 5000 & edgeCoordinates.length <=25000)
        	step = 50;
        if(edgeCoordinates.length >25000)
        	step = 100;
        if(edgeCoordinates.length >50000)
        	step=150;
        //if(edgePoints > 3000)
        //	maxPairs = 1300;
        	
        int currentPair 	= 0;
        
        int accLength		= (int)(this.majAxisLargeEllipse - this.minAxisSmallEllipse);
        accumulator 		= new Integer[accLength];
     	clear(accumulator);
     	
     	if(this.debug) {
     		System.out.println("Searching   	: "+maxPairs);
     		System.out.println("Total 		: "+edgePoints);
     		System.out.println("Acc len   	: "+accLength);
     	}
        // for speed - // fara prea multe alocari de mem
        int 	p1 		= 0;
        int 	p2 		= 0;
        double 	dP1P2 	= 0.0;
        double 	cx		= 0.0;
        double 	cy		= 0.0;
        double 	a		= 0.0;
		double 	d		= 0.0;        
		double 	f		= 0.0;        
		double 	cos		= 0.0;        
		double 	b		= 0.0;   
		int    	bIDX	= 0; 
		int 	maxData[];
		int 	maxAccElement 		= 0;
		int 	houghMinorLength 	= 0;
		
        //******************************************************************************
//        int idleStopCounter = 0;
//
//			if (idleStopCounter++ >= this.IDLE_STOP_SEARCH)
//				break;
		for (p1 = 0; p1 < edgeCoordinates.length; p1++) {
			if (edgeCoordinates[p1][2] == 0)
				continue;
			for (p2 = p1; p2 < edgeCoordinates.length; p2 += step) {
				if (edgeCoordinates[p2][2] == 0)
					continue;
				// am punctele
				dP1P2 = Point2D.distance(edgeCoordinates[p1][0],
						edgeCoordinates[p1][1], edgeCoordinates[p2][0],
						edgeCoordinates[p2][1]);

				if (dP1P2 >= 2 * majAxisSmallEllipse && dP1P2 <= 2 * majAxisLargeEllipse) {
					currentPair++;
					dst.setSample((int) edgeCoordinates[p1][0],
							(int) edgeCoordinates[p1][1], 0, Utils.WHITE);
					dst.setSample((int) edgeCoordinates[p2][0],
							(int) edgeCoordinates[p2][1], 0, Utils.WHITE);

					cx = (edgeCoordinates[p1][0] + edgeCoordinates[p2][0]) / 2.0;
					cy = (edgeCoordinates[p1][1] + edgeCoordinates[p2][1]) / 2.0;
					a = dP1P2 / 2.0;

					for (int p3 = 0; p3 < edgePoints; p3++) {
						if (p3 == p1 || p3 == p2 || edgeCoordinates[p3][2] == 0)
							continue;
						d = Point2D.distance(edgeCoordinates[p3][0],
								edgeCoordinates[p3][1], cx, cy);

						if (d >= this.minAxisSmallEllipse && d <= a) {
							f = Point2D.distance(edgeCoordinates[p3][0],
									edgeCoordinates[p3][1],
									edgeCoordinates[p2][0],
									edgeCoordinates[p2][1]);
							cos = (a * a + d * d - f * f) / (2 * a * d);
							b = Math.sqrt((a * a * d * d * (1 - cos * cos))
									/ (a * a - d * d * cos * cos));

							if (b > minAxisLargeEllipse)
								continue;

							bIDX = (int) (Math.round(b) - minAxisSmallEllipse);

							if (bIDX < 0 || bIDX >= accumulator.length)
								continue;
							accumulator[bIDX]++;

						}

					}

					// find max in acc array
					maxData = this.max(accumulator);
					maxAccElement = maxData[1];
					houghMinorLength = maxData[0] + (int) minAxisSmallEllipse;
					// am elipsa posibila cu val = raza mica
//					System.out.println(maxAccElement);
					if (maxAccElement >= this.MIN_VOTES_TO_DETECT_ELLIPSE) {
						// output ellipse
						// System.out.println(maxAccElement);

						EllipseDescriptor ellipse = addEllipse(edgeCoordinates,
								p1, p2, cx, cy, a, houghMinorLength);

						// rem pixels of ellipse from edge array
						removeEllipse(edgeCoordinates, ellipse);

						// idleStopCounter = 0;
					}
					clear(accumulator);

				}

			}
		}

        // get Clustered Ellipses
        if(this.debug) {
        	System.out.println("Total Ellipses	: "+this.ellipses.size()+" ");
        }
		/*
		for(int i=0;i<this.ellipses.size();i++)
		{
			EllipseDescriptor desc = ellipses.elementAt(i);
			System.out.println(desc.toString());
		}
		*/				
		
    	List<EllipseDescriptor> centroids = EllipseDescriptor.getCentroidalEllipses(ellipses);
        ellipses.clear();
        ellipses.addAll(centroids); 
    
        this.setProperty(EllipseDescriptor.DETECTED_ELLIPSES,ellipses);
        //for(int i=0;i<centroids.size();i++)
        //	ellipses.addElement(centroids.elementAt(i));
        
    	// draw ellipse center
        if(this.debug) {
        	System.out.println("Ellipses	: "+this.ellipses.size()+" ellipses");
        }
		for(int i=0;i<this.ellipses.size();i++)
		{
			EllipseDescriptor desc = ellipses.get(i);
			drawPixel(0,(int)desc.getCenter().getX(),(int)desc.getCenter().getY(),dst,5);
			drawPixel(1,(int)desc.getCenter().getX(),(int)desc.getCenter().getY(),dst,5);
			drawPixel(2,(int)desc.getCenter().getX(),(int)desc.getCenter().getY(),dst,5);
			
			drawPixel(1,(int)desc.getVertex1().getX(),(int)desc.getVertex1().getY(),dst,5);
			drawPixel(1,(int)desc.getVertex2().getX(),(int)desc.getVertex2().getY(),dst,5);
			if(this.debug) {
				System.out.println(desc.toString());
			}
		}
		
	}


	private EllipseDescriptor addEllipse(double[][] edgeCoordinates, int p1,
			int p2, double cx, double cy, double a, int houghMinorLength) {
		EllipseDescriptor ellipse = new EllipseDescriptor(
				new Point2D.Double(edgeCoordinates[p1][0],
						edgeCoordinates[p1][1]), // vertex 1
				new Point2D.Double(edgeCoordinates[p2][0],
						edgeCoordinates[p2][1]), // vertex 2
				new Point2D.Double(cx, cy), // center
				(int) a, // major axis
				houghMinorLength); // minor axis

		ellipses.add(ellipse);
		return ellipse;
	}
   
   	private void drawPixel(int band,int x,int y,WritableRaster dst,int w)
   	{
   		int _x =  x-w/2;
   		int _y =  y-w/2;
   		
		for(int i=0;i<w;i++)
   			for(int j=0;j<w;j++)   			
   				dst.setSample(_x+i,_y+j,band,255);   		
   	}
   
//   	@SuppressWarnings("unused")
	private void removeEllipse(double edge[][],EllipseDescriptor ellipse)
   	{
   		double a = ellipse.getHalfMajorAxis();
   		double b = ellipse.getHalfMinorAxis();
   		
   		double cx= ellipse.getCenter().getX();
   		double cy= ellipse.getCenter().getY();
   		
   		//double x1= ellipse.getVertex1().getX();
   		//double y1= ellipse.getVertex1().getY();
   		
   		double x2= ellipse.getVertex2().getX();
   		double y2= ellipse.getVertex2().getY();
   		
   		for(int p3=0; p3<edge.length; p3++)
   		{
   			if(edge[p3][2]==0)
   				continue;
   			
   			double d 		= Point2D.distance(edge[p3][0],edge[p3][1],cx,cy);
   			double f 		= Point2D.distance(edge[p3][0],edge[p3][1],x2,y2);
			double cos 		= (a*a + d*d - f*f) / (2*a*d);
			double calcB 	= (int)Math.round( Math.sqrt( (a*a*d*d*(1-cos*cos)) / (a*a - d*d*cos*cos)) );
			
			if(calcB == b) // remove
				edge[p3][2] = 0;
   		}
   	} 
   
    private int[] max(Integer[] integers)
    {
    	int max = 0;
    	int idx = -1;
    	for(int i=0;i<integers.length;i++)
    		if( integers[i] > max )
    		{
    			max = integers[i];
    			idx = i;
    		}
    	return new int[]{idx,max};
    }
    
    private void clear(Integer[] accumulator2)
    {
    	for(int i=0;i<accumulator2.length;i++)
    		accumulator2[i] = 0;
    }

	public Raster computeTile(int tileX, int tileY) {
		// Create a new raster.
		Point org = new Point(getMinX(), getMinY());
		WritableRaster dest = createWritableRaster(sampleModel, org);

		// Determine the active area. Since the image has a single
		// tile equal in coverage to the image bounds just set this
		// to the image bounds.
		Rectangle destRect = getBounds();

		// Cobble source image(s).
		int numSources = getNumSources();
		Raster[] rasterSources = new Raster[numSources];
		for (int i = 0; i < numSources; i++) {
			PlanarImage source = getSource(i);
			Rectangle srcRect = mapDestRect(destRect, i);
			rasterSources[i] = source.getData(srcRect);
		}
		System.out.println("before compute image");
		computeImage(rasterSources, dest, destRect);
		for (int i = 0; i < numSources; i++) {
			Raster sourceData = rasterSources[i];
			if (sourceData != null) {
				PlanarImage source = getSourceImage(i);

				// Recycle the source tile
				if (source.overlapsMultipleTiles(sourceData.getBounds())) {
					recycleTile(sourceData);
				}
			}
		}
		return dest;
	}
}
