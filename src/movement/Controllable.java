package movement;

public abstract class Controllable extends Moveable {
	int moveSpeed;
	int stopSpeed;
	int directionThreshold;
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
	public int getMoveSpeed() {
		return moveSpeed;
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
	public void tick(int direction) {
		if (direction == getDirection()) {
			movementTick(direction);
		} else if (direction != getDirection() && acceptableDirectionChange(direction)) {
			movementTick(direction);
		} else if (direction != getDirection() && stopped) {
			setDirection(direction);
			movementTick(direction);
		} else {
			stopTick();
		}
	}
	protected void stopTick() {
		setAcceleration(0-stopSpeed);
		if (getNextSpeed() <= 0) {
			setSpeed(0);
			setAcceleration(0);
			stopped = true;
		}
		move();
	}
	boolean acceptableDirectionChange(int direction) {
		return (directionThreshold >= Math.abs(distanceBetweenDegrees(direction, (int) getDirection())));
	}
	private void movementTick(int direction) {
		stopped = false;
		turnHandling(direction);
		setAcceleration(moveSpeed);
		move();
	}
	private void turnHandling(int direction) {
		if (getDirection() != direction) {
			if (stopped) {
				setDirection(direction);
			} else {
				turn(direction);
			}
		}
	}
}
