package domain.building_mode;
import java.io.Serializable;
import java.util.ArrayList;

public class Building implements Serializable{

    String name;
    int objCount;
    ArrayList<Object> objects;
    int keyIndex;

    

    public Building(String name, int objCount){
        this.name = name;
        this.objCount = objCount;
        objects = new ArrayList<Object>();
    }

    //getters
    public String getName(){
        return name;
    }
    public int getObjCount(){
        return objCount;
    }
    public ArrayList<Object> getObjects(){
        return objects;
    }
    public int getKeyIndex(){
        return keyIndex;
    }
    
    public void setKeyIndex(int index){
        keyIndex = index;
    }

    public void addObject(Object o){
        objects.add(o);
    }
    public void removeObject(Object o){
        objects.remove(o);
    }

    public void printObjects(){
        for (Object obj : objects){
            obj.printObject();
        }
    }
    
}
