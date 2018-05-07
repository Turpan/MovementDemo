package movementTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.SelfPropelled;
import movementGraphics.ImageRotater;

public class PlayerTurret extends SelfPropelled {
	static final int XOFFSET = 3;
	static final int YOFFSET = 17;
	static final int DIRECTIONTHRESHOLD = 359;
	static final int STARTINGDIRECTION = 270;
	static final double TIMESCALE = 0.1;
	static final int TURNSPEED = 20;
	private BufferedImage origSprite;
	private BufferedImage origBitMask;
	public PlayerTurret() {
		setDirectionThreshold(DIRECTIONTHRESHOLD);
		setTurnSpeed(TURNSPEED);
		setTimeScale(TIMESCALE);
		loadImage();
		setDirection(STARTINGDIRECTION);
	}
	private void loadImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/turret.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(new File("graphics/turret-bitmask.png"));
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
