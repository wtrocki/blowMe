package blowme.lib;

import net.rim.device.api.crypto.SHA1Digest;

/**
 * An implementation of SecureRandom specifically for the light-weight API, JDK
 * 1.0, and the J2ME. Random generation is based on the traditional SHA1 with
 * counter. Calling setSeed will always increase the entropy of the hash.
 * <p>
 * <b>Do not use this class without calling setSeed at least once</b>! There are
 * some example seed generators in the org.bouncycastle.prng package.
 */
public class SecureRandom extends java.util.Random {
	// Note: all objects of this class should be deriving their random data from
	// a single generator appropriate to the digest being used.
	private static final RandomGenerator sha1Generator = new DigestRandomGenerator(new SHA1Digest());

	protected RandomGenerator generator;

	// public constructors
	public SecureRandom() {
		this(sha1Generator);
		setSeed(System.currentTimeMillis());
	}

	public SecureRandom(byte[] inSeed) {
		this(sha1Generator);
		setSeed(inSeed);
	}

	protected SecureRandom(RandomGenerator generator) {
		super(0);
		this.generator = generator;
	}

	/**
	 * @param algorithm
	 * @return instance of secure random
	 */
	public static SecureRandom getInstance(String algorithm) {
		return new SecureRandom();
	}

	public static byte[] getSeed(int numBytes) {
		byte[] rv = new byte[numBytes];
		sha1Generator.addSeedMaterial(System.currentTimeMillis());
		sha1Generator.nextBytes(rv);
		return rv;
	}

	// public instance methods
	public byte[] generateSeed(int numBytes) {
		byte[] rv = new byte[numBytes];

		nextBytes(rv);

		return rv;
	}

	// public final Provider getProvider();
	public void setSeed(byte[] inSeed) {
		generator.addSeedMaterial(inSeed);
	}

	// public methods overriding random
	public void nextBytes(byte[] bytes) {
		generator.nextBytes(bytes);
	}

	public synchronized void setSeed(long rSeed) {
		if (rSeed != 0) // to avoid problems with Random calling setSeed in
						// construction
		{
			generator.addSeedMaterial(rSeed);
		}
	}

	public int nextInt() {
		byte[] intBytes = new byte[4];

		nextBytes(intBytes);

		int result = 0;

		for (int i = 0; i < 4; i++) {
			result = (result << 8) + (intBytes[i] & 0xff);
		}

		return result;
	}

	protected final synchronized int next(int numBits) {
		int size = (numBits + 7) / 8;
		byte[] bytes = new byte[size];

		nextBytes(bytes);

		int result = 0;

		for (int i = 0; i < size; i++) {
			result = (result << 8) + (bytes[i] & 0xff);
		}

		return result & ((1 << numBits) - 1);
	}
}
