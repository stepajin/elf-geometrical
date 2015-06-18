package cz.cvut.elf.geom.geomplanet.mrtile;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import cz.cvut.elf.geom.AssetBundle;

public class MrTileAssetBundle extends AssetBundle {
	
	private static MrTileAssetBundle INSTANCE = new MrTileAssetBundle();

	// Images & animations ------------------------------------------
	// --------------------------------------------------------------				
	private 		BitmapTextureAtlas 			mInfrontCaveBgAtlas;
	public 			TextureRegion 				mInfrontCave, mInfrontCaveSky, mInfrontCaveClouds, mInfrontCaveSun;	
	
	public 			BitmapTextureAtlas 			mrTileCharacterAtlas;
	public 			TiledTextureRegion 			mrTileCharacter;

	public 			BitmapTextureAtlas 			mInsideCaveAtlas;
	public 			TextureRegion 				mInsideCaveTR, mInsideCaveCandleTR, mInsideCavePicasoTR;
	
	public 			BitmapTextureAtlas 			mTableAtlas;
	public 			TiledTextureRegion 			mTableAnim;
	
	public 			BitmapTextureAtlas 			mMrTileAtlas;
	public 			TiledTextureRegion 			mMrTileBodyTR, mMrTileEyeTR, mMrTileMouthTR, mMrTileNoseTR;
	
	public 			BitmapTextureAtlas 			mTableBgAtlas;
	public 			TextureRegion 				mTableBgTR;
	
	public 			BitmapTextureAtlas 			mWhiteDeskAtlas;
	public 			TextureRegion 				mWhiteDeskTR;
	
	public 			BitmapTextureAtlas 			mDeskSecretAtlas;
	public 			TextureRegion 				mDeskSecretTR;
	
	public 			BitmapTextureAtlas 			mBlackDeskAtlas;
	public 			TextureRegion 				mBlackDeskTR;

	public 			BitmapTextureAtlas 			mBlueTileAtlas;
	public 			TextureRegion  				mBlueTileTR;
	
	public 			BitmapTextureAtlas 			mBlueShadeTileAtlas;
	public 			TextureRegion  				mBlueShadeTileTR;
	
	public 			BitmapTextureAtlas 			mBlueWhitePatternTileAtlas;
	public 			TextureRegion  				mBlueWhitePatternTileTR;
	
	public 			BitmapTextureAtlas 			mGreenOrangePatternTileAtlas;
	public 			TextureRegion  				mGreenOrangePatternTileTR;
	
	public 			BitmapTextureAtlas 			mGreenTileAtlas;
	public 			TextureRegion  				mGreenTileTR;
	
	public 			BitmapTextureAtlas 			mPinkShadeTileAtlas;
	public 			TextureRegion  				mPinkShadeTileTR;
	
	public 			BitmapTextureAtlas 			mVioletGreenPatternAtlas;
	public 			TextureRegion  				mVioletGreenPatternTR;
	
	public 			BitmapTextureAtlas 			mPinkTileAtlas;
	public 			TextureRegion  				mPinkTileTR;
	
	public 			BitmapTextureAtlas 			mVioletOrangePatternTileAtlas;
	public 			TextureRegion  				mVioletOrangePatternTileTR;
	
	public 			BitmapTextureAtlas 			mVioletWhitePatternTileAtlas;
	public 			TextureRegion  				mVioletWhitePatternTileTR;
	// Sounds -------------------------------------------------------
	// --------------------------------------------------------------
	
	public 			Sound 						mSnuff, mComeOn, mTaskInstruction, mInCave, mWelcome, mInstructions, mByeBye;
	public			Sound []					mGoodJob, mBadJob;	//task sounds
	
	//fonts ---------------------------------------------------------
	// --------------------------------------------------------------
	
	public static MrTileAssetBundle getInstance() {
		return INSTANCE;
	}
	
