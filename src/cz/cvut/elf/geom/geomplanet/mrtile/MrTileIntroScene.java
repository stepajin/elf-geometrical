package cz.cvut.elf.geom.geomplanet.mrtile;

import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;
import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.ElfActivity;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.general.GlobalAssetBundle;
import cz.cvut.elf.geom.general.controls.ActionButton;
import cz.cvut.elf.geom.general.controls.TextButton;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.MrTileInfrontCaveCharacter;
import cz.cvut.elf.geom.interfaces.ButtonAction;

public class MrTileIntroScene extends BaseScene {
	public static final MrTileIntroScene INSTANCE = new MrTileIntroScene();
	private int idTask=1;
	Text mText;
	TextButton mToCave;


	private ActionButton mButtonToCave = new ActionButton(220,240,GlobalAssetBundle.getInstance().mDefaultHighlightingAnim, 
    		new ButtonAction(){

				@Override
				public boolean execute() {
					SceneManager.getInstance().setScene(MrTileCaveScene.INSTANCE);
					return false;
				}
    });
	private ActionButton mButtonToMenu = new ActionButton(770,50,GlobalAssetBundle.getInstance().mDefaultHighlightingAnim, 
    		new ButtonAction(){

				@Override
				public boolean execute() {
					onBackKeyPressed();
//					MrTileTask.getInstance().finish();
					return false;
				}
    });
	
	private MrTileInfrontCaveCharacter mMrTile;
	@Override
	public void onBuild() {

		//load assets
		MrTileAssetBundle assetBundle = MrTileAssetBundle.getInstance();
		assetBundle.load();
		
		AutoParallaxBackground background = new AutoParallaxBackground(0, 0, 0, 5);
	    background.attachParallaxEntity(new ParallaxEntity(0, 		new Sprite(ElfActivity.CAMERA_WIDTH/2, ElfActivity.CAMERA_HEIGHT/2, ElfActivity.CAMERA_WIDTH, ElfActivity.CAMERA_HEIGHT, assetBundle.mInfrontCaveSky, GameHolder.getInstance().mVBOM )) );	   
	    background.attachParallaxEntity(new ParallaxEntity(0, 		new Sprite(650,400, assetBundle.mInfrontCaveSun, GameHolder.getInstance().mVBOM ) ));
	    background.attachParallaxEntity(new ParallaxEntity(-3.0f, 	new Sprite(ElfActivity.CAMERA_WIDTH/2, 340, assetBundle.mInfrontCaveClouds, GameHolder.getInstance().mVBOM )));
	    background.attachParallaxEntity(new ParallaxEntity(0, 		new Sprite(ElfActivity.CAMERA_WIDTH/2, ElfActivity.CAMERA_HEIGHT/2, assetBundle.mInfrontCave, GameHolder.getInstance().mVBOM )));	    
	    
	    this.setBackground(background);

		this.attachChild(mButtonToCave);
		this.attachChild(mButtonToMenu);
		this.registerTouchArea(mButtonToMenu);
		this.registerTouchArea(mButtonToCave);

		//the god has created Mr. Tile
		mMrTile = new MrTileInfrontCaveCharacter(600,250);
		//and put him to the world
		mMrTile.load();
		attachChild( mMrTile );
		//started his brain
		mMrTile.start();

		//the god has said: The work is done
		mIsBuilt = true;

	}
	
	
	
	
	private int resumeCounter = 0;
	
	@Override
	public void onResume() {
		
		if( resumeCounter == 0 ){

			//mrTile has decided to say introduction
			mMrTile.onEvent("INTRO_SPEAK");
			
		}else{
			
			mMrTile.onSceneResume();
			
			this.unregisterTouchArea(mButtonToCave);
			this.unregisterTouchArea(mButtonToMenu);
			this.detachChild(mButtonToCave);
			this.detachChild(mButtonToMenu);			
			mMrTile.onEvent("BYE_BYE_SPEAK");			
			
		}

		resumeCounter++;
		super.onResume();
	}
	
	@Override
	public void onPause() {
		
		mMrTile.onScenePause();
		
		super.onPause();
	}
	
	
	
	
	
	@Override
	public void onDestroy() {
		
		if( mIsBuilt == false ){
			return;
			
		}
		
		Log.d(getClass().getName(), "onDestroy()");
		
		resumeCounter = 0;
		
		detachChild( mMrTile );
		detachChild(mButtonToCave);
		detachChild(mButtonToMenu);
		
		clearTouchAreas();
		
		mMrTile.unload();
		
		super.onDestroy();
	}
	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().setScene(GeomPlanetScene.INSTANCE);
	}
}
