package domain.handler;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import domain.aliens.Bullet;
import domain.aliens.attack.IndecisiveWaste;
import domain.aliens.attack.NoWaste;
import domain.aliens.attack.RangedAttack;
import domain.aliens.attack.WasteTime;
import domain.running_mode.Player;
import ui.RunningModeFrame;
import domain.factory.PowerUpFactory;
import domain.aliens.Alien;
import domain.factory.AlienFactory;


public class TimerHandler implements Serializable{
	public static final int DURATION_INFINITY = -1;
	public static TimerHandler instance;
	private volatile boolean isRunning = false;
	private long interval;
	private long initalTime;
	private long elapsedTime;
	private long duration;
	private long powerUpTimePassed = 0; //FOR SPAWNING POWERUPS
	private long alienTimePassed = 0; //FOR SPAWNING ALIENS
	private long hintTime = 0; //for hint powerup
	private long vestTime = 0; //for vest powerup
	private ArrayList<Long> alienTimers; //consists timers for aliens  (one timer for each alien)
	private long initialDuration; //for spawning aliens
	private ScheduledExecutorService execService = Executors
			.newSingleThreadScheduledExecutor();
	private Future<?> future = null;


	/**
	 * interval: The time gap between each tick in second.
	 * duration: The period in second for which the timer should run. Set it to {@code Timer#DURATION_INFINITY} if the timer has to run indefinitely.
	 */
	private TimerHandler() {
		this.interval = 1;
		this.duration = RunningModeHandler.getInstance().getCurrentBuilding().getObjCount() * 5 * 1000;//change 30 to 5;
		this.initalTime = this.duration;
		this.elapsedTime = 0;
		alienTimers = new ArrayList<Long>();
	}

