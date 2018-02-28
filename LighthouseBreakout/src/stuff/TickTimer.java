package stuff;

import java.util.TimerTask;

public abstract class TickTimer extends TimerTask {
	private double fps;
	private long lastTick;
	private long tickCount;
	
	@Override
	public void run() {
		fps = 1_000_000_000.0 / (System.nanoTime() - lastTick);
		tick();
		lastTick = System.nanoTime();
		tickCount++;
	}
	
	public abstract void tick();
	
	public double getCurrentFPS() {
		return fps;
	}
	
	public long getTickCount() {
		return tickCount;
	}
}
