package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Bouncy;
import movement.Enemy;
import movement.Entity;
import movement.GameListener;
import movement.Projectile;
import movement.Staggerable;
import movement.Velocity;

public class ChaserEnemy extends Enemy {
	static final double MASS = 7;
	static final double BASEMOVEFORCE = 40;
	static final double TIMESCALE = 0.1;
	static final double COEFFICIENT_OF_RESTITUTION = 0.9;	
	static final double COEFFICIENT_OF_DRAG = 0.05;			
	static final double  COEFFICIENT_OF_FRICTION = 0.25;
	int staggerDecay;
	public ChaserEnemy(GameListener listener) {
		super(listener);
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
	@Override
	public void collisionWith(Entity entity) {
		if (entity instanceof Projectile) {
			var projectile = (Projectile) entity;
			damaged(projectile.getDamage());
			stagger((int) projectile.getDirection(), projectile.getForce());
		}
		if (entity instanceof PlayerStrike) {
			gainInvincibility();
		}
		if (entity instanceof Bouncy) {
			var bouncy = (Bouncy) entity;
			var velocity = getOwnVelocity();
            velocity.setDecayRate(2);
            velocity.setTimeScale(getTimeScale());
			addVelocity(bouncy.getBounceVelocity(velocity));
			setSpeed(0);
		}
	}
	@Override
	public void die() {
		getListener().removeEntity(this);
	}
	@Override
	public void setStaggerDecay(int staggerDecay) {
		this.staggerDecay = staggerDecay;
	}
	@Override
	public int getStaggerDecay() {
		return staggerDecay;
	}
	@Override
	public void stagger(int direction, int speed) {
		Velocity stagger = new Velocity();
		stagger.setDirection(direction);
		stagger.setDecayRate(getStaggerDecay());
		stagger.setSpeed(speed);
		stagger.setTimeScale(getTimeScale());
		addVelocity(stagger);
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
}
