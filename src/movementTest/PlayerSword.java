package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphicsHelper.ImageRotater;
import movement.SelfPropelled;

public class PlayerSword extends SelfPropelled{
	private BufferedImage origSprite;
	private BufferedImage origBitMask;
	public PlayerSword() {
		loadImage();
	}
	private void loadImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/sword.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(new File("graphics/sword.png"));
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
}
