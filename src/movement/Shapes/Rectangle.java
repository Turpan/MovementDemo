package movement.Shapes;

public class Rectangle implements OutlineShape{
	double width;
	double height;
	
	public Rectangle (double width, double height) {
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
	public int getAngleAt(int xPosition, int yPosition) {
		return 0;
	}
}

