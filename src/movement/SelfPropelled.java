package movement;

import java.util.ArrayList;

import movement.Vectors.Acceleration;
import movement.Vectors.Force;

public abstract class SelfPropelled extends Moveable {
	double baseMoveForce;

	public double getBaseMoveForce() {
		return baseMoveForce;
	}
	public void setBaseMoveForce(double baseMoveForce) {
		if (baseMoveForce < 0) {
			baseMoveForce = 0;
		}
		this.baseMoveForce = baseMoveForce;
	}
	public void locomote(int direction) {
		var movementForce = new Force();
		movementForce.setMagnitude(baseMoveForce);
		movementForce.setDirection(direction);
		applyForce(movementForce);
		
	}

}