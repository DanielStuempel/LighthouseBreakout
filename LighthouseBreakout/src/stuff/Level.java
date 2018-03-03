package stuff;

import java.awt.Dimension;

public class Level {
	public Dimension size = new Dimension(28, 14);
	private Brick[][] state;
	private Map map;
	
	public Level(Map m) {
		state = new Brick[size.width][size.height];
		map = m;
		buildLevel();
	}
	
	private void buildLevel() {
		for (int y = 0; y < size.height && y < map.getMap().length; y++) {
			for (int x = 0; x < size.width && y < map.getMap()[y].length; x++)
				state[x][y] =  new Brick(map.getMap()[y][x]);
		}
	}
	
	public Brick get(int x, int y) {
		return state[x][y];
	}
	
	public void reset() {
		buildLevel();
	}
}
