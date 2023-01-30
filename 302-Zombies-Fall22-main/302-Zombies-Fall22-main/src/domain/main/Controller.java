package domain.main;

import javax.swing.*;
import java.awt.*;
import domain.building_mode.Building;
//import domain.handler.BuildingModeHandler;
//import domain.running_mode.BottomPanel;
//import domain.running_mode.GameArea;
//import domain.running_mode.RightPanel;
//import ui.RunningModeFrame;
import ui.LoginModeFrame;
import ui.BuildingModeFrame;
import ui.RunningModeFrame;

import java.util.ArrayList;

public class Controller {
	public static void main(String[] args) {

		 LoginModeFrame.getInstance();	
	}
	
	
	
	public static void StartBuildingMode() {
        
		LoginModeFrame.getInstance().disposeMethod();
		BuildingModeFrame.getInstance();
	}

	public static void StartRunningMode() {
		BuildingModeFrame.getInstance().disposeMethod();	
		

		RunningModeFrame.getInstance();

	}

	
}