package menu;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import stuff.Settings;
import stuff.Style;
import stuff.Window.MainPanel;

public class OptionsMenu extends Menu {
	public OptionsMenu(CardLayout layout, MainPanel contentPane) {
		super(layout, contentPane, "optionsMenu");
		setLayout(new GridLayout(6, 0));
		
		add(new MenuLabel("", 40));
		add(new MenuButton("STYLE: " + Settings.THEME.toString()) {
			@Override
			public void onClick(ActionEvent e) {
				Style.next();
				Style.loadTheme(Settings.THEME);
				setText("STYLE: " + Settings.THEME.toString());
				contentPane.reload();
			}
		});
		
		add(new MenuButton("SETTINGS") {
			@Override
			public void onClick(ActionEvent e) {
				layout.show(contentPane, "settingsMenu");
			}
		});
		
		add(new MenuButton("CONTROLS") {
			@Override
			public void onClick(ActionEvent e) {
				layout.show(contentPane, "controlMenu");
			}
		});
		
		add(new MenuButton("BACK") {
			@Override
			public void onClick(ActionEvent e) {
				layout.previous(contentPane);
			}
		});
		add(new MenuLabel("", 40));

		new SettingsMenu(layout, contentPane);
	}
}
