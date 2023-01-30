package domain.factory;

import javax.swing.ImageIcon;
import java.awt.Point;
import java.io.Serializable;

import domain.building_mode.Object;
import domain.handler.BuildingModeHandler;
import ui.BuildingModeFrame;

public class ObjectFactory implements Serializable{

    private static ObjectFactory instance;

    private ObjectFactory() {} //Singleton
    
    //Singleton
    public static ObjectFactory getInstance(){
        if (instance == null){
            instance = new ObjectFactory();
        }
        return instance;
    }

    public void spawnObject(String objName){
        Object obj;
        if (objName == "Chair") { //spawn chair object
            
            obj = new Object("Chair", BuildingModeFrame.getInstance().getChairImage(), (new Point(BuildingModeFrame.getInstance().getXCoordinateChairAdder(), BuildingModeFrame.getInstance().getYCoordinateChairAdder())));
        }
        else if (objName == "Shelf") { //spawn Shelf object
            
            obj = new Object("Shelf", BuildingModeFrame.getInstance().getShelfImage(), (new Point(BuildingModeFrame.getInstance().getXCoordinateShelfAdder(), BuildingModeFrame.getInstance().getYCoordinateShelfAdder())));
                
        }
        else if (objName == "Shelf2") { //spawn Shelf2 object

            obj = new Object("Shelf2", BuildingModeFrame.getInstance().getShelf2Image(), (new Point(BuildingModeFrame.getInstance().getXCoordinateShelf2Adder(), BuildingModeFrame.getInstance().getYCoordinateShelf2Adder())));

        }
        else { // (objName == "Sofa"), spawn Sofa object

        obj = new Object("Sofa", BuildingModeFrame.getInstance().getSofaImage(), (new Point(BuildingModeFrame.getInstance().getXCoordinateSofaAdder(), BuildingModeFrame.getInstance().getYCoordinateSofaAdder())));

        }

        BuildingModeHandler.getInstance().getCurrentBuilding().addObject(obj);

    }
}
