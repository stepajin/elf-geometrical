package cz.cvut.elf.geom;

import org.andengine.entity.scene.Scene;

import cz.cvut.elf.geom.general.GlobalAssetBundle;

/**
 * Main structure representing game scene. It has same set of method as an
 * android activity.
 * 
 * @author Tom
 * 
 */
public abstract class BaseScene extends Scene {
	protected boolean mIsBuilt = false;

	protected GlobalAssetBundle mGlobalAssetBundle = GlobalAssetBundle
			.getInstance();

	/*************************************
	 * Methods
	 */

	/**
	 * First setting of the scene. For example loading resources.
	 */
	public abstract void onBuild();

	/**
	 * Preparing scene to be launched.
	 */
	public void onStart() {
	}

	/**
	 * The scene is viewed in the end of this method. This is where sounds
	 * should start playing.
	 */
	public void onResume() {
	}

	/**
	 * The scene was interrupted.
	 */
	public void onPause() {
	}

	/**
	 * The scene is detaching entities. And doing other reverse operations to
	 * onStart();
	 */
	public void onStop() {
	}

	/**
	 * A possibility to reconfigure something before onStart() is called again.
	 */
	public void onRestart() {
	}

	/**
	 * Disposing the scene. Unloading resources from the memory.
	 */
	public void onDestroy() {

		mIsBuilt = false;
	}

	public void onHomeKeyPressed() {
	}

	public void onBackKeyPressed() {
	}

	public boolean isBuilt() {
		return this.mIsBuilt;
	}

	public void setBuilt(boolean pIsBuilt) {
		this.mIsBuilt = pIsBuilt;
	}
}
