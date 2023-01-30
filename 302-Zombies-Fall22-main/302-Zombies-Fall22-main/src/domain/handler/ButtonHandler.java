package domain.handler;

import java.io.Serializable;

import ui.RunningModeFrame;

public class ButtonHandler implements Serializable{

    public static ButtonHandler instance;

    private ButtonHandler() {}

    public static ButtonHandler getInstance(){

        if (instance == null){
            instance = new ButtonHandler();
        }
        return instance;
    }

    // pause button methods:
    public void resumeGame() {
        TimerHandler.getInstance().resume();
        RunningModeHandler.getInstance().setPlay(true);
        RunningModeFrame.getInstance().getBottomPanel().getPauseButton().setText("Pause");
        RunningModeFrame.getInstance().getBottomPanel().getSaveButton().setEnabled(false);
    }

    public void pauseGame() {
        TimerHandler.getInstance().pause();
        RunningModeHandler.getInstance().setPlay(false);
        RunningModeFrame.getInstance().getBottomPanel().getPauseButton().setText("Play");
        RunningModeFrame.getInstance().getBottomPanel().getSaveButton().setEnabled(true);
    }

    // quit button method:
    public void quitGame() {
        System.exit(0);
    }


}
