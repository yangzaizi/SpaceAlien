import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.HashMap;

/**
 * A resource manager for sound effects in the game. This works with the Sound
 * object to organize and load all sounds for the game.
 * 
 * @author Kai Lieth
 */

public class SoundStore {
	// Single instance of this class
	private static SoundStore store = new SoundStore();

	// Cached HashMap -- Key: String, Value: Sound
	private HashMap<String, Sound> sounds = new HashMap<String, Sound>();

	/**
	 * Get the single instance of this class
	 * 
	 * @return The single instance of this class
	 */
	public static SoundStore get() {
		return store;
	}

	/**
	 * Retrieve a sound from the store
	 * 
	 * @param ref
	 *            The reference to the clip to use for the sprite (including
	 *            .wav, .au, .aiff, etc.)
	 * @return A Sound instance containing the AudioClip
	 */
	public Sound getSound(String ref) {
		if (sounds.get(ref) != null) {
			return (Sound) sounds.get(ref);
		}

		URL url = this.getClass().getClassLoader().getResource(ref);
		if (url == null) {
			System.out.println("failed to load sound");
		}

		AudioClip newClip = Applet.newAudioClip(url);
		Sound newSound = new Sound(newClip);
		sounds.put(ref, newSound);
		return newSound;
	}
}
