package movement;

public interface Dashing {
	boolean canDash();
	boolean isDashing();
	void setDashCoolDown(int dashCoolDown);
	int getDashCoolDown();
	void setDashCoolDownCount(double dashCoolDownCount);
	double getDashCoolDownCount();
	void setDashCounter(double dashCounter);
	double getDashCounter();
	void setDashDirection(double dashDirection);
	double getDashDirection();
	void dashCoolDownTick();
	void dashTick();
	void dash(int direction);
}
