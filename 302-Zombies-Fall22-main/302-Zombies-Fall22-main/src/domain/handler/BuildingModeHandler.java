package domain.handler;

import java.util.ArrayList;



import ui.BuildingModeFrame;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import domain.building_mode.Building;
import domain.building_mode.Object;
import domain.factory.ObjectFactory;
import domain.main.Controller;

public class BuildingModeHandler implements Serializable{

    private static BuildingModeHandler instance;
    ArrayList<Building> buildings;//
    int currentBuildingIndex = 0;//
    Building currentBuilding;//

    int currentObjCount = 0;//

    //pressed object related
    boolean pressedOnObj = false;//
    Object pressedObject;//

    Point createdObjectAdder_corner;

    private BuildingModeHandler(){


        instance = this;

        buildings = new ArrayList<Building>();

        Building omer = new Building("Student Center", 5); //5
        Building kase = new Building("CASE Building", 7); //7
        Building sos = new Building("SOS Building", 10); //10
        Building sci = new Building("SCI Building", 14); //14
        Building eng = new Building("ENG Building", 19); //19
        Building sna = new Building("SNA Building", 25); //25

        buildings.add(omer);
        buildings.add(kase);
        buildings.add(sos);
        buildings.add(sci);
        buildings.add(eng);
        buildings.add(sna);

        currentBuilding = buildings.get(currentBuildingIndex);

        BuildingModeFrame.getInstance().setTitle(currentBuilding.getName()+ ": "+ BuildingModeFrame.getInstance().getUsername() +" please put "+ currentBuilding.getObjCount()+ " objects.");


    }



    //getters
    public int getCurrentObjCount(){
        return currentObjCount;
    }
    public Building getCurrentBuilding(){
        return currentBuilding;
    }
    public int getCurrentBuildingIndex(){
        return currentBuildingIndex;
    }
    public ArrayList<Building> getBuildings(){
        return buildings;
    }
    public Object getPressedObject(){
        return pressedObject;
    }
    public boolean getPressedOnObject(){
        return pressedOnObj;
    }
    public static BuildingModeHandler getInstance() {
        if (instance == null){
            instance = new BuildingModeHandler();
        }
        return instance;
    }

    //checks if there are any problems with the submission request
    public void checkSubmission(){
        if (currentObjCount > currentBuilding.getObjCount()) { //obj count is more than the required amount
            BuildingModeFrame.getInstance().submissionAlert(1);
        }
        else if (currentObjCount < currentBuilding.getObjCount()) { //obj count is less than the required amount
            BuildingModeFrame.getInstance().submissionAlert(2);
        }
        else if (checkIfObjectsCollide()) { //objects collide with each other
            BuildingModeFrame.getInstance().submissionAlert(3);
        }
        else if (checkObjectsNearDoorOrPlayer() == 1) { //there are objects too close to the door
            BuildingModeFrame.getInstance().submissionAlert(4);
        }
        else if (checkObjectsNearDoorOrPlayer() == 2) { //there are objects too close to the charcter
            BuildingModeFrame.getInstance().submissionAlert(5);
        }
        
        else { //submision is correct
            submitCurrentBuilding();
        }
    }

    //when submit button is pressed
    public void submitCurrentBuilding(){

            
        if (!currentBuilding.getName().equals("SNA Building")){ //enter to the next building
            System.out.println(currentBuilding.getName() + " is saved");
            currentBuilding.printObjects();
            currentBuildingIndex += 1;
            currentBuilding = buildings.get(currentBuildingIndex);
            currentObjCount = 0;
            BuildingModeFrame.getInstance().setTitle(currentBuilding.getName()+ ": "+ BuildingModeFrame.getInstance().getUsername() +" please put "+ currentBuilding.getObjCount()+ " objects.");
            BuildingModeFrame.getInstance().repaint();
        }
        else{ // enter running mode
            Controller.StartRunningMode();
        }
            
      
    }

