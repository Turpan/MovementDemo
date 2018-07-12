package movement.Vectors;

public class Acceleration extends Vector{
	boolean active = true;
	public Acceleration(double magnitude, double[] direction) throws MalformedVectorException {
		super(magnitude,direction);
	}
	public Acceleration() throws MalformedVectorException{
		super();
	}
	public boolean active() {
		return active;
	}
	public void deactivate() {
		active = false;
	}
}