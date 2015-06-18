package cz.cvut.elf.geom.general;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;

public class LoadingScene extends BaseScene {

	public static final LoadingScene INSTANCE = new LoadingScene();

	AnimatedSprite mSprite;

	public LoadingScene() {

		float x = GameHolder.getInstance().mCamera.getCenterX();
		float y = GameHolder.getInstance().mCamera.getCenterY();

		mSprite = new AnimatedSprite(x, y,
				GlobalAssetBundle.getInstance().mShipLoadingAnim,
				GameHolder.getInstance().mVBOM);
		Sprite bgSprite = new Sprite(x, y,
				GlobalAssetBundle.getInstance().mSpace,
				GameHolder.getInstance().mVBOM);
		setBackground(new SpriteBackground(bgSprite));

		attachChild(mSprite);
		this.mIsBuilt = true;
	}

	@Override
	public void onBuild() {
	}

	@Override
	public void onResume() {

		mSprite.animate(GameHolder.ANIMATION_FRAME_DURATION);

		super.onResume();
	}

	@Override
	public void onStop() {

		mSprite.stopAnimation();

		super.onStop();

	}

}
