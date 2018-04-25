package movementTest;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerScore {
	static final int WIDTH = 50;
	static final int HEIGHT = 20;
	JPanel textPanel;
	JLabel label;
	double score;
	public PlayerScore() {
		textPanel = new JPanel(new GridBagLayout());
		textPanel.setOpaque(true);
		textPanel.setBounds(1600 - WIDTH, 0, WIDTH, HEIGHT);
		textPanel.setBackground(new Color(0, 0, 0, 50));
		label = new JLabel();
		label.setForeground(Color.WHITE);
		textPanel.add(label);
	}
	public void updateLabels(double score) {
		this.score = score;
		label.setText(score + "");
	}
}
