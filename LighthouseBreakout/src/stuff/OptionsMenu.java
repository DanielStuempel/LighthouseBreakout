package stuff;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

public class OptionsMenu extends Menu {
	public OptionsMenu(CardLayout layout, JPanel contentPane) {
		super(layout, contentPane, "optionsMenu");
		setLayout(new GridLayout(4, 0));
		
		add(new MenuButton("STYLE: " + Settings.THEME.toString()) {
			@Override
			public void onClick(ActionEvent e) {
				System.out.println("style");
			}
		});
		
		add(new MenuButton("CONTROLS") {
			@Override
			public void onClick(ActionEvent e) {
				layout.show(contentPane, "controlsMenu");
			}
		});
		
		add(new MenuButton("BACK") {
			@Override
			public void onClick(ActionEvent e) {
				layout.previous(contentPane);
			}
		});

		new ControlMenu(layout, contentPane);
	}
}
