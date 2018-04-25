package movementTest;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
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
	boolean moveRight;
	boolean moveLeft;
	boolean moveUp;
	boolean moveDown;
	boolean turretRight;
	boolean turretLeft;
	boolean turretUp;
	boolean turretDown;
	int turretCounter;
	int shootCooldown = 10;
	int counter;
	int enemyskilled;
	PaintedPanel playerLabel;
	PaintedPanel playerSwordLabel;
	PaintedPanel playerStrikeLabel;
	PaintedPanel playerTurretLabel;
	PaintedPanel longWallLLabel;
	PaintedPanel longWallRLabel;
	PaintedPanel wideWallTLabel;
	PaintedPanel wideWallBLabel;
	Player player;
	PlayerHealthBar healthBar;
	PlayerScore scoreBar;
	Timer tickTimer;
	TestWall longWallL;
	TestWall longWallR;
	TestWall wideWallT;
	TestWall wideWallB;
	List<Entity> roomContents = new ArrayList<Entity>();
	List<PlayerBullet> playerBullets = new ArrayList<PlayerBullet>();
	List<EnemyBullet> enemyBullets = new ArrayList<EnemyBullet>();
	List<Entity> toRemove = new ArrayList<Entity>();
	List<ChaserEnemyMapObject> chaserEnemys = new ArrayList<ChaserEnemyMapObject>();
	List<ShooterEnemyMapObject> shooterEnemys = new ArrayList<ShooterEnemyMapObject>();
	CollisionEngine collisionCheck;
	MovementDemo() {
		createVariables();
	}
	private void createBullet() {
		var direction = player.turret.getDirection();
		var x = player.getPositionX() + PlayerTurret.XOFFSET + (player.turret.getWidth() / 2);
		var y = player.getPositionY() + PlayerTurret.YOFFSET + (player.turret.getHeight() / 2);
		x += (15 * Math.cos(Math.toRadians(direction)));
		y += (15 * Math.sin(Math.toRadians(direction)));
		var bullet = new PlayerBullet(direction, x, y);
		bullet.listener = this;
		DemoMain.mainPanel.add(bullet.label);
		playerBullets.add(bullet);
		roomContents.add(bullet);
	}
	private void playerBulletTick() {
		Iterator<PlayerBullet> iter = playerBullets.iterator();
		while (iter.hasNext()) {
			var bullet = iter.next();
			bullet.tick((int) bullet.getDirection());
			if (bullet.getPositionX() < 0 || bullet.getPositionY() < 0 || bullet.getPositionX() > 1600 || bullet.getPositionY() > 900) {
				iter.remove();
				roomContents.remove(bullet);
				DemoMain.mainPanel.remove(bullet.label);
			}
		}
	}
	private void enemyBulletTick() {
		Iterator<EnemyBullet> iter = enemyBullets.iterator();
		while (iter.hasNext()) {
			var bullet = iter.next();
			bullet.tick((int) bullet.getDirection());
			if (bullet.getPositionX() < 0 || bullet.getPositionY() < 0 || bullet.getPositionX() > 1600 || bullet.getPositionY() > 900) {
				iter.remove();
				roomContents.remove(bullet);
				DemoMain.mainPanel.remove(bullet.label);
			}
		}
	}
	private void enemyTick() {
		for (var enemy : chaserEnemys) {
			enemy.tick();
		}
		for (var enemy : shooterEnemys) {
			enemy.tick();
		}
	}
	private void createChaserEnemy(int x, int y) {
		var enemy = new ChaserEnemyMapObject(x, y, this);
		chaserEnemys.add(enemy);
		roomContents.add(enemy.getEnemy());
		DemoMain.mainPanel.add(enemy.mainPanel);
	}
	private void createShooterEnemy(int x, int y) {
		var enemy = new ShooterEnemyMapObject(x, y, this);
		shooterEnemys.add(enemy);
		roomContents.add(enemy.getEnemy());
		DemoMain.mainPanel.add(enemy.mainPanel);
	}
	int calculateDirection() {
		return lookupDirection(moveUp, moveDown, moveLeft, moveRight);
	}
	int calculateTurretDirection() {
		return lookupDirection(turretUp, turretDown, turretLeft, turretRight);
	}
	private int lookupDirection(boolean up, boolean down, boolean left, boolean right) {
		int x = 1;
		int y = 1;
		if (up) y -= 1;
		if (down) y += 1;
		if (left) x -= 1;
		if (right) x += 1;
		return directionTable[y][x];
	}
	private boolean canShoot() {
		return (turretCounter == 0);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			moveUp = true;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			moveDown = true;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = true;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			turretUp = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			turretDown = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			turretLeft = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			turretRight = true;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (calculateDirection() > -1) {
				player.dash(calculateDirection());
			}
		} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			player.attack();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			DemoMain.reset();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			moveUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			moveDown = false;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = false;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			turretUp = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			turretDown = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			turretLeft = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			turretRight = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		playerBulletTick();
		enemyBulletTick();
		enemyTick();
		if (calculateTurretDirection() > -1) {
			player.turnTurret(calculateTurretDirection());
			if (canShoot()) {
				turretCounter = shootCooldown;
				createBullet();
			}
		}
		if (turretCounter > 0) {
			turretCounter -= 1;
		}
		player.tick(calculateDirection());
		collisionCheck.checkCollision(roomContents);
		playerLabel.setBounds((int) Math.round(player.getPositionX()), (int) Math.round(player.getPositionY()), player.getWidth(), player.getHeight());
		playerSwordLabel.setBounds((int) Math.round(player.sword.getPositionX()), (int) Math.round(player.sword.getPositionY()), player.sword.getWidth(), player.sword.getHeight());
		playerSwordLabel.bgImage = new ImageIcon(player.sword.getSprite());
		playerStrikeLabel.setBounds((int) Math.round(player.strike.getPositionX()), (int) Math.round(player.strike.getPositionY()), player.strike.getWidth(), player.strike.getHeight());
		playerStrikeLabel.bgImage = new ImageIcon(player.strike.getSprite());
		playerTurretLabel.setBounds((int) Math.round(player.turret.getPositionX()), (int) Math.round(player.turret.getPositionY()), player.turret.getWidth(), player.turret.getHeight());
		playerTurretLabel.bgImage = new ImageIcon(player.turret.getSprite());
		healthBar.updateLabels(player.getCurrentHealth());
		scoreBar.updateLabels(enemyskilled);
		counter++;
		if (counter % 100 == 0) {
			createChaserEnemy(50, 50);
		}
		if (counter % 400 == 0) {
			createShooterEnemy(1550, 850);
		}
		DemoMain.mainPanel.repaint();
		remove();
	}

	@Override
	public Dimension getPlayerLocation() {
		return new Dimension((int) player.getPositionX(), (int) player.getPositionY());
	}
	private void createVariables() {
		DemoMain.frame.requestFocus();
		DemoMain.frame.addKeyListener(this);
		playerLabel = new PaintedPanel();
		playerLabel.setOpaque(false);
		playerSwordLabel = new PaintedPanel();
		playerSwordLabel.setOpaque(false);
		playerStrikeLabel = new PaintedPanel();
		playerStrikeLabel.setOpaque(false);
		playerTurretLabel = new PaintedPanel();
		playerTurretLabel.setOpaque(false);
		player = new Player();
		player.listener = this;
		playerLabel.bgImage = new ImageIcon(player.getSprite());
		playerSwordLabel.bgImage = new ImageIcon(player.sword.getSprite());
		playerStrikeLabel.bgImage = new ImageIcon(player.strike.getSprite());
		playerTurretLabel.bgImage = new ImageIcon(player.turret.getSprite());
		tickTimer = new Timer(10, this);
		collisionCheck = new CollisionEngine();
		player.updatePosition(20, 20);
		playerLabel.setBounds(20, 20, 50, 50);
		healthBar = new PlayerHealthBar(player.getMaxHealth());
		scoreBar = new PlayerScore();
		longWallLLabel = new PaintedPanel();
		longWallRLabel = new PaintedPanel();
		wideWallTLabel = new PaintedPanel();
		wideWallBLabel = new PaintedPanel();
		longWallLLabel.setOpaque(false);
		longWallRLabel.setOpaque(false);
		wideWallTLabel.setOpaque(false);
		wideWallBLabel.setOpaque(false);
		longWallLLabel.setBounds(0,0, 20, 900);
		longWallRLabel.setBounds(1580,0, 20, 900);
		wideWallTLabel.setBounds(0,0, 1600, 20);
		wideWallBLabel.setBounds(0,880, 1600, 20);
		longWallL = new TestWall(90, 1.0);
		longWallR = new TestWall(90, 1.0);
		wideWallT = new TestWall(0, 1.0);
		wideWallB = new TestWall(0, 1.0);
		longWallL.updatePosition(0, 0);
		longWallR.updatePosition(1580, 0);
		wideWallT.updatePosition(0, 0);
		wideWallB.updatePosition(0, 880);
		longWallL.loadLongImage();
		longWallR.loadLongImage();
		wideWallT.loadWideImage();
		wideWallB.loadWideImage();
		longWallLLabel.bgImage = new ImageIcon(longWallL.getSprite());
		longWallRLabel.bgImage = new ImageIcon(longWallR.getSprite());
		wideWallTLabel.bgImage = new ImageIcon(wideWallT.getSprite());
		wideWallBLabel.bgImage = new ImageIcon(wideWallB.getSprite());
		DemoMain.mainPanel.add(playerLabel);
		DemoMain.mainPanel.add(playerSwordLabel);
		DemoMain.mainPanel.add(playerStrikeLabel);
		DemoMain.mainPanel.add(playerTurretLabel);
		DemoMain.mainPanel.add(healthBar.mainPanel);
		DemoMain.mainPanel.add(scoreBar.textPanel);
		DemoMain.mainPanel.add(longWallLLabel);
		DemoMain.mainPanel.add(longWallRLabel);
		DemoMain.mainPanel.add(wideWallTLabel);
		DemoMain.mainPanel.add(wideWallBLabel);
		roomContents.add(player);
		roomContents.add(player.strike);
		roomContents.add(player.turret);
		roomContents.add(longWallL);
		roomContents.add(longWallR);
		roomContents.add(wideWallT);
		roomContents.add(wideWallB);
		tickTimer.start();
	}
	private void markForRemoval(Entity entity) {
		toRemove.add(entity);
	}
	private void remove() {
		Iterator<Entity> iter = toRemove.iterator();
		while (iter.hasNext()) {
			var entity = iter.next();
			iter.remove();
			if (entity instanceof PlayerBullet) {
				var bullet = (PlayerBullet) entity;
				removePlayerBullet(bullet);
			}
			if (entity instanceof EnemyBullet) {
				var bullet = (EnemyBullet) entity;
				removeEnemyBullet(bullet);
			}
			if (entity instanceof ChaserEnemy) {
				enemyskilled++;
				var enemy = (ChaserEnemy) entity;
				removeChaserEnemy(enemy);
			}
			if (entity instanceof ShooterEnemy) {
				enemyskilled++;
				var enemy = (ShooterEnemy) entity;
				removeShooterEnemy(enemy);
			}
			if (entity instanceof Player) {
				removePlayer();
			}
		}
	}
	private void removePlayerBullet(PlayerBullet bullet) {
		playerBullets.remove(bullet);
		roomContents.remove(bullet);
		DemoMain.mainPanel.remove(bullet.label);
	}
	private void removeEnemyBullet(EnemyBullet bullet) {
		enemyBullets.remove(bullet);
		roomContents.remove(bullet);
		DemoMain.mainPanel.remove(bullet.label);
	}
	private void removeChaserEnemy(ChaserEnemy enemy) {
		Iterator<ChaserEnemyMapObject> iter = chaserEnemys.iterator();
		while (iter.hasNext()) {
			var mapChaser = iter.next();
			if (mapChaser.getEnemy() == enemy) {
				iter.remove();
				roomContents.remove(enemy);
				DemoMain.mainPanel.remove(mapChaser.mainPanel);
			}
		}
	}
	private void removeShooterEnemy(ShooterEnemy enemy) {
		Iterator<ShooterEnemyMapObject> iter = shooterEnemys.iterator();
		while (iter.hasNext()) {
			var mapShooter = iter.next();
			if (mapShooter.getEnemy() == enemy) {
				iter.remove();
				roomContents.remove(enemy);
				DemoMain.mainPanel.remove(mapShooter.mainPanel);
			}
		}
	}
	private void removePlayer() {
		roomContents.remove(player);
		player.setSpeed(0);
		DemoMain.frame.removeKeyListener(this);
	}
	@Override
	public void removeEntity(Entity entity) {
		markForRemoval(entity);
	}
	@Override
	public void createEntity(Entity entity) {
		if (entity instanceof EnemyBullet) {
			var bullet = (EnemyBullet) entity;
			enemyBullets.add(bullet);
			roomContents.add(bullet);
			DemoMain.mainPanel.add(bullet.label);
		}
	}
}
