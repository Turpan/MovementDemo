package movement;

public abstract class Entity {
	int positionX;
	int positionY;
	void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
		return positionY;
	}
}
