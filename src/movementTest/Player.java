package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Collidable;
import movement.Controllable;
import movement.Dashing;
import movement.Staggerable;
import movement.Velocity;

public class Player extends Controllable implements Dashing, Collidable, Staggerable {
	static final int MAXSPEED = 20;
	static final int STOPSPEED = 5;
	static final int MOVESPEED = 2;
	static final int DIRECTIONTHRESHOLD = 90;
	static final int TURNSPEED = 15;
	static final double TIMESCALE = 0.1;
	static final int DASHCOOLDOWN = 10;
	static final int DASHLENGTH = 2;
	static final int DASHSPEED = 50;
	static final int STAGGERSPEED = 20;
	static final int STAGGERDECAY = 2;
	int dashCoolDown;
	double dashDirection;
	double dashCounter; //this will be used for checking i frames
	double dashCoolDownCount;
	int staggerSpeed;
	int staggerDecay;
	public Player() {
		setMaxSpeed(MAXSPEED);
		setStopSpeed(STOPSPEED);
		setMoveSpeed(MOVESPEED);
		setDirectionThreshold(DIRECTIONTHRESHOLD);
		setTurnSpeed(TURNSPEED);
		setTimeScale(TIMESCALE);
		setDashCoolDown(DASHCOOLDOWN);
		setStaggerSpeed(STAGGERSPEED);
		setStaggerDecay(STAGGERDECAY);
		loadImage();
	}
	private void loadImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/funnyman.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		setSprite(img);
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
	public void setStaggerSpeed(int staggerSpeed) {
		if (staggerSpeed < 0) {
			staggerSpeed = 0;
		}
		this.staggerSpeed = staggerSpeed;
	}
	@Override
	public int getStaggerSpeed() {
		return staggerSpeed;
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
	public void stagger(int direction) {
		Velocity stagger = new Velocity();
		stagger.setDirection(direction);
		stagger.setDecayRate(getStaggerDecay());
		stagger.setSpeed(getStaggerSpeed());
		stagger.setTimeScale(getTimeScale());
		addVelocity(stagger);
	}
}
