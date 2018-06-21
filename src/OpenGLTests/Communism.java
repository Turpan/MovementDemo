package OpenGLTests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import movement.Entity;

public class Communism extends Entity {
	public Communism() {
		loadImage();
	}
	public void loadImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("graphics/communism.png"));
		} catch (IOException e) {
			System.exit(1);
		}
		setSprite(img);
	}
}