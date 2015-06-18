package cz.cvut.elf.geom;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * A singleton class designed to maintain all important components for the game.
 * For example reference to engine, user or camera.
 * 
 * @author Tom
 * 
 */
public class GameHolder {

	/*
	 * Variables
	 */
	private static final GameHolder INSTANCE = new GameHolder();

	public static final int ANIMATION_FRAME_DURATION = 100;

	public Engine mEngine;
	public ElfActivity mActivity;
	public Camera mCamera;
	public VertexBufferObjectManager mVBOM;
	public User mUser = new User();

	private GameHolder() {
	}

	/*
	 * Methods
	 */

	/*
	 * Functions
	 */

	/**
	 * @param engine
	 * @param activity
	 * @param camera
	 * @param vbom
	 */
	public static void prepareGameHolder(Engine pEngine, ElfActivity pActivity,
			Camera pCamera, VertexBufferObjectManager pVBOM) {
		getInstance().mEngine = pEngine;
		getInstance().mActivity = pActivity;
		getInstance().mCamera = pCamera;
		getInstance().mVBOM = pVBOM;
	}

	/*
	 * Getters and setters
	 */
	public static GameHolder getInstance() {
		return INSTANCE;
	}
}
