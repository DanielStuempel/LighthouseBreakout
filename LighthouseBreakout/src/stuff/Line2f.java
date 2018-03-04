package stuff;

public class Line2f {
	Vector2f p1, p2;
	
	public Line2f(Vector2f p1, Vector2f p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public float distPoint(Vector2f p) {
		float
			x0 = p.getX(),
			y0 = p.getY(),
			x1 = p1.getX(),
			y1 = p1.getY(),
			x2 = p2.getX(),
			y2 = p2.getY();
		
		return (float) (Math.abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1)
				/ Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (y2 - y1)));
	}
}
