package cz.cvut.elf.geom.geomplanet.mrtile.entity;

import org.andengine.audio.sound.Sound;

import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.character.CharacterBehavior;
import cz.cvut.elf.geom.general.GlobalAssetBundle;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileAssetBundle;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileTaskScene;

public class MrTileCaveCharacter extends MrTileCharacter {

	private Sound mInCave 		= MrTileAssetBundle.getInstance().mInCave;
	private Sound mInstructions 	= MrTileAssetBundle.getInstance().mInstructions;
	private Sound mElfStart	= GlobalAssetBundle.getInstance().mElfStart;

	public MrTileCaveCharacter( float pX, float pY ) {
		super(pX, pY);				
		
		create();
	}
	
	
	private void create(){
		
		//TODO automatic count of duration
		addBehavior( new CharacterBehavior("IN_CAVE", 4.0f , true ){ // name, duration, isImmediate
			
			@Override
			protected void onPause() {
				
				mMouth.stopAnimation();
				mInCave.pause();				
				
			}
			
			@Override
			protected void onStart() {

				mInCave.play();
				mMouth.animate( GameHolder.ANIMATION_FRAME_DURATION, true );				

				
			}
			
			@Override
			protected void onResume() {				
				
				mInCave.resume();
				mMouth.animate( GameHolder.ANIMATION_FRAME_DURATION, true );
			}
			
			@Override
			protected void onFinish() {
					
				mMouth.stopAnimation();
				mInCave.stop();							
				
			}

		});
		
		
		//TODO automatic count of duration
				addBehavior( new CharacterBehavior("INSTRUCTIONS_SPEAK", 7.0f , true ){ // name, duration, isImmediate
					
					@Override
					protected void onPause() {
						
						mMouth.stopAnimation();
						mInstructions.pause();				
						
					}
					
					@Override
					protected void onStart() {

						mInstructions.play();
						mMouth.animate( GameHolder.ANIMATION_FRAME_DURATION, true );				

						
					}
					
					@Override
					protected void onResume() {				
						
						mInstructions.resume();
						mMouth.animate( GameHolder.ANIMATION_FRAME_DURATION, true );
					}
					
					@Override
					protected void onFinish() {
							
						mMouth.stopAnimation();
						mInstructions.stop();							
						
					}

				});
			
				
				addBehavior( new CharacterBehavior("GO_TO_TASK", 1.5f, true ){
					
					@Override
					protected void onStart() {
						MrTileCaveCharacter.this.removeBehavior("IN_CAVE");	
						mElfStart.play();
					}
					
					@Override
					protected void onResume() {
						mElfStart.resume();
					}
					
					@Override
					protected void onPause() {
						mElfStart.pause();
					}
					
					@Override
					protected void onFinish() {
						
						mElfStart.stop();
						SceneManager.getInstance().setScene( MrTileTaskScene.INSTANCE );
					}
				});
		
			
		
	}
}
