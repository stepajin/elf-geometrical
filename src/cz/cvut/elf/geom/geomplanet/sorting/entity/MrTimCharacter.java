package cz.cvut.elf.geom.geomplanet.sorting.entity;

import cz.cvut.elf.geom.character.Character;
import cz.cvut.elf.geom.character.CharacterBehavior;

public class MrTimCharacter extends Character {

	public MrTimCharacter(float pX, float pY ) {
		super(pX, pY, 0,0);

	}

	
	public void start() {
		
		super.start( new CharacterBehavior("DEFAULT",-1.0f, false) {
			
			@Override
			protected void onStart() {}
			
			@Override
			protected void onResume() {}
			
			@Override
			protected void onPause() {}
			
			@Override
			protected void onFinish() {}
		});
	}

}
