package stuff;

public class Brick {
	private int type;
	
	public Brick(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void hit() {
		if (type > 0) type--;
	}
}
