package movement;

public interface Staggerable {
	public void setStaggerDecay(int staggerDecay);
	public int getStaggerDecay();
	public void stagger(int direction, int speed);
}
