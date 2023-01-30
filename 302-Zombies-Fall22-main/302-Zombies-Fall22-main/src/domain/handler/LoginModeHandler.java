package domain.handler;



import java.io.Serializable;

import domain.main.Controller;
import domain.running_mode.Player;
import domain.save_load.adapter.DatabaseSaveLoadAdapter;
import ui.LoginModeFrame;

public class LoginModeHandler implements Serializable{

    private static LoginModeHandler instance;


    private LoginModeHandler() {
       
    }

    public static LoginModeHandler getInstance(){
        if (instance == null) {
            instance = new LoginModeHandler();
        }
        return instance;
    }

    /*
    public void setUsernamePassword(String username, String password){
        this.usernameZ = username;
        this.passwordZ = password;
    }
    */

    public void startNewGame(String username, String password){
        Player.getInstance().setUsername(username);
        Player.getInstance().setPassword(password);
        LoginModeFrame.getInstance().alertPlayer(1);
        Controller.StartBuildingMode();
    }

    public void wrongPassword(){
        LoginModeFrame.getInstance().alertPlayer(3);
    }

    public void logIn(String username, String password){
        Player.getInstance().getSaveLoadAdapter().checkIfUserExists(username, password);
    }

    /*
    public void checkIfUserExists(String username, String password) {
       

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("MyDatabase");
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println("Connected successfully to server.");
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }

            MongoCollection<Document> gameCollection = database.getCollection("Game");

            
            Bson userNameExistsComparison = eq("username", username);
            Bson passwordComparison = eq("password", password);

            Document doc = gameCollection.find(userNameExistsComparison).first();
            if (doc == null) { // username does not exist in the database!

                //building mode starts, new user!
                Player.getInstance().setUsername(username);
                Player.getInstance().setPassword(password);
                //alert player: loginsuccess!
                LoginModeFrame.getInstance().alertPlayer(1);
                Controller.StartBuildingMode();
            }
            else { //username exists in the database
                String docPwd = doc.getString("password");
                if (docPwd.equals(password)){ // passwords match!
                    LoginModeFrame.getInstance().alertPlayer(2);
                    //retrieve data
                    Player.getInstance().setUsername(username);
                    Player.getInstance().setPassword(password);
                }

                else { //wrong password

                    //alert player that username is wrong, re-enter!
                    LoginModeFrame.getInstance().alertPlayer(3);

                }
            }

            

            



        }


    }

    */
    
}
