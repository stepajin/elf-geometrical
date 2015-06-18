package cz.cvut.elf.geom.geomplanet.sorting;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.general.controls.BlinkButton;
import cz.cvut.elf.geom.general.controls.TextButton;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;

public class SortingIntroScene extends BaseScene {
	public static final SortingIntroScene INSTANCE = new SortingIntroScene();
	private int idTask=2;
	Text mText;
	TextButton mToBanquet;

	@Override
	public void onBuild() {
		SortingAssetBundle.getInstance();
		
		mText = new Text(GameHolder.getInstance().mCamera.getCenterX(),
				GameHolder.getInstance().mCamera.getCenterY(),
				mGlobalAssetBundle.mDefaultFont,
				"Uvodni scena hry Razeni\n "
				+ "(obraz na dum, ve kterem probiha hostina)",
				GameHolder.getInstance().mVBOM);

		this.attachChild(mText);

		mToBanquet = new TextButton(200, 100, "POKRACOVAT", SortingBanquetScene.INSTANCE);
		attachChild(mToBanquet);
		registerTouchArea(mToBanquet);

		mIsBuilt = true;
	}
	
	@Override
	public void onDestroy(){
		this.detachChildren();
		this.clearTouchAreas();
	}
}
