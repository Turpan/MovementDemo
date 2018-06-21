package movement;

import java.awt.image.BufferedImage;

//import movement.Shapes.OutlineShape;

public abstract class Entity {
	double[] position;
	double[] dimensions;
	BufferedImage sprite;
	//OutlineShape outline;	//determines collisions. Technically, doesn't at all map to the sprite

	public void setDimensions(double[] dimensions) {
		for (double dim : dimensions) {
			if (dim <0) {dim = 0;}
		}
		this.dimensions= dimensions;
	}
	public double[] getDimensions() {
		return dimensions;
	}
	public BufferedImage getSprite() {
		return sprite;
	}
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	public void setPosition(double[] position) {
		this.position = position;
	}
	public double[] getPosition() {
		return position;
	}
	/*public void setOutline(OutlineShape outline) {
		this.outline = outline;
	}
	public OutlineShape getOutline() {
		return outline;
	}*/
	
	
	
	public class MalformedEntityException extends Exception {
		private static final long serialVersionUID = 1L;

		public MalformedEntityException (String message) {
	        super (message);
	    }
	}
}
