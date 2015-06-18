package cz.cvut.elf.geom.geomplanet.logicalrow;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.general.controls.BlinkButton;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;

public class LogicalRowByeByeScene extends BaseScene {

	public static final LogicalRowByeByeScene INSTANCE = new LogicalRowByeByeScene();

	private BlinkButton mButton;
	private Text mText;
	Sprite mBgSprite;

	public LogicalRowByeByeScene() {
	}

	@Override
	public void onBuild() {
		float x = GameHolder.getInstance().mCamera.getCenterX();
		float y = GameHolder.getInstance().mCamera.getCenterY();

		mBgSprite = new Sprite(x, y, LogicalRowAssetBundle.getInstance().mFieldRegion,
				GameHolder.getInstance().mVBOM);
		setBackground(new SpriteBackground(mBgSprite));

		mText = new Text(x, y - 100, mGlobalAssetBundle.mDefaultFont,
				"Bye bye", GameHolder.getInstance().mVBOM);

		attachChild(mText);

		mButton = new BlinkButton(
				GameHolder.getInstance().mCamera.getWidth() * 5 / 6,
				GameHolder.getInstance().mCamera.getHeight() * 1 / 4,
				mGlobalAssetBundle.mDefaultHighlightingAnim, this,
				GeomPlanetScene.INSTANCE);

		this.attachChild(mButton);
	}

	@Override
	public void onStop() {
		this.onDestroy();
	}

	@Override
	public void onDestroy() {
		this.detachChildren();
		this.detachSelf();
		clearTouchAreas();
		setBuilt(false);

		LogicalRowTask.getInstance().destroy();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().setScene(LogicalRowOutroScene.INSTANCE);
	}
}
