package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Enemy;
import movement.EnemyAI;

public class ChaserEnemy extends Enemy{
	static final int MAXSPEED = 15;
	static final int STOPSPEED = 5;
	static final int MOVESPEED = 2;
	static final int DIRECTIONTHRESHOLD = 90;
	static final int TURNSPEED = 10;
	static final double TIMESCALE = 0.1;
	public ChaserEnemy(EnemyAI AI) {
		super(AI);
		setMaxSpeed(MAXSPEED);
		setStopSpeed(STOPSPEED);
		setMoveSpeed(MOVESPEED);
		setDirectionThreshold(DIRECTIONTHRESHOLD);
		setTurnSpeed(TURNSPEED);
		setTimeScale(TIMESCALE);
		loadImage();
	}
	private void loadImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/evilman.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		BufferedImage img2 = null;
		try {
		    img2 = ImageIO.read(new File("graphics/evilman-bitmask.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		setSprite(img);
		setCollisionMap(img2);
	}
}
