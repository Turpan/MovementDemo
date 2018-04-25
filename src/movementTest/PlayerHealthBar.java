package movementTest;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class PlayerHealthBar {
	static final int WIDTH = 200;
	static final int HEIGHT = 20;
	JLayeredPane mainPanel;
	JPanel healthBarPanel;
	JPanel textPanel;
	JLabel label;
	double maxHealth;
	double currentHealth;
	public PlayerHealthBar(double maxHealth) {
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		mainPanel = new JLayeredPane();
		mainPanel.setBounds(0, 0, WIDTH, HEIGHT);
		mainPanel.setBackground(new Color(0, 0, 0, 50));
		mainPanel.setOpaque(true);
		healthBarPanel = new JPanel();
		healthBarPanel.setBackground(Color.RED);
		healthBarPanel.setBounds(0, 0, WIDTH, HEIGHT);
		textPanel = new JPanel(new GridBagLayout());
		textPanel.setOpaque(false);
		textPanel.setBounds(0, 0, WIDTH, HEIGHT);
		label = new JLabel();
		label.setForeground(Color.WHITE);
		textPanel.add(label);
		mainPanel.add(healthBarPanel, Integer.valueOf(1));
		mainPanel.add(textPanel, Integer.valueOf(2));
	}
	public void updateLabels(double currentHealth) {
		this.currentHealth = currentHealth;
		if (currentHealth < 0) {
			currentHealth = 0;
		}
		healthBarPanel.setBounds(0, 0, healthBarWidth(), HEIGHT);
		label.setText(round(currentHealth, 10) + " / " + maxHealth);
	}
	private int healthBarWidth() {
		var percentage = currentHealth / maxHealth;
		var width = (double) WIDTH;
		width = width * percentage;
		return (int) width;
	}
	private double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
}
