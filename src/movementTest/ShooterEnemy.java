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

public class ShooterEnemy extends Enemy implements Staggerable{
	static final int MAXSPEED = 15;
	static final int STOPSPEED = 5;
	static final int MOVESPEED = 2;
	static final int DIRECTIONTHRESHOLD = 90;
	static final int TURNSPEED = 10;
	static final int MAXHEALTH = 1;
	static final double TIMESCALE = 0.1;
	static final int STAGGERSPEED = 10;
	static final int STAGGERDECAY = 2;
	static final int INVINCIBILITYTIME = 5;
	static final int FORCE = 0;
	static final double DAMAGE = 0;
	static final double ATTACKCOOLDOWN = 10;
	static final int DISTANCE = 200;
	int staggerDecay;
	public ShooterEnemy(GameListener listener) {
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
		setAttackCooldown(ATTACKCOOLDOWN);
		loadImage();
	}
	private void loadImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/sadman.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		BufferedImage img2 = null;
		try {
		    img2 = ImageIO.read(new File("graphics/sadman-bitmask.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		setSprite(img);
		setCollisionMap(img2);
	}
	@Override
	public void collisionWith(Entity entity) {
		if (entity instanceof Projectile && !(entity instanceof EnemyBullet)) {
			var projectile = (Projectile) entity;
			damaged(projectile.getDamage());
			stagger((int) projectile.getDirection(), projectile.getForce());
		}
		if (entity instanceof PlayerStrike) {
			gainInvincibility();
		}
		if (entity instanceof Enemy) {
			stagger((int) adjustDegrees(getDirection() - 180), 2);
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
		int playerDistance = (int) (getPositionX() - getListener().getPlayerLocation().getWidth());
		if (Math.abs(playerDistance) > DISTANCE) {
			return (int) getListener().getPlayerLocation().getWidth();
		} else {
			return (int) getPositionX() + playerDistance;
		}
	}
	@Override
	public int getDesiredY() {
		int playerDistance = (int) (getPositionY() - getListener().getPlayerLocation().getHeight());
		if (Math.abs(playerDistance) > DISTANCE) {
			return (int) getListener().getPlayerLocation().getHeight();
		} else {
			return (int) getPositionY() + playerDistance;
		}
	}
	@Override
	public boolean isActive() {
		// TODO
		return true;
	}
	@Override
	public boolean canAttack() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected Projectile createAttack() {
		var x = getPositionX() + (getWidth() / 2);
		var y = getPositionY() + (getHeight() / 2);
		x += (30 * Math.cos(Math.toRadians(getBulletDirection())));
		y += (30 * Math.sin(Math.toRadians(getBulletDirection())));
		var bullet = new EnemyBullet(getBulletDirection(), x, y);
		bullet.listener = getListener();
		getListener().createEntity(bullet);
		return bullet;
	}
	private double getBulletDirection() {
		var x = getListener().getPlayerLocation().getWidth();
		var y = getListener().getPlayerLocation().getHeight();
		return (int) Math.toDegrees(Math.atan2(y - getPositionY(), x - getPositionX()));
	}
}
