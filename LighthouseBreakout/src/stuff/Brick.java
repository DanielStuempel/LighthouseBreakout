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
		return type == 1 ? null : new Brick(type - 1);
	}
}
