package movementGraphics;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class LightMap {
	static final double BASEAMBIENCE = ShadowDrawer.BASEAMBIENCE;
	static final double LINEDETECTIONMARGIN = 0.050;
	double ambience;
	Dimension roomSize;
	double[][] pixelLightLevel;
	List<LightPoint> lightPoints;
	public LightMap(double ambience, Dimension roomSize) {
		this.ambience = ambience;
		this.roomSize = roomSize;
		initialiseArray();
	}
	public LightMap(Dimension roomSize) {
		this(BASEAMBIENCE, roomSize);
	}
	private void initialiseArray() {
		int x = roomSize.width;
		int y = roomSize.height;
		pixelLightLevel = new double[x][y];
	}
	public double[][] getPixelLightLevel() {
		calculatePixelLightLevel();
		return pixelLightLevel;
	}
	private void calculatePixelLightLevel() {
		for (int x=0; x<pixelLightLevel.length; x++) {
			for (int y=0; y<pixelLightLevel[x].length; y++) {
				pixelLightLevel[x][y] = calculateLight(x, y);
			}
		}
	}
	private double calculateLight(int x, int y) {
		double lightLevel = ambience;
		for (var lightPoint : lightPoints) {
			if (lightLevel < lightPoint.getIntensity()) {
				var lightArea = lightPoint.getLightArea();
				if (lightArea.contains(x, y)) {
					lightLevel = lightPoint.getIntensity();
				}
			}
		}
		return lightLevel;
	}
/*	private double calculateLight(int x, int y) { //this shit is cursed
		int counter = 0;
		var lightLevel = ambience;
		var testPoint = new Dimension(x, y);
		for (var lightPoint : lightPoints) {
			if (lightLevel < lightPoint.getIntensity()) {
				for (var lightRay : lightPoint.getLightRays()) {
					counter++;
					System.out.println(counter);
					var ray = new Rectangle(lightPoint.getX(), lightPoint.getY(),
							(int) lightRay.getWidth(), (int) lightRay.getHeight());
					if (pointIsOnLine(testPoint, ray)) {
						lightLevel = lightPoint.getIntensity();
						break;
					}
				}
			}
		}
		return lightLevel;
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
	}*/
	public double getAmbience() {
		return ambience;
	}
	public void setAmbience(double ambience) {
		this.ambience = ambience;
	}
	public Dimension getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(Dimension roomSize) {
		initialiseArray();
		this.roomSize = roomSize;
	}
	public List<LightPoint> getLightPoints() {
		return lightPoints;
	}
	public void setLightPoints(List<LightPoint> lightPoints) {
		this.lightPoints = lightPoints;
	}
	
}
