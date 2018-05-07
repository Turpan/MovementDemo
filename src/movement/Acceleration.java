package movement;

public class Acceleration extends Vector{
	public double duration; //is decremented everytime the acceleration is called on, and when it runs out, is removed. For continually updating
							//vectors, will need to have duration 1.
	public boolean infinite;
	
	public Acceleration(double x, double y, double duration) {
		super(x,y);
		this.duration = duration;
		infinite = false;
	}
	public Acceleration(){
		this (0.0,0.0,0.0);
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
	public void tick() {
		if (!getInfinite()){
		duration -= 1;
		}
	}
}