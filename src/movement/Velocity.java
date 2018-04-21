package movement;

public class Velocity {
	int direction;
	double speed;
	double decayRate;
	double timeScale;
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getDecayRate() {
		return decayRate;
	}
	public void setDecayRate(double decayRate) {
		this.decayRate = decayRate;
	}
	public double getTimeScale() {
		return timeScale;
	}
	public void setTimeScale(double timeScale) {
		this.timeScale = timeScale;
	}
	public void tick() {
		setSpeed(getSpeed() - (getDecayRate() * getTimeScale()));
	}
}
