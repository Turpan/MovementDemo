package movement;

public abstract class Entity {
	double positionX;
	double positionY;
	void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	void setPositionY(double positionY) {
		this.positionY = positionY;
	}
	public double getPositionX() {
		return positionX;
	}
	public double getPositionY() {
		return positionY;
	}
}
