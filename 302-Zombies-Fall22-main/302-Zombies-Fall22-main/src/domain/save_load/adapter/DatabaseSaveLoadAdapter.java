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

import java.io.IOException;
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
import ui.BuildingModeFrame;
import ui.LoginModeFrame;
import ui.RunningModeFrame;

public class DatabaseSaveLoadAdapter implements ISaveLoadAdapter{

    private String uri = "mongodb+srv://BartuUzun:Halenur2001@projectcluster.eqpichj.mongodb.net/test";

    public DatabaseSaveLoadAdapter(){}

    


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

            //connect to database
            try (MongoClient mongoClient = MongoClients.create(uri)) {
                MongoDatabase database = mongoClient.getDatabase("MyDatabase").withCodecRegistry(pojoCodecRegistry);
                try {
                    Bson command = new BsonDocument("ping", new BsonInt64(1));
                    Document commandResult = database.runCommand(command);
                    System.out.println("Connected successfully to server.");
                } catch (MongoException me) {
                    System.err.println("An error occurred while attempting to run a command: " + me);
                }


                // get collections from the database
                MongoCollection<Document> gameCollection = database.getCollection("Game");

                Bson userNameExistsComparison = eq("username", username);
                Document doc = gameCollection.find(userNameExistsComparison).first();

                if (doc != null) { //user has an already saved game

                    gameCollection.deleteOne(userNameExistsComparison);

                }
                
                //main doc
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
                    gameCollection.insertOne(userDocument);

                    RunningModeFrame.getInstance().gameSaved();
                    
                    

                } catch (MongoException me) {
                    System.err.println("Unable to insert data due to error: " + me);
                }

                
                
            }

        
    }

    
    @Override
    public void load(String username) throws IOException{
        // TODO Auto-generated method stub

        //set codec stuff
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
            try (MongoClient mongoClient = MongoClients.create(uri)) {
                MongoDatabase database = mongoClient.getDatabase("MyDatabase").withCodecRegistry(pojoCodecRegistry);
                try {
                    Bson command = new BsonDocument("ping", new BsonInt64(1));
                    Document commandResult = database.runCommand(command);
                    System.out.println("Connected successfully to server.");
                } catch (MongoException me) {
                    System.err.println("An error occurred while attempting to run a command: " + me);
                }

                MongoCollection<Document> gameCollection = database.getCollection("Game");
            
               

                

                
                Bson userNameExistsComparison = eq("username", username);
                Document retrieveDoc = gameCollection.find(userNameExistsComparison).first();
               
           
                /* PLAYER RELATED
                userDocument.put("username", Player.getInstance().getUsername()); ++
                userDocument.put("password", Player.getInstance().getPassword()); ++
                userDocument.put("image_corner", Player.getInstance().getCharacterImageCorner()); ++
                userDocument.put("lifeCount", Player.getInstance().getLifeCount()); ++
                */

                
                Document pointDocObj = retrieveDoc.get("image_corner", Document.class);
                PointCodec pointCodec2 = (PointCodec) pojoCodecRegistry.get(Point.class);
                BsonReader reader = new JsonReader(( pointDocObj).toJson());
                Point player_corner = pointCodec2.decode(reader, DecoderContext.builder().build());
                
                
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

                
              

                Document powerUpDocObj = retrieveDoc.get("powerUp", Document.class);
                if (powerUpDocObj != null) {
                    String name = powerUpDocObj.get("name", String.class);

                    //for imageIcon
                    Document iconDoc = powerUpDocObj.get("icon", Document.class);
                    String path = iconDoc.get("path", String.class);
                    
                    //for image_corner
                    int xCoor = powerUpDocObj.get("positionX", Integer.class);
                    int yCoor = powerUpDocObj.get("positionY", Integer.class);

                    PowerUp powerUpToAdd;
                    if (name.equals("ProtectionVest")) {
                        powerUpToAdd = new ProtectionVest(name, new ImageIcon(path));
                    }
                    else if (name.equals("Hint")){
                        powerUpToAdd = new Hint(name, new ImageIcon(path));
                    }
                    else if (name.equals("ExtraTime")){
                        powerUpToAdd = new ExtraTime(name, new ImageIcon(path));
                    }
                    else if (name.equals("ExtraLife")){
                        powerUpToAdd = new ExtraLife(name, new ImageIcon(path));
                    }
                    else {
                        powerUpToAdd = new PlasticBottle(name, new ImageIcon(path));
                    }

                    powerUpToAdd.setImageCorner(new Point(xCoor, yCoor));
                    
                    RunningModeHandler.getInstance().setPowerUpDirect(powerUpToAdd);
                }
            
                /*  //BUILDING RELATED
                */


                Document buildingDocObj;
                BsonReader buildingReader;
                BuildingCodec buildingCodec2 = new BuildingCodec(pojoCodecRegistry);
                
                ArrayList<Building> buildingsToAdd = new ArrayList<Building>();
                ArrayList<Document> buildingsDoc = (ArrayList<Document>) retrieveDoc.getList("buildings", Document.class);


                for (Document buildingDoc : buildingsDoc) {
                    buildingDocObj = buildingDoc;
                    buildingReader = new JsonReader(( buildingDocObj).toJson());
                    Building buildingToAdd = buildingCodec2.decode(buildingReader, DecoderContext.builder().build());
                    buildingsToAdd.add(buildingToAdd);
                    
                    
                }
                RunningModeHandler.getInstance().setBuildings(buildingsToAdd);
                RunningModeHandler.getInstance().setCurrentBuildingIndex(retrieveDoc.get("currentBuildingIndex", Integer.class));
                RunningModeHandler.getInstance().setCurrentBuilding(retrieveDoc.get("currentBuildingIndex", Integer.class)); //setCurrentBuilding takes the index and finds the building

                RunningModeFrame.getInstance().setFrameTitle();

                /*
                //ALIEN RELATED
             
                */

                Document alienDocOBj;
                BsonReader alienReader;
                AlienCodec alienCodec = new AlienCodec(pojoCodecRegistry);
                ArrayList<Alien> aliens = new ArrayList<Alien>();
                ArrayList<Document> aliensDoc = (ArrayList<Document>) retrieveDoc.getList("aliens", Document.class);

                for (Document alienDoc : aliensDoc) {
                    alienDocOBj = alienDoc;
                    alienReader = new JsonReader(( alienDocOBj).toJson());
                    Alien alienToAdd = alienCodec.decode(alienReader, DecoderContext.builder().build());
                    
                   
                    aliens.add(alienToAdd);
                    
                }
                RunningModeHandler.getInstance().setAliens(aliens);


                ArrayList<Document> bulletsDoc = (ArrayList<Document>) retrieveDoc.getList("bullets", Document.class);
                ArrayList<Bullet> bullets = new ArrayList<Bullet>();

                Bullet bulletToAdd;
                for (Document bulletDoc : bulletsDoc) {
                    int xCoor = bulletDoc.get("positionX", Integer.class);
                    int yCoor = bulletDoc.get("positionY", Integer.class);
                    int bulletDirection = bulletDoc.get("bulletDirection", Integer.class);
                    boolean playerCollision = bulletDoc.get("playerCollision", Boolean.class);
                    boolean isVisible = bulletDoc.get("isVisible", Boolean.class);

                    if (isVisible) {
                        bulletToAdd = new Bullet(xCoor, yCoor, bulletDirection);
                        bulletToAdd.setPlayerCollision(playerCollision);
                        bulletToAdd.setIsVisible(isVisible);
                        bullets.add(bulletToAdd);
                    }
                }

                
                RunningModeHandler.getInstance().setBullets(bullets);

                /* BAG RELATED
                userDocument.put("bagPowerUps", BagHandler.getInstance().getPowerUps()); ++
                */


                Document powerUpDOcObj;
                BsonReader powerUpReader;
                PowerUpCodec powerUpCodec2 = new PowerUpCodec(pojoCodecRegistry);
                ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
                ArrayList<Document> powerUpsDoc = (ArrayList<Document>) retrieveDoc.getList("bagPowerUps", Document.class);

                for (Document powerUpDoc : powerUpsDoc) {
                    powerUpDocObj = powerUpDoc;
                    powerUpReader = new JsonReader((powerUpDocObj).toJson());
                    PowerUp powerUpToAdd = powerUpCodec2.decode(powerUpReader, DecoderContext.builder().build());

                    BagHandler.getInstance().addToBag(powerUpToAdd);
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
        
    }
    

    @Override
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
            //Bson passwordComparison = eq("password", password);

            Document doc = gameCollection.find(userNameExistsComparison).first();
            if (doc == null) { // username does not exist in the database!

                //building mode starts, new user!
                LoginModeHandler.getInstance().startNewGame(username, password);
               
            }
            else { //username exists in the database
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
                    }
                }

                else { //wrong password

                    //alert player that username is wrong, re-enter!
                    LoginModeHandler.getInstance().wrongPassword();

                }
            }

            

            



        }


    }

    @Override
    public void delete(String username) {
        // TODO Auto-generated method stub

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


            try {
                gameCollection.deleteOne(userNameExistsComparison);
            } catch (MongoException me) {
                System.err.println("Unable to delete user due to error: " + me);
            }




        }
        
    }
    
}
