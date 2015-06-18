package cz.cvut.elf.geom.geomplanet.mrtile.entity;

import java.util.Random;

import org.andengine.audio.sound.Sound;

import cz.cvut.elf.geom.character.Character;
import cz.cvut.elf.geom.character.CharacterBehavior;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileAssetBundle;

public class MrTileTaskCharacter extends Character {
	
	private Sound [] mGoodJob, mBadJob;
	private Sound mStartTask;

	public MrTileTaskCharacter() {
		super(0.f,0.f,0.f,0.f);
		
		mGoodJob 	= MrTileAssetBundle.getInstance().mGoodJob;
		mBadJob 	= MrTileAssetBundle.getInstance().mBadJob;
		mStartTask 	= MrTileAssetBundle.getInstance().mTaskInstruction;
		
		create();
	}

	
	public void create(){
		
		addBehavior( new CharacterBehavior( "START_TASK",6.0f, true ) {
			
			Random random = new Random();
			Sound actualSound;
			
			@Override
			protected void onStart(){
				
				actualSound = mStartTask;
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
		
		addBehavior( new CharacterBehavior("GOD_JOB",1.5f, true ) {
			
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

		addBehavior( new CharacterBehavior("BAD_JOB",3.0f, true ) {

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
	
	
	
}
