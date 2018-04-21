package movement;

public abstract class Controllable extends Moveable {
	int maxSpeed;
	int moveSpeed;
	int stopSpeed;
	int directionThreshold;
	double direction;
	double speed;
	double acceleration;
	boolean stopped;
	public int getStopSpeed() {
		return stopSpeed;
	}
	public void setStopSpeed(int stopSpeed) {
		if (stopSpeed < 1 ) {
			stopSpeed = 1;
		}
		this.stopSpeed = stopSpeed;
	}
	public double getAcceleration() {
		return acceleration;
	}
	protected void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
	public int getMoveSpeed() {
		return moveSpeed;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public double getDirection() {
		return direction;
	}
	public void setDirection(double direction) {
		direction = adjustDegrees(direction);
		this.direction = direction;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		if (speed > maxSpeed) {
			speed = maxSpeed;
		} else if (speed < 0-maxSpeed) {
			speed = 0-maxSpeed;
		}
		this.speed = speed;
	}
	public void setMoveSpeed(int moveSpeed) {
		if (moveSpeed < 0) {
			moveSpeed = 0;
		} else if (moveSpeed > getMaxSpeed()) {
			moveSpeed = getMaxSpeed();
		}
		this.moveSpeed = moveSpeed;
	}
	public int getDirectionThreshold() {
		return directionThreshold;
	}
	public void setDirectionThreshold(int directionThreshold) {
		this.directionThreshold = directionThreshold;
	}
	@Override
	public void move() {
		var velocity = new Velocity();
		velocity.setDirection((int) getDirection());
		velocity.setSpeed(getSpeed());
		calculatePosition(velocity);
		super.move();
	}
	public void tick(int direction) {
		if (direction == getDirection()) {
			movementTick(direction);
		} else if (direction != getDirection() && acceptableDirectionChange(direction)) {
			movementTick(direction);
		} else if (direction != getDirection() && stopped) {
			movementTick(direction);
		} else {
			stopTick();
		}
	}
	private double getNextSpeed() {
		return getSpeed() + (getAcceleration() * getTimeScale());
	}
	public void stopTick() {
		setAcceleration(0-stopSpeed);
		if (getNextSpeed() <= 0) {
			setSpeed(0);
			setAcceleration(0);
			stopped = true;
		}
		setSpeed(getNextSpeed());
		move();
	}
	boolean acceptableDirectionChange(int direction) {
		return (directionThreshold >= Math.abs(distanceBetweenDegrees(direction, (int) getDirection())));
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
	private void movementTick(int direction) {
		stopped = false;
		turnHandling(direction);
		setAcceleration(moveSpeed);
		setSpeed(getNextSpeed());
		move();
	}
	private void turnHandling(int direction) {
		if (getDirection() != direction) {
			if (getSpeed() == 0) {
				setDirection(direction);
			} else {
				turn(direction);
			}
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
