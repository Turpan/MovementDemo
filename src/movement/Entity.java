package movement;

import java.awt.image.BufferedImage;

import movement.Shapes.OutlineShape;

public abstract class Entity {
	double positionX;
	double positionY;
	int width;
	int height;
	BufferedImage sprite;
	BufferedImage collisionMap;
	OutlineShape outline;
	public BufferedImage getCollisionMap() {
		return collisionMap;
	}
	public void setCollisionMap(BufferedImage collisionMap) throws MalformedEntityException {
		this.collisionMap = collisionMap;
		if (collisionMap.getWidth() != getWidth() || collisionMap.getHeight() != getHeight()) {
			throw new MalformedEntityException("collisionMap of different dimensions to sprite");
		}
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
	public void updatePosition(double positionX, double positionY) {
		setPositionX(positionX);
		setPositionY(positionY);
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
	public void setOutline(OutlineShape outline) {
		this.outline = outline;
	}
	public OutlineShape getOutline() {
		return outline;
	}
	
	
	
	public class MalformedEntityException extends Exception {
		private static final long serialVersionUID = 1L;

		public MalformedEntityException (String message) {
	        super (message);
	    }
	}
}
