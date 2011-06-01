package circledetection.util;

public class Utils {

	   public static final int BLACK = 0;
       public static final int WHITE = 255;
       
       public static double getHarmonicMean(double vals[])
   	{
   		double sum 	= 0.0;
   		double n	= vals.length;
   		for(int i=0;i<vals.length;i++)
   			sum += 1.0 / vals[i];
   		return n / sum;	
   	}
}
