package movement.Vectors;


public abstract class Vector {
/** Creates a generic, 2 dimensional vector, expressed in x and y components. Has commands for getting speed/direction and adding**/
	double xComponent;
	double yComponent;

	public Vector(double xMagnitude, double yMagnitude) {
		setX(xMagnitude);
		setY(yMagnitude);
	}
	public Vector() {
		setX(0);
		setY(0);
	}
	public void setX(double xMagnitude) {
		this.xComponent = xMagnitude;
	}
	public void setY(double yMagnitude) {
		this.yComponent = yMagnitude;
	}
	public double getX() {
		return xComponent;
	}
	public double getY() {
		return yComponent;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	
	public double getDirection() {
		return (double) Math.toDegrees(Math.atan2(getY(),getX()));
	}
	public void setDirection(double direction) {
		double speed = getMagnitude();
		if (speed == 0) {
		//	throw new ArithmeticException("can't store direction when speed == 0");
		}else { 
			setX(speed * Math.cos(Math.toRadians(direction)));
			setY(speed * Math.sin(Math.toRadians(direction)));
		}
	}
	public double getMagnitude() {
		return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
	}
	public void setMagnitude(double magnitude) { //WARNING: If you lower speed to 0, then start moving again in terms of speed you /MUST/ set direction anew. Direction can't be stored when still.
		double direction = getDirection(); 
		setX(Math.abs(magnitude) * Math.cos(Math.toRadians(direction)));
		setY(Math.abs(magnitude) * Math.sin(Math.toRadians(direction)));
	}
	
	///////////////////////////////////////////////////////////////////////////////
	
	public void addVector (Vector vToAdd) {
		setX(getX() + vToAdd.getX());
		setY(getY() + vToAdd.getY());
	}
	public static boolean vectorMovingAway(Vector v, int angle) { //Checks if a vector is moving into or away from a surface
		int vectorAngle = Math.floorMod((int)v.getDirection(),360);
		angle = Math.floorMod(angle,360);
		return (angle>180 && vectorAngle>=angle-180 && vectorAngle <= angle) || (angle<=180 && (vectorAngle <=angle || vectorAngle >= angle+180));
		
	}
	public static double getComponentInto(Vector v , int angle) {//gets the component vector pushing into an angled surface. Surprisingly common
		
		if (vectorMovingAway(v, angle)){
			return 0;
		}
		return v.getMagnitude() *Math.sin(Math.toRadians(v.getDirection() + angle));
	}
}