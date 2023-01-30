package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import domain.handler.ButtonHandler;
import domain.handler.RunningModeHandler;
import domain.power_ups.PowerUp;
import domain.aliens.*;
import domain.building_mode.Building;
import domain.building_mode.Object;
import domain.running_mode.Player;
import domain.handler.BagHandler;
import domain.handler.BuildingModeHandler;
import domain.handler.TimerHandler;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.border.Border;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;  


public class RunningModeFrame{

	private static RunningModeFrame instance; // Singleton pattern
	
	ArrayList<Building> buildings;

	Random random;
	

	private RightPanel right;
	private GameArea middle;
	private BottomPanel bottom;
	
	
	ImageIcon closedDoorImage = new ImageIcon("src/images/door_closed.png");
    ImageIcon openDoorImage = new ImageIcon("src/images/door_open.png");
	public final int DOOR_IMG_WIDTH;
	public final int DOOR_IMG_HEIGHT;

	ImageIcon gameIcon = new ImageIcon("src/images/escape_from_koc.png");

	public int hintXCoor;
	public int hintYCoor;

	Point door_corner;

	JFrame frame;

	private RunningModeFrame() {

		this.buildings = BuildingModeHandler.getInstance().getBuildings();

		frame = new JFrame("Escape From Koc: " + buildings.get(0).getName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 650);
		frame.setLayout(new BorderLayout(10,10));
		frame.setVisible(true);
		
		
		right = new RightPanel();
		middle = new GameArea(right);
		bottom = new BottomPanel(middle);
		

		door_corner = new Point((800 - closedDoorImage.getIconWidth()), 20);
		DOOR_IMG_HEIGHT = closedDoorImage.getIconHeight();
		DOOR_IMG_WIDTH = closedDoorImage.getIconWidth();
		System.out.println("door width: " + DOOR_IMG_WIDTH + " door height: " + DOOR_IMG_HEIGHT);


		random = new Random();


		//frame.setSize(r.width, r.height);
		frame.setResizable(false);
		frame.add(bottom, BorderLayout.SOUTH);
		frame.add(right, BorderLayout.EAST);
		frame.add(middle, BorderLayout.CENTER);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		frame.setVisible(true);
		
	}

	//getters
	public ArrayList<Building> getBuildings(){
		return buildings;
	}
	public static RunningModeFrame getInstance(){ //Singleton pattern
		if (instance == null){
            instance = new RunningModeFrame();
        }
		return instance;
	}