    //when adding an object
    public void addObjectToBuilding(Point click, int button){
        //check if an object from the object pool is pressed
        if (button == MouseEvent.BUTTON1) {

            //add chair object
            if (checkIfPressedOnObj(BuildingModeFrame.getInstance().getXCoordinateChairAdder(), BuildingModeFrame.getInstance().getYCoordinateChairAdder(), BuildingModeFrame.getInstance().getChairImage().getIconWidth(), BuildingModeFrame.getInstance().getChairImage().getIconHeight(), click)){
                
                ObjectFactory.getInstance().spawnObject("Chair");
                createdObjectAdder_corner = BuildingModeFrame.getInstance().getChairAdderCorner();
                currentObjCount++;
                
        
             
            }
            //add shelf object
            else if (checkIfPressedOnObj(BuildingModeFrame.getInstance().getXCoordinateShelfAdder(), BuildingModeFrame.getInstance().getYCoordinateShelfAdder(), BuildingModeFrame.getInstance().getShelfImage().getIconWidth(), BuildingModeFrame.getInstance().getShelfImage().getIconHeight(), click)){
                
                ObjectFactory.getInstance().spawnObject("Shelf");
                createdObjectAdder_corner = BuildingModeFrame.getInstance().getShelfAdderCorner();
                currentObjCount++;
                
            
            }
            //add shelf2 object
            else if (checkIfPressedOnObj(BuildingModeFrame.getInstance().getXCoordinateShelf2Adder(), BuildingModeFrame.getInstance().getYCoordinateShelf2Adder(), BuildingModeFrame.getInstance().getShelf2Image().getIconWidth(), BuildingModeFrame.getInstance().getShelf2Image().getIconHeight(), click)){
                
                ObjectFactory.getInstance().spawnObject("Shelf2");
                createdObjectAdder_corner = BuildingModeFrame.getInstance().getShelf2AdderCorner();
                currentObjCount++;
               
            
            }
            //add sofa object
            else if (checkIfPressedOnObj(BuildingModeFrame.getInstance().getXCoordinateSofaAdder(), BuildingModeFrame.getInstance().getYCoordinateSofaAdder(), BuildingModeFrame.getInstance().getSofaImage().getIconWidth(), BuildingModeFrame.getInstance().getSofaImage().getIconHeight(), click)){
                
                ObjectFactory.getInstance().spawnObject("Sofa");
                createdObjectAdder_corner = BuildingModeFrame.getInstance().getSofaAdderCorner();
                currentObjCount++;
                
     
            }

            BuildingModeFrame.getInstance().giveInfoToPlayer();
            
        }
    }

   public void pressOnExistingObject(Point click, int button){
    for (Object obj : currentBuilding.getObjects()){//for each object inside the building, check if any of them is pressed
        if ((checkIfPressedOnObj((int) obj.getImageCorner().getX(), (int) obj.getImageCorner().getY(), obj.getWidth(), obj.getHeight(), click))) {
            if (button == MouseEvent.BUTTON1){
                obj.setPreviousPoint(click);
                pressedOnObj = true;
                pressedObject = obj;
                break;
            }
            else {
         
                //System.out.println("Object deleted");
                currentBuilding.removeObject(obj);
                currentObjCount--;
                BuildingModeFrame.getInstance().giveInfoToPlayer();
                BuildingModeFrame.getInstance().repaint();
                break;
            }
        }
    }
   }

