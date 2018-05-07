package movementGraphics;

import java.awt.Dimension;
import java.awt.Polygon;
import java.util.List;

public class LightPoint {
	private int x;
	private int y;
	private double intensity;
	private List<Dimension> lightRays;
	private Polygon lightArea;
	public LightPoint() {
		this(0, 0);
	}
	public LightPoint(int x, int y) {
		setPosition(x, y);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
	}
	public double getIntensity() {
		return intensity;
	}
	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}
	public List<Dimension> getLightRays() {
		return lightRays;
	}
	public void setLightRays(List<Dimension> lightRays) {
		this.lightRays = lightRays;
		createPolygon();
	}
	
	public Polygon getLightArea() {
		return lightArea;
	}
	private void createPolygon() {
		lightArea = new Polygon();
		for (var point : lightRays) {
			int x = (int) point.getWidth();
			int y = (int) point.getHeight();
			lightArea.addPoint(x, y);
		}
	}
}
