package cz.cvut.elf.geom.geomplanet.sorting;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.general.controls.BlinkButton;
import cz.cvut.elf.geom.general.controls.TextButton;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;

public class SortingOutroScene extends BaseScene {
	public static final SortingOutroScene INSTANCE = new SortingOutroScene();
	Text mText;

	private BlinkButton mButton = new BlinkButton(500, 100,
			mGlobalAssetBundle.mDefaultHighlightingAnim, this,
			GeomPlanetScene.INSTANCE);

	@Override
	public void onBuild() {
		mText = new Text(GameHolder.getInstance().mCamera.getCenterX(),
				GameHolder.getInstance().mCamera.getCenterY(),
				mGlobalAssetBundle.mDefaultFont,
				"Ukoncovaci scena\n "
				+ "Louceni s uzivatelem pred domem hostiny",
				GameHolder.getInstance().mVBOM);

		this.attachChild(mText);
		this.attachChild(mButton);
		
		mButton.animate(GameHolder.getInstance().ANIMATION_FRAME_DURATION);

		mIsBuilt = true;
	}
}
