package stuff;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;

import stuff.Window.MainPanel;

public class EndScreen extends Menu {
	CoolLabel cl;
	CoolLabel top;
	public EndScreen(CardLayout layout, MainPanel contentPane) {
		super(layout, contentPane, "endScreen");
		top = new CoolLabel("NOT DONE YET...", 80);
		setLayout(new GridLayout(4, 1));
		if (Settings.GAME_WON)
			top.setText("YOU DID IT!!!");
			Settings.GAME_WON = false;
		add(top);
		CoolLabel scoreboard = new CoolLabel("SCOREBOARD", 60);

		add(scoreboard);
		loadScores();
		
		add(new MenuButton("AGAIN") {
			
			@Override
			public void onClick(ActionEvent e) {
				layout.show(contentPane, "display");
				
			}
		});
	}
	public void reload() {
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
		scores.add(new CoolLabel("WINNER   :   more", 40));
		cl = new CoolLabel(Settings.PLAYER_NAME + "  :  " + Settings.SCORE, 40);
		scores.add(cl);
		scores.add(new CoolLabel("LOOSER   :   less", 40));
		Settings.SCORE = 0;
		add(scores);
	}
}
