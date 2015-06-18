package cz.cvut.elf.geom;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import cz.cvut.elf.geom.db.DbDAO;
import cz.cvut.elf.geom.general.GlobalAssetBundle;
import cz.cvut.elf.geom.general.LoadingScene;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;

/**
 * An access point to the application. There's just one activity and different
 * scenes are displayed during the game usage.
 * 
 * @author Tom
 * 
 */
public class ElfActivity extends BaseGameActivity {

	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;

	private final Camera mCamera = new BoundCamera(0, 0, CAMERA_WIDTH,
			CAMERA_HEIGHT);

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);

		Intent intent = getIntent();
		int userId = intent.getIntExtra("USER_ID", -1);

		if (userId == -1) {
			Log.e("GEOMETRICAL PLANET", "wrong intent");
			System.exit(1);
		}

		ContentResolver cr = getContentResolver();
		try {
			Cursor c = cr.query(
					Uri.parse("content://cz.cvut.elf.mainapp.provider/users/"
							+ userId), null, null, null, null);
			c.moveToFirst();
			String userName = c.getString(c.getColumnIndex("name"));
			Toast.makeText(this, "Zvolený uživatel " + userName,
					Toast.LENGTH_SHORT).show();
			GameHolder.getInstance().mUser.setId(userId);
			GameHolder.getInstance().mUser.setName(userName);

		} catch (Exception e) {
			Log.e("GEOMETRICAL PLANET", "WRONG USE OF GEOMETRICAL PLANET");
		}
		
		DbDAO.getInstance().printResults(this);
	}

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {

		GameHolder.prepareGameHolder(mEngine, this, mCamera,
				getVertexBufferObjectManager());
		GlobalAssetBundle.getInstance().load();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	/**
	 * Display splash scene
	 */
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		try {
			LoadingScene loading = new LoadingScene();
			loading.onResume();
			SceneManager.getInstance().setFirstScene(loading);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pOnCreateSceneCallback.onCreateSceneFinished(SceneManager.getInstance()
				.getActualScene());
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {

		mEngine.registerUpdateHandler(new TimerHandler(3f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						SceneManager.getInstance().getActualScene().onStop();
						SceneManager.getInstance().setScene(
								GeomPlanetScene.INSTANCE);
					}
				}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SceneManager.getInstance().getActualScene().onBackKeyPressed();
		}/*
		 * else if (keyCode == KeyEvent.KEYCODE_HOME) {
		 * SceneManager.getInstance().getActualScene().onHomeKeyPressed(); }
		 */
		return false;
	}

	/**
	 * TODO nefunguje
	 * */
	@Override
	public void onPause() {
		// SceneManager.getInstance().onPause();
		super.onPause();
	}
}
