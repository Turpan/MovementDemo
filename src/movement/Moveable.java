package movement;

import java.util.ArrayList;
import java.util.Iterator;

import movement.Vectors.Acceleration;
import movement.Vectors.Force;
import movement.Vectors.Velocity;

public abstract class Moveable extends Entity implements Collidable{
//	int turnSpeed;
	double timeScale;
	Velocity velocity = new Velocity();
	ArrayList<Acceleration> accelerations = new ArrayList<Acceleration>();
	double mass; 						// don't let this one equal 0.... If you want a default value, go with 1.
	double coefficientOfDrag;		
	double coefficientOfFriction; 	
	double coefficientOfRestitution; 	//Because CoR is kinda a terrible measure, in a collision, this value is averaged with the enemies
										//because physics doesn't actually have any more direct concept of the 'bounciness' of an object in isolation
																				//yet...
	public ArrayList<Acceleration> getAccelerations() {
		return accelerations;
	}
	public void addAcceleration(Acceleration acceleration) {
		getAccelerations().add(acceleration);
	}
	public void removeAcceleration(Acceleration acceleration) {
		getAccelerations().remove(acceleration);
	}
	protected void setAccelerations(ArrayList<Acceleration> accelerations) {
		this.accelerations = accelerations;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
	public double getMass() {
		return mass;
	}
	public void setCoD(double CoD) { //Coefficient of Drag
		this.coefficientOfDrag = CoD;
	}
	public double getCoD() {
		return coefficientOfDrag;
	}
	public void setCoF(double CoF) { // Coefficient of Friction
		this.coefficientOfFriction = CoF;
	}
	public double getCoF() {
		return coefficientOfFriction;
	}
	public void setCoR(double CoR) { // Coefficient of Friction
		this.coefficientOfRestitution = CoR;
	}
	public double getCoR() {
		return coefficientOfRestitution;
	}
	public Velocity getVelocity(){
		return velocity;
	}
	public void setVelocity(Velocity v) {
		velocity = v;
	}
	public double getTimeScale() {
		return timeScale;
	}
	public void setTimeScale(double timeScale) {
		if (timeScale < 0) {
			timeScale = 0;
		}
		this.timeScale = timeScale;
	}
	
	
	
	public void addVelocity(Velocity velocity) {
		getVelocity().addVector(velocity);
	}
	public boolean isStopped() {
		return (getVelocity().getMagnitude() == 0);
	}
	//////////////////////////////////////////////////////////////////////////////////
	
	
	protected void move() {
		setPositionX((getPositionX() + getVelocity().getX() * getTimeScale()));
		setPositionY((getPositionY() + getVelocity().getY() * getTimeScale()));
	}
	public void applyForce(Force force) {
		addAcceleration(force.getAcceleration(getMass()));
	}
	public void accelerate(Acceleration acceleration) {
		var nextVelocity = new Velocity (getVelocity().getX() + acceleration.getX() * getTimeScale() , 
								   		 getVelocity().getY() + acceleration.getY() * getTimeScale());
		setVelocity(nextVelocity);
	}
	protected void applyFriction() {
		if (!isStopped()){
			Force friction = new Force ();	
			double magnitude = getCoF() * getMass()+ getCoD() * Math.pow(getVelocity().getMagnitude(),2);
			double direction = getVelocity().getDirection() + 180;
			friction.setMagnitude(magnitude);
			friction.setDirection(direction);
			friction.setDuration(1);
			applyForce(friction);
		}
		
	}
	@Override
	public void collisionWith(Entity entity) {
		var outputForce = new Force();
		if (entity instanceof Moveable) {
			var collider= (Moveable) entity;
			var relativeSpeedCollidee = getVelocity().getMagnitude()* Math.sin(Math.toRadians(getVelocity().getDirection() + collider.getVelocity().getDirection()));
			var relativeSpeedCollider = collider.getVelocity().getMagnitude() * Math.sin(Math.toRadians(collider.getVelocity().getDirection() + getVelocity().getDirection()));
			outputForce.setMagnitude(((getMass() * relativeSpeedCollidee + collider.getMass() * relativeSpeedCollider + (collider.getCoR() + getCoR())/2 *getMass() *(relativeSpeedCollidee-relativeSpeedCollider)) *Math.pow(getTimeScale(),-1))/getMass() + collider.getMass());
			outputForce.setDirection(relativeSpeedCollider);
			outputForce.setDuration(1);
			applyForce(outputForce);
		}
		if (entity instanceof Wall) {
			var wall= (Wall) entity;
			outputForce.setMagnitude((getCoR() * wall.getBounciness() *getVelocity().getMagnitude() * Math.pow(getTimeScale(),-1)*getMass()* Math.sin(Math.toRadians(getVelocity().getDirection() + wall.getAngle())))+0.01); 
			outputForce.setDirection(wall.getAngle() - 90);
			outputForce.setDuration(1);
			applyForce(outputForce);
		}
	}
	//////////////////////////////////////////////////////////////////////////////////
	public void tick() {
		applyFriction();
		Iterator<Acceleration> iter = getAccelerations().iterator();
		Acceleration acceleration;
		while (iter.hasNext()) {	
			acceleration = iter.next();
			if (acceleration.getDuration() <= 0) {
				iter.remove();
			}
			accelerate(acceleration);
			acceleration.tick();
		}
		System.out.println(getVelocity().getMagnitude());
		move();
	}
}