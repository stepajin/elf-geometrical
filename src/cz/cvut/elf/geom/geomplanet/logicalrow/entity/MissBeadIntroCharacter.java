package cz.cvut.elf.geom.geomplanet.logicalrow.entity;

import org.andengine.audio.sound.Sound;

import cz.cvut.elf.geom.character.CharacterBehavior;
import cz.cvut.elf.geom.general.GlobalAssetBundle;
import cz.cvut.elf.geom.general.controls.BlinkButton;
import cz.cvut.elf.geom.geomplanet.logicalrow.LogicalRowAssetBundle;
import cz.cvut.elf.geom.geomplanet.logicalrow.LogicalRowInstructionScene;
import cz.cvut.elf.geom.geomplanet.logicalrow.LogicalRowIntroScene;


public class MissBeadIntroCharacter extends MissBeadCharacter {

	private Sound mIntroSpeak1 	= LogicalRowAssetBundle.getInstance().mIntroSpeak1;
	private Sound mIntroSpeak2 	= LogicalRowAssetBundle.getInstance().mIntroSpeak2;
	
	public MissBeadIntroCharacter(float pX, float pY ) {
		super(pX, pY );

		create();
	}
	

	private void create(){
		
		//TODO automatic count of duration
		addBehavior( new CharacterBehavior("INTRO_SPEAK", 4.5f , true ){ // name, duration, isImmediate
			
			@Override
			protected void onPause() {				
			
				mIntroSpeak1.pause();					
				
			}
			
			@Override
			protected void onStart() {		
				
				mIntroSpeak1.play();			

			}
			
			@Override
			protected void onResume() {				
				
				mIntroSpeak1.resume();

			}
			
			@Override
			protected void onFinish() {
				
				// TODO better button
				LogicalRowIntroScene.INSTANCE.mButton = new BlinkButton(500, 100,GlobalAssetBundle.getInstance().mDefaultHighlightingAnim, LogicalRowIntroScene.INSTANCE, LogicalRowInstructionScene.INSTANCE);				
				LogicalRowIntroScene.INSTANCE.attachChild(LogicalRowIntroScene.INSTANCE.mButton);

				mIntroSpeak1.stop();
				MissBeadIntroCharacter.this.onEvent("INTRO_SPEAK2");
			}

		});
		
		
		//TODO automatic count of duration
		addBehavior( new CharacterBehavior("INTRO_SPEAK2", 2.5f , true ){ // name, duration, isImmediate
			
			@Override
			protected void onPause() {				
			
				mIntroSpeak2.pause();					
				
			}
			
			@Override
			protected void onStart() {		
				
				mIntroSpeak2.play();			

			}
			
			@Override
			protected void onResume() {				
				
				mIntroSpeak2.resume();

			}
			
			@Override
			protected void onFinish() {

				mIntroSpeak2.stop();
	
			}

		});
		
	}

}