   public void dragObject(Point click){
    if (pressedOnObj) {
        //if object is in the bounds of the building
        if ((new Rectangle (0, 0, BuildingModeFrame.getInstance().getPanelWidth() - BuildingModeFrame.getInstance().getPanelWidthBuffer(), BuildingModeFrame.getInstance().getPanelHeight()).contains(pressedObject.getImageCorner().getX(), pressedObject.getImageCorner().getY(), pressedObject.getWidth(), pressedObject.getHeight()))){
            Point currentPoint = click;
            pressedObject.getImageCorner().translate(
                (int) (currentPoint.getX() - pressedObject.getPreviousPoint().getX()),
                (int) (currentPoint.getY() - pressedObject.getPreviousPoint().getY()));
            pressedObject.setPreviousPoint(currentPoint);
        }
        
        else {
            //if obj in left side
            if (pressedObject.getImageCorner().getX() < 0){ 
                pressedObject.getImageCorner().setLocation(0, pressedObject.getImageCorner().getY());
            }
            //if obj in upside
            if (pressedObject.getImageCorner().getY() < 0){ 
                pressedObject.getImageCorner().setLocation(pressedObject.getImageCorner().getX(), 0);
            }
            //if obj in right side
            if (pressedObject.getImageCorner().getX() + pressedObject.getWidth() > BuildingModeFrame.getInstance().getPanelWidth() - BuildingModeFrame.getInstance().getPanelWidthBuffer()){ //width of the panel
                pressedObject.getImageCorner().setLocation(BuildingModeFrame.getInstance().getPanelWidth() - pressedObject.getWidth() - BuildingModeFrame.getInstance().getPanelWidthBuffer(), pressedObject.getImageCorner().getY());
            }
            //if obj in downside
            if (pressedObject.getImageCorner().getY() + pressedObject.getHeight() >= BuildingModeFrame.getInstance().getPanelHeight()) {// height of the panel
                pressedObject.getImageCorner().setLocation(pressedObject.getImageCorner().getX(), BuildingModeFrame.getInstance().getPanelHeight() - pressedObject.getHeight());
            }
        }

        BuildingModeFrame.getInstance().repaint();
    }
   }

   public void releaseObject(){
    if (pressedOnObj) {
        if (checkIfReleasedOnAdder(pressedObject.getImageCorner())) {
            currentBuilding.removeObject(pressedObject);
            currentObjCount--;
        }
        else {
            pressedObject.printObject();
        }
        
        BuildingModeFrame.getInstance().giveInfoToPlayer();
        pressedOnObj = false;
    }
   }
    

    public boolean checkIfObjectsCollide(){
        for (Object obj1 : currentBuilding.getObjects()){
            for (Object obj2 : currentBuilding.getObjects()){
                if (obj1.equals(obj2)) continue;
                if (checkCollision((int) obj1.getImageCorner().getX(), (int) obj1.getImageCorner().getY(), obj1.getWidth(), obj1.getHeight(),
                (int) obj2.getImageCorner().getX(), (int) obj2.getImageCorner().getY(), obj2.getWidth(), obj2.getHeight())){
                    return true;
                }
                
            }
        }

        return false;
    }

    //checks if any of the objects is too close to the door
    public boolean checkObjectsNearDoor(){
        boolean flag = false;
        int near = 50; //area near the door that should not contain any object

        for (Object object : currentBuilding.getObjects()) {
            if (checkCollision(object.getPositionX(), object.getPositionY(), object.getWidth(), object.getHeight(), (int) BuildingModeFrame.getInstance().getDoorCorner().getX(), (int) BuildingModeFrame.getInstance().getDoorCorner().getY(), BuildingModeFrame.getInstance().getDoorWidth() + near, BuildingModeFrame.getInstance().getDoorHeight() + near)){
                flag = true;
                break;
            }
        }

        return flag;
    }

