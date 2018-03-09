package model;

import java.awt.Dimension;

public class Level {
	public Dimension size = new Dimension(28, 14);
	private Brick[][] state;
	private Map map;
	public int maxScore;
	private int score;
	
	public Level(Map m) {
		state = new Brick[size.width][size.height];
		map = m;
		buildLevel();
	}
	
	private void buildLevel() {
		maxScore = 0;
		score = 0;
		for (int y = 0; y < size.height && y < map.getMap().length; y++) {
			for (int x = 0; x < size.width && y < map.getMap()[y].length; x++) {
				state[x][y] =  new Brick(map.getMap()[y][x]);
				maxScore += state[x][y].getType();
			}
		}
	}
	
	public Brick get(int x, int y) {
		return state[x][y];
	}
	
	public void reset() {
		buildLevel();
	}
	
	public int getScore() {
		return score;
	}
	
	public int hit(int x, int y) {
		if (x < 0 || y < 0 || x > size.width || y > size.height)
			return 0;
		Brick b = state[x][y];
		int type = b.getType();
		if (type > 0) score++;
		b.hit();
		return type;
	}
}
