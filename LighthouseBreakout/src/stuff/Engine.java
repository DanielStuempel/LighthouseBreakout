package stuff;

public class Engine implements Runnable {
	private boolean running;
	
	private Paddel pad;
	private Ball ball;

	private Display display;
	
	public Engine() {
		
	}
	
	public Engine(Display display) {
		this.display = display;
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
		pad = new Paddel(0, 13, 27);
		ball = new Ball(0, 0);
		
		ball.setVelocity(1f, 1f);
		
		Engine e = this;
		TickTimer gameTickTimer = new TickTimer() {
			@Override
			public void tick() {
				synchronized (e) {
					e.notify();
				}
			}
		};
		Main.systemTimer.schedule(gameTickTimer, 0, 200);
	}
	
	private synchronized void main() throws InterruptedException {
		wait();
		
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
		Vector2f newPos = pos.add(vel);
//		System.out.println(pos + ":" + vel);
		float angle = vel.angle(new Vector2f(1, 0));
//		System.out.println(angle);
		
		if (newPos.getX() < 0) {
			ball.setPosition(0, pos.getY());
			ball.setVelocity(-vel.getX(), vel.getY());
		} else if (newPos.getX() > 27) {
			ball.setPosition(27, pos.getY());
			ball.setVelocity(-vel.getX(), vel.getY());
		}
		if (newPos.getY() < 0) {
			ball.setPosition(pos.getX(), 0);
			ball.setVelocity(vel.getX(), -vel.getY());
		} else if (newPos.getY() > 13) {
			if (newPos.getX() >= pad.getPosition().getX() && newPos.getX() < pad.getPosition().getX() + pad.getSize().getX() - 1) {
				ball.setPosition(newPos.getX(), 12);
				ball.setVelocity(vel.rotate((float) (Math.PI + 2 * angle)));
			} else {
				//loose
				ball.setPosition(0, 0);
			}
		}
		
		byte[] data = new byte[28 * 14 * 3];
		
		int p = Math.round(ball.getPosition().getX() + ball.getPosition().getY() * 28) * 3;
		data[p++] = (byte) Style.ballColor.getRed();
		data[p++] = (byte) Style.ballColor.getGreen();
		data[p] = (byte) Style.ballColor.getBlue();

		// draw paddel
		p = (int) (pad.getPosition().getX() + pad.getPosition().getY() * 28) * 3;
		for (int i = 0; i < pad.getSize().getX(); i++) {
			data[p++] = -1;
			data[p++] = -1;
			data[p++] = -1;
		}
		
		display.send(data);
	}
	
	//TODO:
	public void changePaddelVelocity() {
		
	}
	
	public Ball getPosition() {
		return ball;
	}
	
	public Paddel getPaddel() {
		return pad;
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
