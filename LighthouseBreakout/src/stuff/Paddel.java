package stuff;

public class Paddel extends Entity {
	public Paddel(float x, float w) {
		setPosition(x, 13);
		setSize(w, 1);
	}
	
	public void setPosition(float x) {
		setPosition(new Vector2f(x, getPosition().getY()));
	}
}
