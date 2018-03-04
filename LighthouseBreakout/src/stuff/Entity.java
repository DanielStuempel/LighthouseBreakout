package stuff;

public abstract class Entity {
	private Vector2f position = new Vector2f(0, 0);
	private Vector2f velocity = new Vector2f(0, 0);
	
	public void move() {
		position = position.add(velocity);
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public void setPosition(Vector2f pos) {
		position = pos;
	}
	
	public Vector2f getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector2f vel) {
		velocity = vel;
	}
}
