package cz.cvut.elf.geom.geomplanet.sorting;

import java.io.IOException;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.SceneManager.GameType;
import cz.cvut.elf.geom.geomplanet.sorting.entity.MrTimTaskCharacter;

public class SortingTaskScene extends BaseScene {
	public static final SortingTaskScene INSTANCE = new SortingTaskScene();
	private SortingAssetBundle bundle;

	private TimerHandler mTimer;
	private float mTimeElapsed;
	private float mTotalGameTime;
	private int mRounds;
	private int mMistakes;
	public static final int rounds = 5;

	public Boolean mComplete;

	public Level mLevel;

	private MrTimTaskCharacter mMrTim;

	@Override
	public void onBuild() {
		bundle = SortingAssetBundle.getInstance();
		bundle.load();

		this.setTouchAreaBindingOnActionDownEnabled(true);

		// the god has created Mr. Tile
		mMrTim = new MrTimTaskCharacter();
		// and put him to the world
		mMrTim.load();
		attachChild(mMrTim);
		// started his brain
		mMrTim.start();

		this.mIsBuilt = true;
	}

	@Override
	public void onStart() {

		mComplete = false;
		mTimeElapsed = 0;
		mTotalGameTime = 0;
		mRounds = 0;
		mMistakes = 0;

		mLevel = new Level(bundle, this, mMrTim);
		try {
			mLevel.loadLevel();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mTimer = new TimerHandler(0.01f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mTimeElapsed += 0.01;
				// System.out.println("time " + mTimeElapsed);
				endGameCheck();
				// System.out.println("checked");
			}

		});
		GameHolder.getInstance().mEngine.registerUpdateHandler(mTimer);

	}

	@Override
	public void onResume() {

		// mr Tim has decided to say instructions
		mMrTim.onEvent("TASK_INSTRUCTIONS_SPEAK");

		super.onResume();
	}
	
	@Override
	public void onPause() {
		mMrTim.onScenePause();
		super.onPause();
	}

	private void endGameCheck() {
		// funkce, ktera se vola kazdy update sceny, kontrolujeme, jestli neni
		// konec hry
		if (!mComplete) {
			if (mLevel.getComplete()) {
				System.out.println("complete");
				mComplete = true;

				mTotalGameTime += mTimeElapsed;

				if (mRounds < rounds) {
					// nove kolo
					mTimeElapsed = 0;
					mComplete = false;
					mTimer.reset();
					mMistakes += mLevel.checkResults();
					
					
					mMrTim.onEvent("LAST_FOOD");
					

					System.out.println("restartuju level, kolo " + mRounds);
				} else {
					// vyhlaseni vysledku
					System.out.println("kompletni");
					System.out.println("mistakes " + mMistakes);
					System.out.println("total time " + mTotalGameTime);

					// ulozeni mezivysledku do uzivatele
					GameHolder.getInstance().mUser
							.setCurrentMistakes(mMistakes);
					GameHolder.getInstance().mUser
							.setCurrentTime(mTotalGameTime);
					GameHolder.getInstance().mUser.setTotal(15);
					GameHolder.getInstance().mUser.setCurrentIDTask(2);

					// prepnuti sceny, ta si nacte vysledky z uzivatele, zobrazi
					// je
					mLevel.unloadLevel();
					System.out.println("vytvarim scenu");

					SceneManager.getInstance()
							.setResultsScene(GameType.SORTING);
				}

				mRounds++;

			}
		}

	}

	@Override
	public void onStop() {
		this.detachChildren();
		GameHolder.getInstance().mEngine.unregisterUpdateHandler(mTimer);
	}
	
	@Override
	public void onBackKeyPressed() {
		mLevel.unloadLevel();
		SceneManager.getInstance().setScene(SortingBanquetScene.INSTANCE);
	}
}
