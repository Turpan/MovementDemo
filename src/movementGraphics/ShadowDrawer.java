package movementGraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import movement.Entity;

public class ShadowDrawer {
	static final double LINEDETECTIONMARGIN = 0.010;
	static final double BASEAMBIENCE = 0.4;
	private List<LightPoint> lightPoints = new ArrayList<LightPoint>();
	private Map<BufferedImage, EdgeMap> edgeCache = new HashMap<BufferedImage, EdgeMap>();
	private List<Entity> roomContents = new ArrayList<Entity>();
	private double ambientLight;
	Dimension viewSize;
	public ShadowDrawer(Dimension roomSize) {
		this(roomSize, BASEAMBIENCE);
	}
	public ShadowDrawer(Dimension roomSize, double ambientLight) {
		this.viewSize = roomSize;
		setAmbientLight(ambientLight);
	}
	public BufferedImage drawShadow() {
		for (var lightPoint : lightPoints) {
			var lines = getLightLines(lightPoint);
			var pointsToDraw = new ArrayList<Dimension>();
			for (var line : lines) {
				pointsToDraw.add(findLightCollision(line));
			}
			lightPoint.setLightRays(pointsToDraw);
		}
		var shadowMap = new BufferedImage((int) viewSize.getWidth() + 1, (int) viewSize.getHeight() + 1, BufferedImage.TYPE_INT_ARGB);
		var lightMap = new LightMap(viewSize);
		lightMap.setLightPoints(lightPoints);
		var pixelData = lightMap.getPixelLightLevel();
		for (int x=0; x<pixelData.length; x++) {
			for (int y=0; y<pixelData.length; y++) {
				var opacity = calculateOpacity(pixelData[x][y]);
				var shadow = new Color(0, 0, 0, opacity);
				shadowMap.setRGB(x, y, shadow.getRGB());
			}
		}
		return shadowMap;
	}
	public void setAmbientLight(double ambientLight) {
		this.ambientLight = ambientLight;
	}
	public double getAmbientLight() {
		return ambientLight;
	}
	public void addEntity(Entity entity) {
		roomContents.add(entity);
		if (!edgeCache.containsKey(entity.getSprite())) {
			edgeCache.put(entity.getSprite(), new EdgeMap(entity.getSprite()));
		}
	}
	public void addLight(LightPoint light) {
		lightPoints.add(light);
	}
	public List<LightPoint> getLightPoints() {
		return lightPoints;
	}
	private List<Rectangle> getLightLines(LightPoint light) { // shoot a ray at every edge pixel of the room
		return lineFromEdge(light);
		//return lineFromEntity(light);
	}
	private List<Rectangle> lineFromEdge(LightPoint light) {
		List<Rectangle> lines = new ArrayList<Rectangle>();
		var x = light.getX();
		var y = light.getY();
		for (int i = 0; i < viewSize.getWidth(); i++) {
			var j = 0;
			lines.add(new Rectangle(x, y, i, j));
		}
		for (int i = 0; i < viewSize.getWidth(); i++) {
			int j = (int) viewSize.getHeight();
			lines.add(new Rectangle(x, y, i, j));
		}
		for (int j = 0; j < viewSize.getHeight(); j++) {
			int i = 0;
			lines.add(new Rectangle(x, y, i, j));
		}
		for (int j = 0; j < viewSize.getHeight(); j++) {
			int i = (int) viewSize.getWidth();
			lines.add(new Rectangle(x, y, i, j));
		}
		return lines;
	}
	private List<Rectangle> lineFromEntity(LightPoint light) {
		List<Rectangle> lines = new ArrayList<Rectangle>();
		var x = light.getX();
		var y = light.getY();
		for (Entity entity : roomContents) {
			var edgeMap = edgeCache.get(entity.getSprite());
			for (var edge : edgeMap.getEdgePoint()) {
				edge = adjustEdgePosition(edge, entity);
				lines.add(new Rectangle(x, y, (int) edge.getWidth(), (int) edge.getHeight())); 
			}
		}
		return lines;
	}
	private Dimension findLightCollision(Rectangle line) {
		var lightPoint = new Dimension((int) line.getX(), (int) line.getY());
		var collisionPoint = new Dimension((int) line.getWidth(), (int) line.getHeight());
		for (var entity : roomContents) {
			if (lineIntersectsEntity(line, entity)) {
				var testPoint = findIntersectPoint(line, entity);
				if (testPoint != null) {
					collisionPoint = findCloserPoint(lightPoint, collisionPoint, testPoint);
				}
			}
		}
		return collisionPoint;
	}
	private Dimension findCloserPoint(Dimension startPoint, Dimension currentPoint, Dimension testPoint) {
		if (currentPoint == null) {
			return testPoint;
		}
		var currentdistance = Math.hypot(startPoint.getWidth() - currentPoint.getWidth(),
				startPoint.getHeight() - currentPoint.getHeight());
		var testdistance = Math.hypot(startPoint.getWidth() - testPoint.getWidth(),
				startPoint.getHeight() - testPoint.getHeight());
		if (testdistance < currentdistance) {
			currentPoint = testPoint;
		}
		return currentPoint;
	}
	private boolean lineIntersectsEntity(Rectangle lightline, Entity entity) {
		int x = (int) entity.getPositionX();
		int y = (int) entity.getPositionY();
		int right = (int) entity.getPositionX() + entity.getWidth();
		int bottom = (int) entity.getPositionY() + entity.getHeight();
		var testedLines = new Rectangle[]{
				new Rectangle(x, y, right, y),
				new Rectangle(right, y, right, bottom),
				new Rectangle(right, bottom, x, bottom),
				new Rectangle(x, bottom, x, y)
		};
		for (var bound : testedLines) {
			if (lineIntersectsLine(lightline, bound)) {
				return true;
			}
		}
		return false;
	}
	private boolean lineIntersectsLine(Rectangle line1, Rectangle line2) {
		var x1 = line1.getX();
		var x2 = line1.getWidth();
		var y1 = line1.getY();
		var y2 = line1.getHeight();
		var x3 = line2.getX();
		var x4 = line2.getWidth();
		var y3 = line2.getY();
		var y4 = line2.getHeight();
		return Line2D.linesIntersect(x1, y1, x2, y2, x3, y3, x4, y4);
	}
	private Dimension findIntersectPoint(Rectangle lightline, Entity entity) {
		var edgeMap = edgeCache.get(entity.getSprite());
		var lightPoint = new Dimension((int) lightline.getX(), (int) lightline.getY());
		var pointfound = false;
		Dimension intersectPoint = null;
		for (var edge : edgeMap.getEdgePoint()) {
			edge = adjustEdgePosition(edge, entity);
			if (pointIsOnLine(edge, lightline)) {
				pointfound = true;
				intersectPoint = findCloserPoint(lightPoint, intersectPoint, edge);
			}
		}
		if (pointfound) {
			return intersectPoint;
		}
		return null;
	}
	private boolean pointIsOnLine(Dimension point, Rectangle line) {
		var x1 = line.getX();
		var y1 = line.getY();
		var x2 = line.getWidth();
		var y2 = line.getHeight();
		var x3 = point.getWidth();
		var y3 = point.getHeight();
		var distance1 = Math.hypot(x1 - x3, y1 - y3);
		var distance2 = Math.hypot(x2 - x3, y2 - y3);
		var totaldistance = Math.hypot(x1 - x2, y1 - y2);
		var error = LINEDETECTIONMARGIN / 2.0;
		if ((distance1 + distance2 >= totaldistance && distance1 + distance2 <= totaldistance + error) || 
				(distance1 + distance2 <= totaldistance && distance1 + distance2 >= totaldistance - error)) {
			return true; //if they are within error margin of eachother
		}
		return false;
	}
	/*
	 * this just adjusts the edge position so that the program does not think that the entity is at 0, 0
	 */
	private Dimension adjustEdgePosition(Dimension edge, Entity entity) { 
		return new Dimension((int) (entity.getPositionX() + edge.getWidth()), (int) (entity.getPositionY() + edge.getHeight())); 
	}
	private int calculateOpacity(double percentage) {
		double opacity = 255;
		percentage = 1 - percentage;
		opacity = opacity * percentage;
		return (int) opacity;
	}
}
