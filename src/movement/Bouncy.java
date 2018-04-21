package movement;

public interface Bouncy {
	void setBounciness(double percentage);
	double getBounciness();
	Velocity getBounceVelocity(Velocity incomingVelocity);
}
