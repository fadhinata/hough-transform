package photoeditor;

//import PhotoEditor.ImageProcessingFrame.SliderListener.MenuItemHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageProcessingFrame extends JFrame implements ActionListener, DropTargetListener{
    private double r = 100, g = 100, b = 100;
    private float h,s,l;

 public static int load = 0;
 public int sw = 0;
 public static String mesaj = "Numele imaginii:  ";
 public static String [] namesThumbnails ={ "img/blur.jpg","img/sharpen.jpg","img/brighten.jpg","img/edge.jpg","img/negativ.jpg","img/sepia.jpg","img/albnegru.jpg","img/red.jpg","img/green.jpg","img/blue.jpg","img/invert.jpg","img/average.jpg"};

    DropTarget dt;
    static DataFlavor urlFlavor, uriListFlavor, macPictStreamFlavor;
  Container frameContainer;

  JToolBar toolBar = new JToolBar("Efects:",JToolBar.VERTICAL);
  JToolBar toolBar1 = new JToolBar("Efects:",JToolBar.VERTICAL);
  JToolBar toolBarBasic = new JToolBar();
  JToolBar toolBarright = new JToolBar();

  Label nume_lb = new Label("");
  Label rezolutie = new Label("");

  public static int img_width = 0,img_height = 0;
  public static String nume= "img/blur.jpg";
  public static String numeleImaginiiCurente = "";
  ImageProcessingPanel ipp = new ImageProcessingPanel();
  //Thumb_ImgProcPanel ippth = new Thumb_ImgProcPanel();
  String[] iconFiles = { "act.jpg", "act.jpg", "act.jpg", "act.jpg", "act.jpg", "act.jpg", "act.jpg","act.jpg" };

  public static String [] thumbIconFiles;
  String[] fis = { "img/open1.png", "img/edit1.png","img/save1.png", "img/zoomin1.png", "img/zoomout1.png","img/fliphor.png", "img/flipver.png","img/rotateLeft.png", "img/rotateRight.png","img/undo1.png","img/cut.png", "img/info1.png"  };
  String[] buttonLabels = { "Blur          ", "Sharpen       ", "Brighten    ", "Edge det.", "Negativ       ", "Sepia", "Black-White   ", "Show Red Band","Show Green Band","Show Blue Band","Invert All Bands","Average Each Band"};
  String[] etich = { "Open", "edit", "Save", "Zoom In", "Zoom Out", "Flip Horizontal","Flip Vertical","Rotate Left", "Rotate Right", "Undo","Crop","Information" };
  ImageIcon[] icons = new ImageIcon[namesThumbnails.length];
  ImageIcon[] toolIcons = new ImageIcon[fis.length];
  ImageIcon rollover, homeicon = new ImageIcon("img/home.png"),convicon = new ImageIcon("img/conv.png");

  JButton[] buttons = new JButton[buttonLabels.length];

  JButton Reset,open,edit,save,zoomIn,zoomOut,flipHor,flipVer,rotleft,rotright,info,crop,undo,home,conv;

  JMenuBar menuBar = new JMenuBar();

  JMenu fileMenu = new JMenu("File");
  JMenu viewMenu = new JMenu("View");
  JMenu toolsMenu = new JMenu("Tools");


  JPanel p = new JPanel();
  JPanel hslPanel = new JPanel();
  JPanel p_ok = new JPanel();
  JPanel tool_p = new JPanel();
  JPanel panel,bottom, panel1;
  JPanel right = new JPanel(new BorderLayout());
  JPanel Left = new JPanel();
  JScrollPane sp;
  Metadata meta = new Metadata();

  JMenuItem fileExit = new JMenuItem("Exit");
  JMenuItem fileOpen = new JMenuItem("Open");
    private JMenuItem openItem;
    private JMenuItem exitItem;
    private JMenuItem viewItem;
    private JMenuItem toolItem;
    private JMenuItem rotateRItem;
    private JMenuItem rotateLItem;

    int min = 0,max = 200,init = 100;
    JSlider sliderR , sliderG ,sliderB,sliderH,sliderS,sliderBr,sliderAlpha;






  public ImageProcessingFrame() throws FileNotFoundException {
      super("Prelucrare imagini");

      addWindowListener(new WindowAdapter() {
     public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
      try {
            ipp.loadImage(nume,namesThumbnails[0]);
            nume_lb.setText("                    ");
            rezolutie.setText("               ");
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    openItem = new JMenuItem("Deschide");
    openItem.addActionListener(this);
    fileMenu.add(openItem);

    exitItem = new JMenuItem("Iesire");
    exitItem.addActionListener(this);
    fileMenu.add(exitItem);

    toolItem = new JMenuItem("Hide Toolbar");
    toolItem.addActionListener(this);
    //toolsMenu.add(toolItem);

    rotateRItem = new JMenuItem("Rotate Right");
    rotateRItem.addActionListener(this);
    toolsMenu.add(rotateRItem);

    rotateLItem = new JMenuItem("Rotate Left");
    rotateLItem.addActionListener(this);
    toolsMenu.add(rotateLItem);



    menuBar.add(fileMenu);
    menuBar.add(toolsMenu);

    setJMenuBar(menuBar);

    BorderLayout b = new BorderLayout();
    bottom = new JPanel(b);


    tool_p.setLayout(new GridLayout(1, 8));
    tool_p.setSize(400,200);
    tool_p.setBorder(new TitledBorder(
        "Efects: "));


    panel  = new JPanel();
    frameContainer = getContentPane();
    //ipp.setBackground(new Color(59,59,59));
    ipp.setBackground(Color.BLACK);
    frameContainer.setLayout(new BorderLayout());
    //*******************************************************

    sliderR = getSlider(0, 200, 100, 100, 10);
    sliderG = getSlider(0, 200, 100, 100, 10);
    sliderB = getSlider(0, 200, 100, 100, 10);
    sliderH = getSlider(0, 100, 50, 50, 10);
    sliderS = getSlider(0, 100, 50, 50, 10);
    sliderBr = getSlider(0, 100, 0, 50, 10);
    sliderAlpha = getSlider(0, 200, 100, 100, 10);

     ChangeListener cl = new SliderListener();

     JSlider slider[] = {sliderR, sliderG, sliderB, sliderH, sliderS, sliderBr, sliderAlpha};
      for (int i = 0; i < slider.length; i++) {
          JSlider sl = slider[i];
          sl.addChangeListener(cl);
      }

    p.setLayout(new GridLayout(9, 1, 0, 00));

    p.add(new JLabel("R-G-B Sliders (0 - 200)"));
    p.add(new JLabel("RED: "));
    p.add(sliderR);
     p.add(new JLabel("GREEN: "));
    p.add(sliderG);
     p.add(new JLabel("BLUE: "));
    p.add(sliderB);
    p.add(new JLabel("Alpha Adjustment (0 - 255): "));
    p.add(sliderAlpha);

    hslPanel.setLayout(new GridLayout(7, 1, 0, 0));
    hslPanel.add(new JLabel("H-S-L Sliders (0 - 200)"));
    hslPanel.add(new JLabel("HUE:"));
    hslPanel.add(sliderH);
    hslPanel.add(new JLabel("SATURATION:"));
    hslPanel.add(sliderS);
    hslPanel.add(new JLabel("LIGHTNESS:"));
    hslPanel.add(sliderBr);

    //panel.add(new JLabel("RGB Value: ", JLabel.RIGHT));
    //*********************************************************
    for (int i = 0; i < etich.length; i++) {
        toolIcons[i] = new ImageIcon(fis[i]);
        switch(i){
            case 0:
                    open = new JButton(etich[i],toolIcons[i]);
                    open.setToolTipText(etich[i]);
                    open.addActionListener(new ButtonListener());
                    toolBarBasic.add(open);
                    break;
            case 1: edit = new JButton(etich[i],toolIcons[i]);
                    edit.setToolTipText(etich[i]);
                    edit.addActionListener(new ButtonListener());
                    if(load == 0){
                        edit.setEnabled(false);
                    }
                    toolBarBasic.add(edit);
                    break;
            case 2: save = new JButton(etich[i],toolIcons[i]);
                    save.setToolTipText(etich[i]);
                    save.addActionListener(new ButtonListener());
                    if(load == 0){
                        save.setEnabled(false);
                    }
                    toolBarBasic.add(save);
                    toolBarBasic.addSeparator(new Dimension(15,15));
                    break;
            case 3: zoomIn = new JButton(toolIcons[i]);
                    zoomIn.setToolTipText(etich[i]);
                    zoomIn.addActionListener(new ButtonListener());
                    if(load == 0){
                        zoomIn.setEnabled(false);
                    }
                    toolBarBasic.add(zoomIn);
                    break;
            case 4: zoomOut = new JButton(toolIcons[i]);
                    zoomOut.setToolTipText(etich[i]);
                    zoomOut.addActionListener(new ButtonListener());
                    if(load == 0){
                        zoomOut.setEnabled(false);
                    }
                    toolBarBasic.add(zoomOut);
                    toolBarBasic.addSeparator(new Dimension(15,15));
                    break;
            case 5: flipHor = new JButton(toolIcons[i]);
                    flipHor.setToolTipText(etich[i]);
                    flipHor.addActionListener(new ButtonListener());
                    if(load == 0){
                        flipHor.setEnabled(false);
                    }
                    toolBarBasic.add(flipHor);
                    break;
            case 6: flipVer = new JButton(toolIcons[i]);
                    flipVer.setToolTipText(etich[i]);
                    flipVer.addActionListener(new ButtonListener());
                    if(load == 0){
                        flipVer.setEnabled(false);
                    }
                    toolBarBasic.add(flipVer);
                    toolBarBasic.addSeparator(new Dimension(15,15));
                    break;

            case 7: rotleft = new JButton(toolIcons[i]);
                    rotleft.setToolTipText(etich[i]);
                    rotleft.addActionListener(new ButtonListener());
                    if(load == 0){
                        rotleft.setEnabled(false);
                    }
                    toolBarBasic.add(rotleft);
                    break;
            case 8: rotright = new JButton(toolIcons[i]);
                    rotright.setToolTipText(etich[i]);
                    rotright.addActionListener(new ButtonListener());
                    if(load == 0){
                        rotright.setEnabled(false);
                    }
                    toolBarBasic.add(rotright);
                    toolBarBasic.addSeparator(new Dimension(15,15));
                    break;
            case 9: undo = new JButton(toolIcons[i]);
                    undo.setToolTipText(etich[i]);
                    undo.addActionListener(new ButtonListener());
                    if(load == 0){
                        undo.setEnabled(false);
                    }
                    toolBarBasic.add(undo);
                    break;
            case 10:
                    crop = new JButton(toolIcons[i]);
                    crop.setToolTipText(etich[i]);
                    crop.addActionListener(new ButtonListener());
                    if(load == 0){
                        crop.setEnabled(false);
                    }
                    toolBarBasic.add(crop);
                    break;
            case 11:
                    info = new JButton(toolIcons[i]);
                    info.setToolTipText(etich[i]);
                    info.addActionListener(new ButtonListener());
                    if(load == 0){
                        info.setEnabled(false);
                    }
                    toolBarBasic.add(info);
                    toolBarBasic.addSeparator(new Dimension(15,15));
                    break;

        }
    }
    toolBarBasic.setFloatable(false);
    toolBarBasic.setVisible(true);
    toolBarBasic.setBorder(new TitledBorder(" "));
    toolBarBasic.setAutoscrolls(true);


    rollover = new ImageIcon("img/rollover.jpg");
    for (int i = 0; i < buttonLabels.length; i++) {
                    icons[i] = new ImageIcon(namesThumbnails[i]);
                    buttons[i] = new JButton(icons[i]);
                    //buttons[i] = new JButton(buttonLabels[i],icons[i]);
                    buttons[i].setLayout(new BoxLayout(buttons[i],BoxLayout.Y_AXIS));
                    buttons[i].setSize(70,70);
                    buttons[i].setToolTipText(buttonLabels[i]);
                    buttons[i].setRolloverIcon(rollover);
                    buttons[i].addActionListener(new ButtonListener());
                    //toolBar.addSeparator(new Dimension(2,2));
                    //setVerticalAlignment(SwingConstants.VERTICAL);
                    toolBar.add(buttons[i]);
                    //toolBar1.add(buttons[i]);
    }
    Reset = new JButton("Reset");
    Reset.setBounds(0,0,60,30);
    Reset.addActionListener(new ButtonListener());

    home = new JButton(homeicon);
    home.setToolTipText("Home");
    toolBarright.add(home);

    conv = new JButton(convicon);
    conv.setToolTipText("Convert to different format");
    conv.addActionListener(new ButtonListener());
    toolBarright.add(conv);




    //toolBar.setAutoscrolls(rootPaneCheckingEnabled);
    //toolBar.CENTER_ALIGNMENT;
    //toolBar.setBackground(new Color(254,222,252));
    //toolBar.setAlignmentX(CENTER_ALIGNMENT);
    //toolBar.setSize(200, 200);
    right.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
    right.setBorder(new TitledBorder("Edit "));

    panel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));


    panel1 = new JPanel();
    panel1.add(p);
    panel1.add(hslPanel);
    hslPanel.setVisible(false);
    panel1.setVisible(true);


    panel.setBorder(new TitledBorder("RGB adjustment: "));
        bottom.setBackground(Color.lightGray);

    bottom.add(nume_lb,BorderLayout.CENTER);
    bottom.add(rezolutie,BorderLayout.EAST);
      panel1.setBackground(Color.lightGray);
      panel1.setSize(120,150);
      //panel.setBackground(new Color(254,222,252));
      toolBar.setLayout(new GridLayout(4,3, 2, 5));
      toolBar.setFloatable(false);
      toolBar.setSize(140, 300);
      toolBar.setForeground(Color.LIGHT_GRAY);
      toolBar.setBorder(new TitledBorder("Efects: "));

      toolBar1.setBorder(new TitledBorder(" "));

      //toolBar.setLayout(new GridLayout(1,4));rightright
      //toolBar.setLayout(new FlowLayout());

      toolBarright.setFloatable(false);



      //panel.setSize(150, 50);
      right.add(toolBar,BorderLayout.WEST);
      //right.add(toolBar1,BorderLayout.EAST);
      right.add(toolBarright,BorderLayout.NORTH);

      //ippth.setVisible(false);
      right.setVisible(false);
      //panel.add(ippth,BorderLayout.CENTER);
      right.add(panel,BorderLayout.SOUTH);
      panel.setVisible(true);

      //panel.add(toolBarright,BorderLayout.NORTH);
      panel.add(panel1,BorderLayout.CENTER);
      //right.setLayout(new GridLayout(0,1));

      //panel.add(sp, BorderLayout.SOUTH);
      //panel.add(tool_p);
      //panel.add(toolBar);
      //frameContainer.add(toolBar, BorderLayout.EAST);

      frameContainer.add(right,BorderLayout.LINE_END);
      frameContainer.add(toolBarBasic, BorderLayout.PAGE_START);



      //ipp.setVisible(true);
      dt = new DropTarget(ipp, this);//**************************************************
      //ipp.setBackground(new Color(59,59,59));
      //frameContainer.setBackground(Color.BLACK);

      right.setBackground(Color.lightGray);
      //frameContainer.add(right, BorderLayout.EAST);
      frameContainer.add(new JScrollPane(ipp),BorderLayout.CENTER);
         frameContainer.add(bottom,BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fileExit.addActionListener(new MenuItemHandler());



    setSize(1100, 1000);
    setVisible(true);
  }



  public void actionPerformed(ActionEvent evt) {
    Object source = evt.getSource();
    if (source == openItem) {
      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));

      chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
        public boolean accept(File f) {
          String name = f.getName().toLowerCase();
          return name.endsWith(".gif") || name.endsWith(".jpg")
              || name.endsWith(".jpeg") || name.endsWith(".png")
              || name.endsWith(".JPG") || name.endsWith(".bmp")|| name.endsWith(".PNG")|| f.isDirectory();
        }

        public String getDescription() {
          return "Image files";
        }
      });

      int r = chooser.showOpenDialog(this);
      if (r == JFileChooser.APPROVE_OPTION) {
        String name = chooser.getSelectedFile().getAbsolutePath();
                try {
                    ipp.loadImage(name, namesThumbnails[0]);
                    //creareImaginiThumb();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                    mesaj = meta.readAndDisplayMetadata( nume );
                    load = 1;
                    //Reset.setVisible(true);
                    //toolBar.setVisible(true);
                    //right.setVisible(true);
                    rezolutie.setText(img_width+"x"+img_height);
                    nume_lb.setText(name+"       ");
                    ipp.reset();
                    ipp.repaint();

                    if(load == 1){
                            edit.setEnabled(true);
                    save.setEnabled(true);
                    zoomIn.setEnabled(true);
                    zoomOut.setEnabled(true);
                    rotleft.setEnabled(true);
                    rotright.setEnabled(true);
                    info.setEnabled(true);
                    undo.setEnabled(true);
                    crop.setEnabled(true);
                    flipHor.setEnabled(true);
                    flipVer.setEnabled(true);
                    }

      }
    } else if (source == exitItem){
      System.exit(0);
    } else if(source ==toolItem){

        }

    if(source == edit){
      //Reset.setVisible(true);
  }
    if(source == toolItem){
        //toolBarBasic.setVisible(true);
    }

  }

  public void dragEnter(DropTargetDragEvent dtde) {
    System.out.println("Drag Enter");
  }

  public void dragExit(DropTargetEvent dte) {
    System.out.println("Drag Exit");
  }

  public void dragOver(DropTargetDragEvent dtde) {
    System.out.println("Drag Over");
  }

  public void dropActionChanged(DropTargetDragEvent dtde) {
    System.out.println("Drop Action Changed");
  }

