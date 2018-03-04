package stuff;

public class Vector2f {
	private float x, y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public float dot(Vector2f vec) {
		return x * vec.x + y + vec.x;
	}
	
	public Vector2f normalize() {
		return normalize(length());
	}
	
	public Vector2f normalize(float length) {
		return new Vector2f(x / length, y / length);
	}
	
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin - y * cos));
	}
	
	public float angle(Vector2f vec) {
		return dot(vec) / ((length() * vec.length()));
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
	
	public float getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}