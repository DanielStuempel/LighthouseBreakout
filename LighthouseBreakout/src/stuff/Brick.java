package stuff;

public class Brick {
	private int type;
	private boolean isDestroyed;
	
	public Brick(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void hit() {
		if (type > 0 && --type == 0)
			destroy();
	}
	
	public void destroy() {
		isDestroyed = true;
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
}
