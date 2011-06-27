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
package circledetection.util.jai;

// JAI
import java.awt.image.renderable.RenderedImageFactory;

import javax.media.jai.JAI;
import javax.media.jai.OperationRegistry;
import javax.media.jai.registry.RIFRegistry;

public class JAIOperatorRegister 
{
	private static OperationRegistry 	opRegistry;
	
	static
	{
		registerOperators();
	}
	
	public static void registerOperators() 
	{
		opRegistry = JAI.getDefaultInstance().getOperationRegistry();
		
        try 
        {
            RenderedImageFactory rif = null;
            String operationName = null;
                    
            //*********************************************************************
            //********	houghellipses
            //*********************************************************************            
            HoughEllipseDescriptor ellipseDescriptor = new HoughEllipseDescriptor();
            operationName = "houghellipses";
            rif = ellipseDescriptor;
            opRegistry.registerDescriptor(ellipseDescriptor);
            RIFRegistry.register(opRegistry, operationName, "", rif);
            rif = null;
            
     
                        
        }
        catch (IllegalArgumentException e)
        {
        	//e.printStackTrace();
        }
    }
}
