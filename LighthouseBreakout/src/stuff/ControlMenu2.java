package stuff;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

public class ControlMenu2 extends Menu {

	public ControlMenu2(CardLayout layout, JPanel contentPane) {
		super(layout, contentPane, "controlMenu");

		setLayout(new GridLayout(11, 1));
		add(new CoolLabel("The ingame shortcuts", 40));
		add(new CoolLabel("", 20));
		add(new CoolLabel("show FPS : F", 40));
		add(new CoolLabel("game start / pause : SPACE", 40));
		add(new CoolLabel("show menu : ESC", 40));
		add(new CoolLabel("switch control : M", 40));
		add(new CoolLabel("switch Hax : C", 40));
		add(new CoolLabel("Debug : D", 40));
		add(new CoolLabel("Restart : R", 40));
		add(new CoolLabel("", 40));

		add(new MenuButton("BACK") {

			@Override
			public void onClick(ActionEvent e) {
				layout.show(contentPane, "optionsMenu");
				;

			}
		});
	}

}
