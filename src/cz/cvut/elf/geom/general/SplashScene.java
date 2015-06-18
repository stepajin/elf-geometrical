package cz.cvut.elf.geom.general;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;

public class SplashScene extends BaseScene {
	public static final SplashScene INSTANCE = new SplashScene();
	
	private 		Sprite 						mSplashSprite;	
	private			BitmapTextureAtlas			mSplashAtlas;
	public			TextureRegion				mSplash;

	@Override
	public void onBuild() {
		mSplashAtlas = new BitmapTextureAtlas( GameHolder.getInstance().mActivity.getTextureManager(), 1280, 768, TextureOptions.DEFAULT );
		mSplash = BitmapTextureAtlasTextureRegionFactory.createFromAsset( mSplashAtlas, GameHolder.getInstance().mActivity, "splash.png",0,0);
		
		mSplashAtlas.load();
		
		float x = GameHolder.getInstance().mCamera.getCenterX();
		float y = GameHolder.getInstance().mCamera.getCenterY(); 
		mSplashSprite = new Sprite(x, y, mSplash, GameHolder.getInstance().mVBOM) {
		    @Override
		    protected void preDraw(GLState pGLState, Camera pCamera) {
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		};

		setBackground(new Background(Color.WHITE));
		attachChild(mSplashSprite);
		
		this.mIsBuilt = true;
	}

	@Override
	public void onStop() {
		onDestroy();
	}

	@Override
	public void onDestroy() {
		mSplashAtlas.unload();
		mSplashSprite.detachSelf();
	    mSplashSprite.dispose();
	    
		this.mIsBuilt=false;	
	    this.detachSelf();
	    this.dispose();
	}
}
