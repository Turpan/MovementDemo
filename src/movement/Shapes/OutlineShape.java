package movement.Shapes;

public interface OutlineShape {
	public double getCoDmodifier();							//impacts CoD. The shape of the object has an effect!
	public int getAngleAt(int xPosition, int yPosition); //if an object bounces off the shape here, what is the angle of the shape at this point?
	public double getWidth();
}															//refers more to the location relative the centre? Not entirely sure how I'm implementing this
