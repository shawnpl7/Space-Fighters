import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public abstract class Ship extends Sprite{
	private int fireDirection;
	private int health;
	private int cooldownTimer;
	private int maxCooldownTime;
	private boolean isExploding;
	public Ship(int xCord, int yCord, int fireDirection,int health, int coolDownTime) {
		super(xCord, yCord);
		this.fireDirection = fireDirection;
		this.health = health;
		maxCooldownTime = coolDownTime;
		
	 
	}
	/**
	 * returns an arraylist of projectiles all fired form the center of the ship and in the ships fire direction
	 */
	public ArrayList<Projectile> fire() {
		cooldownTimer =maxCooldownTime;
		ArrayList<Projectile> projectiles = new ArrayList<>();
		projectiles.add(new Projectile((getX()+getX()+getWidth())/2,(getY()+getY()+getHeight())/2, fireDirection));
		return projectiles;
	}
	/**
	 * returns health
	 */
	public int getHealth() {
		return health;
	}
	/**
	 * sets health to parameter h
	 */
	public void setHealth(int h) {
		health = h;
	}
	/**
	 * gets damage taken
	 * decreases health by that damage amount 
	 */
	public void takeDamage(int damage) {
		health-=damage;
	}
	/**
	 * returns whether the firing cooldown timer has finished
	 */
	public boolean canFire() {
		return cooldownTimer<=0;
	}
	/**
	 * gets time passed as a parameter
	 * depreciates cooldown timer by the amount of time which has passed
	 */
	public void cooldownDecrease(int timePassed) {
		cooldownTimer-=timePassed;
	}
	
	/**
	 *sets firing direction
	 */
	public void setFireDirection(int direction){
		fireDirection = direction;
	}
	/**
	 * returns firing direction
	 */
	public int getFireDirection() {
		return fireDirection;
	}
	/**
	 * sets the maximum value at which the ships cooldown timer can be (when reset)
	 */
	public void setMaxCooldownTime(int time){
		maxCooldownTime = time;
	}
	/**
	 * activates exploding procedure
	 * changes image
	 */
	public void explode() {
		isExploding = true;
		setImage("Explosion.png");
	}
	/**
	 * returns is ship is exploding
	 */
	public boolean isExploding() {
		return isExploding;
	}

}
