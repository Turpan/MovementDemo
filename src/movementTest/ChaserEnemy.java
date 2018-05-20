package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Enemy;
import movement.GameListener;
import movement.Projectile;
import movement.Shapes.Ellipse;
import movement.Shapes.OutlineShape;

public class ChaserEnemy extends Enemy{

	static final double MASS = 6;
	static final double BASEMOVEFORCE = 40;
	static final double TIMESCALE = 0.1;
	static final double COEFFICIENT_OF_RESTITUTION = 1;	
	static final double COEFFICIENT_OF_DRAG = 0.05;			
	static final double  COEFFICIENT_OF_FRICTION = 0.5; 
	
	public ChaserEnemy(GameListener listener) throws MalformedEntityException {
		super(listener);
		setMass(MASS);
		setBaseMoveForce(BASEMOVEFORCE);
		setTimeScale(TIMESCALE);
		setCoF(COEFFICIENT_OF_FRICTION);
		setCoD(COEFFICIENT_OF_DRAG);
		setCoR(COEFFICIENT_OF_RESTITUTION);
		loadImage();
		setOutline((OutlineShape)(new Ellipse(getWidth(), getHeight())));
	}
	private void loadImage() throws MalformedEntityException {
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
	@Override
	public int getDesiredX() {
		return getListener().getPlayerLocation().width;
	}
	@Override
	public int getDesiredY() {
		return getListener().getPlayerLocation().height;
	}
	@Override
	public boolean isActive() {
		// TODO
		return true;
	}
	@Override
	public boolean canAttack() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected Projectile createAttack() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}
}
