package movement.Vectors;

public class Force extends Vector {
	public Force(double magnitude, double direction) {
		super(magnitude,direction);
	}
	public Force() {
		super();
	}
	public Acceleration getAcceleration(double mass) {
		var output = new Acceleration();
		output.setX(getX()/mass);
		output.setY(getY()/mass);
		return output;
	}
}