	/**
	 * Starts the timer. If the timer was already running, this call is ignored.
	 */
	public void start() {
		if (isRunning)
			return;

		isRunning = true;
		future = execService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {

				alienTimePassed += 1;
				powerUpTimePassed += 1; //FOR SPAWNING ETC
				duration -= TimerHandler.this.interval;
				if (duration <= 0) {
					
					RunningModeHandler.getInstance().gameEnd(1);
					future.cancel(false);
				}

				//set each alien's action according to its corresponding timer
				for (int i = 0; i < alienTimers.size(); i++){

					

					alienTimers.set(i, alienTimers.get(i) + 1);

					if (RunningModeHandler.getInstance().getAliens().get(i).getName().equals("BlindAlien")) {


						if ((float) alienTimers.get(i) / 1000 % 4 == 0) { //sets a new destination
							RunningModeHandler.getInstance().getAliens().get(i).performSetDestination();
						}
						if ((float) alienTimers.get(i) / 1000 % 6 == 0) { //attacks
							RunningModeHandler.getInstance().getAliens().get(i).performAttack();
						}
						if ((float) alienTimers.get(i) % 6500 == 0) { //finish attack animation (animation lasts 0.5 seconds)
							RunningModeHandler.getInstance().getAliens().get(i).setHasAttacked(false);
							alienTimers.set(i, (long) 0);
						}

					} else if (RunningModeHandler.getInstance().getAliens().get(i).getName().equals("TimeWastingAlien")) {

						

						if (duration < initalTime * 0.3) {
							// timer is below 30%
							// change location of key once and then disappear
							// typeForTimeAttack = 1

							//System.out.print("Hi %30, duration: " + duration);

							

							if (RunningModeHandler.getInstance().getAliens().get(i).getTypeForTimeAttack() != 1) {

								RunningModeHandler.getInstance().getAliens().get(i).setTypeForTimeAttack(1);
								alienTimers.set(i, (long) 0);
							}

							RunningModeHandler.getInstance().getAliens().get(i).setAttackBehavior(new NoWaste());

							RunningModeHandler.getInstance().getAliens().get(i).performAttack();
							System.out.print(" Duration: " + duration);

							if ((float) alienTimers.get(i) % 500 == 0) { //finish attack animation (animation lasts 0.5 seconds)
								RunningModeHandler.getInstance().getAliens().get(i).setHasAttacked(false);
							}

							//RunningModeHandler.getInstance().getAliens().get(i).setIsVisible(false);


						} else if (duration > initalTime * 0.7) {
							// timer is above 70%
							// changes key location in every 3 secs

							//System.out.print("Hi %70, duration: " + duration);

							

							if (RunningModeHandler.getInstance().getAliens().get(i).getTypeForTimeAttack() != 2) {

								RunningModeHandler.getInstance().getAliens().get(i).setTypeForTimeAttack(2);

								alienTimers.set(i, (long) 0);

							}

							RunningModeHandler.getInstance().getAliens().get(i).setAttackBehavior(new WasteTime());

							if (((float) alienTimers.get(i) / 1000 % 3 == 0) && ((float) alienTimers.get(i) != 0)) {

								int oldIndex = RunningModeHandler.getInstance().getCurrentBuilding().getKeyIndex();

								RunningModeHandler.getInstance().getAliens().get(i).performAttack();

								int newIndex = RunningModeHandler.getInstance().currentBuilding.getKeyIndex();

								System.out.print("Old index: " + oldIndex + "New index: " + newIndex + "Duration: " + duration + "type: " + RunningModeHandler.getInstance().getAliens().get(i).getTypeForTimeAttack() + "\n" );

							}

							if ((float) alienTimers.get(i) % 3500 == 0) { //finish attack animation (animation lasts 0.5 seconds)
								RunningModeHandler.getInstance().getAliens().get(i).setHasAttacked(false);
							}

						} else {
							// timer is between 30%-70%
							// disappear after 2 seconds without doing anything

							//System.out.print("Hi 30-70, duration: " + duration);

							

							if (RunningModeHandler.getInstance().getAliens().get(i).getTypeForTimeAttack() != 3) {

								RunningModeHandler.getInstance().getAliens().get(i).setTypeForTimeAttack(3);
								alienTimers.set(i, (long) 0);

							}

							RunningModeHandler.getInstance().getAliens().get(i).setAttackBehavior(new IndecisiveWaste());

							if (((float) alienTimers.get(i) / 1000 % 2 == 0) && ((float) alienTimers.get(i) != 0)) {

								RunningModeHandler.getInstance().getAliens().get(i).performAttack();

								System.out.println("Bye 30-70, duration: " + duration + "type: " + RunningModeHandler.getInstance().getAliens().get(i).getTypeForTimeAttack() + "\n");

							}

						}

					}  else if (RunningModeHandler.getInstance().getAliens().get(i).getName().equals("ShooterAlien")) {

						if ((float) alienTimers.get(i) / 1000 % 2 == 0) { // it was 1, i made it 2
							RunningModeHandler.getInstance().getAliens().get(i).performAttack();
						}

					}



					if (!RunningModeHandler.getInstance().getAliens().get(i).getIsVisible()) {
						alienTimers.remove(alienTimers.get(i));
						RunningModeHandler.getInstance().getAliens().remove(RunningModeHandler.getInstance().getAliens().get(i));
					}

				}

				if (!RunningModeHandler.getInstance().getBullets().isEmpty()) {

					for (Bullet bullet: RunningModeHandler.getInstance().getBullets()) {

						if ((duration * 1000) % 900 == 0) {
							bullet.move();
						}

						if ((RunningModeHandler.getInstance().checkIfBulletCollision(bullet.getPositionX(), bullet.getPositionY(), bullet.getWidth(), bullet.getHeight())) && bullet.getIsVisible()){

							

							if ((!RunningModeHandler.getInstance().getHasVest()) && (!bullet.getPlayerCollision())) {
								if (Player.getInstance().getLifeCount() == 1) {
									Player.getInstance().death();
								} else {
									Player.getInstance().decrementLifeCount();
									RunningModeFrame.getInstance().getRightPanel().setLifeCount();
									bullet.setPlayerCollision(true);
								}

							} else if ((RunningModeHandler.getInstance().getHasVest()) && (!bullet.getPlayerCollision())) {
								//RunningModeHandler.getInstance().setHasVest(false);
								bullet.setPlayerCollision(true);
							}

						}

					}

				}



				if (((float) alienTimePassed / 1000 != 0) && ((float) alienTimePassed / 1000 % 10 == 0)){ //spawn alien
					AlienFactory.getInstance().spawnAlien();
				}

				/*
				if (alienTimers.isEmpty()){ //for testing, spawn just 1 alien
					if (((float) alienTimePassed / 1000 != 0) && ((float) alienTimePassed / 1000 % 10 == 0)){ //spawn alien
						AlienFactory.getInstance().spawnAlien();
					}
				}*/

				
				
				if (((float) powerUpTimePassed / 1000 != 0) && ((float) powerUpTimePassed / 1000 % 6 == 0) && ((RunningModeHandler.getInstance().getPowerUpCollectible()) || RunningModeHandler.getInstance().getPowerUpCollected())){
					RunningModeHandler.getInstance().setPowerUpCollectible(false);
					RunningModeHandler.getInstance().setPowerUpCollected(false);

				}
				else if (((float) powerUpTimePassed / 1000 != 0) && ((float) powerUpTimePassed / 1000 % 12 == 0) && !(RunningModeHandler.getInstance().getPowerUpCollectible())){
					PowerUpFactory.getInstance().spawnPowerUp();
					powerUpTimePassed = 0; //reset powerUpTimePassed
				}

				//for hint powerup
				if (RunningModeHandler.getInstance().getShowHint()){
					if (hintTime == 10000) { //if it has been 10 seconds, stop giving hint
						RunningModeHandler.getInstance().setShowHint(false);
						hintTime = 0;
					}
					else {
						hintTime += 1;
					}
				}

				if (RunningModeHandler.getInstance().getHasVest()){
					if (vestTime == 20000) {
						RunningModeHandler.getInstance().setHasVest(false);
						vestTime = 0;
					}
					else{
						vestTime += 1;
					}
				}
				
				
				RunningModeFrame.getInstance().getRightPanel().setTime("Time Remaining:" + (duration / 1000) + "\n");
				//if ((float) duration % 1000 == 0) System.out.print("duration " + duration / 1000);

				if (((float) alienTimePassed != 0) && ((float) alienTimePassed % 24 == 0)){
					for (Alien alien : RunningModeHandler.getInstance().aliens){
						alien.performMove();
					}
				}
				RunningModeFrame.getInstance().getGameArea().repaint();
			}
		}, 0, this.interval, TimeUnit.MILLISECONDS);
	}

	/**
	 * Paused the timer. If the timer is not running, this call is ignored.
	 */
	public void pause() {
		if(!isRunning) return;
		future.cancel(false);
		isRunning = false;
	}

	
	/**
	 * Resumes the timer if it was paused, else starts the timer.
	 */
	public void resume() {
		this.start();
	}

	/**
	 * Stops the timer. If the timer is not running, then this call does nothing.
	 */
	public void cancel() {
		pause();
		this.elapsedTime = 0;
	}

	
	/**
	 * @return the elapsed time (in second) since the start of the timer.
	 */
	public long getElapsedTime() {
		return this.elapsedTime;
	}
	public long getInitialTime(){
		return this.initalTime;
	}
	
	public long getDuration() {
		return this.duration;
	}
	public long getAlienTimePassed() {
		return alienTimePassed;
	}
	public long getPowerUpTimePassed() {
		return powerUpTimePassed;
	}
	public long getHintTime() {
		return hintTime;
	}
	public long getVestTime() {
		return vestTime;
	}
	public ArrayList<Long> getAlienTimers(){
		return alienTimers;
	}


	public static TimerHandler getInstance(){ //Singleton Pattern
		if (instance == null){
			instance = new TimerHandler();
		}
		return instance;
	}
	
	//setters
	public void setDuration(int changeInSeconds) {
		this.duration = this.duration + changeInSeconds * 1000;
	}
	public void setDurationDirect(long duration) {
		this.duration = duration;
	}
	public void setInitialTime(long initialTime) {
		this.initalTime = initialTime;
	}
	public void setAlienTimePassed(long alienTimePassed) {
		this.alienTimePassed = alienTimePassed;
	}
	public void setPowerUpTimePassed(long powerUpTimePassed) {
		this.powerUpTimePassed = powerUpTimePassed;
	}
	public void setHintTime(long hintTime){
		this.hintTime = hintTime;
	}
	public void setVestTime(long vestTime){
		this.vestTime = vestTime;
	}
	public void setAlienTimers(ArrayList<Long> alienTimers) {
		this.alienTimers = alienTimers;
	}
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void resetVestTime(){
		vestTime = 0;
	}
	public void resetHintTime(){
		hintTime = 0;
	}

	public void resetAlienTimers(){
		for (long alienTimer : alienTimers){
			alienTimer = 0;
		}
	}

	public void resetDuration(){
		this.duration = RunningModeHandler.getInstance().getCurrentBuilding().getObjCount() * 5 * 1000;//change 30 to 5;
		this.powerUpTimePassed = 0;
		this.alienTimePassed = 0;
		this.hintTime = 0;
		this.vestTime = 0;
		alienTimers = new ArrayList<Long>();
	}
	
	/**
	 * @return true if the timer is currently running, and false otherwise.
	 */
	public boolean isRunning() {
		return isRunning;
	}

	public void addAlienTimer(){
		alienTimers.add((long) 0);
	}
}
