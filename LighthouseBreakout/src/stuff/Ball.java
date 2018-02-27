package stuff;

public class Ball {
	//x ,y pos start at index 0
	private static int[] pos = {10,0};
	private static double[] speed = {1,1};
	
	public static int getPos() {
		move();
		int posInt = pos[0]*28+pos[1] ;
		return posInt;
	}
	public static double[] getSpeed() {
		return speed;
	}
	public static void setSpeed(double[] newSpeed) {
		speed = newSpeed;
	}
	public static void move() {
		speed[0] = pos[0]+speed[0] > 13 ? speed[0] = -speed[0] : speed[0];
		speed[0] = pos[0]+speed[0] < 0  ? speed[0] = -speed[0] : speed[0];
		speed[1] = pos[1]+speed[1] > 27 ? speed[1] = -speed[1] : speed[1];
		speed[1] = pos[1]+speed[1] < 0 ? speed[1] = -speed[1] : speed[1];
		
		pos[0] += (int)speed[0];
		pos[1] += (int)speed[1];
		
	}
}
