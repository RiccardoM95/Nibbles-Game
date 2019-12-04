import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class GameSounds {
	private static AudioStream audios;

	public static void playMusic(String filepath) {
		InputStream music;
		try {
			music = new FileInputStream(new File(filepath));
			audios = new AudioStream(music);
			AudioPlayer.player.start(audios);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: no music file found");
		}
	}

	public static void stopMusic() {
		AudioPlayer.player.stop(audios);
	}
	
	public static synchronized void playSound(final String url) {
		  new Thread(new Runnable() {
		    public void run() {
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
		  }).start();
		}	
}