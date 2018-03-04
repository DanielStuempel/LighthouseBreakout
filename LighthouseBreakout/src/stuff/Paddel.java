package stuff;

public class Paddel extends Entity {
	public Paddel(float x, float y, float w) {
		setPosition(x, y);
		setSize(w, 1);
	}
	
	public void setPosition(float x) {
		setPosition(new Vector2f(x, getPosition().getY()));
	}
}
