package stuff;

import java.awt.Point;

public abstract class Entity {
	private Vector2f position = new Vector2f(0, 0);
	private Vector2f velocity = new Vector2f(0, 0);
	private Vector2f size = new Vector2f(0, 0);
	
	public void move() {
		this.position = this.position.add(velocity);
	}

//	public Point getLocation() {
//		return new Point((int) (position.getX() + size.getX() / 2), (int) (position.getY() + size.getY() / 2));
//	}
	
	public Vector2f getPosition() {
		return new Vector2f(position);
	}
	
	public void setPosition(Vector2f pos) {
		this.position = new Vector2f(pos);
	}
	
	public void setPosition(float x, float y) {
		this.position = new Vector2f(x, y);
	}
	
	public Point getDirection() {
		return new Point((int) (velocity.getX() / Math.abs(velocity.getX())), (int) (velocity.getY() / Math.abs(velocity.getY())));
	}
	
	public Vector2f getVelocity() {
		return new Vector2f(velocity);
	}
	
	public void setVelocity(Vector2f vel) {
		this.velocity = new Vector2f(vel);
	}
	
	public void setVelocity(float x, float y) {
		this.velocity = new Vector2f(x, y);
	}
	
	public Vector2f getSize() {
		return new Vector2f(size);
	}
	
	public void setSize(Vector2f size) {
		this.size = new Vector2f(size);
	}
	
	public void setSize(float x, float y) {
		this.size = new Vector2f(x, y);
	}
	
	public Vector2f getStart() {
		return getPosition();
	}
	
	public Vector2f getEnd() {
		return getPosition().add(getSize());
	}
}
