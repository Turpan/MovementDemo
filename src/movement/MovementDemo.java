package movement;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MovementDemo implements KeyListener, ActionListener{
	static final int[][] directionTable = new int[][]
			{{224, 269, 314},
			{179, -1, 0},
			{134, 89, 44}};
	boolean rightKey;
	boolean leftKey;
	boolean upKey;
	boolean downKey;
	JPanel playerLabel;
	Player player;
	Timer tickTimer;
	MovementDemo() {
		createVariables();
	}

	private void createVariables() {
		DemoMain.frame.requestFocus();
		DemoMain.frame.addKeyListener(this);
		playerLabel = new JPanel();
		playerLabel.setBackground(Color.GREEN);
		player = new Player();
		tickTimer = new Timer(10, this);
		tickTimer.start();
		player.setPositionX(300);
		player.setPositionY(300);
		playerLabel.setBounds(300, 300, 50, 50);
		DemoMain.mainPanel.add(playerLabel);
	}
	int calculateDirection() {
		int x = 1;
		int y = 1;
		if (upKey) y -= 1;
		if (downKey) y += 1;
		if (leftKey) x -= 1;
		if (rightKey) x += 1;
		return directionTable[y][x];
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			upKey = true;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			downKey = true;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			leftKey = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			rightKey = true;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (calculateDirection() > -1) {
				player.dash(calculateDirection());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			upKey = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			downKey = false;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			leftKey = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			rightKey = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if ((rightKey || leftKey || upKey || downKey) && calculateDirection() > -1) {
			player.tick(calculateDirection());
		} else {
			player.stopTick();
		}
		playerLabel.setBounds(player.getPositionX(), player.getPositionY(), 50, 50);
		playerLabel.setBackground((player.canDash()) ? Color.GREEN : Color.RED);
		DemoMain.mainPanel.repaint();
	}
}
