package movement;

import java.util.List;
import java.util.ArrayList;

public class Velocity {
	int direction;
	double speed;
	double decayRate;
	double timeScale;
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getDecayRate() {
		return decayRate;
	}
	public void setDecayRate(double decayRate) {
		this.decayRate = decayRate;
	}
	public double getTimeScale() {
		return timeScale;
	}
	public void setTimeScale(double timeScale) {
		this.timeScale = timeScale;
	}
	public void tick() {
		setSpeed(getSpeed() - (getDecayRate() * getTimeScale()));
	}
	public static Velocity addVelocities(List<Velocity> velocities) {
		Velocity outputVelocity = new Velocity();
		int size = velocities.size();
		double maxVelocity = -1;
		double outputTimeScale;
		double outputDecayRate;
		switch (size){
			case 0:
				// set output to be all 0 velocity. so that you don't break methods entirely by running this output in all velocity cases for all lists of velocities
				outputVelocity.setSpeed(0);
				outputVelocity.setDirection(0);
				outputVelocity.setDecayRate(100);
				outputVelocity.setTimeScale(0);
			case 1:	// ofcourse, summing 1 vector outputs the summand
				outputVelocity = velocities.get(0); 
				break;
			case 2: // Slightly more efficient formula that removes two trig functions, kept for the likely common case of summing two vectors together
				Velocity v1 = velocities.get(0);
				Velocity v2 = velocities.get(1);
				outputVelocity.setSpeed(Math.sqrt(Math.pow(v1.getSpeed(), 2) + 
												  Math.pow(v2.getSpeed(), 2) +
												  2 * v1.getSpeed() * v2.getSpeed() * 
												  Math.cos(v2.getDirection()-v1.getDirection())));
				
				outputVelocity.setDirection((int)(v1.getDirection() + Math.atan2(
						v2.getSpeed() *	Math.sin(v2.getDirection()-v1.getDirection()),
						v2.getSpeed() * Math.cos(v2.getDirection()-v1.getDirection()))));
				// average timescale and decay rate
				for (Velocity v: velocities) {
					if (v.getSpeed() > maxVelocity) {
						maxVelocity = v.getSpeed();
						outputDecayRate = v.getDecayRate();
						outputTimeScale = v.getTimeScale();
				}
				}
				
				outputVelocity.setDecayRate(outputDecayRate);
				outputVelocity.setTimeScale(outputTimeScale);
				break;
			default: //i.e length greater than 2. Used a general formula for calculating resultant, due to easier scaling up. 
					 //Better than iterative uses of above method
			/*	List<ArrayList<Double>> cartesianVelocities = new ArrayList<ArrayList<Double>>(); //inner lists being the XY vectors of each V
				ArrayList<Double> entry = new ArrayList<Double>();
				double xComponent = 0;
				double yComponent = 0;
				for (Velocity v: velocities) {
					entry.add(v.getSpeed() * Math.cos(v.getDirection()));
					entry.add(v.getSpeed()* Math.sin(v.getDirection()));
					cartesianVelocities.add(entry);
					
					if (v.getDecayRate() > maxDecayRate) {
						maxDecayRate = v.getDecayRate();
					}if (v.getTimeScale() < minTimeScale || minTimeScale== -1) {
						minTimeScale = v.getTimeScale();
					}
				}for (ArrayList<Double> cartV : cartesianVelocities) {
					xComponent += cartV.get(0);
					yComponent += cartV.get(1);
				}
				
				outputVelocity.setDecayRate(maxDecayRate);
				outputVelocity.setTimeScale(minTimeScale);
				outputVelocity.setSpeed(Math.sqrt(Math.pow(xComponent,2) + Math.pow(yComponent, 2)));
			 	outputVelocity.setDirection((int)Math.atan2(xComponent, yComponent)); 				*/
				ArrayList<Velocity> newVelocities =  new ArrayList<Velocity>();
				if (!(velocities.isEmpty())){
					newVelocities.add(velocities.get(0));
					newVelocities.add(outputVelocity);
					velocities.remove(0);
					outputVelocity = Velocity.addVelocities(newVelocities);
					}
			}
				
		return outputVelocity;
	}
}
