package cz.cvut.elf.geom.geomplanet.mrtile;

import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.adt.color.Color;

import android.util.Log;
import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.ElfActivity;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.User;
import cz.cvut.elf.geom.SceneManager.GameType;
import cz.cvut.elf.geom.general.GameEvent;
import cz.cvut.elf.geom.general.GameTimer;
import cz.cvut.elf.geom.general.GlobalAssetBundle;
import cz.cvut.elf.geom.general.controls.TextButton;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.ChessGrid;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.DialogScene;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.MrTileCaveCharacter;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.MrTileTaskCharacter;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.RigidSquareTile;

public class MrTileTaskScene extends BaseScene implements
		IOnMenuItemClickListener {
	public static final String RESET = "Začít znovu";
	public static final String TIMER = "Času je hrozně spousta";
	public static final String INSTRUCTION_MESSAGE = "Nandej kachličky podle vzoru do mříky";
	public static final String RESULTS_MESSAGE = "Moc dobře... Finální čas: ";
	public static final int NUMBER_OF_ROUNDS = 3;
	public static final int NUMBER_OF_LEVELS = 5;

	public static final MrTileTaskScene INSTANCE = new MrTileTaskScene();

	private boolean mShowInstruction = true;
	private DialogScene mResults;

	private MrTileTaskLoader mTaskLoader;

	private Text mTimerText;
	// private Text mRestartText;

	private GameTimer mTimer;
	private Random mRandom;

	private Sprite mShowTaskGrid;
	private GameEvent mHideEvent;

	private MrTileTaskCharacter mMrTile;

	private int mRoundCnt = 0;
	private int mIncorrectTotalCnt = 0;
	private int mTileTotalCnt = 0;
	private int mNumbLvl = 0;

	@Override
	public void onBuild() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(
				ElfActivity.CAMERA_WIDTH / 2, ElfActivity.CAMERA_HEIGHT / 2,
				MrTileAssetBundle.getInstance().mTableBgTR, GameHolder
						.getInstance().mVBOM)));

		this.setBackground(background);

		// getBackground().setColor(0.09804f, 0.6274f, 0.8784f);
		/**
		 * Restart button on the scene
		 */
		/*
		 * mRestartText = new Text(300, 450,
		 * GlobalAssetBundle.getInstance().mDefaultFont, RESET,
		 * GameHolder.getInstance().mVBOM) { public boolean
		 * onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX,
		 * float pTouchAreaLocalY) { if (pSceneTouchEvent.isActionDown()) {
		 * MrTileTaskScene.this.onRestart(); // Log.d("reset", "reset touched");
		 * } return true; } }; mRestartText.setColor(Color.BLUE);
		 * this.attachChild(mRestartText); this.registerTouchArea(mRestartText);
		 */

		/**
		 * Timer counting seconds.
		 */
		mTimerText = new Text(
				GameHolder.getInstance().mCamera.getWidth() - 200, 450,
				GlobalAssetBundle.getInstance().mDefaultFont, TIMER,
				GameHolder.getInstance().mVBOM);
		mTimerText.setColor(Color.WHITE);
		attachChild(mTimerText);
		mTimer = new GameTimer(GameHolder.getInstance().mActivity, mTimerText);

		/**
		 * Event which will hide the task grid in if the difficulty expert is
		 * set.
		 */
		mHideEvent = new GameEvent(5) {
			public void processOnTime() {
				MrTileTaskScene.this.mTaskLoader.getTaskGrid()
						.setVisible(false);
				/**
				 * To be sure I won't parent the btn for second time.
				 */
				MrTileTaskScene.this.unregisterTouchArea(mShowTaskGrid);
				mShowTaskGrid.detachSelf();

				MrTileTaskScene.this.attachChild(mShowTaskGrid);
				mShowTaskGrid.setPosition(mTaskLoader.getTaskGrid().getX(),
						mTaskLoader.getTaskGrid().getY());
				MrTileTaskScene.this.registerTouchArea(mShowTaskGrid);
			}
		};

		/**
		 * Button that will uncover the hidden grid for expert player.
		 */
		mShowTaskGrid = new Sprite(0, 0, 100, 100,
				MrTileAssetBundle.getInstance().mDeskSecretTR,
				GameHolder.getInstance().mVBOM) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					MrTileTaskScene.this.mTaskLoader.getTaskGrid().setVisible(
							true);
					MrTileTaskScene.this.unregisterTouchArea(this);
					detachSelf();
					MrTileTaskScene.this.mHideEvent
							.setOnTime(MrTileTaskScene.this.mTimer
									.getSecondCnt() + 4);
				}
				return true;
			}
		};

		MrTileAssetBundle.getInstance().loadTaskGraphics();

		registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				if (mTaskLoader == null)
					return;

				MrTileTaskScene.this.mTaskLoader.isTaskCompleted();
			}
		});

		// Mr Tile
		// the god has created Mr. Tile
		mMrTile = new MrTileTaskCharacter();
		// and put him to the world
		mMrTile.load();
		attachChild(mMrTile);
		// started his brain
		mMrTile.start();

		this.setTouchAreaBindingOnActionDownEnabled(true);
		mIsBuilt = true;
	}

	/**
	 * Method is called every time we want to load new level.
	 */
	public void onStart() {

		if (mTaskLoader == null) {
			mTaskLoader = new MrTileTaskLoader(this,
					GameHolder.getInstance().mVBOM);
			mRandom = new Random();
		} else {
			mTaskLoader.unloadLevel();
		}
		// int level = mRandom.nextInt(4) + 1;
		mTaskLoader.loadLevel(((mNumbLvl++) % NUMBER_OF_LEVELS) + 1);

		mTimerText.setText("Čas: " + 0);
		mTimer.reset();
		mTimer.unregisterAllGameEvents();

		if (GameHolder.getInstance().mUser.getCurrentSkillType() == User.SkillType.SKILL_EXPERT) {
			mHideEvent.setOnTime(5);
			mTimer.registerGameEvent(mHideEvent);
		}

		/**
		 * If the scene is showed for first time the instruction must be shown.
		 */
		if (mShowInstruction) {
			mMrTile.onEvent("START_TASK");
			mShowInstruction = false;
		}
		mRoundCnt++;
	}

	/**
	 * Cleaning of the scene.
	 */
	public void onStop() {
		/**
		 * Maybe the button for showing the task grid in expert was set on
		 * screen so we are clearing it.
		 */
		mShowTaskGrid.detachSelf();
		unregisterTouchArea(mShowTaskGrid);
		mTimer.reset();
		if (mResults != null) {
			mResults.dispose();
			mResults = null;
		}
		mShowInstruction = true;
		mRoundCnt = 0;
	}

	public void onRestart() {
		/**
		 * Maybe the button for showing the task grid in expert was set on
		 * screen so we are clearing it.
		 */
		mShowTaskGrid.detachSelf();
		unregisterTouchArea(mShowTaskGrid);
		onStart();
	}

	@Override
	public void onResume() {
		mMrTile.onSceneResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		mMrTile.onScenePause();
		super.onPause();
	}

	public void onDestroy() {

		if (mIsBuilt == false) {
			return;

		}
		Log.d(getClass().getName(), "onDestroy()");

		mTaskLoader.unloadLevel();

		mShowTaskGrid.detachSelf();
		unregisterTouchArea(mShowTaskGrid);
		mTimer.cancel();
		mTimerText = null;
		mHideEvent = null;

		MrTileAssetBundle.getInstance().unloadTaskGraphics();
		detachChildren();

		setBackground(new Background(Color.BLACK));

		super.onDestroy();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().setScene(MrTileCaveScene.INSTANCE);
	}

	public void onTaskCompleted(int pNumberOfIncorrect, int pTileCnt) {
		mIncorrectTotalCnt += pNumberOfIncorrect;
		mTileTotalCnt += pTileCnt;
		if (mRoundCnt % NUMBER_OF_ROUNDS == 0) {

			// ulozeni do usera
			GameHolder.getInstance().mUser
					.setCurrentMistakes(mIncorrectTotalCnt);
			// TODO opravit
			GameHolder.getInstance().mUser.setCurrentTime(0);
			GameHolder.getInstance().mUser.setTotal(mTileTotalCnt);
			GameHolder.getInstance().mUser.setCurrentIDTask(3);

			System.out.println("chyb " + mIncorrectTotalCnt + " / "
					+ mTileTotalCnt);

			// prepnuti sceny, ta si nacte vysledky z uzivatele
			SceneManager.getInstance().setResultsScene(GameType.MRTILE);

			/*
			 * mTimerText.setText("�as: " + mTimer.getSecondCnt());
			 * if(pNumberOfIncorrect==0){ mResults = new
			 * DialogScene(RESULTS_MESSAGE + mTimer.getSecondCnt() + " sekund",
			 * GameHolder.getInstance()); } else{ mResults = new
			 * DialogScene("Z " + mTileTotalCnt + " jsi um�stil dob�e " +
			 * (mTileTotalCnt - mIncorrectTotalCnt) + ". To nen� �patn� v�kon!",
			 * GameHolder.getInstance());
			 * 
			 * }
			 * 
			 * mResults.addOption(DialogScene.RESET, "Hr�t znovu");
			 * mResults.addOption(DialogScene.MENU, "Do jeskyn�");
			 * 
			 * mResults.setOnMenuItemClickListener(this);
			 * mResults.buildAnimations(); mTimer.stop();
			 * setChildScene(mResults, false, true, true);
			 */
			mIncorrectTotalCnt = 0;
			mTileTotalCnt = 0;
		} else {
			onRestart();
		}
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		/*
		 * case DialogScene.MESSAGE: this.clearChildScene(); return true;
		 */
		case DialogScene.RESUME:
			pMenuScene.back(true);
			mTimer.go();
			// this.clearChildScene();
			return true;
		case DialogScene.RESET:
			this.onStop();
			this.onRestart();
			this.clearChildScene();
			mTimer.go();
			return true;
		case DialogScene.MENU:
			this.clearChildScene();
			SceneManager.getInstance().setScene(MrTileCaveScene.INSTANCE);
			return true;
		default:
			return false;
		}
	}

	/**
	 * Putting a tile to some position in Grid. This is place for encouragements
	 * and other reactions of the game.
	 * 
	 * @param pTile
	 */
	public void onTilePlaced(RigidSquareTile pTile) {
		if (mTaskLoader.getTaskGrid().isSameTileOnSamePosition(pTile)
				&& pTile.isInGrid()) {
			mMrTile.onEvent("GOD_JOB");
		} else {
			mMrTile.onEvent("BAD_JOB");
		}
	}

}
