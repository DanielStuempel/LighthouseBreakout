package stuff;

public class Brick {
	private int type;
	
	public Brick(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	public Brick hit() {
		return new Brick(type - 1);
	}
}
