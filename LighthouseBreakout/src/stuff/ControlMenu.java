package stuff;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ControlMenu extends Menu {
	public ControlMenu(CardLayout layout, JPanel contentPane) {
		super(layout, contentPane, "controlsMenu");
		setLayout(new GridLayout(10, 1));
		
		add(new MenuButton("Name : " + Settings.PLAYER_NAME, 40) {
			@Override
			public void onClick(ActionEvent e) {
				String input = JOptionPane.showInputDialog("your name", Settings.PLAYER_NAME);
				if (input == null || input.isEmpty())
					return;
				Settings.PLAYER_NAME = input;
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
		
		add(new SwitchButton("Annoying Start Sound", 40, Settings.SOUND_XP_START) {
			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_XP_START ^= true;
				changeValue(Settings.SOUND_XP_START);
			}
		});
		
		add(new MenuButton("End Sound : " + (Settings.SOUND_XP_SHUTDOWN ? "ON" : "OFF"), 40) {
			@Override
			public void onClick(ActionEvent e) {
				Settings.SOUND_SAD_TRUMPET ^= true;
				Settings.SOUND_XP_SHUTDOWN ^= true;
				setText("End Sound : " + (Settings.SOUND_XP_SHUTDOWN ? "ON" : "OFF"));
			}
		});
		
		add(new CoolLabel("", 20));
	}
}
