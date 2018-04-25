package movement;

public abstract class Enemy extends Attacker {
	private GameListener listener;
	double attackCooldown;
	double attackCounter;
	public Enemy(GameListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		setListener(listener);
	}
	
	public double getAttackCooldown() {
		return attackCooldown;
	}

	public void setAttackCooldown(double attackCooldown) {
		this.attackCooldown = attackCooldown;
	}

	public double getAttackCounter() {
		return attackCounter;
	}

	public void setAttackCounter(double attackCounter) {
		this.attackCounter = attackCounter;
	}

	public GameListener getListener() {
		return listener;
	}
	private void setListener(GameListener listener) {
		this.listener = listener;
	}
	protected int calculateDirection() {
		int x = getDesiredX();
		int y = getDesiredY();
		return (int) Math.toDegrees(Math.atan2(y - getPositionY(), x - getPositionX()));
	}
	public void tick() {
		if (isActive()) {
			super.tick((int) adjustDegrees(calculateDirection()));
			if (canAttack() && attackReady()) {
				attack();
			} else if (!attackReady()) {
				attackCoolDownTick();
			}
		}
	}
	public boolean attackReady() {
		return (attackCounter == 0);
	}
	protected void attack() {
		getListener().createEntity(createAttack());
		attackCooldown();
	}
	protected void attackCooldown() {
		setAttackCounter(getAttackCooldown());
	}
	protected void attackCoolDownTick() {
		setAttackCounter(getAttackCounter() - getTimeScale());
		if (getAttackCounter() < 0) {
			setAttackCounter(0);
		}
	}
	protected abstract int getDesiredX();
	protected abstract int getDesiredY();
	public abstract boolean isActive();
	public abstract boolean canAttack();
	protected abstract Projectile createAttack();
}
