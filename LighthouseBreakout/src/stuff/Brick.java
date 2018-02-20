package stuff;

import java.awt.Color;

public class Brick {

	private Color color;
	private int type;
	
	public Brick(int type) {
		this.type = type;
		//this.color = Style.typeColor[this.type];
		this.color = Color.WHITE;			//Standart Farbe des Typen
	}
	
	public int getType() {
		return this.type;
	}
	public Color getColor() {
		return this.color;
	}
	public Brick downgrade() {
		if (type == 1) {
			return null;
		}
		return new Brick(type-1);
	}
	
}
