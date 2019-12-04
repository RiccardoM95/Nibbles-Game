import java.io.File;

import javax.sound.sampled.*;

public class GameSounds {

	private static Clip music;

	public static void playMusic(final String url) {

		try {
			music = AudioSystem.getClip();
			File f = new File("./" + url);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(f.toURI().toURL());
			music.open(inputStream);
			music.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void stopMusic() {
		music.stop();
	}

	public static synchronized void playSound(final String url) {
		try {
			Clip clip = AudioSystem.getClip();
			File f = new File("./" + url);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(f.toURI().toURL());
			clip.open(inputStream);
			clip.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}