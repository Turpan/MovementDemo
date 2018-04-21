package movement;

public abstract class Wall extends Entity implements Bouncy {
	static double bounciness;
	static int angle;
	public void setBounciness(double percentage) {
		bounciness = percentage;
	}
	public double getBounciness() {
		return bounciness;
	}
	public void setAngle(int angle) {
		this.angle = angle;
	}
	public int getAngle() {
		return angle;
	}
	public Velocity getBounceVelocity (Velocity incomingVelocity) {
		incomingVelocity.setSpeed(incomingVelocity.getSpeed() * getBounciness());
		incomingVelocity.setDirection(angle + incomingVelocity.getDirection());
		return incomingVelocity;
	}
}
