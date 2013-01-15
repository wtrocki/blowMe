package blowme.test;

import blowme.lib.BCrypt;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class BcriptScreen extends MainScreen {
	/**
	 * Creates a new BcriptScreen object
	 */
	public BcriptScreen() {
		// Set the displayed title of the screen
		setTitle("BCript");
		new Thread() {
			public void run() {
				for (int i = 4; i < 30; i++) {
					long executionTime = System.currentTimeMillis();
					execute(i);
					long afterTime = System.currentTimeMillis();
					synchronized (UiApplication.getEventLock()) {
						add(new LabelField("Time:" + (afterTime - executionTime) + " iterations:" + i));
					}
				}
			}
		}.start();

	}

	/**
	 * @param i 
	 * @return
	 */
	private boolean execute(int i) {
		String password = "12345678";
		// Hash a password for the first time
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt(i));
		// Check that an unencrypted password matches one that has
		// previously been hashed
		System.out.println(hashed);
		if (BCrypt.checkpw(password, hashed))
			return true;
		return false;
	}
}
