package stuff;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class ControlMenu extends Menu {
	public ControlMenu() {
		setLayout(new GridLayout(10, 1));
		add(new MenuButton("Name : " + Settings.PLAYER_NAME, 40) {

			@Override
			public void onClick(ActionEvent e) {
				
			}
		});
		add(new MenuButton("Control : " + (Settings.MOUSE_CONTROL ? "MOUSE" : "KEYBOARD"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.MOUSE_CONTROL ^= true;
				validate();
			}
		});
		add(new MenuButton("Hax : " + (Settings.HAX_ON ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.HAX_ON ^= true;
				validate();
			}
		});
		add(new MenuButton("Cool Font : " + (Settings.FONT_CUSTOM ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.FONT_CUSTOM ^= true;
				
			}
		});
		add(new MenuButton("Annoying Start Sound : " + (Settings.SOUND_XP_START ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_XP_START ^= true;
			}
		});
		add(new MenuButton("Annoying End Sound : " + (Settings.SOUND_XP_SHUTDOWN ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_XP_SHUTDOWN ^= true;
			}
		});
		add(new MenuButton("Funny End Sound : " + (Settings.SOUND_SAD_TRUMPET ? "ON" : "OFF"), 40) {

			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_SAD_TRUMPET ^= true;
			}
		});

//		static boolean DO_ANIMATIONS = true;
//		static boolean PLAYER_INVINCEBLE = false;
//		static boolean BRICK_COLLISION = true;
//		static boolean GAME_RUNNING = false;
//		static boolean MENU_VIEW = true;
//		static boolean HAX_ON = true;
//		static boolean OPTIONS_MENU = false;
//		static boolean MOUSE_CONTROL = false;
//		static boolean GAME_WON = false;
//		static boolean SOUND_XP_START = false;	//on restart
//		static boolean SOUND_XP_SHUTDOWN = true; //on game lost
//		static boolean SOUND_SAD_TRUMPET = false; //alternative on game lost
//		static boolean FONT_CUSTOM = true;
	}
}
