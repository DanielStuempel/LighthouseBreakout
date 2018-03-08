package stuff;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class ControlMenu extends Menu {
	public ControlMenu() {
		setLayout(new GridLayout(10, 1));
		LinkedList<MenuButton> menuButton = new LinkedList<>();
		
		menuButton.add(new MenuButton("Name : " + Settings.PLAYER_NAME, 40) {

			@Override
			public void onClick(ActionEvent e) {
				
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
				Settings.FONT_CUSTOM ^= true;
				update("Cool Font : " + (Settings.FONT_CUSTOM ? "ON" : "OFF"));
				//TODO: anpassen auf Daniels Code
				for (MenuButton b : Window.w.button) {
					b.update();
				}
				Window.w.validate();
				
				for (MenuButton b : menuButton) {
					b.update();
				}
				
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
				update("Annoying End Sound : " + (Settings.SOUND_XP_SHUTDOWN ? "ON" : "OFF"));
			}
		});
		menuButton.add(new MenuButton("Funny End Sound : " + (Settings.SOUND_SAD_TRUMPET ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_SAD_TRUMPET ^= true;
				update("Funny End Sound : " + (Settings.SOUND_SAD_TRUMPET ? "ON" : "OFF"));
			}
		});

		for (MenuButton b : menuButton) {
			add(b);
		}
	}
}
