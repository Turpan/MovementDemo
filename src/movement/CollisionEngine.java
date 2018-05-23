package movement;

import java.awt.Color;
import java.util.List;

import movement.Vectors.Acceleration;
import movement.Vectors.Force;
import movement.Vectors.Vector;

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
		for (int i = position; i<entityList.size(); i++) {
			var entity2 = entityList.get(i);
			if (checkBoundsCollision(entity1, entity2)) {
				var collisionLocation = exactCollisionPosition(entity1, entity2);
				if (collisionLocation != null) {
					collision(entity1, entity2, collisionLocation);
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
	private int[] exactCollisionPosition(Entity entity1, Entity entity2) {
		var leftEntity = entity1.getPositionX()<entity2.getPositionX() ? entity1:entity2;
		var topEntity =entity1.getPositionY()<entity2.getPositionY() ? entity1:entity2;

		//top left of rectangle of collision, relative screen
		var xTop = (int) Math.max(entity1.getPositionX(), entity2.getPositionX());
		var yTop = (int) Math.max(entity1.getPositionY(), entity2.getPositionY());
		
		//dimensions of rectangle of collision
		var width = (int) Math.min(getEntityRight(entity1), getEntityRight(entity2)) - xTop;
		var height = (int) Math.min(getEntityBottom(entity1), getEntityBottom(entity2)) - yTop;
		
		// coords of rectangle of collision, relative to each entity
		var xTopInLeft = xTop -(int) leftEntity.getPositionX();
		var yTopInTop = yTop - (int) topEntity.getPositionY() ;
		
		int entity1x = 0;
		int entity2x = 0;		//Don't need a top/current. Only loops through once, only needs initialisation once.
		int entity1yTop = 0;
		int entity2yTop = 0;
		int entity1yCurrent = 0;
		int entity2yCurrent = 0;
		
		if (entity1.getPositionY()<entity2.getPositionY()) { //if entity 1 is the top entity
			entity1yTop = yTopInTop;
		} else {
			entity2yTop = yTopInTop;
		}if (entity1.getPositionX()<entity2.getPositionX()) { //if entity 1 is the left entity
			entity1x = xTopInLeft;
		} else {
			entity2x = xTopInLeft;
		}
		//note, no need to assign values to the right/bottom entites, as they default to 0, and that's what they need to be.
		
		//just loops through all the pixels in the rectangle of collision relative to both entites and sees if they collide
		for (int i=0; i<width; i++) {
			entity1yCurrent = entity1yTop;
			entity2yCurrent = entity2yTop;
			for (int j=0; j<height; j++) {
				var entity1Pixel = new Color(entity1.getCollisionMap().getRGB(entity1x, entity1yCurrent));
				var entity2Pixel = new Color(entity2.getCollisionMap().getRGB(entity2x, entity2yCurrent));
				if (entity1Pixel.equals(bitMaskColor) && entity2Pixel.equals(bitMaskColor)) {
					int[] output = {entity1x, entity1yCurrent, entity2x, entity2yCurrent};
					return output;
				}
				entity1yCurrent++;entity2yCurrent++;
			}
			entity1x++;entity2x++;
			}
		return null;
	}
	private double getEntityRight(Entity entity) {
		return entity.getPositionX() + entity.getWidth();
	}
	private double getEntityBottom(Entity entity) {
		return entity.getPositionY() + entity.getHeight();
	}
	
	
	public void collision(Entity object1, Entity object2, int[] collisionLocation) {
		if (object1 instanceof Moveable && object2 instanceof Moveable) {
			collisionBetween((Moveable)object1, (Moveable)object2, collisionLocation);
		}
		if ((object2 instanceof Wall)&&(object1 instanceof Moveable)||((object1 instanceof Wall) && (object2 instanceof Moveable)) ) {
			if (object1 instanceof Wall) {wallCollision((Wall)object1, (Moveable)object2);}
			else {wallCollision((Wall)object2, (Moveable)object1);}
		}
	}
	private static void collisionBetween(Moveable object1, Moveable object2, int[] collisionLocation) {

		var outputForce1 = new Force();
		var outputForce2 = new Force();
		
		int collisionAngle1 =  object1.getOutline().getAngleAt(collisionLocation[0], collisionLocation[1]);
		int collisionAngle2 = object2.getOutline().getAngleAt(collisionLocation[2], collisionLocation[3]);
		
		var CoR_Effect = (1+(object1.getCoR() * object2.getCoR()))/2; //you gotta average it with 1, because otherwise a value of 0 would violate conservation of momentum. To get objects that didn't pass through things (barring the anti-acceleration forces), you'd need a CoR_Effect of -1)
		var timeScaleInverse = 1/((object1.getTimeScale()+ object2.getTimeScale())/2); //this averages the two timescales? Honestly, collisions between objects of different timescales don't /really/ make sense. Pretty sure it's planned to have timescale be universal.
		//^^^^^ FIX TIMESCALES, MUST BE UNIVERSAL!! ^^^^^^
		double massRatio1 = object1.getMass()/object2.getMass();
		double massRatio2 = 1/massRatio1;
		System.out.println(CoR_Effect);
		var collisionForce1 = new Force(CoR_Effect *timeScaleInverse * Vector.getComponentInto(object1.getVelocity(),collisionAngle2)* object1.getMass() * (1-((1-massRatio1)/(1+massRatio1))),
										Vector.vectorMovingAway(object1.getVelocity() ,collisionAngle2) ? collisionAngle1-90 : collisionAngle1+90);
		var collisionForce2 = new Force(CoR_Effect * timeScaleInverse* Vector.getComponentInto(object2.getVelocity(),collisionAngle1)* object2.getMass() * (1-((1-massRatio2)/(1+massRatio2))),
										Vector.vectorMovingAway(object2.getVelocity() ,collisionAngle1) ? collisionAngle2-90 : collisionAngle2+90);
		
		var temp = new Force();
		for (Acceleration a: object1.getAccelerations()) {
			temp.setMagnitude(object1.getMass()*Vector.getComponentInto(a, collisionAngle2));
			temp.setDirection(collisionAngle1+ 90);
			collisionForce1.addVector(temp);
		}
		for (Acceleration a: object2.getAccelerations()) {
			temp.setMagnitude(object2.getMass()*Vector.getComponentInto(a, collisionAngle1));
			temp.setDirection(collisionAngle2+ 90);
			collisionForce2.addVector(temp);
		}
		
		outputForce1.addVector(collisionForce1);
		outputForce2.addVector(collisionForce2);
		collisionForce1.setDirection(collisionForce1.getDirection() + 180);
		collisionForce2.setDirection(collisionForce2.getDirection() + 180);
		outputForce1.addVector(collisionForce2);
		outputForce2.addVector(collisionForce1);
		
		object1.applyForce(outputForce1);
		object2.applyForce(outputForce2);
	}
	private static void wallCollision(Wall w, Moveable m) {
		var outputForce = new Force((1+m.getCoR()) * w.getBounciness() *Vector.getComponentInto(m.getVelocity(), w.getAngle()) * Math.pow(m.getTimeScale(),-1)*m.getMass(),
									w.getAngle() - 90);
		var temp = new Force();
		for (Acceleration a: m.getAccelerations()) {
			temp.setMagnitude(m.getMass()*Vector.getComponentInto(a, w.getAngle()));
			temp.setDirection(w.getAngle() - 90);
			outputForce.addVector(temp);
		}
		m.applyForce(outputForce);
	}
}
