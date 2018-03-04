package stuff;

public class Engine implements Runnable {
	private boolean running;
	
	private Paddel pad;
	private Ball ball;

	public Engine() {
		
	}
	
	@Override
	public void run() {
		init();
		while (true) {
			try {
				main();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		pad = new Paddel(11, 13, 6);
		ball = new Ball(14, 7);
		
		Engine e = this;
		TickTimer gameTickTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (e) {
					//pause function
					if (Settings.GAME_RUNNING) e.notify();
				}
			}
		};
		Main.systemTimer.schedule(gameTickTimer, 0, Settings.GAME_TICK__MS);
	}
	
	private synchronized void main() throws InterruptedException {
		pad.move();
		if (pad.getPosition().getX() < 0) {
			pad.setPosition(0);
			pad.setVelocity(0, 0);
		} else if (pad.getPosition().getX() + pad.getSize().getX() > 27) {
			pad.setPosition(27);
			pad.setVelocity(0, 0);
		}
		
		ball.move();
		Vector2f pos = ball.getPosition();
		Vector2f vel = ball.getVelocity();
		float angle = vel.angle(pad.getVelocity());
		if (pos.getX() < 0) {
			ball.setPosition(0, pos.getY());
			ball.setVelocity(vel.getX(), -vel.getY());
		} else if (pos.getX() > 27) {
			ball.setPosition(27, pos.getY());
			ball.setVelocity(vel.getX(), -vel.getY());
		} else if (pos.getY() < 0) {
			ball.setPosition(pos.getX(), 0);
			ball.setVelocity(-vel.getX(), vel.getY());
		} else if (pos.getY() > 13) {
			if (pos.getX() >= pad.getPosition().getX() && pos.getX() < pad.getPosition().getX()) {
				ball.setPosition(pos.getX(), 13);
				ball.setVelocity(vel.rotate((float) (Math.PI - 2 * angle)));
			} else {
				//loose
			}
		}
	}
	
	//TODO:
	public void changePaddelVelocity() {
		
	}

	public void pause() {
		running = false;
	}

	public void unpause() {
		running = true;
	}

	public boolean isPaused() {
		return !running;
	}
}
