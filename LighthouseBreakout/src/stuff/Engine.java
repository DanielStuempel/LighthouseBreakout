package stuff;

public abstract class Engine implements Runnable {
	private boolean paused;

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