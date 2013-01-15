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
			private long executionTime;
			private long afterTime;

			public void run() {
				for (int i = 4; i < 30; i++) {
					executionTime = System.currentTimeMillis();
					String password = "12345678";
					// Hash a password for the first time
					String gensalt = BCrypt.gensalt(i);
					afterTime = System.currentTimeMillis();
					synchronized (UiApplication.getEventLock()) {
						add(new LabelField("Gensalt:" + (afterTime - executionTime)
								+ " iterations:" + i));
					}
					executionTime = System.currentTimeMillis();
					String hashed = BCrypt.hashpw(password, gensalt);
					afterTime = System.currentTimeMillis();
					synchronized (UiApplication.getEventLock()) {
						add(new LabelField("Hashed:" + (afterTime - executionTime)
								+ " iterations:" + i));
					}
					executionTime = System.currentTimeMillis();
					BCrypt.checkpw(password, hashed);
					afterTime = System.currentTimeMillis();
					synchronized (UiApplication.getEventLock()) {
						add(new LabelField("Time:" + (afterTime - executionTime)
								+ " iterations:" + i));
					}
				}
			}
		}.start();

	}

}
