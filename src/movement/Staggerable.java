package movement;

public interface Staggerable {
	public void setStaggerSpeed(int staggerSpeed);
	public int getStaggerSpeed();
	public void setStaggerDecay(int staggerDecay);
	public int getStaggerDecay();
	public void stagger(int direction);
}
