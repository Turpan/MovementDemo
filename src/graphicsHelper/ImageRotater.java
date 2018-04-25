package graphicsHelper;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageRotater {
	private ImageRotater() {
		
	}
	public static BufferedImage rotateImage(BufferedImage toRotate, double direction) {
		direction = Math.toRadians(direction);
		var sin = Math.abs(Math.sin(direction));
		var cos = Math.abs(Math.cos(direction));
		var width = toRotate.getWidth();
		var height = toRotate.getHeight();
		//var newHeight = (int) Math.floor(width * cos + height * sin);
		//var newWidth = (int) Math.floor(height * cos + width * sin);
		var newHeight = (int) Math.floor(height * cos + width * sin);
		var newWidth = (int) Math.floor(width * cos + height * sin);
		var rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		var g2d = rotated.createGraphics();
		var at = new AffineTransform();
		at.translate((newWidth - width) / 2, (newHeight - height) / 2);
		var x = width / 2;
		var y = height / 2;
		at.rotate(direction, x, y);
		g2d.setTransform(at);
		g2d.drawImage(toRotate, 0, 0, null);
		g2d.dispose();
		return rotated;
	}
}
