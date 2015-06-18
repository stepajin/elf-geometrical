package cz.cvut.elf.geom.geomplanet.logicalrow.entity;

import cz.cvut.elf.geom.character.Character;
import cz.cvut.elf.geom.character.CharacterBehavior;

public class MissBeadCharacter extends Character {

	public MissBeadCharacter(float pX, float pY ) {
		super(pX, pY, 0,0);
		
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
		 
	}

}
