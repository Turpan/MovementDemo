package movementTest;

import movement.EnemyAI;
import movement.GameListener;

public class ChaserAI implements EnemyAI{
	GameListener gameListener;
	public ChaserAI(GameListener gameListener) {
		if (gameListener == null) {
			throw new NullPointerException();
		}
		this.gameListener = gameListener;
	}
	@Override
	public int getDesiredX() {
		return gameListener.getPlayerLocation().width;
	}
	@Override
	public int getDesiredY() {
		return gameListener.getPlayerLocation().height;
	}
	@Override
	public boolean isActive() {
		// TODO
		return true;
	}
}
