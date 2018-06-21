package amyGLGraphics;

import amyGLInterface.GLInterfaceRenderer;
import amyGraphics.GraphicsHandler;

public class GLGraphicsHandler extends GraphicsHandler {
	public static final int viewWidth = 3840;
	public static final int viewHeight = 2160;
	public static final int viewDepth = 2000;
	public GLGraphicsHandler() {
		roomRenderer = new GLRoomRenderer();
		interfaceRenderer = new GLInterfaceRenderer();
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
