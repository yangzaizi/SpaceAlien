import java.applet.AudioClip;

public class Sound {
	// The clip to be played by the Sound object
	private AudioClip sound;

	/**
	 * Create a new Sound based on the given AudioClip
	 * 
	 * @param sound
	 *            - the AudioClip used to create the object
	 */
	public Sound(AudioClip sound) {
		this.sound = sound;
	}

	// Play the sound -- the sound restarts each time this is called and
	// stops automatically at the end.
	public void playSound() {
		sound.play();
	}

	// Stop the sound
	public void stopSound() {
		sound.stop();
	}

	// Loop the sound
	public void loopSound() {
		sound.loop();
	}
}
