package cz.cvut.elf.geom.general;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.ContentValues;
import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.ElfActivity;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.User;
import cz.cvut.elf.geom.db.DbDAO;
import cz.cvut.elf.geom.db.DbOpenHelper;

/**
 * 
 * 
 * @author Ivana Houdkova
 * 
 */

public class GameResultsScene extends BaseScene {
	public static final GameResultsScene INSTANCE = new GameResultsScene();
	// zobrazovani vysledku jednotlivych her

	private int mState;
	private int mWaiter;
	private int mAttached;
	private int mRewardsCount;
	private int mLevelId;

	private TimerHandler mTimer;
	private ResultsAssetBundle bundle;

	private TextureRegion mBackgroundTexture, mRewardTexture;

	private List<AnimatedSprite> mSparkle; // odmena za hru - rekneme 1-6
											// jablicek
	private List<Sprite> mReward;

	private final AnimatedSprite mContinue = new AnimatedSprite(700, 70,
			mGlobalAssetBundle.mDefaultHighlightingAnim,
			GameHolder.getInstance().mVBOM) {
		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
				final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			if (pSceneTouchEvent.isActionDown())
				SceneManager.getInstance().getBackFromResultsScene();
			return true;
		}
	};

	@Override
	public void onBuild() {

		bundle = ResultsAssetBundle.getInstance();
		bundle.load();

		mReward = new ArrayList<Sprite>();
		mSparkle = new ArrayList<AnimatedSprite>();

		mLevelId = GameHolder.getInstance().mUser.getCurrentIDTask();

		// TODO: udelejte si to lepe panove :), ja nic lepsiho nestiham
		switch (mLevelId) {
		case 1: // logical row
			mBackgroundTexture = bundle.mBackgroundLogicalRow;
			mRewardTexture = bundle.mRewardLogicalRow;
			mRewardsCount = bundle.getLogicalRowReward();
			break;
		case 2: // sorting
			mBackgroundTexture = bundle.mBackgroundSorting;
			mRewardTexture = bundle.mRewardSorting;
			mRewardsCount = bundle.getSortingReward();
			break;
		case 3: // mrtile
			mBackgroundTexture = bundle.mBackgroundMrTile;
			mRewardTexture = bundle.mRewardMrTile;
			mRewardsCount = bundle.getSortingReward();
			break;

		default:
			mBackgroundTexture = bundle.mBackgroundSorting;
			mRewardTexture = bundle.mRewardSorting;
			mRewardsCount = 0;
			break;
		}

		// attach background
		this.attachChild(new Sprite(ElfActivity.CAMERA_WIDTH / 2,
				ElfActivity.CAMERA_HEIGHT / 2, mBackgroundTexture, GameHolder
						.getInstance().mVBOM));

		System.out.println("scena s vysledky");

		mIsBuilt = false;

		mTimer = new TimerHandler(0.01f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				animation();
			}

		});
		GameHolder.getInstance().mEngine.registerUpdateHandler(mTimer);
		System.out.println("update timer vytvoren");

		int x, y;

		for (int i = 0; i < mRewardsCount; i++) {
			System.out.println("jablko cislo " + i);

			// jablko
			x = bundle.mPositions[i][0];
			y = bundle.mPositions[i][1];
			mReward.add(new Sprite(x, y, mRewardTexture, GameHolder
					.getInstance().mVBOM));

			// sparkle
			mSparkle.add(new AnimatedSprite(x, y,
					bundle.mDefaultHighlightingAnim,
					GameHolder.getInstance().mVBOM));
		}

		System.out.println("vytvoreno " + mReward.size() + " odmen");

	}

	@Override
	public void onStart() {
		System.out.println("scena s vysledky - onStart");
		mState = 0;
		mWaiter = 0;
		mAttached = 0;

		DbDAO dao = DbDAO.getInstance();
		List<DbDAO.TaskCarrier> tasks = dao
				.getTasks(GameHolder.getInstance().mActivity);
		String taskName = "";
		int timeRating = 6;
		int accuracyRating = mRewardsCount;
		int taskId = -1;
		int difficulty = 0;
		User.SkillType skillType = GameHolder.getInstance().mUser.getCurrentSkillType();
		if (skillType == User.SkillType.SKILL_BEGINNER) {
			difficulty = 1;
		} else if (skillType == User.SkillType.SKILL_INTERMEDIATE) {
			difficulty = 2;
		} else {
			difficulty = 3;
		}
		
		switch (mLevelId) {
		case 1: // logical row
			taskName = DbOpenHelper.LOGICAL_TASK;
			break;
		case 2: // sorting
			taskName = DbOpenHelper.SORTING_TASK;
			break;
		case 3: // mrtile
			taskName = DbOpenHelper.MR_TILE_TASK;
			break;
		}

		for (DbDAO.TaskCarrier tc : tasks) {
			if (tc.getName().equals(taskName)) {
				taskId = tc.getId();
				break;
			}
		}

		System.out.println(GameHolder.getInstance().mUser.getName() + " "
				+ GameHolder.getInstance().mUser.getId() + "\n" + taskName
				+ " " + taskId + " " + timeRating + ", " + accuracyRating + "*");
	
	
		ContentValues cv = new ContentValues();
		cv.put(DbOpenHelper.RESULTS_TASK_ID, taskId);
		cv.put(DbOpenHelper.RESULTS_USER_ID, GameHolder.getInstance().mUser.getId());
		cv.put(DbOpenHelper.RESULTS_DIFFICULTY, difficulty);
		cv.put(DbOpenHelper.RESULTS_ACCURACY, accuracyRating);
		cv.put(DbOpenHelper.RESULTS_TIME, timeRating);

		dao.writeResult(cv);
	}

	@Override
	public void onStop() {

		for (int i = 0; i < mRewardsCount; i++) {
			this.detachChild(mReward.get(i));
		}
		this.detachChild(mContinue);
		this.unregisterTouchArea(mContinue);

		GameHolder.getInstance().mEngine.unregisterUpdateHandler(mTimer);
	}

	@Override
	public void onDestroy() {
		// komplet smazani sceny
		this.detachChildren();
		bundle.unload();
	}

	private void animation() {
		switch (mState) {
		case 0: // wait 2 sec
			mWaiter++;
			if (mWaiter > 100) {
				mState = 1;
				mWaiter = 0;
			}
			break;
		case 1: // zobrazeni jablek
			mWaiter++;
			if (mWaiter > 100) {
				if (mAttached == mRewardsCount) { // uz je vsechno zobrazeno
					if (mAttached != 0) {
						mSparkle.get(mAttached - 1).stopAnimation();
						this.detachChild(mSparkle.get(mAttached - 1));
					}
					mWaiter = 0;
					mState = 2;
					break;
				}
				this.attachChild(mReward.get(mAttached));
				this.attachChild(mSparkle.get(mAttached));
				mSparkle.get(mAttached).animate(
						GameHolder.ANIMATION_FRAME_DURATION);

				if (mAttached != 0) { // mazani animace prechoziho jablka
					mSparkle.get(mAttached - 1).stopAnimation();
					this.detachChild(mSparkle.get(mAttached - 1));
				}

				mAttached++;
				mWaiter = 0;
			}
			break;
		case 2:
			mWaiter++;
			if (mWaiter > 200) {
				mState = 3;
				mWaiter = 0;
			}
			break;
		case 3:
			this.attachChild(mContinue);
			this.registerTouchArea(mContinue);
			mContinue.animate(GameHolder.ANIMATION_FRAME_DURATION);
			mState = 4;
			break;
		case 4:
			break;

		}
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().getBackFromResultsScene();
	}
}
