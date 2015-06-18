package cz.cvut.elf.geom.general;

import org.andengine.entity.sprite.Sprite;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;

public class ManaBottle {
	public Sprite mBottle;
	private int mCurrentMana;
	private float mScale;

	private int mX, mY;

	ManaBottle(BaseScene pScene) {
		mScale = 0.3f;
		mX = 700;
		mY = 350;

		mBottle = new Sprite(mX, mY, ResultsAssetBundle.getInstance().mBottle,
				GameHolder.getInstance().mVBOM);
		mBottle.setScale(mScale);
		pScene.attachChild(mBottle);

		mCurrentMana = 0;
	}

	public void addMana(int pCount) {
		mCurrentMana += pCount;
	}

	public void useMana(int pCount) {
		mCurrentMana -= pCount;
	}

	public void animate() {
		System.out.println("mbottle scale " + mScale);
		if (mScale < 1) {
			mScale += 0.01f;
			mBottle.setScale(mScale);
			
			mX -= 1;
			mY -= 2;
			mBottle.setPosition(mX, mY);
			System.out.println("mbottle position " + mX + "  " + mY);
		}
	}

	public float getScale() {
		return mScale;
	}
}
