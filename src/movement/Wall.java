package movement;

public abstract class Wall extends Entity implements Bouncy {
	double bounciness;
	int angle;
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
}
