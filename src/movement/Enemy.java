package movement;

public abstract class Enemy extends Controllable {
	EnemyAI AI;
	public Enemy(EnemyAI AI) {
		if (AI == null) {
			throw new NullPointerException();
		}
		this.AI = AI;
	}
	protected int calculateDirection() {
		int x = AI.getDesiredX();
		int y = AI.getDesiredY();
		return (int) Math.toDegrees(Math.atan2(y - getPositionY(), x - getPositionX()));
	}
	public void tick() {
		if (AI.isActive()) {
			super.locomote(calculateDirection());
		}
		super.tick();
	}
}
