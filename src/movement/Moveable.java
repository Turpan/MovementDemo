package movement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Moveable extends Entity {
	int turnSpeed;
	double timeScale;
	List<Velocity> velocityList = new ArrayList<Velocity>();
	public int getTurnSpeed() {
		return turnSpeed;
	}
	public List<Velocity> getVelocities(){
		return velocityList;
	}
	public void setTurnSpeed(int turnSpeed) {
		this.turnSpeed = turnSpeed;
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
		velocityList.add(velocity);
	}
	protected void calculatePosition(Velocity velocity) {
		setPositionX((getPositionX() + (velocity.getSpeed() * getTimeScale() * Math.cos(Math.toRadians(velocity.getDirection())))));
		setPositionY((getPositionY() + (velocity.getSpeed() * getTimeScale() * Math.sin(Math.toRadians(velocity.getDirection())))));
	}
	public void move() {
		Iterator<Velocity> iter = velocityList.iterator();
		while (iter.hasNext()) {
			Velocity velocity = iter.next();
			calculatePosition(velocity);
			velocity.tick();
			if (velocity.getSpeed() <= 0) {
				iter.remove();
			}
		}
	}
}
