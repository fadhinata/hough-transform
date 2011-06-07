package photoeditor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
import org.w3c.dom.*;

import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;

public class Metadata {
    public static String metadata = "";

    

    public  String readAndDisplayMetadata( String fileName ) {
        try {

            File file = new File( fileName );
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            metadata = "";

            if (readers.hasNext()) {

                // pick the first available ImageReader
                ImageReader reader = readers.next();

                // attach source to the reader
                reader.setInput(iis, true);

                // read metadata of first image
                IIOMetadata metadataIO = reader.getImageMetadata(0);

                String[] names = metadataIO.getMetadataFormatNames();
                int length = names.length;
                
                for (int i = 0; i < length; i++) {
                    System.out.println( "Format name: " + names[ i ] );
                    metadata += "Format name: " + names[i]+"\n";
                    displayMetadata(metadataIO.getAsTree(names[i]));
                   
                }
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return metadata;
    }

    void displayMetadata(Node root) {
        displayMetadata(root, 0);
    }

    void indent(int level) {
        for (int i = 0; i < level; i++)
            System.out.print("       ");
        metadata += "       ";
    }

    void displayMetadata(Node node, int level) {
        // print open tag of element
        indent(level);
        if(level == 1)metadata +="       ";
        else if(level == 2){
            metadata +="       ";
            metadata +="       ";
        }else if(level == 3){
            metadata +="       ";
            metadata +="       ";
            metadata +="       ";
        }else if(level == 4){
            metadata +="       ";
            metadata +="       ";
            metadata +="       ";
            metadata +="       ";
        }
        metadata +="       ";

        String nume1 = node.getNodeName();
        if(nume1.equals("markerSequence")||nume1.equals("Palette") || nume1.equals("PLTE")){
            return;
        }
        System.out.print("" + nume1);
        metadata +="" + nume1;


        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                String nume= attr.getNodeName();
                if(nume.endsWith("Entry")){

                }else{
                System.out.print("" + attr.getNodeName() +
                                 "=\"" + attr.getNodeValue() + "\"");
                
                metadata +=" " + attr.getNodeName() +
                                 "=\"" + attr.getNodeValue() + "\"";


                }
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            // no children, so close element and return
            System.out.println("");
            metadata +="\n";
            return;
        }

        // children, so close current tag
        System.out.println("");
        metadata +="\n";

        if((child.getNodeName().equals("ColorTableEntry") == false) && (child.getNodeName().equals("markerSequence") == false) )
        while (child != null) {
            
            //if(child.equals("LocalColorTable")){
            //    child = null;
            //}
            // print children recursively
            displayMetadata(child, level + 1);
            child = child.getNextSibling();
        }

        // print close tag of element
        indent(level);
        //metadata +="       ";
        //System.out.println("" + node.getNodeName() + "");
        System.out.println("");
        metadata +="\n";
    }
}
