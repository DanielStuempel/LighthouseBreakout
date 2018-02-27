package stuff;

import java.util.Arrays;

public enum Maps {
	TEST();
	
	public int[][] getMap() {
		switch (this) {
		case TEST:
			int[][] a = new int[14][28];
			int[] b = new int[28];
			for (int i = 0; i < 10; i++) {
				Arrays.fill(b, 10 - i);
				a[i] = Arrays.copyOf(b, 28);
			}
			return a;
			default:
				return null;
		}
	}
}
