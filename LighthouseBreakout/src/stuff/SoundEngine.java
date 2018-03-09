package stuff;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEngine {

	public static final int GAME_START = 0;
	public static final int GAME_LOST = 1;

	public SoundEngine() {
		
	}

	public void playSound(int sound) {
		String file;
		
		switch (sound) {
		case GAME_START:
			file = "XP_START.wav";
			break;
		case GAME_LOST:
			if (Settings.SOUND_XP_SHUTDOWN)
				file = "XP_SHUTDOWN.wav";
			else
				file = "trump.wav";
		default:
			return;
		}
		
		Clip c;
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream i = classloader.getResourceAsStream(file);
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(i);
			c = AudioSystem.getClip();
			c.open(audioIn);
			c.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}