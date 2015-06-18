package cz.cvut.elf.geom.geomplanet.sorting;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.general.controls.TextButton;

public class SortingDiningTableScene extends BaseScene {
	public static final SortingDiningTableScene INSTANCE = new SortingDiningTableScene();
	Text mText;
	TextButton mSkip;

	@Override
	public void onBuild() {

		mText = new Text(GameHolder.getInstance().mCamera.getCenterX(),
				GameHolder.getInstance().mCamera.getCenterY(),
				mGlobalAssetBundle.mDefaultFont, "Scena privitani u stolu s ukolem\n "
						+ "radit jidlo\n "
						+ "(zadavani instrukci)",
				GameHolder.getInstance().mVBOM);

		this.attachChild(mText);

		mSkip = new TextButton(200, 100, "PRESKOCIT A HRAT", SortingTaskScene.INSTANCE);
		attachChild(mSkip);
		registerTouchArea(mSkip);

		mIsBuilt = true;
	}
	
	@Override
	public void onDestroy(){
		this.detachChildren();
		this.clearTouchAreas();
	}
}