	private MrTileAssetBundle() {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("mrtile/gfx/");		
		
	// Images & animations ------------------------------------------
	// --------------------------------------------------------------				
	
		mInfrontCaveBgAtlas = new BitmapTextureAtlas( mActivity.getTextureManager(), 1024, 1024);
		mInfrontCave 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mInfrontCaveBgAtlas, mActivity, "caveEntry.png",2,2);
		mInfrontCaveSky 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mInfrontCaveBgAtlas, mActivity, "sky.png",0,0);		
		mInfrontCaveSun 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mInfrontCaveBgAtlas, mActivity, "sun.png",0,482);
		mInfrontCaveClouds 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mInfrontCaveBgAtlas, mActivity, "clouds.png",0,483);
		
		mInsideCaveAtlas	= new BitmapTextureAtlas( mActivity.getTextureManager(), 1024,1024 );
		mInsideCaveTR		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mInsideCaveAtlas, mActivity, "cave.png",0,0);
		mInsideCaveCandleTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mInsideCaveAtlas, mActivity, "candle.png",0,481);
		mInsideCavePicasoTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mInsideCaveAtlas, mActivity, "picaso.png",101,481);
		
		mTableAtlas 		= new BitmapTextureAtlas(mActivity.getTextureManager(), 1080, 512 );
		mTableAnim			= BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset( mTableAtlas, mActivity, "tableHighlightingAnim.png",0,0, 3, 5 );


		mTableBgAtlas		= new BitmapTextureAtlas(mActivity.getTextureManager(), 1024,1024 );
		mTableBgTR		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTableBgAtlas, mActivity, "table_bg.png",0,0);
		
		mWhiteDeskAtlas		= new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );
		mWhiteDeskTR		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mWhiteDeskAtlas, mActivity, "white_desk.png",0,0);

		mDeskSecretAtlas		= new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );
		mDeskSecretTR		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mDeskSecretAtlas, mActivity, "desk_secret.png",0,0);
		
		mBlackDeskAtlas		= new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );
		mBlackDeskTR		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBlackDeskAtlas, mActivity, "black_desk.png",0,0);	
		
		mBlueTileAtlas		= new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );
		mBlueTileTR		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBlueTileAtlas, mActivity, "blue_tile.png",0,0);
		
		mBlueShadeTileAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );;
		mBlueShadeTileTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBlueShadeTileAtlas, mActivity, "blue_shade_tile.png",0,0);;
		
		mBlueWhitePatternTileAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );;
		mBlueWhitePatternTileTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBlueWhitePatternTileAtlas, mActivity, "blue_white_pattern_tile.png",0,0);;
		
		mGreenOrangePatternTileAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );;
		mGreenOrangePatternTileTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGreenOrangePatternTileAtlas, mActivity, "green_orange_pattern_tile.png",0,0);;
		
		mGreenTileAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );;
		mGreenTileTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGreenTileAtlas, mActivity, "green_tile.png",0,0);;
		
		mPinkShadeTileAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );;
		mPinkShadeTileTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mPinkShadeTileAtlas, mActivity, "pink_shade_tile.png",0,0);;
		
		mVioletGreenPatternAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );;
		mVioletGreenPatternTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mVioletGreenPatternAtlas, mActivity, "violet_green_pattern_tile.png",0,0);;
		
		mPinkTileAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );;
		mPinkTileTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mPinkTileAtlas, mActivity, "pink_tile.png",0,0);;
		
		mVioletOrangePatternTileAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );;
		mVioletOrangePatternTileTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mVioletOrangePatternTileAtlas, mActivity, "violet_orange_pattern_tile.png",0,0);;
		
		mVioletWhitePatternTileAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 512, 512 );;
		mVioletWhitePatternTileTR	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mVioletWhitePatternTileAtlas, mActivity, "violet_white_pattern_tile.png",0,0);;
	
		// Sounds -------------------------------------------------------
		// --------------------------------------------------------------
	
		SoundFactory.setAssetBasePath("mrtile/sounds/");		
		
		try {
			
			mSnuff 			= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_snuff.mp3");
			
			mWelcome 		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_intro.mp3");
			mInCave			= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_in_cave.mp3");
			mInstructions 	= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_instruction.mp3");
			mByeBye 		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_goodbye.mp3");
			mTaskInstruction = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_instruction.mp3");

			
			SoundFactory.setAssetBasePath("mrtile/sounds/taskSounds/");
			
			mGoodJob		= new Sound [4];
			mGoodJob[0]		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_good_job_1.mp3");
			mGoodJob[1]		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_good_job_2.mp3");
			mGoodJob[2]		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_good_job_3.mp3");
			mGoodJob[3]		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_good_job_4.mp3");
			
			mBadJob 		= new Sound [4];
			mBadJob[0]		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_bad_job_1.mp3");
			mBadJob[1]		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_bad_job_2.mp3");
			mBadJob[2]		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_bad_job_3.mp3");
			mBadJob[3]		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "mr_tile_bad_job_4.mp3");
			
		} catch (IOException e) {
			// TODO exception handler
			e.printStackTrace();
		}
		
	//fonts ---------------------------------------------------------
	// --------------------------------------------------------------
		
	}
	
	@Override
	public void load() {

	// Images & animations ------------------------------------------
	// --------------------------------------------------------------				
	
		mTableAtlas.load();
		mInsideCaveAtlas.load();
		mInfrontCaveBgAtlas.load();
		
	// Sounds -------------------------------------------------------
	// --------------------------------------------------------------
	
	
		
	
	//fonts ---------------------------------------------------------
	// --------------------------------------------------------------
		super.load();
	}
	
	//TODO hodit kachle do jednoho atlasu a nacitat je v metode load
	public void loadTaskGraphics(){
		mTableBgAtlas.load();	
		mBlueTileAtlas.load();		
		mBlackDeskAtlas.load();
		mDeskSecretAtlas.load();
		mWhiteDeskAtlas.load();	
		mBlueShadeTileAtlas.load();	
		mBlueWhitePatternTileAtlas.load();
		mGreenOrangePatternTileAtlas.load();
		mGreenTileAtlas.load();
		mPinkShadeTileAtlas.load();
		mVioletGreenPatternAtlas.load();
		mPinkTileAtlas.load();
		mVioletOrangePatternTileAtlas.load();
		mVioletWhitePatternTileAtlas.load();
	}
	
	public void unloadTaskGraphics(){
		mBlueTileAtlas.unload();		
		mBlackDeskAtlas.unload();
		mDeskSecretAtlas.unload();
		mWhiteDeskAtlas.unload();
		mBlueShadeTileAtlas.unload();	
		mBlueWhitePatternTileAtlas.unload();
		mGreenOrangePatternTileAtlas.unload();
		mGreenTileAtlas.unload();
		mPinkShadeTileAtlas.unload();
		mVioletGreenPatternAtlas.unload();
		mPinkTileAtlas.unload();
		mVioletOrangePatternTileAtlas.unload();
		mVioletWhitePatternTileAtlas.unload();
	}
	
	@Override
	public void unload() {
		
	// Images & animations ------------------------------------------
	// --------------------------------------------------------------				
	
		mTableAtlas.unload();
		mInsideCaveAtlas.unload();
		mInfrontCaveBgAtlas.unload();
		
	// Sounds -------------------------------------------------------
	// --------------------------------------------------------------
	
	
	//fonts ---------------------------------------------------------
	// --------------------------------------------------------------		
	
		super.unload();
	}


}
