package menu;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import stuff.Window.MainPanel;

public class Credits extends Menu{

	public Credits(CardLayout layout, MainPanel contentPane) {
		super(layout, contentPane, "credits");
		
		setLayout(new GridLayout(2, 1));
		String coolText= null;
		
		add(new MenuLabel(coolText, 40));
		add(new MenuButton("BACK") {
			
			@Override
			public void onClick(ActionEvent e) {
				layout.show(contentPane, "main");
				
			}
		});
		
	}

}
