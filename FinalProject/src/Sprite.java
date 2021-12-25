import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import javax.swing.Action;

public abstract class Sprite {
	private int x;
	private int y;
	private int speedX,speedY;
	private Image image;
	private int xDirection, yDirection;
	
	public Sprite(int xCord, int yCord) {
		x = xCord;
		y = yCord;
	}
	/**
	 * returns width of the sprite's image
	 */
	public int getWidth() {
		return image.getWidth(null);
	}
	/**
	 * returns height of the sprite's image
	 */
	public int getHeight() {
		return image.getHeight(null);
	}
	/**
	 * returns sprite's x coordinate
	 */
	public int getX () {
		return x;
	}
	/**
	 * returns sprite's y coordinate
	 */
	public int getY() {
		return y;
	}
	/**
	 * accumulates coordinates based on speed and direction
	 */
	public void move() {
		x+=speedX*xDirection;
		y+=speedY*yDirection;
	}
	/**
	 * sets speed in the x direction based to speed passed
	 */
	public void setXSpeed(int speed) {
		speedX=speed;
	}
	/**
	 * sets speed in the y direction based to speed passed
	 */
	public void setYSpeed(int speed) {
		speedY = speed;
	}
	/**
	 * returns speed in the x direction
	 */
	public int getXSpeed() {
		return speedX;
	}
	/**
	 * returns speed in the y direction
	 */
	public int getYSpeed() {
		return speedY;
	}
	/**
	 * sets direction on the horizontal plane to integer direction parameter
	 */
	public void setXDirection(int direction) {
		xDirection = direction;
	}
	/**
	 * sets direction on the vertical plane to integer direction parameter
	 */
	public void setYDirection(int direction) {
		yDirection = direction;
	}
	/**
	 * gets path as string parameter
	 * sets image based on path
	 */
	public void setImage(String path) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * returns image
	 */
	public Image getImage() {
		return image;
	}
	/**
	 * returns horizontal direction
	 */
	public int getXDirection() {
		return xDirection;
	}
	/**
	 * returns vertical direction
	 * @return
	 */
	public int getYDirection() {
		return yDirection;
	}
	/**
	 * sprite passed as parameter
	 * checks if this sprite's bounds intersect passed sprite's bounds 
	 */
	public boolean collided(Sprite sprite) {
		return getBounds().intersects(sprite.getBounds());
		
		
	}
	/**
	 * returns a rectangle representing the sprite's bounds/border
	 */
	private Rectangle getBounds() {
		return new Rectangle(x,y,image.getWidth(null),image.getHeight(null));
	}
}