package cz.cvut.elf.geom.general.controls;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;

public class BlinkButton extends AnimatedSprite {

	
	private BaseScene		mNextScene;
	
	public BlinkButton( float pX, float pY, TiledTextureRegion pAnimation, BaseScene pCurrentScene, BaseScene pNextScene) {
		super( pX, pY, pAnimation, GameHolder.getInstance().mVBOM);
		
		mNextScene = pNextScene;
		pCurrentScene.registerTouchArea( this );
		this.animate(GameHolder.ANIMATION_FRAME_DURATION);
		
	}
	
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {

		SceneManager.getInstance().setScene( mNextScene );
		
		return true;
	}
}
