package domain.save_load.adapter;


import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Base64.Decoder;

import javax.swing.ImageIcon;

import org.bson.BsonReader;
import org.bson.Document;
import org.bson.json.JsonReader;
import org.bson.codecs.DecoderContext;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.BsonReader;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.awt.Point;

import com.mongodb.DBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
//import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;


// ensure you use the following static imports above your class definition
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static com.mongodb.client.model.Filters.eq;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import domain.building_mode.Building;
import domain.building_mode.Object;
import domain.handler.*;
import domain.main.Controller;
import domain.aliens.*;
import domain.aliens.attack.*;
import domain.aliens.move.*;

import domain.aliens.attack.*;
import domain.power_ups.*;
import domain.power_ups.job.AddProtection;
import domain.power_ups.job.GainLife;
import domain.power_ups.job.GainTime;
import domain.power_ups.job.GiveHint;
import domain.power_ups.job.IJobBehavior;
import domain.power_ups.job.ThrowPlasticBottle;
import domain.running_mode.*;
import domain.save_load.codec.AlienCodec;
import domain.save_load.codec.AttackBehaviorCodec;
import domain.save_load.codec.BuildingCodec;
import domain.save_load.codec.ImageIconCodec;
import domain.save_load.codec.JobBehaviorCodec;
import domain.save_load.codec.MoveBehaviorCodec;
import domain.save_load.codec.ObjectCodec;
import domain.save_load.codec.PointCodec;
import domain.save_load.codec.PowerUpCodec;
import domain.aliens.Alien;
import domain.aliens.BlindAlien;
import domain.aliens.Bullet;
import domain.power_ups.ExtraLife;
import domain.power_ups.ExtraTime;
import domain.power_ups.Hint;
import domain.aliens.attack.IAttackBehavior;
import domain.aliens.move.IMoveBehavior;
import domain.aliens.attack.IndecisiveWaste;
import domain.aliens.attack.MeleeAttack;
import domain.aliens.move.MoveToSound;
import domain.aliens.move.NoMove;
import domain.aliens.attack.NoWaste;
import domain.power_ups.PlasticBottle;
import domain.power_ups.PowerUp;
import domain.power_ups.ProtectionVest;
import domain.aliens.move.RandomMove;
import domain.aliens.attack.RangedAttack;
import domain.aliens.ShooterAlien;
import domain.aliens.TimeWastingAlien;
import domain.aliens.attack.WasteTime;
import ui.BuildingModeFrame;
import ui.LoginModeFrame;
import ui.RunningModeFrame;

public class FileSaveLoadAdapter implements ISaveLoadAdapter{
    public FileSaveLoadAdapter(){}

    

    

    @Override
    public void save(String username) throws IOException {
        // TODO Auto-generated method stub

    	//set codec stuff
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry());
        
