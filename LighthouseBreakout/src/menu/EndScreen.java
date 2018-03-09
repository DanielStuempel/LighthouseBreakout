package menu;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import stuff.Settings;
import stuff.Style;
import stuff.Window.MainPanel;

public class EndScreen extends Menu {
	private MenuLabel cl;
	private MenuLabel top;

	public EndScreen(CardLayout layout, MainPanel contentPane) {
		super(layout, contentPane, "endScreen");
		top = new MenuLabel("NOT DONE YET...", 80);
		setLayout(new GridLayout(4, 1));
		if (Settings.GAME_WON)
			top.setText("YOU DID IT!!!");
		Settings.GAME_WON = false;
		add(top);
		MenuLabel scoreboard = new MenuLabel("SCOREBOARD", 60);

		add(scoreboard);
		loadScores();

		add(new MenuButton("AGAIN") {

			@Override
			public void onClick(ActionEvent e) {
				layout.show(contentPane, "display");

			}
		});
	}

	@Override
	public void reload() {
		super.reload();
		cl.setText(Settings.PLAYER_NAME + "  :  " + Settings.SCORE);
		if (Settings.GAME_WON) {
			top.setText("YOU DID IT!!!");
			Settings.GAME_WON = false;
		} else {
			top.setText("NOT DONE YET...");
		}
	}

	private void loadScores() {
		JPanel scores = new JPanel();
		scores.setLayout(new GridLayout(4, 1));
		scores.setBackground(Style.background);
		scores.add(new MenuLabel("WINNER   :   more", 40));
		cl = new MenuLabel(Settings.PLAYER_NAME + "  :  " + Settings.SCORE, 40);
		scores.add(cl);
		scores.add(new MenuLabel("LOOSER   :   less", 40));
		Settings.SCORE = 0;
		add(scores);
	}
}
