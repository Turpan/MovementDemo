package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Attacker;
import movement.Bouncy;
import movement.Dashing;
import movement.Enemy;
import movement.Entity;
import movement.GameListener;
import movement.Projectile;
import movement.Staggerable;
import movement.Velocity;

public class Player extends Attacker implements Dashing, Staggerable {
	static final int MAXSPEED = 30;
	static final int STOPSPEED = 5;
	static final int MOVESPEED = 1;
	static final int DIRECTIONTHRESHOLD = 90;
	static final int TURNSPEED = 9;
	static final double TIMESCALE = 0.1;
	static final int DASHCOOLDOWN = 20;
	static final int DASHLENGTH = 3;
	static final int DASHSPEED = 70;
	static final int STAGGERDECAY = 2;
	static final int STRIKEDISTANCE = 25;
	static final int MAXHEALTH = 10;
	static final int INVINCIBILITYTIME = 10;
	static final double ATTACKDURATION = 2;
	int dashCoolDown;
	double dashDirection;
	double dashCounter; //this will be used for checking i frames
	double dashCoolDownCount;
	double attackCounter;
	double attackDuration;
	int staggerDecay;
	PlayerSword sword;
	PlayerStrike strike;
	PlayerTurret turret;
	GameListener listener;
	public Player() {
		setMaxSpeed(MAXSPEED);
		setStopSpeed(STOPSPEED);
		setMoveSpeed(MOVESPEED);
		setDirectionThreshold(DIRECTIONTHRESHOLD);
		setTurnSpeed(TURNSPEED);
		setTimeScale(TIMESCALE);
		setDashCoolDown(DASHCOOLDOWN);
		setStaggerDecay(STAGGERDECAY);
		setMaxHealth(MAXHEALTH);
		setCurrentHealth(MAXHEALTH);
		setAttackDuration(ATTACKDURATION);
		setInvincibilityTime(INVINCIBILITYTIME);
		loadImage();
		createObjects();
	}
	private double getAttackCounter() {
		return attackCounter;
	}
	private void setAttackCounter(double attackCounter) {
		this.attackCounter = attackCounter;
	}
	private double getAttackDuration() {
		return attackDuration;
	}
	private void setAttackDuration(double attackDuration) {
		this.attackDuration = attackDuration;
	}
	private void loadImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/funnyman.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(new File("graphics/funnyman-bitmask.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		setSprite(img);
		setCollisionMap(img2);
	}
	private void createObjects() {
		sword = new PlayerSword();
		strike = new PlayerStrike();
		turret = new PlayerTurret();
	}
	private void moveStrike(double x, double y) {
		x += (STRIKEDISTANCE * Math.cos(Math.toRadians(getDirection())));
		y += (STRIKEDISTANCE * Math.sin(Math.toRadians(getDirection())));
		strike.updatePosition(x - (strike.getWidth() / 2), y - (strike.getHeight() / 2));
	}
	private void moveTurret() {
		turret.updatePosition(getPositionX() + PlayerTurret.XOFFSET, getPositionY() + PlayerTurret.YOFFSET);
	}
	private void moveSword() {
		var x = getPositionX() + (getWidth() / 2);
		var y = getPositionY() + (getHeight() / 2);
		x += (STRIKEDISTANCE * Math.cos(Math.toRadians(getDirection())));
		y += (STRIKEDISTANCE * Math.sin(Math.toRadians(getDirection())));
		sword.updatePosition(x, y);
	}
	private boolean isAttacking() {
		return (getAttackCounter() > 0);
	}
	private void attackTick() {
		setAttackCounter(getAttackCounter() - getTimeScale());
	}
	public void attack() {
		setAttackCounter(getAttackDuration());
	}
	public void turnTurret(int direction) {
		turret.turn(direction);
	}
	@Override
	public void updatePosition(double positionX, double positionY) {
		super.updatePosition(positionX, positionY);
		double x;
		double y;
		if (isAttacking()) {
			x = getPositionX() + (getWidth() / 2);
			y = getPositionY() + (getHeight() / 2);
		} else {
			x = -50;
			y = -50;
		}
		moveSword();
		moveStrike(x, y);
		moveTurret();
	}
	@Override
	public void setDirection(double direction) {
		super.setDirection(direction);
		strike.setDirection(getDirection());
		sword.setDirection(getDirection());
	}
	@Override
	public void tick(int direction) {
		if (isDashing()) {
			dashTick();
		} else {
			setMaxSpeed(MAXSPEED);
			super.tick(direction);
		}
		if (!canDash()) {
			dashCoolDownTick();
		}
		if (isAttacking()) {
			attackTick();
		}
	}
	@Override
	public boolean isAttackable() {
		return (!isDashing() && !isInvincible());
	}
	@Override
	public boolean canDash() {
		return (getDashCoolDownCount() == 0);
	}
	@Override
	public boolean isDashing() {
		return (dashCounter > 0);
	}
	@Override
	public void setDashCoolDown(int dashCoolDown) {
		if (dashCoolDown < 0) {
			dashCoolDown = 0;
		}
		this.dashCoolDown = dashCoolDown;
	}
	@Override
	public int getDashCoolDown() {
		return dashCoolDown;
	}
	@Override
	public void setDashCoolDownCount(double dashCoolDownCount) {
		this.dashCoolDownCount = dashCoolDownCount;
	}
	@Override
	public double getDashCoolDownCount() {
		return dashCoolDownCount;
	}
	@Override
	public void setDashCounter(double dashCounter) {
		this.dashCounter = dashCounter;
	}
	@Override
	public double getDashCounter() {
		return dashCounter;
	}
	@Override
	public void setDashDirection(double dashDirection) {
		this.dashDirection = adjustDegrees(dashDirection);
	}
	@Override
	public double getDashDirection() {
		return dashDirection;
	}
	@Override
	public void dashCoolDownTick() {
		setDashCoolDownCount(getDashCoolDownCount() - getTimeScale());
		if (getDashCoolDownCount() < 0) {
			setDashCoolDownCount(0);
		}
	}
	@Override
	public void dashTick() {
		setDirection(getDashDirection());
		setMaxSpeed(DASHSPEED);
		setAcceleration(DASHSPEED);
		setDashCounter(getDashCounter() - getTimeScale());
		setSpeed(getNextSpeed());
		move();
	}
	@Override
	public void dash(int direction) {
		if (!canDash()) {
			return;
		}
		setDashDirection(direction);
		setDashCoolDownCount(getDashCoolDown());
		setDashCounter(DASHLENGTH);
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
		var playerSpeed = getSpeed() - speed;
		if (playerSpeed < 0) {
			playerSpeed = 0;
		}
		setSpeed(playerSpeed);
	}
	@Override
	public void collisionWith(Entity entity) {
		if (entity instanceof Enemy && isAttackable()) {
			var enemy = (Enemy) entity;
			stagger((int) adjustDegrees(enemy.getDirection()), enemy.getForce());
			damaged(enemy.getDamage());
			gainInvincibility();
		}
		if (entity instanceof Bouncy) {
			var bouncy = (Bouncy) entity;
			var velocity = getOwnVelocity();
            velocity.setDecayRate(2);
            velocity.setTimeScale(getTimeScale());
            velocity = bouncy.getBounceVelocity(velocity);
			addVelocity(velocity);
			setSpeed(0);
			setDirection(velocity.getDirection());
		}
		if (entity instanceof Projectile && !(entity instanceof PlayerBullet) && !(entity instanceof PlayerStrike) && isAttackable()) {
			var projectile = (Projectile) entity;
			stagger((int) adjustDegrees(projectile.getDirection()), projectile.getForce());
			damaged(projectile.getDamage());
		}
	}
	@Override
	public void die() {
		listener.removeEntity(this);
	}
}
