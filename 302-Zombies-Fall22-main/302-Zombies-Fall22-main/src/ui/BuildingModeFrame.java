package ui;

import domain.building_mode.Building;
import domain.building_mode.Object;
import domain.handler.BuildingModeHandler;
import domain.running_mode.Player;
import domain.handler.ButtonHandler;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.*;
//import java.util.*;

public class BuildingModeFrame extends JFrame implements Serializable{

    private static BuildingModeFrame instance;

    private BuildingPanel image_panel;
    private BottomPanel bottom_panel;

    private static String username;

    final int panelWidth;
    final int panelHeight;
    final int panelWidthBuffer = 160; //buffer for the object adders
    
    ImageIcon chairImage = new ImageIcon("src/images/chair.png");
    ImageIcon shelfImage = new ImageIcon("src/images/shelf.png");
    ImageIcon shelf2Image = new ImageIcon("src/images/shelf2.png");
    ImageIcon sofaImage = new ImageIcon("src/images/sofa.png");
    ImageIcon closedDoorIcon = new ImageIcon("src/images/door_closed.png");
    ImageIcon characterIcon = new ImageIcon("src/images/character_normal.png");
    ImageIcon gameIcon = new ImageIcon("src/images/escape_from_koc.png");

    private final int DOOR_IMG_WIDTH;
	private final int DOOR_IMG_HEIGHT;
    private Point door_corner;
    private Point character_corner;
    private final int CHARACTER_IMG_WIDTH;
	private final int CHARACTER_IMG_HEIGHT;

     

 
    //coordinates of the objects inside object pool
    private final int xCoordinateChairAdder;
    private final int yCoordinateChairAdder = 100;
    private final int xCoordinateShelfAdder;
    private final int yCoordinateShelfAdder = 200;
    private final int xCoordinateShelf2Adder;
    private final int yCoordinateShelf2Adder = 300;
    private final int xCoordinateSofaAdder;
    private final int yCoordinateSofaAdder = 400;

     
     
 
     //points that specify the object adders
     Point chairAdderCorner;
     Point shelfAdderCorner;
     Point shelf2AdderCorner;
     Point sofaAdderCorner;
 
    
    

