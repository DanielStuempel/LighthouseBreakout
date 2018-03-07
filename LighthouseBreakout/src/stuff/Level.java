package stuff;

import java.awt.Dimension;

public class Level {
	public Dimension size = new Dimension(28, 14);
	private Brick[][] state;
	private Map map;
	private int pointsToEnd = 0;
	
	public Level(Map m) {
		state = new Brick[size.width][size.height];
		map = m;
		buildLevel();
	}
	
	private void buildLevel() {
		for (int y = 0; y < size.height && y < map.getMap().length; y++) {
			for (int x = 0; x < size.width && y < map.getMap()[y].length; x++) {
				state[x][y] =  new Brick(map.getMap()[y][x]);
				pointsToEnd += state[x][y].getType();
			}
		}
	}
	
	public Brick get(int x, int y) {
		return state[x][y];
	}
	
	public void reset() {
		buildLevel();
	}
	public int neededPoints() {
		return pointsToEnd/2;
	}
}
