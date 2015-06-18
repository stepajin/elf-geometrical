package cz.cvut.elf.geom.geomplanet.sorting.entity;

import java.util.Random;

import org.andengine.audio.sound.Sound;

import cz.cvut.elf.geom.character.Character;
import cz.cvut.elf.geom.character.CharacterBehavior;
import cz.cvut.elf.geom.geomplanet.sorting.SortingAssetBundle;
import cz.cvut.elf.geom.geomplanet.sorting.SortingTaskScene;

public class MrTimTaskCharacter extends Character {

	private Sound 		mInstructions 	= SortingAssetBundle.getInstance().mInstructions;
	private Sound		mTaskEndSpeak	= SortingAssetBundle.getInstance().mTaskEndSpeak;
	private Sound [] 	mGoodJob 		= SortingAssetBundle.getInstance().mGoodJob;
	private Sound [] 	mBadJob 		= SortingAssetBundle.getInstance().mBadJob;
	
	public MrTimTaskCharacter() {
		super(0,0,0,0);
		
		create();
	}
	
	public void create(){
		
		
		addBehavior( new CharacterBehavior("TASK_INSTRUCTIONS_SPEAK",6.f,true) {
			
			@Override
			protected void onStart() {
				mInstructions.play();
			}
			
			@Override
			protected void onResume() {
				mInstructions.resume();
			}
			
			@Override
			protected void onPause() {
				mInstructions.pause();
			}
			
			@Override
			protected void onFinish() {
				mInstructions.stop();
			}
		});
		
		addBehavior( new CharacterBehavior("TASK_END_SPEAK",6.f,true) {
			
			@Override
			protected void onStart() {
				mInstructions.play();
			}
			
			@Override
			protected void onResume() {
				mInstructions.resume();
			}
			
			@Override
			protected void onPause() {
				mInstructions.pause();
			}
			
			@Override
			protected void onFinish() {
				mInstructions.stop();
			}
		});		
	
		addBehavior( new CharacterBehavior("GOOD_JOB",1.5f, true) {
			
			Random random = new Random();
			Sound actualSound;
			
			@Override
			protected void onStart(){
				
				actualSound = mGoodJob[ random.nextInt( mGoodJob.length ) ];
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
		

		addBehavior( new CharacterBehavior("BAD_JOB",1.5f, true) {
			
			Random random = new Random();
			Sound actualSound;
			
			@Override
			protected void onStart(){
				
				actualSound = mBadJob[ random.nextInt( mBadJob.length ) ];
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
		

		addBehavior( new CharacterBehavior("LAST_FOOD",1.0f, true) {
			
			@Override
			protected void onStart(){
				SortingTaskScene.INSTANCE.mComplete = true;
				//TODO odstranit predchozi chovani z mr Tima
			}
			
			@Override
			protected void onResume() {
			}
			
			@Override
			protected void onPause() {
			}
			
			@Override
			protected void onFinish() {
				
				SortingTaskScene.INSTANCE.mLevel.restartLevel();
				SortingTaskScene.INSTANCE.mComplete = false;
			}
		});
	
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
