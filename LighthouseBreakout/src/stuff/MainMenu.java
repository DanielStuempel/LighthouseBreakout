package stuff;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import stuff.Window.MainPanel;

public class MainMenu extends Menu {
	public MainMenu(CardLayout layout, MainPanel contentPane) {
		super(layout, contentPane, "main");
		setLayout(new GridLayout(4, 1));

		add(new MenuButton("START") {
			@Override
			public void onClick(ActionEvent e) {
				Settings.MENU_SHOWN = false;
				layout.previous(contentPane);
			}
		});
		
		add(new MenuButton("OPTIONS") {
			@Override
			public void onClick(ActionEvent e) {
				layout.show(contentPane, "optionsMenu");
			}
		});
		
		add(new MenuButton("SCORES") {
			@Override
			public void onClick(ActionEvent e) {
				System.out.println("score");
			}
		});

		new OptionsMenu(layout, contentPane);
	}
}
