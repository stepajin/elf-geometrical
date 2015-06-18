package cz.cvut.elf.geom.geomplanet.mrtile.entity;

import org.andengine.audio.sound.Sound;

import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.character.CharacterBehavior;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileAssetBundle;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileIntroScene;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileTask;

public class MrTileInfrontCaveCharacter extends MrTileCharacter {
	
	private static Sound mWelcome 	= MrTileAssetBundle.getInstance().mWelcome;
	private static Sound mByeBye 	= MrTileAssetBundle.getInstance().mByeBye;

	
	public MrTileInfrontCaveCharacter( float pX, float pY ) {
		super(pX, pY);				
		
		create();
	}
	
	
	private void create(){
		
		//TODO automatic count of duration
		addBehavior( new CharacterBehavior("INTRO_SPEAK", 6.0f , true ){ // name, duration, isImmediate
			
			@Override
			protected void onPause() {				
				
				mMouth.stopAnimation();				
				mWelcome.pause();	
				
				
			}
			
			@Override
			protected void onStart() {		
				

				mWelcome.play();
				mMouth.animate( GameHolder.ANIMATION_FRAME_DURATION, true );				

			}
			
			@Override
			protected void onResume() {				
				
				mWelcome.resume();
				mMouth.animate( GameHolder.ANIMATION_FRAME_DURATION, true );
			}
			
			@Override
			protected void onFinish() {
					
				mMouth.stopAnimation();
				mWelcome.stop();
				
				
				
				
			}

		});
		
		
		//TODO automatic count of duration
				addBehavior( new CharacterBehavior("BYE_BYE_SPEAK", 4.0f , true ){ // name, duration, isImmediate
					
					@Override
					protected void onPause() {				
						
						mMouth.stopAnimation();				
						mByeBye.pause();	
						
						
					}
					
					@Override
					protected void onStart() {		

						mByeBye.play();
						mMouth.animate( GameHolder.ANIMATION_FRAME_DURATION, true );				

					}
					
					@Override
					protected void onResume() {				
						
						mByeBye.resume();
						mMouth.animate( GameHolder.ANIMATION_FRAME_DURATION, true );
					}
					
					@Override
					protected void onFinish() {
							
						mMouth.stopAnimation();
						mByeBye.stop();
						
						MrTileTask.getInstance().finish();
						//SceneManager.getInstance().setScene( GeomPlanetScene.INSTANCE );
						
						
					}

				});
		
			
		
	}
	
	


}
