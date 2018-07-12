package movement.Shapes;

import java.util.ArrayList;

import movement.Vectors.Vector;
import movement.Vectors.Vector.MalformedVectorException;

public class Triangle implements OutlineShape {
	double[] dimensions;
	
	public Triangle (double[] dimensions) {
		setDimensions(dimensions);
	}
	public void setDimensions(double[] dimensions) {
		this.dimensions= dimensions;
	}
	public double[] getDimensions() {
		return dimensions;
	}
	public double getCoDmodifier() {							//impacts CoD. The shape of the object has an effect!
		return 0.0;
	}
	public Vector getNormal(float[] position) throws MalformedVectorException {
		return new Vector();
	}
	@Override
	public ArrayList<float[]> getCollisionNet() {
		return null;
	}
	@Override
	public boolean inside(float[] position) {
		return false;
	}
	@Override
	public void initialiseCollisionNet() {
		
	}
	@Override
	public float[] getPointOnEdge(float[] position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double getDistanceIn(float[] position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
