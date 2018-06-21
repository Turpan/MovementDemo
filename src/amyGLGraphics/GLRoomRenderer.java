package amyGLGraphics;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;

import amyGLGraphicsIO.DecodedPNG;
import amyGraphics.RoomRenderer;
import movement.Entity;
import movement.Room;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class GLRoomRenderer extends RoomRenderer{
	public static final int VERTEXPOSITION = 0;
	public static final int COLOURPOSITION = 1;
	public static final int TEXTUREPOSITION = 2;
	Map<BufferedImage, GLTexture> textureMap = new HashMap<BufferedImage, GLTexture>();
	Map<Entity, GLEntity> entityMap = new HashMap<Entity, GLEntity>();
	GLBackground background = new GLBackground();
	public GLRoomRenderer() {
		resetState();
	}
	public GLRoomRenderer(Room room) {
		this();
		setRoom(room);
	}
	private void addEntity(Entity entity) {
		if (!entityMap.containsKey(entity)) {
			createBufferedEntity(entity);
			createTexture(entity);
		}
	}
	private void addEntity(List<Entity> entityList) {
		for (var entity : entityList) {
			addEntity(entity);
		}
	}
	private void removeEntity(Entity entity) {
		boolean textureUnbound = unbindEntity(entity);
		entityMap.remove(entity);
		if (textureUnbound) {
			var sprite = entity.getSprite();
			textureMap.remove(sprite);
		}
	}
	private void removeEntity(List<Entity> entityList) {
		for (var entity : entityList) {
			removeEntity(entity);
		}
	}
	private void setBackground(BufferedImage sprite) {
		background.setSprite(sprite);
	}
	/*
	 * Eveything in this class, but this most of all, assumes the openGl context has already been created
	 */
	public void renderRoom() {
		if (background.isGLBound()) {
			//renderBackground();
		}
		for (var entity : room.getContents()) {
			render(entity);
		}
	}
	private void render(Entity entity) {
		var bufferedtexture = textureMap.get(entity.getSprite());
		int textureID = bufferedtexture.getTextureID();
		var bufferedentity = entityMap.get(entity);
		int objectID = bufferedentity.getObjectID();
		int objectIndicesID = bufferedentity.getObjectIndicesBufferID();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, textureID);
		glBindVertexArray(objectID);
		bufferedentity.updateBuffer();
		glEnableVertexAttribArray(VERTEXPOSITION);
		glEnableVertexAttribArray(COLOURPOSITION);
		glEnableVertexAttribArray(TEXTUREPOSITION);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, objectIndicesID);
		glDrawElements(GL_TRIANGLES, bufferedentity.getDrawLength(), GL_UNSIGNED_BYTE, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(VERTEXPOSITION);
		glDisableVertexAttribArray(COLOURPOSITION);
		glDisableVertexAttribArray(TEXTUREPOSITION);
		glBindVertexArray(0);
	}
	private void renderBackground() {
		int textureID = background.getTextureID();
		int objectID = background.getObjectID();
		int objectIndicesID = background.getObjectIndicesBufferID();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, textureID);
		glBindVertexArray(objectID);
		background.updateBuffer();
		glEnableVertexAttribArray(VERTEXPOSITION);
		glEnableVertexAttribArray(COLOURPOSITION);
		glEnableVertexAttribArray(TEXTUREPOSITION);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, objectIndicesID);
		glDrawElements(GL_TRIANGLES, background.getDrawLength(), GL_UNSIGNED_BYTE, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(VERTEXPOSITION);
		glDisableVertexAttribArray(COLOURPOSITION);
		glDisableVertexAttribArray(TEXTUREPOSITION);
		glBindVertexArray(0);
	}
	private void createBufferedEntity(Entity entity) {
		var bufferedEntity = new GLEntity(entity);
		entityMap.put(entity, bufferedEntity);
	}
	private void createTexture(Entity entity) {
		if (!textureMap.containsKey(entity.getSprite())) {
			var texture = new GLTexture(entity.getSprite());
	        textureMap.put(entity.getSprite(), texture);
		}
	}
	public void resetState() {
		unBindOpenGL();
		textureMap = new HashMap<BufferedImage, GLTexture>();
		entityMap = new HashMap<Entity, GLEntity>();
	}
	private void unBindOpenGL() {
		if (hasRoom()) {
			removeEntity(room.getContents());
		}
		if (background.isGLBound()) {
			background.unbindObject();
		}
	}
	/*
	 * returns true if texture was unbound
	 */
	private boolean unbindEntity(Entity entity) {
		boolean textureUnbound = false;
		unbindEntityBuffer(entity);
		if (!textureRemains(entity)) {
			unbindTexture(entity);
			textureUnbound = true;
		}
		return textureUnbound;
	}
	private void unbindEntityBuffer(Entity entity) {
		var bufferedentity = entityMap.get(entity);
		bufferedentity.unbindObject();
	}
	private void unbindTexture(Entity entity) {
		var texture = textureMap.get(entity.getSprite());
		texture.unbindTexture();
	}
	private boolean textureRemains(Entity entity) {
		for (var testedentity : room.getContents()) {
			if (testedentity != entity) {
				if (entity.getSprite().equals(testedentity.getSprite())) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void setRoom(Room room) {
		if (hasRoom()) {
			getRoom().removeListener(this);
			unBindOpenGL();
		}
		addEntity(room.getContents());
		setBackground(room.getBackground());
		room.addListener(this);
		super.setRoom(room);
	}
	
	@Override
	public void entityAdded(Entity entity) {
		addEntity(entity);
	}
	@Override
	public void entityRemoved(Entity entity) {
		removeEntity(entity);
	}
	@Override
	public void backgroundChanged(BufferedImage background) {
		setBackground(background);
	}
}