    //returns 0 if object is not near the door nor the player
    //returns 1 if object is near the door
    //returns 2 if object is near the player
    public int checkObjectsNearDoorOrPlayer(){
        int flag = 0;
        int near = 50; //area near the door that should not contain any object

        for (Object object : currentBuilding.getObjects()) { //an object is near the door
            if (checkCollision(object.getPositionX(), object.getPositionY(), object.getWidth(), object.getHeight(), (int) BuildingModeFrame.getInstance().getDoorCorner().getX(), (int) BuildingModeFrame.getInstance().getDoorCorner().getY(), BuildingModeFrame.getInstance().getDoorWidth() + near, BuildingModeFrame.getInstance().getDoorHeight() + near)){
                flag = 1;
                break;
            }
            else if (checkCollision(object.getPositionX(), object.getPositionY(), object.getWidth(), object.getHeight(), (int) BuildingModeFrame.getInstance().getCharacterCorner().getX(), (int) BuildingModeFrame.getInstance().getCharacterCorner().getY(), BuildingModeFrame.getInstance().getCharacterWidth() + near, BuildingModeFrame.getInstance().getCharacterHeight() + near)){
                flag = 2;
                break;
            }

        }

        return flag;
    }


    //checks if two rectangular areas collide with each other
    public boolean checkCollision(int xCoor1, int yCoor1, int width1, int height1, int xCoor2, int yCoor2, int width2, int height2){
        Rectangle rect1 = new Rectangle(xCoor1, yCoor1, width1, height1);
        Rectangle rect2 = new Rectangle(xCoor2, yCoor2, width2, height2);
        return rect1.intersects(rect2);
    }

    //checks if the click is on the given retangular area
    public boolean checkIfPressedOnObj(int xCoor, int yCoor, int width, int height, Point click){
        return (new Rectangle(xCoor, yCoor, width, height).contains(click.getX(), click.getY()));
    }

    public boolean checkIfReleasedOnAdder(Point image_corner){
        return (
            (new Rectangle(BuildingModeFrame.getInstance().getXCoordinateChairAdder(), BuildingModeFrame.getInstance().getYCoordinateChairAdder(), BuildingModeFrame.getInstance().getChairImage().getIconWidth(), BuildingModeFrame.getInstance().getChairImage().getIconHeight()).contains(image_corner)) ||
            (new Rectangle(BuildingModeFrame.getInstance().getXCoordinateShelfAdder(), BuildingModeFrame.getInstance().getYCoordinateShelfAdder(), BuildingModeFrame.getInstance().getShelfImage().getIconWidth(), BuildingModeFrame.getInstance().getShelfImage().getIconHeight()).contains(image_corner)) ||
            (new Rectangle(BuildingModeFrame.getInstance().getXCoordinateShelf2Adder(), BuildingModeFrame.getInstance().getYCoordinateShelf2Adder(), BuildingModeFrame.getInstance().getShelf2Image().getIconWidth(), BuildingModeFrame.getInstance().getShelf2Image().getIconHeight()).contains(image_corner)) ||
            (new Rectangle(BuildingModeFrame.getInstance().getXCoordinateSofaAdder(), BuildingModeFrame.getInstance().getYCoordinateSofaAdder(), BuildingModeFrame.getInstance().getSofaImage().getIconWidth(), BuildingModeFrame.getInstance().getSofaImage().getIconHeight()).contains(image_corner))
        );
    }
    
    /*
    public void giveInfoToPlayer() {
    	
    	 if((currentBuilding.getObjCount() - currentObjCount) < 0) {
         	BuildingModeFrame.getInstance().setTitle(currentBuilding.getName()+ ": "+ BuildingModeFrame.getInstance().getUsername() +" please delete "+ (currentObjCount - currentBuilding.getObjCount()) + " object(s).");
         }else if (((currentBuilding.getObjCount() - currentObjCount) > 0)){
             BuildingModeFrame.getInstance().setTitle(currentBuilding.getName()+ ": "+ BuildingModeFrame.getInstance().getUsername() +" please put "+ (currentBuilding.getObjCount() - currentObjCount) + " more object(s).");
         }else {
         	BuildingModeFrame.getInstance().setTitle(currentBuilding.getName()+ ": "+ BuildingModeFrame.getInstance().getUsername() +" you can continue.");
         }
    	
    }
    */

   











}


