package amyGLGraphics;

import java.awt.image.BufferedImage;

import movement.Entity;

public class GLEntity extends GLObject{
	
	private Entity entity;
	private boolean is3d;
	
	public GLEntity(Entity entity) {
		super();
		this.entity = entity;
		determine3D();
		super.setDrawOrder();
		createVertices();
		calculateVertices();
		bindBuffer();
	}
	@Override
	public BufferedImage getSprite() {
		return entity.getSprite();
	}
	@Override
	protected void calculateVertices() {
		if (is3D()) {
			calculate3DVertices();
		} else {
			calculate2DVertices();
		}
		//TODO factor in camera position
	}
	
	private void calculate3DVertices() {
		int x = (int) entity.getPosition()[0];
		int y = (int) entity.getPosition()[1];
		int z = (int) entity.getPosition()[2];
		int right = (int) (x + entity.getDimensions()[0]);
		int bottom = (int) (y + entity.getDimensions()[1]);
		int back = (int) (z + entity.getDimensions()[2]);
		float fx = calculateRelativePosition(x, viewWidth);
		float fy = calculateRelativePosition(y, viewHeight);
		float fz = calculateRelativePosition(z, viewDepth);
		float fr = calculateRelativePosition(right, viewWidth);
		float fb = calculateRelativePosition(bottom, viewHeight);
		float fba = calculateRelativePosition(back, viewDepth);
		vertices.get(0).setXYZ(fx, flipY(fy), fz);
		vertices.get(1).setXYZ(fr, flipY(fy), fz);
		vertices.get(2).setXYZ(fr, flipY(fb), fz);
		vertices.get(3).setXYZ(fx, flipY(fb), fz);
		vertices.get(4).setXYZ(fr, flipY(fy), fz);
		vertices.get(5).setXYZ(fr, flipY(fy), fba);
		vertices.get(6).setXYZ(fr, flipY(fb), fz);
		vertices.get(7).setXYZ(fr, flipY(fb), fba);
		vertices.get(8).setXYZ(fx, flipY(fy), fba);
		vertices.get(9).setXYZ(fx, flipY(fy), fz);
		vertices.get(10).setXYZ(fx, flipY(fb), fba);
		vertices.get(11).setXYZ(fx, flipY(fb), fz);
		vertices.get(12).setXYZ(fx, flipY(fb), fz);
		vertices.get(13).setXYZ(fr, flipY(fb), fz);
		vertices.get(14).setXYZ(fx, flipY(fb), fba);
		vertices.get(15).setXYZ(fr, flipY(fb), fba);
		vertices.get(16).setXYZ(fx, flipY(fy), fz);
		vertices.get(17).setXYZ(fr, flipY(fy), fz);
		vertices.get(18).setXYZ(fx, flipY(fy), fba);
		vertices.get(19).setXYZ(fr, flipY(fy), fba);
		vertices.get(20).setXYZ(fx, flipY(fy), fba);
		vertices.get(21).setXYZ(fr, flipY(fy), fba);
		vertices.get(22).setXYZ(fr, flipY(fb), fba);
		vertices.get(23).setXYZ(fx, flipY(fb), fba);
	}
	
	private void calculate2DVertices() {
		int x = (int) entity.getPosition()[0];
		int y = (int) entity.getPosition()[1];
		int right = (int) (x + entity.getDimensions()[0]);
		int bottom = (int) (y + entity.getDimensions()[1]);
		float fx = calculateRelativePosition(x, viewWidth);
		float fy = calculateRelativePosition(y, viewHeight);
		float fr = calculateRelativePosition(right, viewWidth);
		float fb = calculateRelativePosition(bottom, viewHeight);
		vertices.get(0).setXY(fx, flipY(fy));
		vertices.get(1).setXY(fr, flipY(fy));
		vertices.get(2).setXY(fr, flipY(fb));
		vertices.get(3).setXY(fx, flipY(fb));
	}
	
	@Override
	protected void createVertices() {
		for (int i=0; i<4; i++) {
			var vertex = new GLVertex();
			vertex.setST(texturecoords[i*2], texturecoords[(i*2) + 1]);
			vertices.add(vertex);
		}
		if (is3D()) {
			create3DVertices();
		}
	}
	
	private void create3DVertices() {
		/*for (int i=0; i<20; i++) {
			var vertex = new GLVertex();
			vertex.setST(-2f, -2f);
			vertices.add(vertex);
		}*/
		for (int j=0; j<5; j++) {
			for (int i=0; i<4; i++) {
				var vertex = new GLVertex();
				vertex.setST(texturecoords[i*2], texturecoords[(i*2) + 1]);
				vertices.add(vertex);
			}
		}
	}
	
	private void determine3D() {
		set3D(entity.getDimensions().length == 3);
	}
	
	@Override
	protected byte[] createDrawOrder() {
		if (is3D()) {
			return create3dDrawOrder();
		} else {
			return create2dDrawOrder();
		}
	}
	
	private byte[] create2dDrawOrder() {
		return new byte[] {
				0, 1, 2,
	            2, 3, 0
		};
	}
	
	private byte[] create3dDrawOrder() {
		return new byte[] {
				0,1,3, 0,3,2,           //Face front
                4,5,7, 4,7,6,           //Face right
                8,9,11, 8,11,10,        //Face left
                12,13,15, 12,15,14,     //Face bottom
                16,17,19, 16,19,18,     //Face top
                20,21,23, 20,23,22,	    //Face back
		};
	}
	
	protected boolean is3D() {
		return is3d;
	}
	
	private void set3D(boolean is3d) {
		this.is3d = is3d;
	}
	
}
