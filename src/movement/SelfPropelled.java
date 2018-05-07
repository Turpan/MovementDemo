package movement;

import java.util.ArrayList;

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
		movementForce.setDuration(1);
		applyForce(movementForce);
		
	}
	public void stop() {
		accelerations = new ArrayList<Acceleration>();
		getVelocity().setMagnitude(0);
	}

}