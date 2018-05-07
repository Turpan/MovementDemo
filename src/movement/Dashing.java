package movement;

public interface Dashing {
	boolean canDash();
	boolean isDashing();
	void setDashCoolDown(int dashCoolDown);
	int getDashCoolDown();
	void setDashCoolDownCount(double dashCoolDownCount);
	double getDashCoolDownCount();
	void dashCoolDownTick();
	void dash(int direction);
}
