package movement;

import java.awt.image.BufferedImage;

public abstract class Entity {
	double positionX;
	double positionY;
	int width;
	int height;
	BufferedImage sprite;
	BufferedImage collisionMap;
	public BufferedImage getCollisionMap() {
		return collisionMap;
	}
	public void setCollisionMap(BufferedImage collisionMap) {
		this.collisionMap = collisionMap;
	}
	public int getWidth() {
		return width;
	}
	private void setWidth(int width) {
		if (width < 0) {
			width = 0;
		}
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	private void setHeight(int height) {
		if (height < 0) {
			height = 0;
		}
		this.height = height;
	}
	public BufferedImage getSprite() {
		return sprite;
	}
	public void setSprite(BufferedImage sprite) {
		setWidth(sprite.getWidth());
		setHeight(sprite.getHeight());
		this.sprite = sprite;
	}
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}
	public double getPositionX() {
		return positionX;
	}
	public double getPositionY() {
		return positionY;
	}
}
