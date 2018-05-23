package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Shapes.*;
import movement.SelfPropelled;
import movement.Dashing;
import movement.Vectors.Force;

public class Player extends SelfPropelled implements Dashing {

	static final double MASS =20;
	static final double BASEMOVEFORCE = 100;
	static final double TIMESCALE = 0.1;
	static final double COEFFICIENT_OF_RESTITUTION = 1;	//'bounciness' Used for collisions. See Controllable. Not the whole story, as I've attempted to disallow objects enterring other objects, regardless of CoR
	static final double COEFFICIENT_OF_DRAG = 0.0;			//coefficient of proportionality between quadratic drag and speed
	static final double  COEFFICIENT_OF_FRICTION = 0.0; 	//the coefficient of proportionality between the constant drag and mass
	static final int DASHCOOLDOWN = 10;
	static final int DASHLENGTH = 10;
	static final int DASHMAGNITUDE = 1000;
	static Force DASHFORCE = new Force(DASHMAGNITUDE, 0); 
	
	int dashCoolDown;
	double dashDirection;
	int dashCounter; //this will be used for checking i frames
	double dashCoolDownCount;
	public Player() throws MalformedEntityException {
		setBaseMoveForce(BASEMOVEFORCE);
		setMass(MASS);
		setTimeScale(TIMESCALE);
		setDashCoolDown(DASHCOOLDOWN);
		setCoF(COEFFICIENT_OF_FRICTION);
		setCoD(COEFFICIENT_OF_DRAG);
		setCoR(COEFFICIENT_OF_RESTITUTION);
		loadImage();
		setOutline((OutlineShape)(new Ellipse(getWidth(), getHeight())));
	}
	private void loadImage() throws MalformedEntityException {
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
	public void moveTick(){
		dashCoolDownTick();
		dashTick();
		if (isDashing()) {
			applyForce(DASHFORCE);
		}		
		super.moveTick();
	}
	@Override
	public boolean canDash() {
		return (getDashCoolDownCount() <= 0);
	}
	@Override
	public boolean isDashing() {
		return (getDashCounter() > 0);
	}
	@Override
	public void setDashCoolDown(int dashCoolDown) {
		this.dashCoolDown = dashCoolDown;
	}
	@Override
	public void setDashCounter(int dashCounter) {
		this.dashCounter= dashCounter;
	}
	@Override
	public int getDashCoolDown() {
		return dashCoolDown;
	}
	@Override
	public int getDashCounter() {
		return dashCounter;
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
	public void dashTick() {
		setDashCounter(getDashCounter() - 1);
	}
	@Override
	public void dash(int direction) {
		if (!canDash()) {
			return;
		}
		stop();
		setDashCounter(DASHLENGTH);
		DASHFORCE.setDirection(direction);
		setDashCoolDownCount(getDashCoolDown());
	}
}
