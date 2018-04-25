package movementTest;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import movement.GameListener;
import paintedPanel.PaintedPanel;

public class ShooterEnemyMapObject {
	static final int HEALTHBARHEIGHT = 10;
	static final int HEALTHBARPAD = 2;
	ShooterEnemy enemy;
	JPanel mainPanel;
	JPanel healthBar;
	PaintedPanel enemyPanel;
	public ShooterEnemyMapObject(int x, int y, GameListener listener) {
		enemy = new ShooterEnemy(listener);
		enemy.updatePosition(x, y);
		mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.setLayout(null);
		mainPanel.setBounds(x, y, enemy.getWidth(), getHeight());
		enemyPanel = new PaintedPanel();
		enemyPanel.setOpaque(false);
		enemyPanel.bgImage = new ImageIcon(enemy.getSprite());
		enemyPanel.setBounds(0, 0, enemy.getWidth(), enemy.getHeight());
		healthBar = new JPanel();
		healthBar.setBackground(Color.RED);
		healthBar.setBounds(0, enemy.getHeight() + HEALTHBARPAD, healthBarWidth(), HEALTHBARHEIGHT);
		mainPanel.add(enemyPanel);
		mainPanel.add(healthBar);
	}
	public ShooterEnemy getEnemy() {
		return enemy;
	}
	public void tick() {
		enemy.tick();
		updateLabels();
	}
	private void updateLabels() {
		mainPanel.setBounds((int) enemy.getPositionX(), (int) enemy.getPositionY(), enemy.getWidth(), getHeight());
		healthBar.setBounds(0, enemy.getHeight() + HEALTHBARPAD, healthBarWidth(), HEALTHBARHEIGHT);
	}
	private int getHeight() {
		return enemy.getHeight() + HEALTHBARPAD + HEALTHBARHEIGHT;
	}
	private int healthBarWidth() {
		var percentage = enemy.getCurrentHealth() / enemy.getMaxHealth();
		var width = (double) enemy.getWidth();
		width = width * percentage;
		return (int) width;
	}
}
