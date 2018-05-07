package movement;

public class Force extends Vector {
	public double duration;
	public boolean infinite;
	public Force(double x, double y, double duration) {
		super(x,y);
		this.duration = duration;
		infinite = false;
	}
	public Force() {
		this(0.0,0.0,0.0);
	}
	
	
	public boolean getInfinite() {
		return infinite;
	}
	public void setInfinite() {
		infinite = true;
	}
	public void setFinite() {
		infinite = false;
	}
	
	
	public double getDuration() {
		return duration;
	}
	public void setDuration( double duration) {
		this.duration = duration;
	}

	
	public Acceleration getAcceleration(double mass) {
		var output = new Acceleration();
		output.setMagnitude(getMagnitude()/mass);
		output.setDirection(getDirection());
		output.setDuration(getDuration());
		if (getInfinite()) {
			output.setInfinite();
		}
		return output;
	}
}