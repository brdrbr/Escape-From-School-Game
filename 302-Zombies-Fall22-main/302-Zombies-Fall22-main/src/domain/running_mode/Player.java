package domain.running_mode;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import domain.handler.BuildingModeHandler;
import domain.handler.RunningModeHandler;
import domain.handler.TimerHandler;
import domain.save_load.adapter.DatabaseSaveLoadAdapter;
import domain.save_load.adapter.FileSaveLoadAdapter;
import domain.save_load.adapter.ISaveLoadAdapter;
import ui.RunningModeFrame;

public class Player {

	private static Player instance; // Singleton
	private String username;
	private String password;

	
	private Point image_corner;
	private final int width;
	private final int height;
	private ImageIcon characterImage;
	private ImageIcon characterNormalImage;
	private ImageIcon characterVestImage;
	private ImageIcon characterHitImage;
	private int lifeCount = 3;
	private Random random;
	private ISaveLoadAdapter saveLoadAdapter;
	
	
	private Player() {
		// TODO Auto-generated constructor stub
		
		image_corner = new Point(10, 10);
		random = new Random();
		
		characterNormalImage = new ImageIcon("src/images/character_normal.png");
		characterVestImage = new ImageIcon("src/images/character_vest.png");
		characterHitImage = new ImageIcon("src/images/character_hit.png");
		characterImage = characterNormalImage;
		width = characterNormalImage.getIconWidth();
		height = characterNormalImage.getIconHeight();
		saveLoadAdapter = new FileSaveLoadAdapter();
		
		
	}

	public void switchAdapter(boolean DBToFile){
		if (DBToFile == true ){
			this.saveLoadAdapter = new DatabaseSaveLoadAdapter();
			System.out.print("SaveLoad switched to database\n");
		}
		else{
			this.saveLoadAdapter = new FileSaveLoadAdapter();
			System.out.print("SaveLoad switched to file\n");
		}
		
	}

	//setters
	public void setCharacterPositionX(int positionX) {
		this.image_corner.setLocation(positionX, image_corner.getY());
	}

	public void setCharacterPositionY(int positionY) {
		this.image_corner.setLocation(image_corner.getX(), positionY);
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setLifeCount(int count) {
		this.lifeCount = count;
	}
	public void setImageCorner(Point image_corner){
		this.image_corner = image_corner;
	}
	

	//getters
	public int getCharacterPositionX() {
		return (int) image_corner.getX();
	}

	public int getCharacterPositionY() {
		return (int) image_corner.getY();
	}
	public Point getCharacterImageCorner(){
		return image_corner;
	}
	public int getCharacterWidth(){
		return width;
	}
	public int getCharacterHeigth(){
		return height;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public ImageIcon getCharacterNormalImage(){
		return characterNormalImage;
	}
	public ImageIcon getCharacterVestImage(){
		return characterVestImage;
	}
	public ImageIcon getCharacterHitImage() {
		return characterHitImage;
	}
	public ISaveLoadAdapter getSaveLoadAdapter() {
		return this.saveLoadAdapter;
	}

	public int getLifeCount(){
		return lifeCount;
	}
	public static Player getInstance() { //Singleton
        if (instance == null){
            instance = new Player();
        }
        return instance;
    }
	

	public void incrementLifeCount(){
		this.lifeCount += 1;
		System.out.println(lifeCount);
	}
	public void decrementLifeCount(){
		this.lifeCount -= 1;

		if (lifeCount == 0){
			death();
		}
	}

	public void death(){ //is an illusion

		lifeCount = 0;
		RunningModeFrame.getInstance().getGameArea().repaint();
		RunningModeHandler.getInstance().gameEnd(2);

        
	}

	public void saveGame() {

		try {
		saveLoadAdapter.save(username);
		} catch (IOException e) {
			System.err.println("Unable to save game due to error: " + e);
		}
	}

	public void deleteUser() {

		saveLoadAdapter.delete(username);
	}

	public void setInitialLocation(){
		
		int xCoor;
		int yCoor;
		do {
			xCoor = random.nextInt(RunningModeFrame.getInstance().getGameAreaWidth() - this.width);
			yCoor = random.nextInt(RunningModeFrame.getInstance().getGameAreaHeight() - this.height);
		} while (RunningModeHandler.getInstance().checkCharacterSpawnCollision(xCoor, yCoor, this.width, this.height));
	
		image_corner = new Point(xCoor, yCoor);
		
	}


}
