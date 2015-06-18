package cz.cvut.elf.geom.geomplanet.logicalrow;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import cz.cvut.elf.geom.GameHolder;

public class Beetle extends AnimatedSprite {

	public static enum BEETLE_TYPE {
		CIRCLE, RECT, TRIANGLE
	};

	public static enum STATE {
		NORMAL, OK, FAIL;
	}

	public static final float X_SPEED = 50f;
	public static final float Y_SPEED = -80f;
	private static final float RUNNING_SCALE = 0.5f;
	private static final float LEGS_RUNNING_SCALE = 0.25f;
	private static final float LEGS_HOLDING_SCALE = 0.25f;

	private boolean mTouched = false;
	private boolean mCrossed = false;
	private final PhysicsHandler mPhysicsHandler;
	private final BEETLE_TYPE mType;
	private STATE mState = STATE.NORMAL;
	private TiledTextureRegion mTiledTextureRegion;

	private AnimatedSprite mLegs;

	/*
	 * Get code of type, used for positions in arrays
	 */
	public static int getCode(BEETLE_TYPE pType) {
		switch (pType) {
		case CIRCLE:
			return 0;
		case RECT:
			return 1;
		default:
			return 2;
		}
	}

	public Beetle(BEETLE_TYPE type, float r, float y, TiledTextureRegion region) {
		super(r, y, region, GameHolder.getInstance().mVBOM);

		mPhysicsHandler = new PhysicsHandler(this);
		registerUpdateHandler(mPhysicsHandler);
		this.mType = type;
		this.mTiledTextureRegion = region;
		setVelocity(0, 0);

		mLegs = new AnimatedSprite(0, 0,
				LogicalRowAssetBundle.getInstance().mLegsRunningRegion,
				GameHolder.getInstance().mVBOM);
	}

	public void attachSelf() {
		mPhysicsHandler.setVelocity(X_SPEED, 0);
		setScale(RUNNING_SCALE);
		LogicalRowTaskScene.INSTANCE.attachChild(this);
		LogicalRowTaskScene.INSTANCE.registerTouchArea(this);

		mLegs.setScale(LEGS_RUNNING_SCALE);
		LogicalRowTaskScene.INSTANCE.attachChild(mLegs);
		mLegs.animate(GameHolder.ANIMATION_FRAME_DURATION);

		this.setZIndex(mLegs.getZIndex() + 1);
		LogicalRowTaskScene.INSTANCE.sortChildren();
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if (mState == STATE.OK)
			return false;

		if (pSceneTouchEvent.isActionDown()) {
			onTouchActionDown();

		} else if (pSceneTouchEvent.isActionMove() && mTouched) {
			this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());

		} else if (pSceneTouchEvent.isActionUp() && mTouched) {
			onTouchActionUp();
		}

		return true;
	}

	private void onTouchActionDown() {
		/******************************
		 * add "ghost" beetle to replace this beetle - to watch crossing max x
		 * coord
		 *****************************/
		Beetle ghost = new Beetle(this.getType(), this.getX(), this.getY(),
				this.mTiledTextureRegion);
		ghost.setVisible(false);
		ghost.setVelocity(X_SPEED, 0);
		LogicalRowTaskScene.INSTANCE.attachChild(ghost);

		this.setVelocity(0, 0);
		this.mTouched = true;

	}

	private void onTouchActionUp() {
		mTouched = false;
		float scale = Level.getRowObjectScale(LogicalRowTaskScene.INSTANCE
				.getLevel().getNumberOfPositions());
		this.setScale(scale);
		this.flipVertical();
		this.setVelocity(0, Y_SPEED);
		LogicalRowTaskScene.INSTANCE.addFallingObject(this);
	}

	public void flipVertical() {
		this.setFlippedVertical(true);
		LogicalRowTaskScene.INSTANCE.detachChild(mLegs);
		mLegs = new AnimatedSprite(0, 0,
				LogicalRowAssetBundle.getInstance().mLegsHoldingRegion,
				GameHolder.getInstance().mVBOM);
		this.setLegsScale();
		mLegs.setFlippedVertical(true);
		this.setLegsPosition();
		LogicalRowTaskScene.INSTANCE.attachChild(mLegs);
	}

	public void catchFibre() {
		mLegs.animate(GameHolder.ANIMATION_FRAME_DURATION, 0);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);

		if (this.isTouched()) {
			return;
		}

		if (mX - this.getWidth() / 2 > GameHolder.getInstance().mCamera
				.getXMax()) {
			onCrossingX();
		} else if (mY - this.getHeight() / 2 < GameHolder.getInstance().mCamera
				.getYMin()) {
			onCrossingY();
		}
	}

	private void onCrossingX() {
		if (!mCrossed) {
			detach();
			LogicalRowTaskScene.INSTANCE.attachNewMenuBeetle();
			mCrossed = true;
		}
	}

	private void onCrossingY() {
		if (!mCrossed) {
			LogicalRowTaskScene.INSTANCE.removeFallingObject(this);
			detach();
			mCrossed = true;
		}
	}

	public void detach() {
		GameHolder.getInstance().mEngine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				detachSelf();
				mLegs.detachSelf();
			}
		});
	}

	public void setState(STATE state) {
		this.mState = state;
	}

	public BEETLE_TYPE getType() {
		return mType;
	}

	public void setVelocity(float x, float y) {
		mPhysicsHandler.setVelocity(x, y);
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		this.setLegsPosition();
	}

	@Override
	public void setScale(float scale) {
		super.setScale(scale);
		setLegsScale();
	}
	
	private void setLegsScale() {
		float scale = this.getScaleX()/2;
		mLegs.setScale(scale);
		setLegsPosition();
	}
	
	private void setLegsPosition() {
		if (isFlippedVertical()) {
			mLegs.setPosition(this.getX(), this.getY() + this.getHeight()*0.68f*getScaleX());
		} else {
			mLegs.setPosition(this.getX(), this.getY() - this.getHeight()*0.6f*getScaleX());
		}
	}

	public boolean isTouched() {
		return mTouched;
	}
}
