package cz.cvut.elf.geom.geomplanet.entity;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;

import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.character.Character;
import cz.cvut.elf.geom.character.CharacterBehavior;

public class ElfNarrator extends Character {
	private static ElfNarrator INSTANCE;
	private Sound mGeomPlanetStory;
	private Sound mSpaceStory;
	
	public ElfNarrator() {
		super(0, 0, 0, 0);
		
        SoundFactory.setAssetBasePath("global/elf/sounds/");
        
		try {
			mGeomPlanetStory  = SoundFactory.createSoundFromAsset(GameHolder.getInstance().mEngine.getSoundManager(), GameHolder.getInstance().mActivity, "geom_planet.mp3");
			mSpaceStory  = SoundFactory.createSoundFromAsset(GameHolder.getInstance().mEngine.getSoundManager(), GameHolder.getInstance().mActivity, "space_intro.mp3");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		create();
	}

	public void create(){
		
		addBehavior( new CharacterBehavior( "GEOM_PLANET_STORY",6.0f, true ) {
			
			Sound actualSound;
			
			@Override
			protected void onStart(){
				
				actualSound = mGeomPlanetStory;
				actualSound.play();
				
			}
			
			@Override
			protected void onResume() {
				
				actualSound.resume();
				
			}
			
			@Override
			protected void onPause() {
				actualSound.pause();
			}
			
			@Override
			protected void onFinish() {
				actualSound.stop();
				
			}
		});
		addBehavior( new CharacterBehavior( "SPACE_STORY",6.0f, true ) {
			
			Sound actualSound;
			
			@Override
			protected void onStart(){
				
				actualSound = mSpaceStory;
				actualSound.play();
				
			}
			
			@Override
			protected void onResume() {
				
				actualSound.resume();
				
			}
			
			@Override
			protected void onPause() {
				actualSound.pause();
			}
			
			@Override
			protected void onFinish() {
				actualSound.stop();
				
			}
		});
	}
	
	public void start() {
		super.start( new CharacterBehavior("DEFAULT",-1.0f, false) {
						
			@Override
			protected void onStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void onResume() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void onPause() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void onFinish() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public static ElfNarrator getInstance(){
		if(INSTANCE==null)INSTANCE = new ElfNarrator();
		return INSTANCE;
	}
}
