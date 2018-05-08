package movement.Shapes;

public class Ellipse implements Shape{
	double width;
	double height;
	
	public Ellipse (double width, double height) {
		setWidth(width);
		setHeight(height);
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public double getCoDmodifier() {							//impacts CoD. The shape of the object has an effect!
		return 0.0;
	}
	public double getAngleAt(int xPosition, int yPosition) {	//uses implicit diff, using the formula x^2/(width/2)^2 + y^2/(height/2)^2 = 1
		return 0.0;
	}
}
