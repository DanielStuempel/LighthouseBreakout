package stuff;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ControlMenu extends Menu {
	public ControlMenu(CardLayout layout, JPanel contentPane) {
		super(layout, contentPane, "controlsMenu");
		setLayout(new GridLayout(10, 1));
		LinkedList<MenuButton> menuButton = new LinkedList<>();

		menuButton.add(new MenuButton("Name : " + Settings.PLAYER_NAME, 40) {

			@Override
			public void onClick(ActionEvent e) {
				String input = JOptionPane.showInputDialog("I hate", Settings.PLAYER_NAME);
				if (input == null || input.isEmpty())
					return;
				Settings.PLAYER_NAME = input;
				update("Name : " + Settings.PLAYER_NAME);
			}
		});
		menuButton.add(new MenuButton("Control : " + (Settings.MOUSE_CONTROL ? "MOUSE" : "KEYBOARD"), 40) {
			@Override
			public void onClick(ActionEvent e) {
				Settings.MOUSE_CONTROL ^= true;
				update("Control : " + (Settings.MOUSE_CONTROL ? "MOUSE" : "KEYBOARD"));
			}
		});
		menuButton.add(new MenuButton("Hax : " + (Settings.HAX_ON ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.HAX_ON ^= true;
				update("Hax : " + (Settings.HAX_ON ? "ON" : "OFF"));
			}
		});
		menuButton.add(new MenuButton("Cool Font : " + (Settings.FONT_CUSTOM ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
//				Settings.FONT_CUSTOM ^= true;
//				update("Cool Font : " + (Settings.FONT_CUSTOM ? "ON" : "OFF"));
//				// TODO: anpassen auf Daniels Code
//				for (MenuButton b : Window.button) {
//					b.update();
//				}
//				Window.w.validate();
//
//				for (MenuButton b : menuButton) {
//					b.update();
//				}
			}
		});
		menuButton.add(new MenuButton("Annoying Start Sound : " + (Settings.SOUND_XP_START ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_XP_START ^= true;
				update("Annoying Start Sound : " + (Settings.SOUND_XP_START ? "ON" : "OFF"));
			}
		});
		menuButton.add(new MenuButton("Annoying End Sound : " + (Settings.SOUND_XP_SHUTDOWN ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_XP_SHUTDOWN ^= true;
				Settings.SOUND_SAD_TRUMPET ^= true;
				update("Annoying End Sound : " + (Settings.SOUND_XP_SHUTDOWN ? "ON" : "OFF"));
			}
		});
		menuButton.add(new MenuButton("Funny End Sound : " + (Settings.SOUND_SAD_TRUMPET ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_SAD_TRUMPET ^= true;
				Settings.SOUND_XP_SHUTDOWN ^= true;

				update("Funny End Sound : " + (Settings.SOUND_SAD_TRUMPET ? "ON" : "OFF"));
			}
		});
		for (MenuButton b : menuButton) {
			add(b);
		}
		add(new CoolLabel("", 20));
	}
}