        Codec<ImageIcon> imageIconCodec = new ImageIconCodec(pojoCodecRegistry);
        Codec<Point> pointCodec = new PointCodec(pojoCodecRegistry);
        Codec<IJobBehavior> jobBehaviorCodec = new JobBehaviorCodec(pojoCodecRegistry);
        Codec<IMoveBehavior> moveBehaviorCodec = new MoveBehaviorCodec(pojoCodecRegistry);
        Codec<IAttackBehavior> attackBehaviorCodec = new AttackBehaviorCodec(pojoCodecRegistry);
        


        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), CodecRegistries.fromCodecs(imageIconCodec, pointCodec, jobBehaviorCodec, moveBehaviorCodec, attackBehaviorCodec), fromProviders(
            PojoCodecProvider.builder().register(Object.class, Building.class, 
            Alien.class, BlindAlien.class, ShooterAlien.class, TimeWastingAlien.class, Bullet.class, 
            IAttackBehavior.class, IndecisiveWaste.class, MeleeAttack.class, NoWaste.class, RangedAttack.class, WasteTime.class,
            IMoveBehavior.class, MoveToSound.class, NoMove.class, RandomMove.class,
            PowerUp.class, PlasticBottle.class, ProtectionVest.class, Hint.class, ExtraTime.class, ExtraLife.class,
            IJobBehavior.class ,AddProtection.class, GiveHint.class, ThrowPlasticBottle.class, GainLife.class, GainTime.class,
            ImageIcon.class, Point.class).build()));
    	
    	Document userDocument = new Document();

        //Player class related
        userDocument.put("username", Player.getInstance().getUsername());
        userDocument.put("password", Player.getInstance().getPassword());
        userDocument.put("image_corner", Player.getInstance().getCharacterImageCorner());
        userDocument.put("lifeCount", Player.getInstance().getLifeCount());
        

        //RunningModeFrame class Related
        userDocument.put("hintXCoor", RunningModeFrame.getInstance().getHintXCoor());
        userDocument.put("hintYCoor", RunningModeFrame.getInstance().getHintYCoor());

        //RunningModeHandler class Related
        userDocument.put("play", RunningModeHandler.getInstance().getPlay());
        userDocument.put("doorUnlocked", RunningModeHandler.getInstance().getDoorUnlocked());
        userDocument.put("powerUpCollectible", RunningModeHandler.getInstance().getPowerUpCollectible());
        userDocument.put("powerUpCollected", RunningModeHandler.getInstance().getPowerUpCollected());
        userDocument.put("showHint", RunningModeHandler.getInstance().getShowHint());
        userDocument.put("hasVest", RunningModeHandler.getInstance().getHasVest());

        userDocument.put("lastPressedKey", RunningModeHandler.getInstance().getLastPressedKey());
        userDocument.put("powerUp", RunningModeHandler.getInstance().getPowerUp());

        //building related
        userDocument.put("currentBuilding", RunningModeHandler.getInstance().getCurrentBuilding());
        userDocument.put("currentBuildingIndex", RunningModeHandler.getInstance().getCurrentBuildingIndex());
        userDocument.put("buildings", RunningModeHandler.getInstance().getBuildings());

        //alien related
        userDocument.put("aliens", RunningModeHandler.getInstance().getAliens());
        userDocument.put("bullets", RunningModeHandler.getInstance().getBullets());
        
        //bag related
        userDocument.put("bagPowerUps", BagHandler.getInstance().getPowerUps());

        //TimerHandler related
        userDocument.put("duration", TimerHandler.getInstance().getDuration());
        userDocument.put("initalTime", TimerHandler.getInstance().getInitialTime());
        userDocument.put("alienTimePassed", TimerHandler.getInstance().getAlienTimePassed());
        userDocument.put("powerUpTimePassed", TimerHandler.getInstance().getPowerUpTimePassed());
        userDocument.put("hintTime", TimerHandler.getInstance().getHintTime());
        userDocument.put("vestTİme", TimerHandler.getInstance().getVestTime());
        userDocument.put("alienTimers", TimerHandler.getInstance().getAlienTimers());
        userDocument.put("elapsedTime", TimerHandler.getInstance().getElapsedTime());
        userDocument.put("isRunning", TimerHandler.getInstance().isRunning());
        
        try {
			String path = "src" + File.separator + "domain" + File.separator + "save_load" + File.separator + "adapter" + File.separator + "users" + File.separator + username+ ".ser";
	         FileOutputStream fileOut =
	         new FileOutputStream(path);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(userDocument);
	         out.close();
	         fileOut.close();

			 RunningModeFrame.getInstance().gameSaved();
	      } catch (IOException i) {
	         i.printStackTrace();
	      }

        
    }

    
    @Override
    public void load(String username) throws IOException{
    	Document retrieveDoc = new Document();
        // TODO Auto-generated method stub

				CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry());
		        
		        Codec<ImageIcon> imageIconCodec = new ImageIconCodec(pojoCodecRegistry);
		        Codec<Point> pointCodec = new PointCodec(pojoCodecRegistry);
		        Codec<IJobBehavior> jobBehaviorCodec = new JobBehaviorCodec(pojoCodecRegistry);
		        Codec<IMoveBehavior> moveBehaviorCodec = new MoveBehaviorCodec(pojoCodecRegistry);
		        Codec<IAttackBehavior> attackBehaviorCodec = new AttackBehaviorCodec(pojoCodecRegistry);
		        Codec<PowerUp> powerUpCodec = new PowerUpCodec(pojoCodecRegistry);
		        Codec<Building> buildingCodec = new BuildingCodec(pojoCodecRegistry);
		        Codec<Object> objectCodec = new ObjectCodec(pojoCodecRegistry);
		        Codec<Document> docCodec = pojoCodecRegistry.get(Document.class);

		        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), CodecRegistries.fromCodecs(imageIconCodec, pointCodec, jobBehaviorCodec, moveBehaviorCodec, attackBehaviorCodec, powerUpCodec, buildingCodec, objectCodec, docCodec), fromProviders(
		            PojoCodecProvider.builder().register(Object.class, Building.class, 
		            Alien.class, BlindAlien.class, ShooterAlien.class, TimeWastingAlien.class, Bullet.class, 
		            IAttackBehavior.class, IndecisiveWaste.class, MeleeAttack.class, NoWaste.class, RangedAttack.class, WasteTime.class,
		            IMoveBehavior.class, MoveToSound.class, NoMove.class, RandomMove.class,
		            PowerUp.class, PlasticBottle.class, ProtectionVest.class, Hint.class,
		            IJobBehavior.class ,AddProtection.class, GiveHint.class, ThrowPlasticBottle.class,
		             ImageIcon.class, Point.class).build()));

		            //connect to database		               
		           
		                /* PLAYER RELATED
		                userDocument.put("username", Player.getInstance().getUsername()); ++
		                userDocument.put("password", Player.getInstance().getPassword()); ++
		                userDocument.put("image_corner", Player.getInstance().getCharacterImageCorner()); ++
		                userDocument.put("lifeCount", Player.getInstance().getLifeCount()); ++
		                */

		        try {
					String path = "src" + File.separator + "domain" + File.separator + "save_load" + File.separator + "adapter" + File.separator + "users" + File.separator + username+ ".ser";
			         FileInputStream fileIn = new FileInputStream(path);
			         ObjectInputStream in = new ObjectInputStream(fileIn);
			         retrieveDoc = (Document) in.readObject();
			         in.close();
			         fileIn.close();
			      } catch (IOException i) {
			         i.printStackTrace();
			         return;
			      } catch (ClassNotFoundException c) {
			         System.out.println(" class not found");
			         c.printStackTrace();
			         return;
			      }
				  		Point player_corner = retrieveDoc.get("image_corner", Point.class);
		                		                
		                Player.getInstance().setLifeCount(retrieveDoc.get("lifeCount", Integer.class));

		                /*  RUNNINGMODEHANDLER RELATED

		                */

		                
		                RunningModeHandler.getInstance().setPlay(false); //
		                RunningModeHandler.getInstance().setDoorUnlocked(retrieveDoc.get("doorUnlocked", Boolean.class));
		                RunningModeHandler.getInstance().setDoorUnlocked(retrieveDoc.get("doorUnlocked", Boolean.class));
		                RunningModeHandler.getInstance().setPowerUpCollectible(retrieveDoc.get("powerUpCollectible", Boolean.class));
		                RunningModeHandler.getInstance().setPowerUpCollected(retrieveDoc.get("powerUpCollected", Boolean.class));
		                RunningModeHandler.getInstance().setShowHint(retrieveDoc.get("showHint", Boolean.class));
		                RunningModeHandler.getInstance().setHasVest(retrieveDoc.get("hasVest", Boolean.class));
		                RunningModeHandler.getInstance().setLastPressedKey(retrieveDoc.get("lastPressedKey", Integer.class));


		                /* Player location */
		                Player.getInstance().setCharacterPositionX((int) player_corner.getX());
		                Player.getInstance().setCharacterPositionY((int) player_corner.getY());

		                
		              

		               

						PowerUp powerUpToAdd = retrieveDoc.get("powerUp", PowerUp.class);
						RunningModeHandler.getInstance().setPowerUpDirect(powerUpToAdd);

						
		            
		                /*  //BUILDING RELATED
		                */

		                

						ArrayList<Building> buildingsToAdd = (ArrayList<Building>) retrieveDoc.getList("buildings", Building.class);
						
		                RunningModeHandler.getInstance().setBuildings(buildingsToAdd);
		                RunningModeHandler.getInstance().setCurrentBuildingIndex(retrieveDoc.get("currentBuildingIndex", Integer.class));
		                RunningModeHandler.getInstance().setCurrentBuilding(retrieveDoc.get("currentBuildingIndex", Integer.class)); //setCurrentBuilding takes the index and finds the building

		                RunningModeFrame.getInstance().setFrameTitle();

		                
		                ArrayList<Alien> aliens = (ArrayList<Alien>) retrieveDoc.getList("aliens", Alien.class);

						
		                RunningModeHandler.getInstance().setAliens(aliens);

						ArrayList<Bullet> bullets = (ArrayList<Bullet>) retrieveDoc.getList("bullets", Bullet.class);

						

		                
		                RunningModeHandler.getInstance().setBullets(bullets);

		                


						ArrayList<PowerUp> powerUps = (ArrayList<PowerUp>) retrieveDoc.getList("bagPowerUps", PowerUp.class);

						for (PowerUp powerUp : powerUps) {
							BagHandler.getInstance().addToBag(powerUp);
						}
						

		                

		                

		                
		                TimerHandler.getInstance().setIsRunning(retrieveDoc.get("isRunning", Boolean.class));
		                TimerHandler.getInstance().setDurationDirect(retrieveDoc.get("duration", Long.class));
		                TimerHandler.getInstance().setInitialTime(retrieveDoc.get("initalTime", Long.class));
		                TimerHandler.getInstance().setAlienTimePassed(retrieveDoc.get("alienTimePassed", Long.class));
		                TimerHandler.getInstance().setAlienTimePassed(retrieveDoc.get("alienTimePassed", Long.class));
		                TimerHandler.getInstance().setPowerUpTimePassed(retrieveDoc.get("powerUpTimePassed", Long.class));
		                TimerHandler.getInstance().setVestTime(retrieveDoc.get("vestTİme", Long.class));
		                TimerHandler.getInstance().setHintTime(retrieveDoc.get("hintTime", Long.class));


		                ArrayList<Long> alienTimers = (ArrayList<Long>) retrieveDoc.getList("alienTimers", Long.class);
		                
		                TimerHandler.getInstance().setAlienTimers(alienTimers);
		                TimerHandler.getInstance().setElapsedTime(retrieveDoc.get("elapsedTime", Long.class));

		                

		                
		                RunningModeFrame.getInstance().setHintXCoor(retrieveDoc.get("hintXCoor", Integer.class));
		                RunningModeFrame.getInstance().setHintYCoor(retrieveDoc.get("hintYCoor", Integer.class));

		                BuildingModeFrame.getInstance().disposeMethod();
		                LoginModeFrame.getInstance().disposeMethod();


		            

            }
        
    
    

    @Override
    public void checkIfUserExists(String username, String password) {
       
    	int flag = 0;
		String path = "src" + File.separator + "domain" + File.separator + "save_load" + File.separator + "adapter" + File.separator + "users" + File.separator;
		File dir = new File(path);
		boolean bool = dir.mkdir();
		File[] directoryListing = dir.listFiles();
		System.out.println("directory listing: " + directoryListing);
			for (File child : directoryListing) {
			      // Do something with child
				  System.out.println(child);
					if(child.getPath().equals(path + username+ ".ser")) {
						flag = 1;
					}
			    }
		
		
		if(flag == 0) {
			LoginModeHandler.getInstance().startNewGame(username, password);
		}
		else {
			Document doc;
			try {
				path = "src" + File.separator + "domain" + File.separator + "save_load" + File.separator + "adapter" + File.separator + "users" + File.separator + username + ".ser";
				FileInputStream fileIn = new FileInputStream(path);
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         doc = (Document) in.readObject();
		         in.close();
		         fileIn.close();
		      } catch (IOException i) {
		         i.printStackTrace();
		         return;
		      } catch (ClassNotFoundException c) {
		         System.out.println(" class not found");
		         c.printStackTrace();
		         return;
		      }
			String docPwd = doc.getString("password");
            if (docPwd.equals(password)){ // passwords match!
                LoginModeFrame.getInstance().alertPlayer(2);
                //retrieve data
                Player.getInstance().setUsername(username);
                Player.getInstance().setPassword(password);

                try {
                load(username);
                } catch (IOException e) {
                    System.out.println("error while loading the game: " + e);
                } catch (NullPointerException en){

				}

            }

            else { //wrong password

                //alert player that username is wrong, re-enter!
                LoginModeHandler.getInstance().wrongPassword();

            }
        }


    }

    @Override
    public void delete(String username) {
        // TODO Auto-generated method stub
    	
    	File file
        = new File("src" + File.separator + "domain" + File.separator + "save_load" + File.separator + "adapter" + File.separator + "users" + File.separator +username+ ".ser");

	    if (file.delete()) {
	        System.out.println("File deleted successfully");
        }
        
    }
    
}