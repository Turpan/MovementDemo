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

public class ChaserEnemy extends Enemy implements Staggerable{
	static final int MAXSPEED = 20;
	static final int STOPSPEED = 5;
	static final int MOVESPEED = 2;
	static final int DIRECTIONTHRESHOLD = 90;
	static final int TURNSPEED = 10;
	static final int MAXHEALTH = 2;
	static final double TIMESCALE = 0.1;
	static final int STAGGERSPEED = 20;
	static final int STAGGERDECAY = 2;
	static final int INVINCIBILITYTIME = 5;
	static final int FORCE = 10;
	static final double DAMAGE = 1;
	int staggerDecay;
	public ChaserEnemy(GameListener listener) {
		super(listener);
		setMaxSpeed(MAXSPEED);
		setStopSpeed(STOPSPEED);
		setMoveSpeed(MOVESPEED);
		setDirectionThreshold(DIRECTIONTHRESHOLD);
		setTurnSpeed(TURNSPEED);
		setTimeScale(TIMESCALE);
		setMaxHealth(MAXHEALTH);
		setCurrentHealth(MAXHEALTH);
		setStaggerDecay(STAGGERDECAY);
		setInvincibilityTime(INVINCIBILITYTIME);
		setForce(FORCE);
		setDamage(DAMAGE);
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
