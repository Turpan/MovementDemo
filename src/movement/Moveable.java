package movement;

public abstract class Moveable extends Entity {
	int maxSpeed;
	int turnSpeed;
	double direction; //in degrees
	double speed;
	double acceleration;
	double timeScale;
	public int getTurnSpeed() {
		return turnSpeed;
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
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public double getSpeed() {
		return speed;
	}
	public double getDirection() {
		return direction;
	}
	public double getAcceleration() {
		return acceleration;
	}
	protected void setDirection(double direction) {
		this.direction = adjustDegrees(direction);
	}
	protected void setMaxSpeed(int maxSpeed) {
		if (maxSpeed < 0) {
			maxSpeed = 0;
		}
		this.maxSpeed = maxSpeed;
	}
	protected void setSpeed(double speed) {
		if (speed > maxSpeed) {
			speed = maxSpeed;
		} else if (speed < 0-maxSpeed) {
			speed = 0 - maxSpeed;
		}
		this.speed = speed;
	}
	protected void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
	protected void calculateSpeed() {
		setSpeed(getNextSpeed());
	}
	protected double getNextSpeed() {
		return getSpeed() + (getAcceleration() * getTimeScale());
	}
	protected void calculatePosition() {
		setPositionX((getPositionX() + (getSpeed() * getTimeScale() * Math.cos(Math.toRadians(getDirection())))));
		setPositionY((getPositionY() + (getSpeed() * getTimeScale() * Math.sin(Math.toRadians(getDirection())))));
	}
	public void move() {
		calculateSpeed();
		calculatePosition();
	}
	public void turn(int direction) {
		direction = (int) adjustDegrees(direction);
		int distance = distanceBetweenDegrees((int) getDirection(), direction);
		if (Math.abs(distance) <= (getTurnSpeed() * getTimeScale())) {
			setDirection(direction);
			return;
		}
		if (distance < 0) { //negative
			setDirection(adjustDegrees(getDirection() + (0-getTurnSpeed() * getTimeScale())));
		} else { //positive
			setDirection(adjustDegrees(getDirection() + (getTurnSpeed() * getTimeScale())));
		}
	}
	public int distanceBetweenDegrees(int positionA, int positionB) {
		int distanceA = incrementThroughDegrees(positionA, positionB, -1);
		int distanceB = incrementThroughDegrees(positionA, positionB, 1);
		return (Math.abs(distanceA) < Math.abs(distanceB)) ? distanceA : distanceB;
	}
	private int incrementThroughDegrees(int countPosition, int positionB, int increment) {
		boolean pointFound = false;
		int counter = 0;
		while (!pointFound) {
			countPosition += increment;
			counter+= increment;
			countPosition = (int) adjustDegrees(countPosition);
			if (countPosition == positionB) {
				pointFound = true;
			}
		}
		return counter;
	}
	public double adjustDegrees(double degree) {
		while (degree < 0 || degree >= 360) {
			if (degree >= 360) {
				degree -= 360;
			} else if (degree < 0) {
				degree += 360;
			}
		}
		return degree;
	}
}