private void dumpDataFlavors (Transferable trans) {// *****************   copiat   *****************
        System.out.println ("Flavors:");
        DataFlavor[] flavors = trans.getTransferDataFlavors();
        for (int i=0; i<flavors.length; i++) {
            System.out.println ("*** " + i + ": " + flavors[i]);
        }
    }

public void drop (DropTargetDropEvent dtde) {
    //String nume = "";
        System.out.println ("drop");
        dtde.acceptDrop (DnDConstants.ACTION_COPY_OR_MOVE);
        Transferable trans = dtde.getTransferable();
        System.out.println ("Flavors:");
        dumpDataFlavors (trans);
        boolean gotData = false;
        try {
            // try to get an image
            if (trans.isDataFlavorSupported (DataFlavor.imageFlavor)) {


                System.out.println ("image flavor is supported");
                //Image img = (Image) trans.getTransferData (DataFlavor.imageFlavor);
                nume = (String)trans.getTransferData (DataFlavor.imageFlavor);
                ipp.loadImage(nume,namesThumbnails[0]);
                //creareImaginiThumb();
                mesaj = meta.readAndDisplayMetadata( nume );
                load = 1;
                //Reset.setVisible(true);
                //right.setVisible(true);
                nume_lb.setText(nume+"          ");
                rezolutie.setText(img_width+"x"+img_height);
                ipp.reset();
                ipp.repaint();
                //panel.loadImage(img);
                gotData = true;
            } else if (trans.isDataFlavorSupported (
                              DataFlavor.javaFileListFlavor)) {


                System.out.println ("javaFileList is supported");
                java.util.List list = (java.util.List)
                    trans.getTransferData (DataFlavor.javaFileListFlavor);
                ListIterator it = list.listIterator();
                while (it.hasNext()) {
                    File f = (File) it.next();
                    ipp.loadImage((String)f.getAbsolutePath(),namesThumbnails[0]);
                    //creareImaginiThumb();
                    mesaj = meta.readAndDisplayMetadata( nume );
                    load = 1;
                    rezolutie.setText(img_width+"x"+img_height);
                    nume_lb.setText((String)f.getAbsolutePath()+"          ");
                    ipp.reset();
                    //Reset.setVisible(true);
                    //right.setVisible(true);
                    ipp.repaint();
                    //ImageIcon icon = new ImageIcon (f.getAbsolutePath());
                    //****************showImageInNewFrame (icon);
                }
                gotData = true;
            } else if (trans.isDataFlavorSupported (uriListFlavor)) {
                System.out.println ("uri-list flavor is supported");
                String uris = (String)
                    trans.getTransferData (uriListFlavor);
                // url-lists are defined by rfc 2483 as crlf-delimited
                StringTokenizer izer = new StringTokenizer (uris, "\r\n");
                while (izer.hasMoreTokens ()) {
                    String uri = izer.nextToken();
                    System.out.println (uri);
                    //ImageIcon icon = new ImageIcon (uri);
                      ipp.loadImage((String)uri,namesThumbnails[0]);
                      //creareImaginiThumb();
                      mesaj = meta.readAndDisplayMetadata( nume );
                      load = 1;
                      //Reset.setVisible(true);
                      //right.setVisible(true);
                      rezolutie.setText(img_width+"x"+img_height);
                      ipp.reset();
                      ipp.repaint();
                    //**************showImageInNewFrame (icon);
                }
                gotData = true;
            } else if (trans.isDataFlavorSupported (urlFlavor)) {
                System.out.println ("url flavor is supported");
                //URL url = (URL) trans.getTransferData (urlFlavor);
                //System.out.println (url.toString());

                //ImageIcon icon = new ImageIcon (url);

                ipp.loadImage((String)trans.getTransferData (urlFlavor),namesThumbnails[0]);
                //creareImaginiThumb();
                mesaj = meta.readAndDisplayMetadata( nume );
                load = 1;
                rezolutie.setText(img_width+"x"+img_height);
                nume_lb.setText((String)trans.getTransferData (urlFlavor)+"          ");
                ipp.reset();
                //Reset.setVisible(true);
                //right.setVisible(true);
                ipp.repaint();
                //************
                gotData = true;
            } else if (trans.isDataFlavorSupported (macPictStreamFlavor)) {
                System.out.println ("mac pict stream flavor is supported");
                InputStream in =
                    (InputStream) trans.getTransferData (macPictStreamFlavor);
                // for the benefit of the non-mac crowd, this is
                // done with reflection.  directly, it would be:
                // Image img =  QTJPictHelper.pictStreamToJavaImage (in);
                Class qtjphClass = Class.forName ("QTJPictHelper");
                Class[] methodParamTypes = { java.io.InputStream.class };
                Method method =
                    qtjphClass.getDeclaredMethod ("pictStreamToJavaImage",
                                                  methodParamTypes);
                InputStream[] methodParams = { in };
                Image img = (Image) method.invoke (null, methodParams);

                ipp.loadImage(img);
                mesaj = meta.readAndDisplayMetadata( nume );
                load = 1;
                //Reset.setVisible(true);
                //right.setVisible(true);
                rezolutie.setText(img_width+"x"+img_height);
                ipp.reset();
                ipp.repaint();

                gotData = true;
            }
            if(gotData == true){
                    edit.setEnabled(true);
                    save.setEnabled(true);
                    zoomIn.setEnabled(true);
                    zoomOut.setEnabled(true);
                    rotleft.setEnabled(true);
                    rotright.setEnabled(true);
                    info.setEnabled(true);
                    undo.setEnabled(true);
                    crop.setEnabled(true);
                     flipHor.setEnabled(true);
                    flipVer.setEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println ("gotData is " + gotData);
            dtde.dropComplete (gotData);
        }
}

private void reset() {
    try {
      Thread.sleep(100);
    } catch (Exception e) {}
  }


  class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JButton button = (JButton) e.getSource();

      if (button.equals(buttons[0])) {
                try {

                    ipp.blur();
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
//panel1.setVisible(true);
        panel.setBorder(new TitledBorder("Blur options:"));
      } if (button.equals(buttons[1])) {
        ipp.sharpen();
        //panel1.setVisible(false);
        panel.setBorder(new TitledBorder("Sharpen options:"));
      } else if (button.equals(buttons[2])) {
        ipp.brighten();
        //panel1.setVisible(false);
        panel.setBorder(new TitledBorder("Brighten options:"));
      } else if (button.equals(buttons[3])) {
        ipp.edgeDetect();
        //panel1.setVisible(false);
        panel.setBorder(new TitledBorder("Edge Detection options:"));
      } else if (button.equals(buttons[4])) {
        ipp.negative();

      } else if (button.equals(buttons[5])) {
                try {
                    //ipp.rotate();
                    ipp.sepia(30);
                    //panel1.setVisible(false);
                    panel.setBorder(new TitledBorder("Rotate options:"));
                } catch (Exception ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
      }else if (button.equals(buttons[6])) {
                try {
                    // ipp.bandCombine(ipp.RED_BAND_MATRIX);
                    ipp.grayOut();
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

      }else if (button.equals(buttons[7])) {
                try {
                    ipp.bandCombine(ipp.RED_BAND_MATRIX);
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
      }else if (button.equals(buttons[8])) {
                try {
                    ipp.bandCombine(ipp.GREEN_BAND_MATRIX);
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
      }else if (button.equals(buttons[9])) {
                try {
                    ipp.bandCombine(ipp.BLUE_BAND_MATRIX);
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
      }else if (button.equals(buttons[10])) {
                try {
                    ipp.bandCombine(ipp.INVERSE_BAND_MATRIX);
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
      }else if (button.equals(buttons[11])) {
          //ipp.rotate();
                try {
                    ipp.bandCombine(ipp.AVERAGE_BAND_MATRIX);
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
      }if(button.equals(Reset)){
          ipp.reset();
          ipp.repaint();
      }if(button.equals(conv)){
          if(hslPanel.isVisible() == false){
                hslPanel.setVisible(true);
                p.setVisible(false);
          }else{
              hslPanel.setVisible(false);
              p.setVisible(true);

          }

          //creareImaginiThumb();
      }
      if(button.equals(open)){
        JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));

      chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
        public boolean accept(File f) {
          String name = f.getName().toLowerCase();
          return name.endsWith(".gif") || name.endsWith(".jpg")
              || name.endsWith(".jpeg") || name.endsWith(".png")
              || name.endsWith(".JPG") || name.endsWith(".bmp")|| name.endsWith(".PNG")|| f.isDirectory();
        }

        public String getDescription() {
          return "Image files";
        }
      });

      int r = chooser.showOpenDialog(open);
      if (r == JFileChooser.APPROVE_OPTION) {
        String name = chooser.getSelectedFile().getAbsolutePath();
                try {
                    ipp.loadImage(name,"t0.jpg");
                        try {
                            //creareImaginiThumb();
                        } catch (Exception ex) {
                            Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }


                    mesaj = meta.readAndDisplayMetadata( nume );
                    load = 1;
                    //Reset.setVisible(true);
                    //right.setVisible(true);
                    rezolutie.setText(img_width+"x"+img_height);
                    nume_lb.setText(name+"  ");

                    ipp.reset();
                    ipp.repaint();


                    edit.setEnabled(true);
                    save.setEnabled(true);
                    zoomIn.setEnabled(true);
                    zoomOut.setEnabled(true);
                    rotleft.setEnabled(true);
                    rotright.setEnabled(true);
                    info.setEnabled(true);
                    undo.setEnabled(true);
                    crop.setEnabled(true);
                    flipHor.setEnabled(true);
                    flipVer.setEnabled(true);
                    //panel.createBufferedImage();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
      }

    }

      if(button.equals(zoomOut)){
            ipp.scaleDown(0.9);
          //ipp.zoomIn(0.9);
       }
      if(button.equals(zoomIn)){
           ipp.scaleUp(1.1);
          //ipp.zoomIn(1.1);
       }
      if(button.equals(undo)){
          ipp.reset();
          ipp.repaint();

           //ipp.repaint();
      }
      if(button.equals(save)){
                try {
                   
                    ipp.saveChanges(100);
                    //ipp.loadImage(nume,"t0.jpg");
                    //ipp.loadImage(numeleImaginiiCurente,"t0.jpg");
                } catch (IOException ex) {
                    Logger.getLogger(ImageProcessingFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
      }
      if(button.equals(edit)){
          if(sw ==0){
              sw = 1;
              right.setVisible(true);
          }else{
              sw = 0;
           right.setVisible(false);
          }
       }
      if(button.equals(rotright)){
           ipp.rotate90DX();
       }
      if(button.equals(rotleft)){
           ipp.rotate90SX();
       }
      if(button.equals(flipVer)){
           ipp.flipVer();
       }
      if(button.equals(flipHor)){
           ipp.flipHor();
       }
      if(button.equals(crop)){
          ipp.reset();
          ipp.repaint();
          //JOptionPane.showMessageDialog(null, "Crop!");
          String fFormat=JOptionPane.showInputDialog(null, "Enter output format: (png|bmp|gif|jpg)" ).trim();
          System.out.println("Ai introdus:  "+fFormat);
          String numele=JOptionPane.showInputDialog(null, "Enter output name: " ).trim();
          System.out.println("Ai introdus:  "+numele);
      }
      if(button.equals(info)){


        //int length = args.length;
        //for ( int i = 0; i < length; i++ )


          //JOptionPane.showMessageDialog(null, "Crop!");
          //mesaj = "Calea completa: \n"+nume+"\n\nDimensiunea: \n"+img_width+"x"+img_height;
          JOptionPane.showMessageDialog(null, mesaj);
      }
  }
  }
  public JSlider getSlider(int min, int max, int init, int mjrTkSp, int mnrTkSp) {
    JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(mjrTkSp);
    slider.setMinorTickSpacing(mnrTkSp);
    slider.setPaintLabels(true);
    //slider.addChangeListener(new SliderListener());
    return slider;
  }


 class MenuItemHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String cmd = e.getActionCommand();
      if (cmd.equals("Exit"))
        System.exit(0);
    }
  }
  class SliderListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
        ipp.reset();
      JSlider slider = (JSlider) e.getSource();

      if (slider == sliderAlpha) {
        //canvas.alphaValue = slider.getValue();
        //canvas.setBackgroundColor();
      } else if (slider == sliderR) {
        r = slider.getValue();

      } else if (slider == sliderG) {
        g = slider.getValue();

      } else if (slider == sliderB) {
        b = slider.getValue();

      } else if (slider == sliderH) {
        h = (float) (slider.getValue() * 0.1);

      } else if (slider == sliderS) {
        s = (float) (slider.getValue() * 0.1);

      } else if (slider == sliderBr) {
        l = (float) (slider.getValue() * 0.1);

      }
      //System.out.println(r+", "+g+", "+b);
      ipp.multRGB(r/100,g/100,b/100);
      ipp.repaint();

    }
}
}