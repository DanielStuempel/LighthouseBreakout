package stuff;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

public class EndScreen extends Menu {
	public EndScreen() {

		CoolLabel top = new CoolLabel("NOT DONE YET...", 80);
		setLayout(new GridLayout(4, 1));
		if (Settings.GAME_WON)
			top.setText("YOU DID IT!!!");

		add(top);
		CoolLabel scoreboard = new CoolLabel("SCOREBOARD", 60);

		add(scoreboard);
		loadScores();
	}

	private void loadScores() {
		JPanel scores = new JPanel();
		scores.setLayout(new GridLayout(4, 1));
		scores.setBackground(Style.background);
		scores.add(new CoolLabel("WINNER   :   more", 40));
		scores.add(new CoolLabel(Settings.PLAYER_NAME + "  :  " + Integer.toString(Settings.SCORE), 40));
		scores.add(new CoolLabel("LOOSER   :   less", 40));
		add(scores);
	}
}
