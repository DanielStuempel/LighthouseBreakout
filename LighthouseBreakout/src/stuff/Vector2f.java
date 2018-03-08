package stuff;

import java.awt.Dimension;
import java.awt.Point;

public class Vector2f {
	private float x, y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public Vector2f(Dimension d) {
		this((float) d.getWidth(), (float) d.getHeight());
	}
	
	public Vector2f(Point p) {
		this((float) p.getX(), (float) p.getY());
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public float dot(Vector2f vec) {
		return x * vec.x + y * vec.y;
	}
	
	public Vector2f normalize() {
		float length = length();
		return new Vector2f(x / length, y / length);
	}
	
	public Vector2f normalize(float l) {
		return normalize().multiply(l);
	}
	
	public Vector2f rotateDegrees(float angle) {
		return rotate((float) Math.toRadians(angle));
	}
	
	public Vector2f rotate(float rad) {
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}
	
	public float angle(Vector2f vec) {
		return (float) Math.acos(dot(vec) / (length() * vec.length()));
	}
	
	public Vector2f add(Vector2f vec) {
		return new Vector2f(x + vec.x, y + vec.y);
	}
	
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}
	
	public Vector2f substract(Vector2f vec) {
		return new Vector2f(x - vec.x, y - vec.y);
	}
	
	public Vector2f substract(float r) {
		return new Vector2f(x - r, y - r);
	}
	
	public Vector2f multiply(Vector2f vec) {
		return new Vector2f(x * vec.x, y * vec.y);
	}
	
	public Vector2f multiply(float r) {
		return new Vector2f(x * r, y * r);
	}
	
	public Vector2f divide(Vector2f vec) {
		return new Vector2f(x / vec.x, y / vec.y);
	}
	
	public Vector2f divide(float r) {
		return new Vector2f(x / r, y / r);
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}