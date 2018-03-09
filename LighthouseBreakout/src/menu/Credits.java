package menu;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import stuff.Window.MainPanel;

public class Credits extends Menu {
	BufferedImage image;

	public Credits(CardLayout layout, MainPanel contentPane) {
		super(layout, contentPane, "credits");

		try {
			image = ImageIO.read(new File(getClass().getResource("/icon.png").toURI()));
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		setLayout(new GridLayout(2, 1));
		String coolText = null;

		add(new MenuLabel(coolText, 40));
		add(new MenuButton("BACK") {

			@Override
			public void onClick(ActionEvent e) {
				layout.show(contentPane, "main");

			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, this.getWidth() / 2 - image.getWidth() / 2, 2, this);
	}

}
