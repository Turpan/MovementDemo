package amyGLGraphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.BufferUtils;

public abstract class GLObject {
	public static final int viewWidth = GLGraphicsHandler.viewWidth;
	public static final int viewHeight = GLGraphicsHandler.viewHeight;
	public static final int viewDepth = GLGraphicsHandler.viewDepth;
	public static final int VERTEXPOSITION = GLRoomRenderer.VERTEXPOSITION;
	public static final int COLOURPOSITION = GLRoomRenderer.COLOURPOSITION;
	public static final int TEXTUREPOSITION = GLRoomRenderer.TEXTUREPOSITION;
	static final float[] texturecoords = {
			0.0f, 0.0f,
			1.0f, 0.0f,
			1.0f, 1.0f,
			0.0f, 1.0f
	};
	//static final byte[] draworder = GLRoomRenderer.draworder;
	protected byte[] draworder;
	private int objectID;
	private int objectBufferID;
	private int objectIndicesBufferID;
	private boolean GLBound;
	protected List<GLVertex> vertices = new ArrayList<GLVertex>();
	
	protected abstract byte[] createDrawOrder();
	
	protected void bindBuffer() {
		GLBound = true;
		objectID = glGenVertexArrays();
		glBindVertexArray(objectID);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(getVertices().size() * GLVertex.elementCount);
		for (var vertex : getVertices()) {
			buffer.put(vertex.getElements());
		}
		buffer.flip();
		objectBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, objectBufferID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STREAM_DRAW);
		glVertexAttribPointer(VERTEXPOSITION, GLVertex.positionElementCount, GL_FLOAT, 
                false, GLVertex.stride, GLVertex.positionByteOffset);
        glVertexAttribPointer(COLOURPOSITION, GLVertex.colorElementCount, GL_FLOAT, 
                false, GLVertex.stride, GLVertex.colorByteOffset);
        glVertexAttribPointer(TEXTUREPOSITION, GLVertex.textureElementCount, GL_FLOAT, 
                false, GLVertex.stride, GLVertex.textureByteOffset);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        objectIndicesBufferID = createOrderBuffer();
		glBindVertexArray(0);
	}
	protected int createOrderBuffer() {
		System.out.println(getDrawLength());
		ByteBuffer buffer = BufferUtils.createByteBuffer(draworder.length);
		buffer.put(draworder);
		buffer.flip();
		int indicesBufferID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		return indicesBufferID;
	}
	public void updateBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(getVertices().size() * GLVertex.elementCount);
		for (var vertex : getVertices()) {
			buffer.put(vertex.getElements());
		}
		buffer.flip();
		glBindBuffer(GL_ARRAY_BUFFER, objectBufferID);
		glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	public void unbindObject() {
		GLBound = false;
		glBindVertexArray(objectID);
		glDeleteBuffers(objectBufferID);
		glDeleteBuffers(objectIndicesBufferID);
		glBindVertexArray(0);
		glDeleteVertexArrays(objectID);
	}
	protected abstract void createVertices();
	
	public List<GLVertex> getVertices() {
		calculateVertices();
		return Collections.unmodifiableList(vertices);
	}
	
	public int getObjectID() {
		return objectID;
	}
	public int getObjectBufferID() {
		return objectBufferID;
	}
	public int getObjectIndicesBufferID() {
		return objectIndicesBufferID;
	}
	public abstract BufferedImage getSprite();
	
	protected abstract void calculateVertices();
	
	protected float calculateRelativePosition(int coordinate, int totalsize) {
		double percentage = (double) coordinate / (double) totalsize; //maths stuff
		percentage = (percentage * 2) - 1; //graphics coordinates go from -1 to 1 so i need percentage that goes from
		return (float) percentage;
	}
	
	protected float flipY(double y) {
		if (y <= 0) {
			return (float) Math.abs(y);
		} else {
			return (float) (0-y);
		}
	}
	
	public boolean isGLBound() {
		return GLBound;
	}

	public int getDrawLength() {
		return draworder.length;
	}
	
	protected void setDrawOrder() {
		draworder = createDrawOrder();
	}
}
