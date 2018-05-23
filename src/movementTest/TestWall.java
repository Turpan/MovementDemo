package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Wall;

public class TestWall extends Wall{
	
	public TestWall(int angle) {
		setAngle (angle);
		setBounciness(1);
	}
	
	public void loadLongImage() throws MalformedEntityException {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/wall-long.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		BufferedImage img2 = null;
		try {
		    img2 = ImageIO.read(new File("graphics/wall-long-bitmask.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		setSprite(img);
		setCollisionMap(img2);
	}
	public void loadWideImage() throws MalformedEntityException {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/wall-wide.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		BufferedImage img2 = null;
		try {
		    img2 = ImageIO.read(new File("graphics/wall-wide-bitmask.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		setSprite(img);
		setCollisionMap(img2);
	}
}
