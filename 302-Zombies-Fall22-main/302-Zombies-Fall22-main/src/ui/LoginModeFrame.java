package ui;
import javax.swing.*;
import domain.main.Controller;
import domain.running_mode.Player;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

//import domain.buttons.HelpButton;
import domain.handler.*;
	
	
	public class LoginModeFrame extends JFrame implements ActionListener, Serializable {
		 
		// Class Variables
		private static JLabel label;
		private static JLabel message_label;
		private static JLabel image_label;
		private static JLabel label_password;
		private static JTextField username;
		private static JPasswordField password;
		private static JButton button;
		private static JButton help_button;
		private static JToggleButton saveLoadButton;

		private static ImageIcon gameIcon;
		

		public static LoginModeFrame instance; //Singleton



		private LoginModeFrame() {
		// TODO Auto-generated constructor stub
		
		// creating a JPanel class
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		// JFrame class
		this.setTitle("ESCAPE FROM KOC: LOGIN PAGE");
		this.setLocation(new Point(500, 300));
		this.add(panel);
		this.setSize(new Dimension(420, 420));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//image label constructor
		gameIcon = new ImageIcon("src/images/escape_from_koc.png");
		image_label = new JLabel(gameIcon);
		image_label.setBounds(140, 50, 141, 70);
		panel.add(image_label);
		
		// Username label constructor
		label = new JLabel("Username");
		label.setBounds(50,150,75,25);
		panel.add(label);
		
		// Username TextField constructor
		username = new JTextField();
		username.setBounds(125,150 ,200, 25);
		panel.add(username);

		// Password label constructor
		label_password = new JLabel("Password");
		label_password.setBounds(50, 225, 75, 25);
		panel.add(label_password);


		// password JPasswordField constructor
		password = new JPasswordField();
		password.setBounds(125, 225, 200, 25);
	    panel.add(password);
				
		// Button constructor
		button = new JButton("Login");
		button.setBounds(125, 275, 100, 25);
		button.addActionListener(this);
		button.setFocusable(false);
		panel.add(button);
		
		help_button = new JButton("Help");
		help_button.setBounds(225, 275, 100, 25);
		help_button.addActionListener(this);
		help_button.setFocusable(false);
		panel.add(help_button);

		saveLoadButton = new JToggleButton("File Save");
		saveLoadButton.setBounds(150, 325, 150, 25);
		saveLoadButton.addActionListener(this);
		saveLoadButton.setFocusable(false);
		panel.add(saveLoadButton);

		message_label = new JLabel();
		message_label.setBounds(100,325,225,100);
		message_label.setFont(new Font(null,Font.ITALIC,12));
		panel.add(message_label);
		this.setVisible(true);

		
		
	}

	public static LoginModeFrame getInstance(){
		if (instance == null){
			instance = new LoginModeFrame();
		}
		return instance;
	}
	
	
	// Imlementing an action event listener class with conditional statement
	@Override
	public void actionPerformed(ActionEvent e) {
	 
		
		if (e.getSource() == button) {
			
			String userID = username.getText();
			String pass = String.valueOf(password.getPassword());

			 if (userID.isEmpty()){

				   message_label.setForeground(Color.red);
				   message_label.setText("The username field cannot be left blank!");

			 }
			 else if (pass.isEmpty()) {

				   message_label.setForeground(Color.red);
				   message_label.setText("The password field cannot be left blank!");

			  }
			  else {

				LoginModeHandler.getInstance().logIn(userID, pass);

				/*
				message_label.setForeground(Color.green);
				message_label.setText("Login Successful");	  
				Controller.StartBuildingMode();	
				*/			  	  
			  }	
		}
		
		if (e.getSource() == help_button) {
		   
			HelpFrame helpFrame = new HelpFrame(false);
			
	    }

		if(saveLoadButton.isSelected()){
			Player.getInstance().switchAdapter(true);
			saveLoadButton.setText("Database Save");

		}
		else{
			Player.getInstance().switchAdapter(false);
			saveLoadButton.setText("File Save");
		}
	}

	
	//if alertType == 1, player succesfully logs in, new game (not exist)
	//if alertType == 2, player successfully logs in, existing game
	//if alertType == 3, player is asked to re-enter username-password
	public void alertPlayer(int alertType){

		if (alertType == 1) {
			
			JOptionPane.showMessageDialog(null,"Login Successful, creating new game!", "Log in", JOptionPane.INFORMATION_MESSAGE, gameIcon);
		}
		else if (alertType == 2) {

			JOptionPane.showMessageDialog(null,"Login successful, starting an already existing game", "Log in", JOptionPane.INFORMATION_MESSAGE, gameIcon);
		}
		else if (alertType == 3) {
			JOptionPane.showMessageDialog(null,"Username-password combination does not match! please check and try again", "Log in", JOptionPane.INFORMATION_MESSAGE, gameIcon);

			message_label.setForeground(Color.red);
			message_label.setText("please try again");
		}

	}
	
	
	public void disposeMethod() {
		dispose();
	}
}
