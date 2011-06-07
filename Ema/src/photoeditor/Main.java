/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package photoeditor;

import java.io.FileNotFoundException;

/**
 *
 * @author v
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    	try {
			ImageProcessingFrame ipf = new ImageProcessingFrame();
			ipf.setVisible(true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
