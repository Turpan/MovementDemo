package movement;

import movement.Vectors.Vector;
import movement.Vectors.Vector.MalformedVectorException;

public abstract class Enemy extends Attacker {
	private GameListener listener;
	private double attackCooldown;
	private double attackCounter;
	public Enemy(GameListener listener) throws MalformedVectorException {
		super();
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
	protected double[] calculateDirection() {
		float[] target = getDesiredPosition();
		float[] location = getPosition();
		double[] output = new double[Vector.DIMENSIONS];
		for (int i=0;i<Vector.DIMENSIONS;i++) {
			output[i] = target[i] - location[i];
		}
		return output;
		
	}
	public void tick() throws MalformedVectorException, MalformedEntityException {
		if (isActive()) {
			super.tick();
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
		setAttackCounter(getAttackCounter() - Moveable.TIMESCALE);
		if (getAttackCounter() < 0) {
			setAttackCounter(0);
		}
	}
	protected abstract float[] getDesiredPosition();
	public abstract boolean isActive();
	public abstract boolean canAttack();
	protected abstract Projectile createAttack();
}
