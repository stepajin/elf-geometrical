package cz.cvut.elf.geom.geomplanet.mrtile.entity;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.character.Character;
import cz.cvut.elf.geom.character.CharacterBehavior;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileAssetBundle;

public class MrTileCharacter extends Character {

	protected BitmapTextureAtlas mMrTileAtlas;
	protected static TiledTextureRegion mMrTileBodyTR;
	protected static TiledTextureRegion mMrTileEyeTR;
	protected static TiledTextureRegion mMrTileMouthTR;
	protected static TiledTextureRegion mMrTileNoseTR;
	
	protected Sprite mBody;
	protected AnimatedSprite mNose;
	protected AnimatedSprite mMouth;
	protected AnimatedSprite mREye;
	protected AnimatedSprite mLEye;
	
	
	private Sound mSnuff 	= MrTileAssetBundle.getInstance().mSnuff;

	public MrTileCharacter( float pX, float pY) {
		super(pX, pY, 75, 150);
		

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("mrtile/gfx/");
		//creating character
		mMrTileAtlas = new BitmapTextureAtlas( mActivity.getTextureManager(), 512,512 );
		mMrTileEyeTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset( mMrTileAtlas, mActivity, "mrTileEyeAnim.png",0,0, 10, 1);
		mMrTileMouthTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset( mMrTileAtlas, mActivity, "mrTileMouthAnim.png",0,49, 10, 1);
		mMrTileNoseTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset( mMrTileAtlas, mActivity, "mrTileNoseAnim.png",0,64, 6, 1);
		mMrTileBodyTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset( mMrTileAtlas, mActivity, "mrTileBody.png",0,115, 1, 1);
		
		attachChild(mBody = new Sprite(0,0,mMrTileBodyTR, mVbom));
				
		mLEye 	= new AnimatedSprite(145, 168, mMrTileEyeTR, mVbom );
		mREye 	= new AnimatedSprite(100, 168, mMrTileEyeTR, mVbom );		
		mMouth 	= new AnimatedSprite(120, 100, mMrTileMouthTR, mVbom );
		mNose 	= new AnimatedSprite(120, 135, mMrTileNoseTR, mVbom ){
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Log.d("TOUCH","mr Tile nose touched");
				MrTileCharacter.this.onEvent("SNUFF");
				return true;
			}			
			
		};	
		
		mBody.attachChild(mMouth);
		mBody.attachChild(mREye);
		mBody.attachChild(mLEye);
		mBody.attachChild(mNose);
		

		SceneManager.getInstance().getActualScene().registerTouchArea( mNose );
		
	}

	@Override
	public void load() {
		mMrTileAtlas.load();
		super.load();
	}

	@Override
	public void unload() {
		mMrTileAtlas.unload();
		super.unload();
	}

	public void start() {		
		// Deafult behavior -----------------------------------------------------------------------
		// ----------------------------------------------------------------------------------------
		 start( new CharacterBehavior("DEFAULT",-1.0f, false){
				
				@Override
				protected void onPause() {
										
				}
				
				@Override
				protected void onStart() {	
															
				}
				
				@Override
				protected void onResume() {
	
					this.onStart();
				}
				
				@Override
				protected void onFinish() {
	
					this.onPause();
				}
				
			});		
		 
		 

			// Snuff behavior -------------------------------------------------------------------------
			// ----------------------------------------------------------------------------------------		
			
			//count automatically animation duration //TODO		
			addBehavior( new CharacterBehavior("SNUFF", 2.0f , true ){ // name, duration, isImmediate
				
				@Override
				protected void onPause() {
					
					
					mNose.stopAnimation();
					mSnuff.stop();
				}
				
				@Override
				protected void onStart() {	
					
					mNose.animate( GameHolder.ANIMATION_FRAME_DURATION, 1 );
					mSnuff.play();
					
					
					
				}
				
				@Override
				protected void onResume() {
					
					this.onStart();
				}
				
				@Override
				protected void onFinish() {
						
					this.onPause();
				}

			});
		
	}

}