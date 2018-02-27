package stuff;

import java.awt.Dimension;

public class Level {
	public Dimension size = new Dimension(28, 14);
	private Brick[][] map;
	
	public static Level buildLevel(int i) {
		Level l = new Level();
		l.map = new Brick[l.size.width][l.size.height];
		int[][] map;
		switch (i) {
		case 1:
			map = Maps.CAU.getMap();
			break;
		case 2:
			map = Maps.FULL.getMap();
			break;
		case 3:
			map = Maps.TEST.getMap();
			break;
		default:
			map = Maps.TEST.getMap();
		}
		
		//don't draw invalid map
		if (map.length < l.size.height)
			map = null;
		
		else
			for (int y = 0; y < l.size.height; y++) {
				// don't draw too short rows
				if (map[y].length < l.size.width)
					continue;

				for (int x = 0; x < l.size.width; x++)
					l.map[x][y] = new Brick(map[y][x]);
			}
		return l;
	}
	
	public Brick[][] getMap() {
		return map;
	}
}
