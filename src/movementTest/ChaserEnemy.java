package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Enemy;
import movement.EnemyAI;

public class ChaserEnemy extends Enemy{

	static final double MASS = 7;
	static final double BASEMOVEFORCE = 40;
	static final double TIMESCALE = 0.1;
	static final double COEFFICIENT_OF_RESTITUTION = 0.9;	
	static final double COEFFICIENT_OF_DRAG = 0.05;			
	static final double  COEFFICIENT_OF_FRICTION = 0.25; 
	
	public ChaserEnemy(EnemyAI AI) {
		super(AI);
		setMass(MASS);
		setBaseMoveForce(BASEMOVEFORCE);
		setTimeScale(TIMESCALE);
		setCoF(COEFFICIENT_OF_FRICTION);
		setCoD(COEFFICIENT_OF_DRAG);
		setCoR(COEFFICIENT_OF_RESTITUTION);
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
