package stuff;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import stuff.Window.MainPanel;

public class SettingsMenu extends Menu {
	public SettingsMenu(CardLayout layout, MainPanel contentPane) {
		super(layout, contentPane, "settingsMenu");
		setLayout(new GridLayout(10, 1));

		add(new MenuButton("Name : " + Settings.PLAYER_NAME, 40) {
			@Override
			public void onClick(ActionEvent e) {
				String input = JOptionPane.showInputDialog("your name", Settings.PLAYER_NAME);
				if (input == null || input.isEmpty())
					return;
				Settings.PLAYER_NAME = input;
				setText("Name : " + Settings.PLAYER_NAME);
			}
		});
		
		add(new MenuButton("Level : " + Settings.LEVEL, 40) {
			@Override
			public void onClick(ActionEvent e) {
				Settings.LEVEL = Map.next();
				setText("Level : " + Settings.LEVEL);
			}
		});

		add(new MenuButton("Control : " + (Settings.MOUSE_CONTROL ? "MOUSE" : "KEYBOARD"), 40) {
			@Override
			public void onClick(ActionEvent e) {
				Settings.MOUSE_CONTROL ^= true;
				setText("Control : " + (Settings.MOUSE_CONTROL ? "MOUSE" : "KEYBOARD"));
			}
		});

		add(new SwitchButton("Hax", 40, Settings.HAX_ON) {
			@Override
			public void onClick(ActionEvent e) {
				Settings.HAX_ON ^= true;
				changeValue(Settings.HAX_ON);
			}
		});
		
		add(new SwitchButton("Cool Font", 40, Settings.CUSTOM_FONT) {
			@Override
			public void onClick(ActionEvent e) {
				Style.font = (Settings.CUSTOM_FONT ^= true) ? Style.customFont : Style.defaultFont;
				changeValue(Settings.CUSTOM_FONT);
				contentPane.reload();
			}
		});

		add(new SwitchButton("Annoying Start Sound", 40, Settings.SOUND_XP_START) {
			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_XP_START ^= true;
				changeValue(Settings.SOUND_XP_START);
			}
		});

		add(new MenuButton("End Sound : " + (Settings.SOUND_XP_SHUTDOWN ? "ANNOYING" : "FUNNY"), 40) {
			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_XP_SHUTDOWN ^= true;
				setText("End Sound : " + (Settings.SOUND_XP_SHUTDOWN ? "Annoying" : "Funny"));
			}
		});

		add(new CoolLabel("", 20));

		add(new MenuButton("BACK") {
			@Override
			public void onClick(ActionEvent e) {
				layout.previous(contentPane);
			}
		});
	}
}
