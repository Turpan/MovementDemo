package movement;

import java.awt.Color;
import java.util.List;

public class CollisionEngine {
	static final Color bitMaskColor = Color.RED;
	public void checkCollision(List<Entity> entityList) {
		for (var entity1 : entityList) {
			if (entity1 instanceof Collidable) {
				collisionDetection(entityList, entity1);
			}
		}
	}
	private void collisionDetection(List<Entity> entityList, Entity entity1) {
		int position = entityList.indexOf(entity1) + 1;
		var collideable1 = (Collidable) entity1;
		for (int i = position; i<entityList.size(); i++) {
			var entity2 = entityList.get(i);
			if (checkBoundsCollision(entity1, entity2)) {
				if (checkPixelCollision(entity1, entity2)) {
					collideable1.collisionWith(entity2);
					if (entity2 instanceof Collidable) {
						var collidable2 = (Collidable) entity2;
						collidable2.collisionWith(entity1);
					}
				}
			}
		}
	}
	private boolean checkBoundsCollision(Entity entity1, Entity entity2) {
		var right1 = getEntityRight(entity1);
		var right2 = getEntityRight(entity2);
		var bottom1 = getEntityBottom(entity1);
		var bottom2 = getEntityBottom(entity2);
		return (entity1.getPositionX() < right2 && right1 > entity2.getPositionX() 
				&& entity1.getPositionY() < bottom2 && bottom1 > entity2.getPositionY()); 
	}
	private boolean checkPixelCollision(Entity entity1, Entity entity2) {
		var leftEntity = entity1.getPositionX()<entity2.getPositionX() ? entity1:entity2;
		var topEntity =entity1.getPositionY()<entity2.getPositionY() ? entity1:entity2;

		//top of rectangle of collision, relative screen
		var xTop = (int) Math.max(entity1.getPositionX(), entity2.getPositionX());
		var yTop = (int) Math.max(entity1.getPositionY(), entity2.getPositionY());
		
		//dimensions of rectangle of collision
		var width = (int) Math.min(getEntityRight(entity1), getEntityRight(entity2)) - xTop;
		var height = (int) Math.min(getEntityBottom(entity1), getEntityBottom(entity2)) - yTop;
		
		// coords of rectangle of collision, relative to each entity
		var xTopInLeft = (int) leftEntity.getPositionX() - xTop;
		var yTopInTop = (int) topEntity.getPositionY() - yTop;
		//top is also left, bottom is also right
		
		int entity1x = 0;
		int entity2x = 0;
		int entity1y = 0;
		int entity2y = 0;
		
		if (entity1.getPositionY()<entity2.getPositionY()) { //if entity 1 is the top entity
			entity1y = yTopInTop;
		} else {
			entity2y = yTopInTop;
		}if (entity1.getPositionX()<entity2.getPositionX()) { //if entity 1 is the left entity
			entity1x = xTopInLeft;
		} else {
			entity2x = xTopInLeft;
		}
		//note, no need to assign values to the right/bottom entites, as they default to 0, and that's what they need to be.
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				var entity1Pixel = new Color(entity1.getCollisionMap().getRGB(entity1x, entity1y));
				var entity2Pixel = new Color(entity2.getCollisionMap().getRGB(entity2x, entity2y));
				if (entity1Pixel.equals(bitMaskColor) && entity2Pixel.equals(bitMaskColor)) {
					return true;
				}
				entity1y++;entity2y++;
			entity1x++;entity2x++;
			}
		}
		return false;
	}
	private double getEntityRight(Entity entity) {
		return entity.getPositionX() + entity.getWidth();
	}
	private double getEntityBottom(Entity entity) {
		return entity.getPositionY() + entity.getHeight();
	}
}
