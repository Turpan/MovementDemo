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
		var collideable = (Collidable) entity1;
		for (int i = position; i<entityList.size(); i++) {
			var entity2 = entityList.get(i);
			if (checkBoundsCollision(entity1, entity2)) {
				if (checkPixelCollision(entity1, entity2)) {
					collideable.collisionWith(entity2);
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
		var x = (int) Math.max(entity1.getPositionX(), entity2.getPositionX());
		var right = (int) Math.min(getEntityRight(entity1), getEntityRight(entity2));
		var y = (int) Math.max(entity1.getPositionY(), entity2.getPositionY());
		var bottom = (int) Math.min(getEntityBottom(entity1), getEntityBottom(entity2));
		for (int i=x; i<right; i++) {
			for (int j=y; j<bottom; j++) {
				/*var entity1Pixel = new Color(entity1.getCollisionMap().getRGB(x, y));
				var entity2Pixel = new Color(entity2.getCollisionMap().getRGB(x, y));
				if (entity1Pixel.equals(bitMaskColor) && entity2Pixel.equals(bitMaskColor)) {
					return true;
				}*/
				return true;
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
