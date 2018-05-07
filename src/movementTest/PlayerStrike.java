package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Entity;
import movement.Projectile;
import movementGraphics.ImageRotater;

public class PlayerStrike extends Projectile {
	static final double DAMAGE = 2;
	static final int FORCE = 10;
	private BufferedImage origSprite;
	private BufferedImage origBitMask;
	public PlayerStrike() {
		super(DAMAGE);
		setForce(FORCE);
		loadImage();
	}
	private void loadImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/swordstrike.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(new File("graphics/swordstrike-bitmask.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		setSprite(img);
		this.origSprite = img;
		setCollisionMap(img2);
		this.origBitMask = img;
	}
	@Override
	public void setDirection(double direction) {
		super.setDirection(direction);
		setSprite(ImageRotater.rotateImage(origSprite, (adjustDegrees(getDirection() + 90))));
		setCollisionMap(ImageRotater.rotateImage(origBitMask, (adjustDegrees(getDirection() + 90))));
	}
	@Override
	public void collisionWith(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void die() {
		System.out.println("this shouldn't happen");
	}
}
