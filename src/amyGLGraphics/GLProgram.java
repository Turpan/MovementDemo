package amyGLGraphics;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

public abstract class GLProgram {
	private GLShader vertexShader = new GLShader();
	private GLShader fragmentShader = new GLShader();
	private int programID;
	
	public GLProgram() {
		createProgram();
	}
	
	private void createProgram() {
		programID = glCreateProgram();
		glBindAttribLocation(programID, GLRoomRenderer.VERTEXPOSITION, "in_Position");
		glBindAttribLocation(programID, GLRoomRenderer.COLOURPOSITION, "in_Color");
		glBindAttribLocation(programID, GLRoomRenderer.TEXTUREPOSITION, "in_TextureCoord");
		glLinkProgram(programID);
		glValidateProgram(programID);
	}
	
	protected void createVertexShader(String fileLocation) {
		vertexShader = new GLShader(fileLocation, GL_VERTEX_SHADER);
		glAttachShader(programID, getVertexShaderID());
	}
	
	protected void createFragmentShader(String fileLocation) {
		fragmentShader = new GLShader(fileLocation, GL_FRAGMENT_SHADER);
		glAttachShader(programID, getFragmentShaderID());
	}
	
	public void unbindProgram() {
		if (vertexShader.isGLBound()) {
			int vertexShaderID = getVertexShaderID();
			glDetachShader(programID, vertexShaderID);
			vertexShader.unbindShader();
		}
		if (fragmentShader.isGLBound()) {
			int fragmentShaderID = getFragmentShaderID();
			glDetachShader(programID, fragmentShaderID);
			fragmentShader.unbindShader();
		}
		glDeleteProgram(programID);
	}
	
	public int getProgramID() {
		return programID;
	}
	
	public int getVertexShaderID() {
		return vertexShader.getShaderID();
	}
	
	public int getFragmentShaderID() {
		return fragmentShader.getShaderID();
	}
}
