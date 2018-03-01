package stuff;

import java.awt.Dimension;

public class Level {
	public Dimension size = new Dimension(28, 14);
	private Brick[][] state;
	private int[][] map;
	
	public static Level buildLevel(int i) {
		Level l = new Level();
		l.state = new Brick[l.size.width][l.size.height];
		switch (i) {
		case 1:
			l.map = Maps.CAU.getMap();
			break;
		case 2:
			l.map = Maps.FULL.getMap();
			break;
		case 3:
			l.map = Maps.TEST.getMap();
			break;
		default:
			l.map = Maps.TEST.getMap();
		}
		
		//don't draw invalid map
		if (l.map.length < l.size.height)
			l.map = null;
		
		else
			for (int y = 0; y < l.size.height; y++) {
				// don't draw too short rows
				if (l.map[y].length < l.size.width)
					continue;

				for (int x = 0; x < l.size.width; x++)
					l.state[x][y] =  new Brick(l.map[y][x]);
			}
		return l;
	}
	
	public Brick get(int x, int y) {
		return state[x][y];
	}
}
