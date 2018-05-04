package movementTest;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import movement.CollisionEngine;
import movement.Entity;
import movement.GameListener;
import paintedPanel.PaintedPanel;

public class MovementDemo implements KeyListener, ActionListener, GameListener{
	static final int[][] directionTable = new int[][]
			{{225, 270, 315},
			{180, -1, 0},
			{135, 90, 45}};
	boolean rightKey;
	boolean leftKey;
	boolean upKey;
	boolean downKey;
	int counter;
	PaintedPanel playerLabel;
	PaintedPanel chaserLabel;
	PaintedPanel longWallLLabel;
	PaintedPanel longWallRLabel;
	PaintedPanel wideWallTLabel;
	PaintedPanel wideWallBLabel;
	Player player;
	ChaserEnemy chaser;
	Timer tickTimer;
	TestWall longWallL;
	TestWall longWallR;
	TestWall wideWallT;
	TestWall wideWallB;
	List<Entity> roomContents = new ArrayList<Entity>();
	CollisionEngine collisionCheck;
	MovementDemo() {
		createVariables();
	}

	private void createVariables() {
		DemoMain.frame.requestFocus();
		DemoMain.frame.addKeyListener(this);
		playerLabel = new PaintedPanel();
		playerLabel.setOpaque(false);
		player = new Player();
		playerLabel.bgImage = new ImageIcon(player.getSprite());
		tickTimer = new Timer(10, this);
		collisionCheck = new CollisionEngine();
		player.setPositionX(25);
		player.setPositionY(25);
		playerLabel.setBounds(25, 25, 50, 50);
		chaserLabel = new PaintedPanel();
		chaserLabel.setOpaque(false);
		var ai = new ChaserAI(this);
		chaser = new ChaserEnemy(ai);
		chaser.setPositionX(300);
		chaser.setPositionY(300);
		chaserLabel.bgImage = new ImageIcon(chaser.getSprite());
		chaserLabel.setBounds(300, 300, 20, 20);
		longWallLLabel = new PaintedPanel();
		longWallRLabel = new PaintedPanel();
		wideWallTLabel = new PaintedPanel();
		wideWallBLabel = new PaintedPanel();
		longWallLLabel.setOpaque(false);
		longWallRLabel.setOpaque(false);
		wideWallTLabel.setOpaque(false);
		wideWallBLabel.setOpaque(false);
		longWallLLabel.setBounds(0,0, 20, 600);
		longWallRLabel.setBounds(580,0, 20, 600);
		wideWallTLabel.setBounds(0,0, 600, 20);
		wideWallBLabel.setBounds(0,580, 600, 20);
		longWallL = new TestWall(90, 1.0);
		longWallR = new TestWall(270, 1.0);
		wideWallT = new TestWall(180, 1.0);
		wideWallB = new TestWall(0, 1.0);
		longWallL.setPositionX(0);
		longWallL.setPositionY(0);
		longWallR.setPositionX(580);
		longWallR.setPositionY(0);
		wideWallT.setPositionX(0);
		wideWallT.setPositionY(0);
		wideWallB.setPositionX(0);
		wideWallB.setPositionY(580);
		longWallL.loadLongImage();
		longWallR.loadLongImage();
		wideWallT.loadWideImage();
		wideWallB.loadWideImage();
		longWallLLabel.bgImage = new ImageIcon(longWallL.getSprite());
		longWallRLabel.bgImage = new ImageIcon(longWallR.getSprite());
		wideWallTLabel.bgImage = new ImageIcon(wideWallT.getSprite());
		wideWallBLabel.bgImage = new ImageIcon(wideWallB.getSprite());
		DemoMain.mainPanel.add(playerLabel);
		DemoMain.mainPanel.add(chaserLabel);
		DemoMain.mainPanel.add(longWallLLabel);
		DemoMain.mainPanel.add(longWallRLabel);
		DemoMain.mainPanel.add(wideWallTLabel);
		DemoMain.mainPanel.add(wideWallBLabel);
		roomContents.add(player);
		roomContents.add(chaser);
		roomContents.add(longWallL);
		roomContents.add(longWallR);
		roomContents.add(wideWallT);
		roomContents.add(wideWallB);
		tickTimer.start();
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
		counter ++;
		if ((rightKey || leftKey || upKey || downKey) && calculateDirection() > -1) {
			player.locomote(calculateDirection());
		} 
		player.tick();
		chaser.tick();
		collisionCheck.checkCollision(roomContents);
		chaserLabel.setBounds((int) Math.round(chaser.getPositionX()), (int) Math.round(chaser.getPositionY()), chaser.getWidth(), chaser.getHeight());
		playerLabel.setBounds((int) Math.round(player.getPositionX()), (int) Math.round(player.getPositionY()), player.getWidth(), player.getHeight());
		DemoMain.mainPanel.repaint();
		
	}

	@Override
	public Dimension getPlayerLocation() {
		return new Dimension((int) player.getPositionX(), (int) player.getPositionY());
	}
}
