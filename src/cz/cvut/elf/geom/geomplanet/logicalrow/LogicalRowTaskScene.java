package cz.cvut.elf.geom.geomplanet.logicalrow;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.SceneManager.GameType;

public class LogicalRowTaskScene extends BaseScene {

	public static final LogicalRowTaskScene INSTANCE = new LogicalRowTaskScene();

	private static final int FINAL_LEVEL_NUMBER = 5;
	private static final float FIBRE_SCALE = 0.75f;

	private Camera mCamera = GameHolder.getInstance().mCamera;

	private List<Beetle> mFallingObjects = new ArrayList<Beetle>();
	private Level mLevel;
	private boolean mIsLevelRunning = false;
	private int mNextRowObjectNumber = 0;
	private int mLevelNumber = 0;

	private int mMistakes;
	private float mTotalGameTime;
	private int mTotal;

	/***
	 * Singleton pattern
	 */
	private LogicalRowTaskScene() {
	}

	/*********************
	 * 
	 * Inherited scene life cycle methods
	 * 
	 *********************/

	@Override
	public void onBuild() {
		LogicalRowAssetBundle bundle = LogicalRowAssetBundle.getInstance();
		if (!bundle.isLoaded()) {
			bundle.load();
		}

		setBackground(new SpriteBackground(new Sprite(mCamera.getCenterX(),
				mCamera.getCenterY(),
				LogicalRowAssetBundle.getInstance().mFieldRegion,
				GameHolder.getInstance().mVBOM)));

		setTouchAreaBindingOnActionDownEnabled(true);

		mMistakes = 0;
		mTotalGameTime = 0;
		mTotal = 0;
	}

	@Override
	public void onStart() {
		registerUpdateHandler(new LogicalRowTaskUpdateHandler());
		mLevelNumber = 0;
		onLevelStart();
	}

	/******************
	 * 
	 * Event handling
	 * 
	 ******************/

	private void onHandlerUpdate(final float pSecondsElapsed) {
		if (mLevel == null)
			return;

		/* Collision detection */
		for (QuestionMark q : mLevel.getQuestionMarks()) {
			for (Beetle b : mFallingObjects) {
				if (b.collidesWith(q)) {
					onCollision(b, q);
					break;
				}
			}
		}

		/* End of game detection */
		if (!mIsLevelRunning) {
			return;
		}

		boolean solved = true;
		for (QuestionMark q : mLevel.getQuestionMarks()) {
			if (!q.isSolved()) {
				solved = false;
				break;
			}
		}

		if (solved) {
			onLevelVictory();
		}
	}

	private void onCollision(Beetle pBeetle, QuestionMark pQuestionMark) {
		if (pQuestionMark.isSolved()) {
			return;
		}

		mTotal++;
		this.removeFallingObject(pBeetle);

		if (pBeetle.getType() != pQuestionMark.getExpectedBeetleType()) {
			this.onBadJob();
		} else {
			this.onGoodJob();
		}

		pQuestionMark.collideWithBeetle(pBeetle);
	}

	public void onGoodJob() {
	}

	public void onBadJob() {
		Sounds.getFailSound().play();
		mMistakes++;
	}
	
	/*****************
	 * 
	 * Level life cycle
	 *
	 ****************/
	
	private void onLevelStart() {
		onLevelDestroy();

		mIsLevelRunning = true;

		LogicalRowAssetBundle bundle = LogicalRowAssetBundle.getInstance();
		VertexBufferObjectManager vbom = GameHolder.getInstance().mVBOM;

		/* attach fibre */
		Sprite fibre = new Sprite(mCamera.getWidth() * 3 / 5,
				mCamera.getHeight() / 2, bundle.mFibreRegion, vbom);
		fibre.setScale(FIBRE_SCALE);
		attachChild(fibre);

		/* attach row beetles */
		mLevel = new Level();
		mLevel.loadLevel(++mLevelNumber);

		/* attach menu beetles */
		mNextRowObjectNumber = 0;
		float y = mCamera.getHeight() * 1 / 6;
		this.generateNextMenuBeetle(mCamera.getXMax() * 6 / 7, y).attachSelf();
		this.generateNextMenuBeetle(mCamera.getXMax() * 4 / 7, y).attachSelf();
		this.generateNextMenuBeetle(mCamera.getXMax() * 2 / 7, y).attachSelf();
		this.generateNextMenuBeetle(mCamera.getXMax() * 0 / 7, y).attachSelf();
		this.generateNextMenuBeetle(mCamera.getXMax() * -2 / 7, y).attachSelf();

		Sounds.getStartSound().play();
	}

