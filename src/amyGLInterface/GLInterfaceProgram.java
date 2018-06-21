package amyGLInterface;

import amyGLGraphics.GLProgram;

public class GLInterfaceProgram extends GLProgram {
	public GLInterfaceProgram() {
		super();
		this.createVertexShader("shaders/UIvertex.glsl");
		this.createFragmentShader("shaders/UIfragment.glsl");
	}
}
