package cz.cvut.elf.geom.geomplanet.sorting.entity;

import org.andengine.audio.sound.Sound;

import cz.cvut.elf.geom.character.CharacterBehavior;
import cz.cvut.elf.geom.general.GlobalAssetBundle;
import cz.cvut.elf.geom.general.controls.BlinkButton;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;
import cz.cvut.elf.geom.geomplanet.sorting.SortingAssetBundle;
import cz.cvut.elf.geom.geomplanet.sorting.SortingBanquetScene;
import cz.cvut.elf.geom.geomplanet.sorting.SortingDiningTableScene;
import cz.cvut.elf.geom.geomplanet.sorting.SortingOutroScene;
import cz.cvut.elf.geom.geomplanet.sorting.SortingTaskScene;

public class MrTimBanquetCharacter extends MrTimCharacter {
	
	private Sound mIntro1 = SortingAssetBundle.getInstance().mIntro1;
	private Sound mIntro2 = SortingAssetBundle.getInstance().mIntro2;

	public MrTimBanquetCharacter(float pX, float pY) {
		super(pX, pY);
		
		create();
	}
	
	public void create(){
		
		addBehavior( new CharacterBehavior("INTRO_SPEAK",3.5f, true ) {
			
			@Override
			protected void onStart() {
				
				mIntro1.play();
			}
			
			@Override
			protected void onResume() {
				
				mIntro1.resume();
			}
			
			@Override
			protected void onPause() {
				
				mIntro1.pause();
			}
			
			@Override
			protected void onFinish() {
				
				mIntro1.stop();
				
				SortingBanquetScene.INSTANCE.mButtonToGame = new BlinkButton(600, 200,	GlobalAssetBundle.getInstance().mDefaultHighlightingAnim, SortingBanquetScene.INSTANCE, SortingTaskScene.INSTANCE);
				SortingBanquetScene.INSTANCE.mButtonToMenu = new BlinkButton(50, 50,	GlobalAssetBundle.getInstance().mDefaultHighlightingAnim, SortingBanquetScene.INSTANCE, GeomPlanetScene.INSTANCE);
				
				SortingBanquetScene.INSTANCE.attachChild(SortingBanquetScene.INSTANCE.mButtonToGame);
				SortingBanquetScene.INSTANCE.attachChild(SortingBanquetScene.INSTANCE.mButtonToMenu);
				
				MrTimBanquetCharacter.this.onEvent("INTRO_SPEAK2");
			}
		});
		
		
		addBehavior( new CharacterBehavior("INTRO_SPEAK2",3.5f, true ) {
			
			@Override
			protected void onStart() {
				
				mIntro2.play();
			}
			
			@Override
			protected void onResume() {
				
				mIntro2.resume();
			}
			
			@Override
			protected void onPause() {
				
				mIntro2.pause();
			}
			
			@Override
			protected void onFinish() {
				
				mIntro2.stop();

			}
		});
		
	}

}
