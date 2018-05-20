package movement.Vectors;

public class Force extends Vector {
	public boolean infinite;
	public Force(double x, double y) {
		super(x,y);
		infinite = false;
	}
	public Force() {
		this(0.0,0.0);
	}
	public Acceleration getAcceleration(double mass) {
		var output = new Acceleration();
		output.setX(getX()/mass);
		output.setY(getY()/mass);
		return output;
	}
}