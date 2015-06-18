package cz.cvut.elf.geom.general.controls;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.interfaces.ButtonAction;

public class ActionButton extends AnimatedSprite {
	
	ButtonAction mButtonAction;

	public ActionButton(float pX, float pY,	TiledTextureRegion pAnim, ButtonAction pAction) {
		super(pX, pY, pAnim, GameHolder.getInstance().mVBOM);
		
		if( pAction == null ){
			throw new IllegalArgumentException("ButtonAction is null!");
		}
		
		mButtonAction = pAction;
		this.animate(GameHolder.ANIMATION_FRAME_DURATION);	

	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {

		return mButtonAction.execute();
	}

}
