package menu;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import stuff.Window.MainPanel;

public class ControlMenu extends Menu {
	public ControlMenu(CardLayout layout, MainPanel contentPane) {
		super(layout, contentPane, "controlMenu");

		setLayout(new GridLayout(11, 1));
		add(new MenuLabel("INGAME KEYS", 40));
		add(new MenuLabel("", 20));
		add(new MenuLabel("show FPS : F", 40));
		add(new MenuLabel("game start / pause : SPACE", 40));
		add(new MenuLabel("show menu : ESC", 40));
		add(new MenuLabel("switch control : M", 40));
		add(new MenuLabel("switch Hax : C", 40));
		add(new MenuLabel("Debug : D", 40));
		add(new MenuLabel("Restart : R", 40));
		add(new MenuLabel("", 40));

		add(new MenuButton("BACK") {
			@Override
			public void onClick(ActionEvent e) {
				layout.show(contentPane, "optionsMenu");
			}
		});
	}
}