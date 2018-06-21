package amyGLGraphics;

import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glShaderSource;

import amyGLGraphicsIO.ShaderLoader;

public class GLShader {
	private int shaderID;
	private boolean GLBound;
	
	public GLShader() {
		
	}
	
	public GLShader(String shaderFile, int shaderType) {
		this();
		createShader(shaderFile, shaderType);
	}
	
	public void createShader(String shaderFile, int shaderType) {
		if (GLBound) {
			unbindShader();
		}
		String shader = ShaderLoader.loadFile(shaderFile);
		shaderID = glCreateShader(shaderType);
		glShaderSource(shaderID, shader);
		glCompileShader(shaderID);
		GLBound = true;
	}
	
	public int getShaderID() {
		return shaderID;
	}
	
	public void unbindShader() {
		glDeleteShader(shaderID);
		GLBound = false;
	}
	
	public boolean isGLBound() {
		return GLBound;
	}
}
