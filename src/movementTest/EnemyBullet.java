package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import movement.Entity;
import movement.GameListener;
import movement.Projectile;
import movementGraphics.ImageRotater;
import paintedPanel.PaintedPanel;

public class EnemyBullet extends Projectile{
	static final double DAMAGE = 0.5;
	static final int SPEED = 20;
	static final double TIMESCALE = 0.1;
	static final int FORCE = 5;
	PaintedPanel label = new PaintedPanel();
	GameListener listener;
	public EnemyBullet(double direction, double x, double y) {
		super(DAMAGE);
		updatePosition(x, y);
		setMaxSpeed(SPEED);
		setSpeed(SPEED);
		setDirection(direction);
		setTimeScale(TIMESCALE);
		setForce(2);
		loadImage();
		label.bgImage = new ImageIcon(getSprite());
	}
	@Override
	public void updatePosition(double positionX, double positionY) {
		super.updatePosition(positionX, positionY);
		label.setBounds((int) Math.round(getPositionX()), (int) Math.round(getPositionY()), getWidth(), getHeight());
	}
	private void loadImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/evillaser.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(new File("graphics/evillaser-bitmask.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		img = ImageRotater.rotateImage(img, adjustDegrees(getDirection() + 90));
		img2 = ImageRotater.rotateImage(img2, adjustDegrees(getDirection() + 90));
		setSprite(img);
		setCollisionMap(img2);
	}
	@Override
	public void collisionWith(Entity entity) {
		if (entity instanceof Player) {
			listener.removeEntity(this);
		}
		if (entity instanceof ChaserEnemy) {
			listener.removeEntity(this);
		}
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}
}
