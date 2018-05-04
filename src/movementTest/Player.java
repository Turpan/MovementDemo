package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Controllable;
import movement.Dashing;
import movement.Force;

public class Player extends Controllable implements Dashing {

	static final double MASS = 10;
	static final double BASEMOVEFORCE = 40;
	static final double TIMESCALE = 0.1;
	static final double COEFFICIENT_OF_RESTITUTION = 0.9;	//'bounciness' Used for collisions. See Controllable.
	static final double COEFFICIENT_OF_DRAG = 0.05;			//coefficient of proportionality between quadratic drag and speed
	static final double  COEFFICIENT_OF_FRICTION = 0.25; 	//the coefficient of proportionality between the constant drag and mass
	static final int DASHCOOLDOWN = 10;
	static final int DASHLENGTH = 15;
	static final int DASHFORCE = 1000;
	
	int dashCoolDown;
	double dashDirection;
	double dashCounter; //this will be used for checking i frames
	double dashCoolDownCount;
	public Player() {
		setBaseMoveForce(BASEMOVEFORCE);
		setMass(MASS);
		setTimeScale(TIMESCALE);
		setDashCoolDown(DASHCOOLDOWN);
		setCoF(COEFFICIENT_OF_FRICTION);
		setCoD(COEFFICIENT_OF_DRAG);
		setCoR(COEFFICIENT_OF_RESTITUTION);
		loadImage();
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
	@Override
	public void tick(){
		super.tick();
		dashCoolDownTick();
	}
	@Override
	public boolean canDash() {
		return (getDashCoolDownCount() <= 0);
	}
	@Override
	public boolean isDashing() {
		return (dashCounter > 0);
	}
	@Override
	public void setDashCoolDown(int dashCoolDown) {
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
	public void dashCoolDownTick() {
		setDashCoolDownCount(getDashCoolDownCount() - getTimeScale());
	}
	@Override
	public void dash(int direction) {
		if (!canDash()) {
			return;
		}
		var dashForce = new Force();
		dashForce.setMagnitude(DASHFORCE);
		dashForce.setDirection(direction);
		dashForce.setDuration(DASHLENGTH);
		stop();
		applyForce(dashForce);
		setDashCoolDownCount(getDashCoolDown());
	}
}
