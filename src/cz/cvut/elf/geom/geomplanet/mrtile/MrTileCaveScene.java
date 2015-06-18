package cz.cvut.elf.geom.geomplanet.mrtile;

import org.andengine.entity.scene.background.ParallaxBackground;
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
import cz.cvut.elf.geom.general.controls.BlinkButton;
import cz.cvut.elf.geom.general.controls.TextButton;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.MrTileCaveCharacter;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.MrTileCharacter;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.MrTileInfrontCaveCharacter;
import cz.cvut.elf.geom.interfaces.ButtonAction;

public class MrTileCaveScene extends BaseScene {
	public static final MrTileCaveScene INSTANCE = new MrTileCaveScene();
	
	private 	MrTileCaveCharacter mMrTile;

	private  	ActionButton btnToTaskIntro;
	private 	ActionButton btnToExitCave;
	
	 public MrTileCaveScene() {
		 
		 	MrTileAssetBundle ab = MrTileAssetBundle.getInstance();
		 	
			
		    ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		    background.attachParallaxEntity(new ParallaxEntity(0,new Sprite(ElfActivity.CAMERA_WIDTH/2, ElfActivity.CAMERA_HEIGHT/2, ab.mInsideCaveTR, GameHolder.getInstance().mVBOM )));	    
		    
		    this.setBackground(background);

		    Sprite table = new Sprite(260,40,ab.mTableAnim, GameHolder.getInstance().mVBOM );	
		    Sprite candle = new Sprite(470,240, ab.mInsideCaveCandleTR, GameHolder.getInstance().mVBOM);
		    this.attachChild( table );
		    this.attachChild( candle );
		    
		    		   
		    btnToExitCave	= new ActionButton(740,170,GlobalAssetBundle.getInstance().mDefaultHighlightingAnim, 
		    		new ButtonAction(){

						@Override
						public boolean execute() {
							SceneManager.getInstance().setScene(MrTileIntroScene.INSTANCE);
							return false;
						}
		    });
		    this.attachChild( btnToExitCave );		

		    btnToTaskIntro 	= new ActionButton(160,50,GlobalAssetBundle.getInstance().mDefaultHighlightingAnim, 
		    		new ButtonAction(){

						@Override
						public boolean execute() {
							mMrTile.onEvent("GO_TO_TASK");
							return false;
						}
		    });
		    this.attachChild( btnToTaskIntro );
		    
	}
	
	@Override
	public void onBuild() {

		registerTouchArea(btnToTaskIntro);
		registerTouchArea(btnToExitCave);
		
		//the god has created Mr. Tile
		mMrTile = new MrTileCaveCharacter(300,300);
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
		
		//TODO lepsi rozeznani z jake se prislo sceny
		if( resumeCounter == 0 ){

			//mrTile has decided to say introduction
			mMrTile.onEvent("IN_CAVE");
			
			
		}else{
			
			mMrTile.onEvent("TASK_END");
			mMrTile.onSceneResume();
			
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
		mMrTile.unload();
		
		resumeCounter = 0;
		
		detachChild(mMrTile);

		clearTouchAreas();
		
		super.onDestroy();
	}
	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().setScene(MrTileIntroScene.INSTANCE);
	}
}
