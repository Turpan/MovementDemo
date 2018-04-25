package movement;

public abstract class Projectile extends Attacker {
	public Projectile(double damage) {
		setDamage(damage);
		setMaxHealth(0);
	}
}
