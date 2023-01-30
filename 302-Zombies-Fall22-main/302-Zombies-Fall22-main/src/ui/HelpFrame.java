package ui;

import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import domain.handler.ButtonHandler;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;


public class HelpFrame extends JFrame  implements Serializable{
	
    private boolean i;
    private JPanel panel;
	public HelpFrame(Boolean i) {
		// TODO Auto-generated constructor stub

		this.i = i;
		this.setTitle("Description");
		this.setSize(1000, 650);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		panel = new JPanel();

		//Create a Scrollbar
		JScrollPane scrollBar=new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.setBackground(Color.white);
        JLabel label = new JLabel("BRIEF INTRODUCTION");
		JTextArea text = new JTextArea(" Escape from KOC is an easy-to-play game that combines fun and "
				 + " challenge. The game space takes place on the KOC university campus "
				 + " where a student is trying to find a sequence of keys in the "
				 + "campus buildings. The game starts when the player enters one of the "
				 + "buildings and starts looking for a key in different rooms. During "
				 + " that journey, aliens may show up and try to catch the player, who "
				 + " should try to escape or distract them. The player is aiming at "
				 + "finding the key before the timeout. To accomplish that, some hints "
				 + "show up here and there. Once the key is found, the building will "
				 + "be marked as complete and the player can choose the next open "
				 + "building, which is basically the next level. Some promotions can "
				 + "be offered, like adding more time. The game is over If the player "
				 + "fails to find the key within the time limit. If he manages to find all "
				 + "the keys, then he wins the game.");

		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		panel.add(label);
		panel.add(text);
        JLabel label2 = new JLabel("PLAYER ACTIONS");
		panel.add(label2);
	
		JTextArea text2 = new JTextArea("The player walks around using the arrow keys."
				+ " He/she can only open the exit door of a building if he/she finds the key. "
				+ "The game starts from the Student Center. Finding the keys one by one, the player's aim"
				+ " is to travel to these buildings in the given order: CASE building, SOS building, SCI building,"
				+ " ENG building and SNA building. Once the player finds the exit key from the SNA building,"
				+ " the game ends and the player wins. To find the keys, the player uses a left click on the objects"
				+ " with the mouse and if the key is there, it appears for a second and then, the door is opened."
				+ " However, to click the objects, the player should be next to the objects. Player has a bag to collect"
				+ " the power ups and keep them for later use.");
		text2.setEditable(false);
		text2.setLineWrap(true);
		text2.setWrapStyleWord(true);
		panel.add(text2);
		
	    JLabel label5 = new JLabel("BUILDING MODE");
	    panel.add(label5);
	    
	    JTextArea text13 = new JTextArea("There must be at least 5 objects in the Student Center.\r\n"
	    		+ "There must be at least 7 objects in the CASE building.\r\n"
	    		+ "There must be at least 10 objects in the SOS building.\r\n"
	    		+ "There must be at least 14 objects in the SCI building.\r\n"
	    		+ "There must be at least 19 objects in the ENG building.\r\n"
	    		+ "There must be at least 25 objects in the SNA building.");
		text13.setEditable(false);
		text13.setLineWrap(true);
		text13.setWrapStyleWord(true);
		panel.add(text13);
		
		JLabel label3 = new JLabel("ALIENS");
		panel.add(label3);
		JTextArea text3 = new JTextArea("While walking around, the player faces some aliens who try to kill the player"
				+ " or prevent her/him from finding the keys. All of the aliens appear randomly in the buildings every"
				+ " 10 seconds and the type of the appearing aliens are again selected randomly. The alien stays in the"
				+ " building and cannot go to the next building. So, when the player finds the key and goes to the next"
				+ " building, the alien does not follow him/her.");
		text3.setEditable(false);
		text3.setLineWrap(true);
		text3.setWrapStyleWord(true);
		panel.add(text3);

		panel.add(new JLabel(new ImageIcon("src/images/shooting_alien.png")));
		JTextArea text4 = new JTextArea("Shooter alien: This type of alien appears in a random location in the building"
				+ " and shoots a bullet every second. The player has three lives at the"
				+ " beginning of the game. He/she can collect some extra lives during the game. "
				+ "Also, if the player wears a protection vest, then he/she can get close to the shooter"
				+ " alien without losing a life.");
		text4.setEditable(false);
		text4.setLineWrap(true);
		text4.setWrapStyleWord(true);
		panel.add(text4);
		
	    panel.add(new JLabel(new ImageIcon("src/images/blind_alien.png")));
		JTextArea text5 = new JTextArea("Blind alien: This type of alien also tries to kill the player. However, in order to kill him/her,"
				+ " the alien must be right next to the player. It cannot see the "
				+ "player. He randomly walks around. However, this alien is sensitive to the voices. When the player has"
				+ " the plastic bottle power-up, if she/he throws the bottle, he/she can fool the alien. For example, "
				+ "if the player throws the bottle to the opposite direction where he/she will go, then the alien will go"
				+ " in the bottle�s direction.");
		text5.setEditable(false);
		text5.setLineWrap(true);
		text4.setWrapStyleWord(true);
		panel.add(text5);
		
		
		panel.add(new JLabel(new ImageIcon("src/images/time_waster.png")));
		JTextArea text6 = new JTextArea("Time-wasting alien: This type of alien does not kill the player but it changes the"
				+ " location of the key.");
		text6.setEditable(false);
		text6.setLineWrap(true);
		text6.setWrapStyleWord(true);
		panel.add(text6);
		JLabel label4 = new JLabel("POWER-UPS");
		panel.add(label4);
		JTextArea text7 = new JTextArea("Power-ups help the player escape from the aliens and find the keys easily."
				+ " Each power-up appears randomly every 12 seconds in the random locations. They disappear"
				+ " if the player does not collect them in 6 seconds. To collect the power-ups, the player needs to right"
				+ " click with the mouse to the power-ups. However, unlike clicking the regular objects, the player is not "
				+ "required to be next to a power-up to collect it. Except the extra time and extra life power-ups, once"
				+ " collected, they can be stored in the player�s bag for later use. Extra time power-up automatically "
				+ "adds some extra time to the player�s timer.");
		text7.setEditable(false);
		text7.setLineWrap(true);
		text7.setWrapStyleWord(true);
		panel.add(text7);
		
		panel.add(new JLabel(new ImageIcon("src/images/extra_time.png")));
		JTextArea text8 = new JTextArea("Extra time power-up: The game starts with a building mode where the"
				+ " player designs inside of the buildings. In each building, there are minimum criteria to"
				+ " fulfill. The time limit in each building is 5 seconds for each"
				+ " object present in the building. When "
				+ "the user collects an extra time power-up extra 5 seconds are added to the player�s timer.");
		text8.setEditable(false);
		text8.setLineWrap(true);
		text8.setWrapStyleWord(true);
		panel.add(text8);
		
		
		panel.add(new JLabel(new ImageIcon("src/images/hint.png")));
		JTextArea text9 = new JTextArea("Hint: This power-up gives a hint about the location of the key to the player."
				+ " Once collected, it goes to the player�s bag. The player can use it as soon as he/she has it or can "
				+ "save it for the next levels. To use this power up, the player hits the H button on the keyboard. "
				+ "Then, the hint"
				+ " shows a small region containing the key. The highlight/border appears for 10 seconds and then"
				+ " disappears.");
		text9.setEditable(false);
		text9.setLineWrap(true);
		text9.setWrapStyleWord(true);
		panel.add(text9);
		
		panel.add(new JLabel(new ImageIcon("src/images/protection_vest.png")));
		JTextArea text10 = new JTextArea("Protection vest: This power-up protects the player from being shot by the"
				+ " shooter alien. To use this power up, the player clicks the P button on the keyboard. Like hint,"
				+ " the player can use this power-up as soon as he/she has it or can save it for later use. When"
				+ " the player wears it, its protection lasts for 20 seconds. ");
		text10.setEditable(false);
		text10.setLineWrap(true);
		text10.setWrapStyleWord(true);
		panel.add(text10);
		
		panel.add(new JLabel(new ImageIcon("src/images/plastic_bottle.png")));
		JTextArea text11 = new JTextArea("Plastic bottle: This power up is used to fool the blind alien."
				+ " To use it, the player clicks the B button and then one of the following buttons A, D, W, or X to "
				+ "decide on the bottle�s direction. A: west, D: east, W: north, X:south.");
		text11.setEditable(false);
		text11.setLineWrap(true);
		text11.setWrapStyleWord(true);
		panel.add(text11);
		
		panel.add(new JLabel(new ImageIcon("src/images/extra_life.png")));
		JTextArea text12 = new JTextArea("Extra life: This power-up adds one extra life to the player�s lives."
				+ " Like extra time power-up, the addition of the extra life is automatic.");
		text12.setEditable(false);
		text12.setLineWrap(true);
		text12.setWrapStyleWord(true);
		panel.add(text12);
		
		
	
		
	    addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	         if(i) {

               ButtonHandler.getInstance().resumeGame();
               RunningModeFrame.getInstance().getGameArea().grabFocus();
	         }
	          
	        }
	      });
	    
		add(scrollBar);				   
		this.setVisible(true);
	}
	
	
	



}