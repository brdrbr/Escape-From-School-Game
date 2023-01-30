package domain.handler;

import domain.aliens.Bullet;
import domain.building_mode.Building;
import domain.running_mode.Player;
import domain.building_mode.Object;
import ui.RunningModeFrame;
import domain.power_ups.PowerUp;
import domain.aliens.Alien;
import domain.power_ups.PowerUp;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class RunningModeHandler implements Serializable{

    
    private static RunningModeHandler instance; //Singleton pattern
    private boolean play = false;
    ArrayList<Building> buildings;

    ArrayList<Alien> aliens;
    ArrayList<Bullet> bullets;
    int lastPressedKey; //holds the int value of the last pressed key. This is for the plastic bottle powerUp
    PowerUp powerUp;
    
    public Building currentBuilding;
    //public int currentBuildinKeyIndex;
    public int currentBuildingIndex = 0;
    public int dx = 10;
    public int dy = 10;
    
    public boolean doorUnlocked = false;
    public boolean powerUpCollectible = false;
    public boolean powerUpCollected = false;
    public boolean showHint = false; //for hint powerUp
    public boolean hasVest = false; //for protectionVest powerUp
    Random random;
    
    

    private RunningModeHandler() {
        instance = this;
        this.buildings = RunningModeFrame.getInstance().getBuildings();
        this.aliens = new ArrayList<Alien>();
        this.bullets = new ArrayList<Bullet>();
        this.currentBuilding = buildings.get(currentBuildingIndex);

        this.lastPressedKey = -1; //no key pressed yet

        random = new Random();
        
        


        //setting up the keys
        for (Building building : buildings){
            if (!building.getObjects().isEmpty()) {
                building.setKeyIndex(random.nextInt(building.getObjCount()));
                Object keyObject = building.getObjects().get(building.getKeyIndex());
                System.out.println(building.getName() + " has key at: " + keyObject.getName());
            }
        }

        spawnPlayer(); //set initial location for the player

    }


    //getters:
    public static RunningModeHandler getInstance() {
        if (instance == null){
            instance = new RunningModeHandler();
        }
        return instance;
    }
    public boolean getPlay() {
        return play;
    }
    public int getCurrentBuildingIndex(){
        return currentBuildingIndex;
    }
    public Building getCurrentBuilding(){
        return currentBuilding;
    }
    public Boolean getDoorUnlocked(){
        return doorUnlocked;
    }
    public ArrayList<Alien> getAliens(){
        return aliens;
    }
    public int getLastPressedKey(){
        return lastPressedKey;
    }
    public PowerUp getPowerUp(){
        return powerUp;
    }
    public Boolean getPowerUpCollectible(){
        return powerUpCollectible;
    }
    public Boolean getPowerUpCollected(){
        return powerUpCollected;
    }
    public Boolean getShowHint(){
        return showHint;
    }
    public Boolean getHasVest(){
        return hasVest;
    }
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
    public ArrayList<Building> getBuildings(){
        return buildings;
    }

    public void addAlien(Alien alien){
        alien.setLocation();
        alien.setDestinationCorner(alien.getImageCorner());
        aliens.add(alien);
        TimerHandler.getInstance().addAlienTimer();
        
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        //System.out.println("bullet added and its location x: " + bullet.getPositionX() + "location y: " + bullet.getPositionY());
    }


    //setters:
    public void setPlay(boolean newPlay) {
        play = newPlay;
    }

    public void spawnPlayer() {
        Player.getInstance().setInitialLocation();
    }

    public void setCurrentBuildingIndex(int newCurrentBuildingIndex) {
        currentBuildingIndex = newCurrentBuildingIndex;
    }

    public void setCurrentBuilding(int newCurrentBuildingIndex) {
        currentBuilding = buildings.get(newCurrentBuildingIndex);
    }
    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }
    public void setAliens(ArrayList<Alien> aliens) {
        this.aliens = aliens;
    }
    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void setDoorUnlocked(Boolean isLocked){
        this.doorUnlocked = isLocked;
    }
    public void setPowerUp(PowerUp powerUp){
        this.powerUp = powerUp;
        powerUp.setLocation();
        setPowerUpCollectible(true);
    }
    public void setPowerUpDirect(PowerUp powerUp) {
        this.powerUp = powerUp;
    }
    public void setPowerUpCollectible(boolean powerUpCollectible) {
        this.powerUpCollectible = powerUpCollectible;
    }
    public void setPowerUpCollected(boolean powerUpCollected){
        this.powerUpCollected = powerUpCollected;
    }
    public void setShowHint(boolean showHint){
        this.showHint = showHint;
    }
    public void setHasVest(boolean hasVest){
        this.hasVest = hasVest;
    }
    public void setLastPressedKey(int key){
        lastPressedKey = key;
    }
    public void resetAliens(){
        this.aliens = new ArrayList<Alien>();
    }
    public void resetBullets() {this.bullets = new ArrayList<Bullet>(); }

    
    
    public void setHintArea(){
        int xCoor = random.nextInt(120 - 53);
		int yCoor = random.nextInt(120 - 53);
        RunningModeFrame.getInstance().setHintXCoor(xCoor);
        RunningModeFrame.getInstance().setHintYCoor(yCoor);
    }


    // player movement methods:

    public void movePlayerRight() {
        if (Player.getInstance().getCharacterPositionX() + Player.getInstance().getCharacterWidth() >= RunningModeFrame.getInstance().getGameAreaWidth()) {
            Player.getInstance().setCharacterPositionX(RunningModeFrame.getInstance().getGameAreaWidth() - Player.getInstance().getCharacterWidth());
        } 
        else {
            if (checkMovementCollisionWithObjects("right")){ //theres an object on the rightside of the character
                Player.getInstance().setCharacterPositionX(Player.getInstance().getCharacterPositionX());
            }
            else {
                Player.getInstance().setCharacterPositionX(Player.getInstance().getCharacterPositionX() + dx);
            }
        }

        checkEscape();
    }

    public void movePlayerLeft() {
        if (Player.getInstance().getCharacterPositionX() <= 0) {
            Player.getInstance().setCharacterPositionX(0);
        } 
        else {
            if (checkMovementCollisionWithObjects("left")){ //theres an object on the leftside of the character
                Player.getInstance().setCharacterPositionX(Player.getInstance().getCharacterPositionX());
            }
            else {
                Player.getInstance().setCharacterPositionX(Player.getInstance().getCharacterPositionX() - dx);
            }
        }

        checkEscape();
    }

    public void movePlayerUp() {
        if (Player.getInstance().getCharacterPositionY() <= 0) {
            Player.getInstance().setCharacterPositionY(0);
        } 
        else {
            if (checkMovementCollisionWithObjects("up")){ //theres an object on the upside of the character
                Player.getInstance().setCharacterPositionY(Player.getInstance().getCharacterPositionY());
            }
            else {
                Player.getInstance().setCharacterPositionY(Player.getInstance().getCharacterPositionY() - dy);
            }
        }

        checkEscape();
    }

    public void movePlayerDown() {
        if (Player.getInstance().getCharacterPositionY() + Player.getInstance().getCharacterHeigth() >= RunningModeFrame.getInstance().getGameAreaHeight()) {
            Player.getInstance().setCharacterPositionY(RunningModeFrame.getInstance().getGameAreaHeight() - Player.getInstance().getCharacterHeigth());
        } 
        else {
            if (checkMovementCollisionWithObjects("down")){    
                Player.getInstance().setCharacterPositionY(Player.getInstance().getCharacterPositionY());
            }
            else {
                Player.getInstance().setCharacterPositionY(Player.getInstance().getCharacterPositionY() + dy);
            }
        }

        checkEscape();
    }

    public boolean checkCollision(int xCoor1, int yCoor1, int width1, int height1, int xCoor2, int yCoor2, int width2, int height2){
        Rectangle rect1 = new Rectangle(xCoor1, yCoor1, width1, height1);
        Rectangle rect2 = new Rectangle(xCoor2, yCoor2, width2, height2);
        return rect1.intersects(rect2);
    }

    public boolean checkIfClickOnArea(int xCoor, int yCoor, int width, int height, Point click){
        return (new Rectangle(xCoor, yCoor, width, height).contains(click.getX(), click.getY()));
    }

    public boolean checkIfNextTo(int xCoor, int yCoor, int width, int height){

        return (
            (checkCollision(Player.getInstance().getCharacterPositionX() + dx, Player.getInstance().getCharacterPositionY(), Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), xCoor, yCoor, width, height)) ||
            (checkCollision(Player.getInstance().getCharacterPositionX() - dx, Player.getInstance().getCharacterPositionY(), Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), xCoor, yCoor, width, height)) ||
            (checkCollision(Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY() + dy, Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), xCoor, yCoor, width, height)) ||
            (checkCollision(Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY() - dy, Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), xCoor, yCoor, width, height))
            );
    }

    public boolean checkIfBulletCollision(int xCoor, int yCoor, int width, int height) {

        return (
                checkCollision(Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY(), Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), xCoor, yCoor, width, height));
    }

    public boolean checkIfPlayerHit(){
        boolean flag = false;

        for (Bullet bullet : bullets) {
            if (checkIfBulletCollision(bullet.getPositionX(), bullet.getPositionY(), bullet.getWidth(), bullet.getHeight())) { //one of the bullets collide w/ player
                flag = true;
                break;
            }
        }

        return flag;
    }

    public boolean checkMovementCollisionWithObjects(String movement){
        boolean flag = false;

        //for objects
        for (Object obj : getCurrentBuilding().getObjects()){
            if (movement.equals("right")){
                if (checkCollision(Player.getInstance().getCharacterPositionX() + dx, Player.getInstance().getCharacterPositionY(), Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), obj.getPositionX(), obj.getPositionY(), obj.getWidth(), obj.getHeight())){
                    flag = true;
                    break;
                }
            }
            if (movement.equals("left")){
                if (checkCollision(Player.getInstance().getCharacterPositionX() - dx, Player.getInstance().getCharacterPositionY(), Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), obj.getPositionX(), obj.getPositionY(), obj.getWidth(), obj.getHeight())){
                    flag = true;
                    break;
                }
            }
            if (movement.equals("down")){
                if (checkCollision(Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY() + dy, Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), obj.getPositionX(), obj.getPositionY(), obj.getWidth(), obj.getHeight())){
                    flag = true;
                    break;
                }
            }
            if (movement.equals("up")){
                if (checkCollision(Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY() - dy, Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), obj.getPositionX(), obj.getPositionY(), obj.getWidth(), obj.getHeight())){
                    flag = true;
                    break;
                }
            } 
        }

        //for the door
        if (movement.equals("right")){
            if (checkCollision(Player.getInstance().getCharacterPositionX() + dx, Player.getInstance().getCharacterPositionY(), Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), (int) RunningModeFrame.getInstance().getDoorCorner().getX(), (int) RunningModeFrame.getInstance().getDoorCorner().getY(), RunningModeFrame.getInstance().getDoorWidth(), RunningModeFrame.getInstance().getDoorHeight())){
                flag = true;
            }
        }
        if (movement.equals("left")){
            if (checkCollision(Player.getInstance().getCharacterPositionX() - dx, Player.getInstance().getCharacterPositionY(), Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), (int) RunningModeFrame.getInstance().getDoorCorner().getX(), (int) RunningModeFrame.getInstance().getDoorCorner().getY(), RunningModeFrame.getInstance().getDoorWidth(), RunningModeFrame.getInstance().getDoorHeight())){
                flag = true;
            }
        }
        if (movement.equals("down")){
            if (checkCollision(Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY() + dy, Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), (int) RunningModeFrame.getInstance().getDoorCorner().getX(), (int) RunningModeFrame.getInstance().getDoorCorner().getY(), RunningModeFrame.getInstance().getDoorWidth(), RunningModeFrame.getInstance().getDoorHeight())){
                flag = true;
            }
        }
        if (movement.equals("up")){
            if (checkCollision(Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY() - dy, Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth(), (int) RunningModeFrame.getInstance().getDoorCorner().getX(), (int) RunningModeFrame.getInstance().getDoorCorner().getY(), RunningModeFrame.getInstance().getDoorWidth(), RunningModeFrame.getInstance().getDoorHeight())){
                flag = true;
            }
        } 
        
        return flag;
    }

    public boolean checkSpawnCollision(int xCoor, int yCoor, int width, int height){
        boolean flag = false;

        for (Object obj : currentBuilding.getObjects()){ //if collide with an object
            if (checkCollision(xCoor, yCoor, width, height, obj.getPositionX(), obj.getPositionY(), obj.getWidth(), obj.getHeight())){
                flag = true;
                break;
            }
        }
        if (checkCollision(xCoor, yCoor, width, height, Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY(), Player.getInstance().getCharacterWidth(), Player.getInstance().getCharacterHeigth())){ //if collide with the player
            flag = true;
        }
        
        if (checkCollision(xCoor, yCoor, width, height, (int) RunningModeFrame.getInstance().getDoorCorner().getX(), (int) RunningModeFrame.getInstance().getDoorCorner().getY(), RunningModeFrame.getInstance().getDoorWidth(), RunningModeFrame.getInstance().getDoorHeight())){ //if collide with the
            flag = true;
        }
        return flag;
    }

    public boolean checkCharacterSpawnCollision(int xCoor, int yCoor, int width, int height){
        boolean flag = false;

        for (Object obj : currentBuilding.getObjects()){ //if collide with an object
            if (checkCollision(xCoor, yCoor, width, height, obj.getPositionX(), obj.getPositionY(), obj.getWidth(), obj.getHeight())){
                flag = true;
                break;
            }
        }
        
        if (checkCollision(xCoor, yCoor, width, height, (int) RunningModeFrame.getInstance().getDoorCorner().getX(), (int) RunningModeFrame.getInstance().getDoorCorner().getY(), RunningModeFrame.getInstance().getDoorWidth(), RunningModeFrame.getInstance().getDoorHeight())){ //if collide with the
            flag = true;
        }
        return flag;
    }

    public void checkEscape(){
        Point door_corner = RunningModeFrame.getInstance().getDoorCorner();
        int doorHeight = RunningModeFrame.getInstance().getDoorHeight();
        int doorWidth = RunningModeFrame.getInstance().getDoorWidth();
        if (getPlay() && getDoorUnlocked()){
            if (checkIfNextTo((int) door_corner.getX(), (int) door_corner.getY(), doorWidth, doorHeight)){ //character next to open door
                escape();
            }
        }
    }

    public void collectPowerUp(int button, Point click){
        if (checkIfClickOnArea(powerUp.getPositionX(), powerUp.getPositionY(), powerUp.getWidth(), powerUp.getHeight(), click)){
            if (button == MouseEvent.BUTTON2 || button == MouseEvent.BUTTON3){
                BagHandler.getInstance().addToBag(powerUp); //add powerUp to bag or cast it if powerUp is autocast
                setPowerUpCollectible(false); //powerUp disappears
                setPowerUpCollected(true); //this will be needed in the TimerHandler
            }
        }
    }


    public void findKey(int button, Point click){
        Object keyObject = currentBuilding.getObjects().get(currentBuilding.getKeyIndex()); //get the object that has the key
        if (checkIfClickOnArea(keyObject.getPositionX(), keyObject.getPositionY(), keyObject.getWidth(), keyObject.getHeight(), click) && checkIfNextTo(keyObject.getPositionX(), keyObject.getPositionY(), keyObject.getWidth(), keyObject.getHeight())) {
            if (button == MouseEvent.BUTTON1){
                System.out.println("!!!!KEY FOUND!!!!!");
                setDoorUnlocked(true);
                System.out.println("door unlocked status: " + getDoorUnlocked());
            }
        }
    }

    public void escape(){ //escape the current building
        if (getCurrentBuildingIndex() < buildings.size() - 1) {
            setCurrentBuildingIndex(getCurrentBuildingIndex() + 1);
            setCurrentBuilding(getCurrentBuildingIndex());

            resetFrame();

            RunningModeFrame.getInstance().getGameArea().grabFocus();

        	TimerHandler.getInstance().cancel();

    		TimerHandler.getInstance().resetDuration();
			if (getPlay()) {
				TimerHandler.getInstance().start();
			}
        }
        else { //escaped from SNA
            
            
            gameEnd(3);
            
            
        }
    }

    public void gameEnd(int alertType) {
       
        setPlay(false);
        TimerHandler.getInstance().cancel();
        RunningModeFrame.getInstance().alertGameEnd(alertType);
        RunningModeFrame.getInstance().getBottomPanel().getPauseButton().setEnabled(false);
        RunningModeFrame.getInstance().getBottomPanel().getSaveButton().setEnabled(false);
        Player.getInstance().deleteUser();
    }

    public void resetFrame(){
        Player.getInstance().setCharacterPositionX(10);
        Player.getInstance().setCharacterPositionY(10);
        setDoorUnlocked(false);
        setPowerUpCollectible(false);
        setPowerUpCollected(false);
        setShowHint(false);
        setHasVest(false);
        resetAliens();
        resetBullets();
        BagHandler.getInstance().resetBag();
        RunningModeFrame.getInstance().setFrameTitle();
    }

}
