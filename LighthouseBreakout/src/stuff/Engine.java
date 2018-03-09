package stuff;

public abstract class Engine implements Runnable {
	private boolean paused = true;
	
	private SyncList<GameEventListener> listeners = new SyncList<>();
	
	public interface GameEventListener {
		public abstract void gameWon();
		public abstract void gameLost();
	}
	
	public void addEventGameListener(GameEventListener l) {
		listeners.syncAdd(l);
	}
	
	public SyncList<GameEventListener> getEventListeners() {
		return listeners;
	}

	public abstract Level getLevel();
	public abstract Paddel getPaddel();
	public abstract Ball getBall();

	public abstract void movePaddel(float f);

	public abstract void reset();

	public abstract void debug();

	public void pause() {
		paused = true;
	}

	public void unpause() {
		paused = false;
	}

	public boolean isPaused() {
		return paused;
	}
}