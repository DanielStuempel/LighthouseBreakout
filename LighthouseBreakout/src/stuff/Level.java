package stuff;

public class Level {
	private Brick[][] map = new Brick[28][14];
	
	public Level() {
		this(0);
	}
	
	public Level(int i) {
		int[][] map;
		if (i == 1) {
			map = Maps.CAU.getMap();
		} 
		else if (i == 2) {
			map = Maps.FULL.getMap();
		}
		else {
			map = Maps.TEST.getMap();
		}
		for (int x = 0; x < 28; x++) {
			for (int y = 0; y < 14; y++) {
				this.map[x][y] = new Brick(map[y][x]);
			}
		}
	}
	
	public Brick[][] getMap() {
		return map;
	}
}