	private void onLevelVictory() {
		mIsLevelRunning = false;
		clearTouchAreas();
		mFallingObjects.clear();

		if (mLevelNumber == FINAL_LEVEL_NUMBER) {
			onGameVictory();
			return;
		}

		Sounds.getSuccessSound().play();

		/* attach button */
		ITextureRegion region = LogicalRowAssetBundle.getInstance().mContinueRegion;
		final Sprite continueSprite = new Sprite(mCamera.getCenterX(),
				mCamera.getHeight() * 3 / 4, region.getWidth(),
				region.getHeight(), region, GameHolder.getInstance().mVBOM) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown())
					onLevelStart();
				return true;
			}
		};
		attachChild(continueSprite);
		registerTouchArea(continueSprite);
	}

	private void onGameVictory() {
		clearTouchAreas();
		Sounds.getVictorySound().play();

		/* attach button */
		ITextureRegion region = LogicalRowAssetBundle.getInstance().mContinueRegion;
		final Sprite continueSprite = new Sprite(mCamera.getCenterX(),
				mCamera.getHeight() * 3 / 4, region.getWidth(),
				region.getHeight(), region, GameHolder.getInstance().mVBOM) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown())
					SceneManager.getInstance().setResultsScene(
							GameType.LOGICAL_ROW);
				return true;
			}
		};
		attachChild(continueSprite);
		registerTouchArea(continueSprite);

		/* Handle statistics */
		GameHolder.getInstance().mUser.setCurrentMistakes(mMistakes);
		GameHolder.getInstance().mUser.setCurrentTime(mTotalGameTime);
		GameHolder.getInstance().mUser.setTotal(mTotal);
		GameHolder.getInstance().mUser.setCurrentIDTask(1);
	}

	private void onLevelDestroy() {
		detachChildren();
		clearTouchAreas();
	}

	/*************
	 * 
	 * Game logic
	 * 
	 *************/

	public void addFallingObject(Beetle pBeetle) {
		mFallingObjects.add(pBeetle);
		unregisterTouchArea(pBeetle);
	}

	public void removeFallingObject(Beetle pBeetle) {
		mFallingObjects.remove(pBeetle);
	}

	public Beetle generateNextMenuBeetle(float x, float y) {
		if (mLevel == null)
			return null;

		Beetle.BEETLE_TYPE type = mLevel.getUsedBeetleTypes().get(
				mNextRowObjectNumber);
		mNextRowObjectNumber = (mNextRowObjectNumber + 1)
				% mLevel.getUsedBeetleTypes().size();
		return new Beetle(
				type,
				x,
				y,
				LogicalRowAssetBundle.getInstance().mBeetleTextureRegions[Beetle
						.getCode(type)]);
	}

	public Beetle generateNextMenuBeetle() {
		return generateNextMenuBeetle(mCamera.getXMax() * -2 / 7,
				mCamera.getHeight() * 1 / 6);
	}

	public void attachNewMenuBeetle(float x, float y) {
		this.generateNextMenuBeetle(x, y).attachSelf();
	}

	public void attachNewMenuBeetle() {
		this.generateNextMenuBeetle().attachSelf();
	}

	/*******************
	 * 
	 * Getters, setters
	 * 
	 *******************/

	public Level getLevel() {
		return this.mLevel;
	}

	public boolean isLevelRunning() {
		return mIsLevelRunning;
	}

	/*********************
	 * 
	 * Inherited scene life cycle methods
	 * 
	 *********************/

	@Override
	public void onStop() {
		setBuilt(false);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance()
				.setScene(LogicalRowInstructionScene.INSTANCE);
	}

	/*****************
	 * 
	 * Inner classes
	 * 
	 *****************/

	class LogicalRowTaskUpdateHandler implements IUpdateHandler {
		@Override
		public void reset() {
		}

		@Override
		public void onUpdate(final float pSecondsElapsed) {
			LogicalRowTaskScene.this.onHandlerUpdate(pSecondsElapsed);
		}
	}
}