	public int getGameAreaHeight(){
		return middle.getHeight();
	}
	public int getGameAreaWidth(){
		return middle.getWidth();
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
	public GameArea getGameArea(){
		return middle;
	}
	public RightPanel getRightPanel(){
		return right;
	}
	public BottomPanel getBottomPanel() {
		return bottom;
	}
	public int getHintXCoor(){
		return hintXCoor;
	}
	public int getHintYCoor(){
		return hintYCoor;
	}
	public JFrame getFrame(){
		return frame;
	}

	//setters
	public void setHintXCoor(int xCoor){
		hintXCoor = xCoor;
	}
	public void setHintYCoor(int yCoor){
		hintYCoor = yCoor;
	}
	public void setFrameTitle(){
		frame.setTitle("Escape From Koc: " + RunningModeHandler.getInstance().getCurrentBuilding().getName());
	}

	// alerts the player that the game has ended
	//if alertType == 1: time-out (player lose)
	//if alertType == 2: player died (player lose)
	//if alertType == 3: player escaped (player won)
	public void alertGameEnd(int alertType){

		if (alertType == 1) {
			ImageIcon icon = new ImageIcon("src/images/time_waster.png");
			JOptionPane.showMessageDialog(null,"Time is up, you lost!", "Game Over", JOptionPane.INFORMATION_MESSAGE, icon);
		}
		else if (alertType == 2) {
			ImageIcon icon = new ImageIcon("src/images/gravestone.png");
			JOptionPane.showMessageDialog(null,"You have died, you lost!", "Game Over", JOptionPane.INFORMATION_MESSAGE, icon);
		}
		else if (alertType == 3) {
			ImageIcon icon = new ImageIcon("src/images/escape_from_koc.png");
			JOptionPane.showMessageDialog(null,"You have escaped from Koc!", "Game Over", JOptionPane.INFORMATION_MESSAGE, icon);
			
		}

		

	}

	public void gameSaved() {
		ImageIcon icon = new ImageIcon("src/images/escape_from_koc.png");
		JOptionPane.showMessageDialog(null,"Game saved!", "Game saved", JOptionPane.INFORMATION_MESSAGE, icon);
	}


	public class BottomPanel extends JPanel{
		private GameArea game;
		private RightPanel right;
		private HelpButton help;
		private PauseButton pause;
		private QuitButton quit;
		private SaveButton save;

		public BottomPanel(GameArea game) {
			this.game = game;
			pause = new PauseButton(this.game);
			quit = new QuitButton();
			help = new HelpButton(this.game);
			save = new SaveButton();
			add(pause);
			add(quit);
			add(help);
			add(save);

			this.setBackground(Color.gray);

			this.setPreferredSize(new Dimension(80, 80));

		}

		public SaveButton getSaveButton() {
			return this.save;
		}

		public PauseButton getPauseButton() {
			return this.pause;
		}

	}

	public class SaveButton extends JButton implements ActionListener{

		public SaveButton(){
			setText("Save");
			addActionListener(this);
			setEnabled(false);
		}

		@Override
		public void actionPerformed(ActionEvent e)  {

			ButtonHandler.getInstance().pauseGame();
			Player.getInstance().saveGame();
		}
	}

	public class HelpButton extends JButton implements ActionListener{
		
		private GameArea gameArea;
		
		public HelpButton(GameArea gameArea) {
			this.gameArea = gameArea;
			setText("Help");
			addActionListener(this);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (RunningModeHandler.getInstance().getPlay()) {
				System.out.println("Play?" + RunningModeHandler.getInstance().getPlay());
				ButtonHandler.getInstance().pauseGame();
				gameArea.grabFocus();
				
				//RunningModeHandler.showHelpMessage();
				HelpFrame helpFrame = new HelpFrame(true);
				
				//ButtonHandler.getInstance().resumeGame();
				//gameArea.grabFocus();
			} else {

				HelpFrame helpFrame = new HelpFrame(false);
				//RunningModeHandler.showHelpMessage();
			}
	
		}

	}


	public class PauseButton extends JButton implements ActionListener{

		private GameArea gameArea;

		public PauseButton(GameArea gameArea) {
			this.gameArea = gameArea;
			
			setText("Play");
			addActionListener(this);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (RunningModeHandler.getInstance().getPlay()) {
				ButtonHandler.getInstance().pauseGame();
				System.out.println("Play?" + RunningModeHandler.getInstance().getPlay());
				setText("Play");
				gameArea.grabFocus();
			}
			else {
				System.out.println("Play?" + RunningModeHandler.getInstance().getPlay());
				ButtonHandler.getInstance().resumeGame();
				setText("Pause");
				gameArea.grabFocus();
			}

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

	public class RightPanel extends JPanel {
		private int time = 0;
		private boolean play = false;
		public JLabel timerDisplay;
		public JLabel livesDisplay;
		public JLabel hintDisplay;
		public JLabel plasticBottleDisplay;
		public JLabel protectionVestDisplay;

		public JLabel itemText;
		private GridLayout gridLayout;

		public RightPanel() {

			gridLayout = new GridLayout(6, 1);

			setLayout(gridLayout);
			
			livesDisplay = new JLabel("Life Count:" + Player.getInstance().getLifeCount());
			timerDisplay = new JLabel("Time Remaining:" + time);
			itemText = new JLabel("Items:");
			hintDisplay = new JLabel();
			plasticBottleDisplay = new JLabel();
			protectionVestDisplay = new JLabel();

			Border border = BorderFactory.createLineBorder(Color.PINK, 5);
			
			hintDisplay.setBorder(border);
			plasticBottleDisplay.setBorder(border);
			protectionVestDisplay.setBorder(border);

			add(timerDisplay);
			add(livesDisplay);
			add(itemText);
			add(hintDisplay);
			add(plasticBottleDisplay);
			add(protectionVestDisplay);

			this.setBackground(Color.gray);

			this.setPreferredSize(new Dimension(150, 150));

		}

		public void setItemsDisplay(String powerUpName) {

			if (powerUpName.equals("Hint")){
				if (BagHandler.getInstance().getHintCount() == 0) {
					hintDisplay.setText(null);
				} 
				else {
					hintDisplay.setText("Hint: " + BagHandler.getInstance().getHintCount());
				}
			}

			else if (powerUpName.equals("ProtectionVest")){
				if (BagHandler.getInstance().getProtectionVestCount() == 0) {
					protectionVestDisplay.setText(null);
				} 
				else {
					protectionVestDisplay.setText("ProtectionVest: " + BagHandler.getInstance().getProtectionVestCount());
				}
			}

			else if (powerUpName.equals("PlasticBottle")){
				if (BagHandler.getInstance().getPlasticBottleCount() == 0) {
					plasticBottleDisplay.setText(null);
				}
				else {
					plasticBottleDisplay.setText("PlasticBottle: " + BagHandler.getInstance().getPlasticBottleCount());

				}
			}
		}
		public void resetItemDisplay() {

			hintDisplay.setText(null);
			plasticBottleDisplay.setText(null);
			protectionVestDisplay.setText(null);

		}
		
		public void setTime(String timeInfo) {
			timerDisplay.setText(timeInfo);
		}
		public void setLifeCount(){
			livesDisplay.setText("Life Count:" + Player.getInstance().getLifeCount());
		}

	}
	public class GameArea extends JPanel implements KeyListener, ActionListener, MouseListener {

		//private Rectangle r;
		private RightPanel right;

		public GameArea(RightPanel right) {	
			this.right = right;

			setFocusable(true);
			this.setVisible(true);

			addKeyListener(this);
			addMouseListener(this);

			this.setBackground(Color.CYAN);

		}
		

		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			// paint the background
			//g.setColor(Color.CYAN);
			//g.fillRect(1,1,r.width, r.height);

			//if hint powerup is on
			if (RunningModeHandler.getInstance().getShowHint()){
				
				Object keyObject = RunningModeHandler.getInstance().getCurrentBuilding().getObjects().get(RunningModeHandler.getInstance().getCurrentBuilding().getKeyIndex());
				g.setColor(Color.orange);
				g.fillRect(keyObject.getPositionX() - hintXCoor, keyObject.getPositionY() - hintYCoor, 120, 120);
			}

			// paint the player
			if (RunningModeHandler.getInstance().getHasVest()){
				Player.getInstance().getCharacterVestImage().paintIcon(this, g, Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY());
			}
			else if (RunningModeHandler.getInstance().checkIfPlayerHit()){
				Player.getInstance().getCharacterHitImage().paintIcon(this, g, Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY());
			}
			else {
				Player.getInstance().getCharacterNormalImage().paintIcon(this, g, Player.getInstance().getCharacterPositionX(), Player.getInstance().getCharacterPositionY());
			}

			//paint aliens
			for (Alien alien : RunningModeHandler.getInstance().getAliens()){
				if (alien.getIsVisible() == true) {
					alien.getIcon().paintIcon(this, g, alien.getXCoordinate(), alien.getYCoordinate());
				}
			}

			//paint bullets
			if (!RunningModeHandler.getInstance().getBullets().isEmpty()) {
				for (Bullet bullet : RunningModeHandler.getInstance().getBullets()) {
					if (bullet.getIsVisible()) {
						bullet.getBulletIcon().paintIcon(this, g, bullet.getPositionX(), bullet.getPositionY());
					}
				}
			}
			//paint objects
			for (Object obj : RunningModeHandler.getInstance().getCurrentBuilding().getObjects()) { //print all the objects inside the building
				obj.getIcon().paintIcon(this, g, (int) obj.getImageCorner().getX(), (int) obj.getImageCorner().getY());
			}

		
			//paint the door
			door_corner.setLocation((int) middle.getWidth() - closedDoorImage.getIconWidth(), 20);
			if (RunningModeHandler.getInstance().getDoorUnlocked()){
				openDoorImage.paintIcon(this, g, (int) door_corner.getX(), (int) door_corner.getY());
				
				
			} 
			else {
				closedDoorImage.paintIcon(this, g, (int) door_corner.getX(), (int) door_corner.getY());
			}
			
			//paint the powerUp
			if (RunningModeHandler.getInstance().getPowerUpCollectible()){
				//System.out.println(RunningModeHandler.getInstance().getPowerUpCollectible());
				RunningModeHandler.getInstance().getPowerUp().getIcon().paintIcon(this, g, RunningModeHandler.getInstance().getPowerUp().getPositionX(), RunningModeHandler.getInstance().getPowerUp().getPositionY());
			}
			g.dispose();
		}

		

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			if (RunningModeHandler.getInstance().getPlay()) {

				int key = -1;

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					
					RunningModeHandler.getInstance().movePlayerRight();

				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				
					RunningModeHandler.getInstance().movePlayerLeft();
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					
					RunningModeHandler.getInstance().movePlayerUp();
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					
					RunningModeHandler.getInstance().movePlayerDown();
				}
				if (e.getKeyCode() == KeyEvent.VK_H) {
					
					BagHandler.getInstance().castPowerUp(e.getKeyCode());
				}
				if (e.getKeyCode() == KeyEvent.VK_P) {
					
					BagHandler.getInstance().castPowerUp(e.getKeyCode());
				}
				//for plastic bottle
				if (e.getKeyCode() == KeyEvent.VK_B) { // first call to bottle
					
					key = KeyEvent.VK_B;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) { //to bottle : up
					
					BagHandler.getInstance().castPowerUp(e.getKeyCode());
				}
				if (e.getKeyCode() == KeyEvent.VK_X) { //to bottle : down
					
					BagHandler.getInstance().castPowerUp(e.getKeyCode());
				}
				if (e.getKeyCode() == KeyEvent.VK_D) { //to bottle : right
					
					BagHandler.getInstance().castPowerUp(e.getKeyCode());
				}
				if (e.getKeyCode() == KeyEvent.VK_A) { //to bottle : left
					
					BagHandler.getInstance().castPowerUp(e.getKeyCode());
				}

				RunningModeHandler.getInstance().setLastPressedKey(key);
				

				
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (RunningModeHandler.getInstance().getPlay()){
				RunningModeHandler.getInstance().findKey(e.getButton(), e.getPoint());
				if (RunningModeHandler.getInstance().getPowerUpCollectible()){ //if there is a powerup on the building
					RunningModeHandler.getInstance().collectPowerUp(e.getButton(), e.getPoint());
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}


}