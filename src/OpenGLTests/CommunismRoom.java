package OpenGLTests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import amyGLGraphics.GLEntity;
import movement.Room;

public class CommunismRoom extends Room {
	Communism comm;
	TestSquare square;
	private boolean movingLeft;
	public CommunismRoom() {
		super();
		comm = new Communism();
		comm.setPosition(new double[] {GLEntity.viewWidth / 2, GLEntity.viewHeight / 2, 0});
		comm.setDimensions(new double[] {comm.getSprite().getWidth(), comm.getSprite().getHeight(), 1000});
		square = new TestSquare();
		square.setPosition(new double[] {0, 0, 0});
		square.setDimensions(new double[] {square.getSprite().getWidth(), square.getSprite().getHeight(), 50});
		try {
			BufferedImage trueneutral = ImageIO.read(new File("graphics/trueneutral.png"));
			setBackground(trueneutral);
		} catch (IOException e) {
			System.out.println("where is ambrose?");
			System.exit(1);
		}
		addEntity(comm);
		addEntity(square);
	}
	
	@Override
	public void tick() {
		if (movingLeft) {
			comm.getPosition()[0] -= 10;
			if (comm.getPosition()[0] <= 0) {
				comm.getPosition()[0] = 0;
				movingLeft = false;
			}
		} else {
			comm.getPosition()[0] += 10;
			if (comm.getPosition()[0] >= GLEntity.viewWidth) {
				comm.getPosition()[0] = GLEntity.viewWidth;
				movingLeft = true;
			}
		}
	}
}
