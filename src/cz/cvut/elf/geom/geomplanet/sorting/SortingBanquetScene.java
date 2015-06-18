package cz.cvut.elf.geom.geomplanet.sorting;

import org.andengine.entity.sprite.Sprite;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.ElfActivity;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.general.controls.BlinkButton;
import cz.cvut.elf.geom.general.controls.TextButton;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.MrTileInfrontCaveCharacter;
import cz.cvut.elf.geom.geomplanet.sorting.entity.MrTimBanquetCharacter;

public class SortingBanquetScene extends BaseScene {
	public static final SortingBanquetScene INSTANCE = new SortingBanquetScene();
	
	TextButton mOut;
	TextButton mLetsPlay;
	
	private MrTimBanquetCharacter mMrTim;
	
	public BlinkButton mButtonToGame;
	public BlinkButton mButtonToMenu;

	
	@Override
	public void onBuild() {
		SortingAssetBundle bundle = SortingAssetBundle.getInstance();
		bundle.load();
		
		this.attachChild(new Sprite(ElfActivity.CAMERA_WIDTH/2, ElfActivity.CAMERA_HEIGHT/2,bundle.mBanquetBackground,GameHolder.getInstance().mVBOM));		
		
		
		//the god has created Mr. Tim
		mMrTim = new MrTimBanquetCharacter(100,200);
		//and put him to the world
		mMrTim.load();
		attachChild( mMrTim );
		//started his brain
		mMrTim.start();
		
		mIsBuilt = true;
	}
	
	
	private int resumeCounter = 0;	
	@Override
	public void onResume() {
		
		if( resumeCounter == 0 ){

			//mrTile has decided to say introduction
			mMrTim.onEvent("INTRO_SPEAK");
			
		}else{
			
			mMrTim.onSceneResume();
			
			
		}

		resumeCounter++;
		
		super.onResume();
	}
	
	@Override
	public void onPause() {
		
		mMrTim.onScenePause();
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		
		SortingAssetBundle.getInstance().unload();
		this.detachChildren();
		this.clearTouchAreas();
		super.onDestroy();
	}
	
	@Override
	public void onStop() {
		// komplet smazani sceny
		//this.detachChildren();
	}
	
	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().setScene(GeomPlanetScene.INSTANCE);
	}
}
