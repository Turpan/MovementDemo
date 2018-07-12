package movement;

import java.awt.image.BufferedImage;

import movement.Vectors.Vector;

public abstract class Entity {
	private float[] position;
	private double[]dimensions;
	private BufferedImage sprite;

	public void setDimensions(double[] dimensions) throws MalformedEntityException {
		if (dimensions.length != Vector.DIMENSIONS) {
			throw new MalformedEntityException("attempt to set dimensions to wrong number of dimensions");
		}
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
	public void setPosition(float[] position) throws MalformedEntityException {
		if (position.length != Vector.DIMENSIONS) {
			throw new MalformedEntityException("attempt to set position to wrong number of dimensions");
		}
		this.position = position;
	}
	public float[] getPosition() {
		return position;
	}
	abstract public boolean inside(float[] point);
	
	
	public class MalformedEntityException extends Exception {
		private static final long serialVersionUID = 1L;

		public MalformedEntityException (String message) {
	        super (message);
	    }
	}
}
