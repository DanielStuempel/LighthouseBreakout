package stuff;

public class Level {

	public static int[][][] map = new int[1][28][14];
	
//	public Brick[][] level = new Brick[28][14];
//
//	Level(int i) {
//		buildLevel(i);
//	}
//
//	Level() {
//		
//	}
	

	public static Brick[][] buildLevel(int i) {
		Brick[][] level = new Brick[28][14];
		for (int x = 0; x < 28; x++) {
			for (int y = 0; y < 14; y++) {
				level[x][y] = new Brick(map[i][x][y]);
			}
		}
		return level;
	}
			
			
}
