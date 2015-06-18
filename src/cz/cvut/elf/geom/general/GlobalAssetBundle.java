package cz.cvut.elf.geom.general;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Color;
import android.graphics.Typeface;
import cz.cvut.elf.geom.AssetBundle;
import cz.cvut.elf.geom.GameHolder;


/*
 * Asset bundle which stores assets used in global scale.
 * 
 * @author Petr Zavadil
 * 
 * */

public class GlobalAssetBundle extends AssetBundle{

	private static GlobalAssetBundle INSTANCE = new GlobalAssetBundle();
	
	// Images & animations ------------------------------------------
	// --------------------------------------------------------------
	
	private 		BitmapTextureAtlas 			mElfRightAtlas;
	public 			TextureRegion 				mElfRight, mHandOk, mLeftHand, mRightHand, mShoeLeft,mShoeRight;
			
	private			BitmapTextureAtlas			mShipLoadingAnimAtlas;
	public			TiledTextureRegion			mShipLoadingAnim;
	
	private			BitmapTextureAtlas			mSpaceAtlas;
	public			TextureRegion				mSpace;
	
	private			BitmapTextureAtlas			mDefaultHighlightingAnimAtlas;
	public			TiledTextureRegion			mDefaultHighlightingAnim;
	
	
	// Sounds -------------------------------------------------------
	// --------------------------------------------------------------
	
	public			Sound						mElfStart;
	
	//fonts ---------------------------------------------------------
	// --------------------------------------------------------------
	
	private 		BitmapTextureAtlas 			mDefaultFontAtlas;
	public			Font						mDefaultFont;
	
	
	public static GlobalAssetBundle getInstance() {
		return INSTANCE;
	}
	
	/*
	 * Assets are created in constructor.
	 * 
	 * */
	public GlobalAssetBundle() {

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("");				
		
		// Images & animations ------------------------------------------
		// --------------------------------------------------------------		
		
		mElfRightAtlas 	= new BitmapTextureAtlas( mActivity.getTextureManager(), 1280, 768, TextureOptions.DEFAULT );
		mElfRight 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset( mElfRightAtlas, mActivity, "elfRight.png",0,0);		
		mHandOk 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset( mElfRightAtlas, mActivity, "skritek-doprava-ruka-l-palec.png",415,0);
		mLeftHand 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset( mElfRightAtlas, mActivity, "skritek-doprava-ruka-l-dlan.png",415,101);
		mRightHand 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset( mElfRightAtlas, mActivity, "skritek-doprava-ruka-r-dlan.png",415,196);
		mShoeLeft 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset( mElfRightAtlas, mActivity, "skritek-doprava-bota-l.png",415,274);
		mShoeRight 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset( mElfRightAtlas, mActivity, "skritek-doprava-bota-r.png",415,349);
		
		mSpaceAtlas = new BitmapTextureAtlas( mActivity.getTextureManager(), 1280, 768, TextureOptions.DEFAULT );
		mSpace = BitmapTextureAtlasTextureRegionFactory.createFromAsset( mSpaceAtlas, mActivity, "space.png",0,0);	

		mShipLoadingAnimAtlas = new BitmapTextureAtlas( mActivity.getTextureManager(), 2000, 960, TextureOptions.DEFAULT );
		mShipLoadingAnim = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset( mShipLoadingAnimAtlas, mActivity.getAssets(), "global/animations/shipLoadingAnim/shipLoadingAnimSmall.png",0,0,5,4);	
		
		mDefaultHighlightingAnimAtlas = new BitmapTextureAtlas( mActivity.getTextureManager(), 550, 550, TextureOptions.DEFAULT);
		mDefaultHighlightingAnim = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mDefaultHighlightingAnimAtlas, GameHolder.getInstance().mActivity.getAssets(),"global/controls/touchAnim.png", 0, 0, 5, 5);
		
		
		// Sounds -------------------------------------------------------
		// --------------------------------------------------------------
		
		SoundFactory.setAssetBasePath("");
		
		try {
			
			mElfStart 		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "global/elf/sounds/elf_starting_1.mp3");
		
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//fonts ---------------------------------------------------------
		// --------------------------------------------------------------
				
		
		mDefaultFontAtlas = new BitmapTextureAtlas( mActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA );
		mDefaultFont = new Font(mActivity.getFontManager(), mDefaultFontAtlas, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.WHITE);
	}
	
	
	@Override
	public void load() {
		
		// Images & animations ------------------------------------------
		// --------------------------------------------------------------				
		
		mElfRightAtlas.load();
		mSpaceAtlas.load();
		mShipLoadingAnimAtlas.load();
		mDefaultHighlightingAnimAtlas.load();
		
		// Sounds -------------------------------------------------------
		// --------------------------------------------------------------
		
		
		//fonts ---------------------------------------------------------
		// --------------------------------------------------------------
		
		mDefaultFontAtlas.load();
		mDefaultFont.load();
		
		super.load();
	}

	@Override
	public void unload() {

		
		// Images & animations ------------------------------------------
		// --------------------------------------------------------------				
				
		mElfRightAtlas.unload();
		mSpaceAtlas.unload();
		mShipLoadingAnimAtlas.unload();
		mDefaultHighlightingAnimAtlas.unload();
				
		// Sounds -------------------------------------------------------
		// --------------------------------------------------------------
		
		
		//fonts ---------------------------------------------------------
		// --------------------------------------------------------------
				

		mDefaultFontAtlas.load();
		mDefaultFont.load();
		
		super.unload();
		
	}

}
