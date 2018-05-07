package movement;

public abstract class Attacker extends SelfPropelled implements Collidable{
	double maxHealth;
	double currentHealth;
	double damage;
	int force;
	double invincibilityTime;
	double invincibilityCounter;
	public int getForce() {
		return force;
	}
	public void setForce(int force) {
		this.force = force;
	}
	public double getInvincibilityTime() {
		return invincibilityTime;
	}
	public void setInvincibilityTime(double invincibilityTime) {
		this.invincibilityTime = invincibilityTime;
	}
	public double getInvincibilityCounter() {
		return invincibilityCounter;
	}
	public void setInvincibilityCounter(double invincibilityCounter) {
		this.invincibilityCounter = invincibilityCounter;
	}
	public void gainInvincibility() {
		setInvincibilityCounter(getInvincibilityTime());
	}
	protected void invincibilityTick() {
		setInvincibilityCounter(getInvincibilityCounter() - getTimeScale());
	}
	@Override
	public void tick() {
		super.tick();
		if (isInvincible()) {
			invincibilityTick();
		}
	}
	public double getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}
	public double getCurrentHealth() {
		return currentHealth;
	}
	public boolean isInvincible() {
		return (getInvincibilityCounter() > 0);
	}
	public void setCurrentHealth(double currentHealth) {
		if (currentHealth < 0) {
			currentHealth = 0;
		} else if (currentHealth > maxHealth) {
			currentHealth = maxHealth;
		}
		this.currentHealth = currentHealth;
	}
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
	public boolean isAttackable() {
		return (getMaxHealth() > 0 && !isInvincible());
	}
	public void damaged(double damage) {
		if (!isAttackable()) {
			return;
		}
		setCurrentHealth(getCurrentHealth() - damage);
		if (!isAlive()) {
			die();
		}
	}
	public boolean isAlive() {
		return (getCurrentHealth() > 0);
	}
	public abstract void die();
}
