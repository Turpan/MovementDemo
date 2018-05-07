package movementGraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class EdgeMap {
	private List<Dimension> edgePoints = new ArrayList<Dimension>();
	public EdgeMap(BufferedImage sprite) {
		calculateEdgePoints(sprite);
	}
	private void calculateEdgePoints(BufferedImage sprite) {
		for (int x=0; x<sprite.getWidth(); x++) {
			for (int y=0; y<sprite.getHeight(); y++) {
				Dimension testPoint = new Dimension(x, y);
				if (isEdge(testPoint, sprite)) {
					addEdgePoint(testPoint);
				}
			}
		}
	}
	private boolean isEdge(Dimension testPoint, BufferedImage sprite) {
		int x = (int) testPoint.getWidth();
		int y = (int) testPoint.getHeight();
		var color = new Color(sprite.getRGB(x, y), true);
		if (color.getAlpha() == 0) {
			return false;
		}
		if (x == 0 || y == 0 || x == sprite.getWidth() - 1 || y == sprite.getHeight() - 1) {
			return true;
		}
		var testedColors = new Color[]{
				new Color(sprite.getRGB(x-1, y), true),
				new Color(sprite.getRGB(x+1, y), true),
				new Color(sprite.getRGB(x, y-1), true),
				new Color(sprite.getRGB(x, y+1), true)
		};
		for (Color neighbour : testedColors) {
			if (neighbour.getAlpha() == 0) {
				return true;
			}
		}
		return false;
	}
	private void addEdgePoint(Dimension point) {
		edgePoints.add(point);
	}
	public List<Dimension> getEdgePoint() {
		return edgePoints;
	}
	public boolean isEdgePoint(Dimension testPoint) {
		return edgePoints.contains(testPoint);
	}
}
