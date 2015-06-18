package cz.cvut.elf.geom.geomplanet.logicalrow;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import cz.cvut.elf.geom.GameHolder;

public class QuestionMark extends Sprite {

	Beetle.BEETLE_TYPE mExpectedBeetleType;
	boolean mSolved = false;

	public QuestionMark(Beetle.BEETLE_TYPE expectedBeadType, float x, float y,
			ITextureRegion region) {
		super(x, y, region, GameHolder.getInstance().mVBOM);
		this.mExpectedBeetleType = expectedBeadType;
		setWidth(region.getWidth());
		setHeight(region.getHeight());
	}

	public void collideWithBeetle(final Beetle beetle) {
		if (this.isSolved()) {
			return;
		}

		if (beetle.getType() == mExpectedBeetleType) {
			beetle.setVelocity(0, 0);
			beetle.setPosition(this.getX(), this.getY());
			beetle.catchFibre();
			LogicalRowTaskScene.INSTANCE.unregisterTouchArea(beetle);
			mSolved = true;
			setVisible(false);
		} else {
			beetle.detach();
		}
	}
	
	public boolean isSolved() {
		return mSolved;
	}

	public Beetle.BEETLE_TYPE getExpectedBeetleType() {
		return mExpectedBeetleType;
	}

	@Override
	public boolean detachSelf() {
		return super.detachSelf();
	}
}
