package model;

public class Brick {
	private int type;
	
	public Brick(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	public boolean hit() {
		if (type == 0) return false;
		if (type > 0) type--;
		return true;
	}
}