        private BuildingModeFrame(){

        instance = this;
        this.username = Player.getInstance().getUsername();

        
        this.setTitle("Building Mode");
        
        this.setLocationRelativeTo(null);
        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10,10));
        this.setIconImage(gameIcon.getImage());

        bottom_panel = new BottomPanel();

        image_panel = new BuildingPanel();
        image_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 5));



        
        


        ClickListener clicklistener = new ClickListener();
        image_panel.addMouseListener(clicklistener);

        DragListener draglistener = new DragListener();
        image_panel.addMouseMotionListener(draglistener);

        ReleaseListener releaselistener = new ReleaseListener();
        image_panel.addMouseListener(releaselistener);


        this.add(bottom_panel, BorderLayout.SOUTH);
        this.add(image_panel, BorderLayout.CENTER);
        
        

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panelWidth = (int) screenSize.getWidth();
        panelHeight = (int) screenSize.getHeight();

        System.out.println("height: " + panelHeight + " width: " + panelWidth);

        xCoordinateChairAdder = panelWidth - 100;
        xCoordinateShelfAdder = panelWidth - 100;
        xCoordinateShelf2Adder = panelWidth - 100;
        xCoordinateSofaAdder = panelWidth - 100;

        chairAdderCorner = new Point(xCoordinateChairAdder, yCoordinateChairAdder);
        shelfAdderCorner = new Point(xCoordinateShelfAdder, yCoordinateShelfAdder);
        shelf2AdderCorner = new Point(xCoordinateShelf2Adder, yCoordinateShelf2Adder);
        sofaAdderCorner = new Point(xCoordinateSofaAdder, yCoordinateSofaAdder);

        door_corner = new Point((800 - closedDoorIcon.getIconWidth()), 20);
		DOOR_IMG_HEIGHT = closedDoorIcon.getIconHeight();
		DOOR_IMG_WIDTH = closedDoorIcon.getIconWidth();

        character_corner = new Point(10, 10);
        CHARACTER_IMG_HEIGHT = characterIcon.getIconHeight();
        CHARACTER_IMG_WIDTH = characterIcon.getIconWidth();


        

        
        this.setVisible(true);
        this.setResizable(false);
    }

    //getters
    public int getXCoordinateChairAdder(){
        return xCoordinateChairAdder;
    }
    public int getYCoordinateChairAdder(){
        return yCoordinateChairAdder;
    }
    public int getXCoordinateShelfAdder(){
        return xCoordinateShelfAdder;
    }
    public int getYCoordinateShelfAdder(){
        return yCoordinateShelfAdder;
    }
    public int getXCoordinateShelf2Adder(){
        return xCoordinateShelf2Adder;
    }
    public int getYCoordinateShelf2Adder(){
        return yCoordinateShelf2Adder;
    }
    public int getXCoordinateSofaAdder(){
        return xCoordinateSofaAdder;
    }
    public int getYCoordinateSofaAdder(){
        return yCoordinateSofaAdder;
    }
    
    public Point getChairAdderCorner(){
        return chairAdderCorner;
    }
    public Point getShelfAdderCorner(){
        return shelfAdderCorner;
    }
    public Point getShelf2AdderCorner(){
        return shelf2AdderCorner;
    }
    public Point getSofaAdderCorner(){
        return sofaAdderCorner;
    }
    public Point getDoorCorner(){
		return door_corner;
	}
	public int getDoorWidth(){
		return DOOR_IMG_WIDTH;
	}
	public int getDoorHeight(){
		return DOOR_IMG_HEIGHT;
	}
    public Point getCharacterCorner(){
        return character_corner;
    }
    public int getCharacterWidth(){
        return CHARACTER_IMG_WIDTH;
    }
    public int getCharacterHeight(){
        return CHARACTER_IMG_HEIGHT;
    }

    public String getUsername(){
        return username;
    }

    public static BuildingModeFrame getInstance() {
        if (instance == null){
            instance = new BuildingModeFrame();
        }
        return instance;
    }

    public ImageIcon getChairImage(){
        return chairImage;
    }
    public ImageIcon getShelfImage(){
        return shelfImage;
    }
    public ImageIcon getSofaImage(){
        return sofaImage;
    }
    public ImageIcon getShelf2Image(){
        return shelf2Image;
    }
    public int getPanelWidth(){
        return image_panel.getWidth();
    }
    public int getPanelHeight(){
        return image_panel.getHeight();
    }

    public int getPanelWidthBuffer(){
        return panelWidthBuffer;
    }

    //raises different alerts if there are problems in the submission request
    //if alertType == 1: obj count is more than the required amount
    //if alertType == 2: obj count is less than the required amount
    //if alertType == 3: objects collide with each other
    //if alertType == 4: there are objects too close to the door
    public void submissionAlert(int alertType){
        if (alertType == 1) {
            JOptionPane.showMessageDialog(null, getUsername()+ " you have extra " + (BuildingModeHandler.getInstance().getCurrentObjCount() - BuildingModeHandler.getInstance().getCurrentBuilding().getObjCount()) + " object(s). Please delete them to continue!", "Cannot submit", JOptionPane.INFORMATION_MESSAGE, gameIcon);
        }
        else if (alertType == 2) {
            JOptionPane.showMessageDialog(null, getUsername()+ " you need " + (BuildingModeHandler.getInstance().getCurrentBuilding().getObjCount() - BuildingModeHandler.getInstance().getCurrentObjCount())+ " more object(s) to continue!", "Cannot submit", JOptionPane.INFORMATION_MESSAGE, gameIcon);
        }
        else if (alertType == 3) {
            JOptionPane.showMessageDialog(null, "Objects collide!", "Cannot submit", JOptionPane.INFORMATION_MESSAGE, gameIcon);
        }
        else if (alertType == 4) {
            JOptionPane.showMessageDialog(null, "There are objects too close to the door!", "Cannot submit", JOptionPane.INFORMATION_MESSAGE, closedDoorIcon);
        }
        else if (alertType == 5) {
            JOptionPane.showMessageDialog(null, "There are objects too close to the character!", "Cannot submit", JOptionPane.INFORMATION_MESSAGE, characterIcon);
        }
    }

    public void giveInfoToPlayer() {
    	
        if((BuildingModeHandler.getInstance().getCurrentBuilding().getObjCount() - BuildingModeHandler.getInstance().getCurrentObjCount()) < 0) {
            setTitle(BuildingModeHandler.getInstance().getCurrentBuilding().getName() + ": " + getUsername() + " please delete " + (BuildingModeHandler.getInstance().getCurrentObjCount() - BuildingModeHandler.getInstance().getCurrentBuilding().getObjCount()) + " object(s).");
        }
        else if (((BuildingModeHandler.getInstance().getCurrentBuilding().getObjCount() - BuildingModeHandler.getInstance().getCurrentObjCount()) > 0)){
            setTitle(BuildingModeHandler.getInstance().getCurrentBuilding().getName() + ": " + getUsername() + " please put " + (BuildingModeHandler.getInstance().getCurrentBuilding().getObjCount() - BuildingModeHandler.getInstance().getCurrentObjCount()) + " more object(s).");
        }
        else {
            setTitle(BuildingModeHandler.getInstance().getCurrentBuilding().getName() + ": " + getUsername() + " you can continue.");
        }
       
   }
    

   

    private class ClickListener extends MouseAdapter{

        public void mousePressed(MouseEvent e){
            //check if an object from the object pool is pressed
            BuildingModeHandler.getInstance().addObjectToBuilding(e.getPoint(), e.getButton());

            BuildingModeHandler.getInstance().pressOnExistingObject(e.getPoint(), e.getButton());

        }

    }

    private class DragListener extends MouseMotionAdapter{
        
        public void mouseDragged(MouseEvent e){
            BuildingModeHandler.getInstance().dragObject(e.getPoint());
        }
    }

    private class ReleaseListener extends MouseAdapter{

        public void mouseReleased(MouseEvent e){
            BuildingModeHandler.getInstance().releaseObject();
        }
    }

    public void disposeMethod() {
		dispose();
	}

    public class BottomPanel extends JPanel{
        private HelpButton help;
        private QuitButton quit;
        private JButton nextBtn;

        public BottomPanel() {
			
			quit = new QuitButton();
			help = new HelpButton();

            nextBtn = new JButton("submit");
            nextBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BuildingModeHandler.getInstance().checkSubmission();
                
            }
            });
			
            add(nextBtn);
			add(quit);
			add(help);
            

			this.setBackground(Color.gray);

			this.setPreferredSize(new Dimension(80, 80));

		}
    }

    public class HelpButton extends JButton implements ActionListener {

        public HelpButton() {
			
			setText("Help");
			addActionListener(this);

		}

       @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            HelpFrame helpFrame = new HelpFrame(false);
        }
    }

    public class QuitButton extends JButton implements ActionListener {

		public QuitButton() {
			setText("Quit");
			addActionListener(this);

		}

		public void quitGame() {
			ButtonHandler.getInstance().quitGame();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			quitGame();
		}

	}



    

    public class BuildingPanel extends JPanel{

        public BuildingPanel(){
        }

            public void paintComponent(Graphics g){

        
                super.paintComponent(g);
        
                //paint addders
                g.setColor(Color.GRAY);
                g.fillRect(panelWidth - panelWidthBuffer, 0, panelWidthBuffer, panelHeight);

                
                chairImage.paintIcon(image_panel, g, xCoordinateChairAdder, yCoordinateChairAdder);
                shelfImage.paintIcon(image_panel, g, xCoordinateShelfAdder, yCoordinateShelfAdder);
                shelf2Image.paintIcon(image_panel, g, xCoordinateShelf2Adder, yCoordinateShelf2Adder);
                sofaImage.paintIcon(image_panel, g, xCoordinateSofaAdder, yCoordinateSofaAdder);

                //paint door
                door_corner.setLocation((int) image_panel.getWidth() - panelWidthBuffer - closedDoorIcon.getIconWidth(), 20);
                closedDoorIcon.paintIcon(this, g, (int) door_corner.getX(), (int) door_corner.getY());
                
                //paint character
                characterIcon.paintIcon(this, g, (int) character_corner.getX(), (int) character_corner.getY());
        
                //paint currently existing objects
                for (Object obj : BuildingModeHandler.getInstance().getCurrentBuilding().getObjects()) { //paint all the objects inside the building
                    obj.getIcon().paintIcon(image_panel, g, (int) obj.getImageCorner().getX(), (int) obj.getImageCorner().getY());
                }
                
        
            }
        

    }
    
}
