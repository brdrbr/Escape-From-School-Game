package domain.save_load.adapter;

import java.io.IOException;
import java.util.ArrayList;

public interface ISaveLoadAdapter {

	public void save(String username) throws IOException;
	public void load(String username) throws IOException;
	public void checkIfUserExists(String username, String password);
	public void delete(String username);
    

    
}
