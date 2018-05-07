package movementGraphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import movement.Entity;

public class ShadowTest extends JPanel implements MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8793852546761824348L;
	static final Dimension ROOMSIZE = new Dimension(599, 599);
	List<Entity> roomContents = new ArrayList<Entity>();
	ShadowDrawer shadowEngine = new ShadowDrawer(ROOMSIZE);
	BufferedImage shadow;
	public ShadowTest() {
		this.addMouseMotionListener(this);
		createEntitys();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Entity object : roomContents) {
			g2d.drawImage(object.getSprite(), (int) object.getPositionX(), (int) object.getPositionY(), null);
		}
		g2d.drawImage(shadow, 0, 0, null);
	}
	private void createEntitys() {
		var square1 = new TestSquare();
		var square2 = new TestSquare();
		var square3 = new TestSquare();
		var square4 = new TestSquare();
		var light = new LightPoint(545, 286);
		light.setIntensity(0.8);
		square1.updatePosition(50, 50);
		square2.updatePosition(200, 50);
		square3.updatePosition(450, 300);
		square4.updatePosition(500, 500);
		roomContents.add(square1);
		roomContents.add(square2);
		roomContents.add(square3);
		roomContents.add(square4);
		shadowEngine.addEntity(square1);
		shadowEngine.addEntity(square2);
		shadowEngine.addEntity(square3);
		shadowEngine.addEntity(square4);
		shadowEngine.addLight(light);
		shadow = shadowEngine.drawShadow();
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		shadowEngine.getLightPoints().get(0).setPosition(e.getX(), e.getY());
		shadow = shadowEngine.drawShadow();
		repaint();
	}
}
