package cz.cvut.elf.geom.character;

import java.util.HashMap;
import java.util.LinkedList;

import org.andengine.entity.Entity;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.interfaces.Loadable;

/**
 * 
 * Class represents game Character. It manages incoming events and calls reactions which are assigned to them. 
 * 
 * If you want to create game character, create your own class which extends this class.
 * In constructor of your class define behavior using CharacterBehavior anonymous class.
 * Add this behaviors using addBehaviour() method.
 * 
 * Implement listeners which you want to do reaction. And call method onEvent(String).
 * 
 * 
 * @author Petr Zavadil
 * 
 * */

public class Character extends Entity implements IEventListener, IOnBehaviourFinishListener, Loadable{

	protected static VertexBufferObjectManager mVbom = GameHolder.getInstance().mVBOM;
	protected static BaseGameActivity mActivity = GameHolder.getInstance().mActivity;
	private static boolean mIsLoaded = false;
	
	
	private LinkedList<CharacterBehavior> mReactionsQueue = new LinkedList<CharacterBehavior>();
	private HashMap<String, CharacterBehavior > mBehaviours = new HashMap<String, CharacterBehavior >();
	private CharacterBehavior mActualBehavior;
	private CharacterBehavior mDefaultBehaviour;

	
	/**
	 * @param pX X coordinates
	 * @param pY Y coordinates
	 * @param pW Width
	 * @param pH Height
	 * @param pDefaultBehaviour Default behavior which is started if there is no another behavior.
	 * 
	 * */
	public Character( float pX, float pY, float pW, float pH ) {
		super( pX, pY, pW, pH );

	}
	
	
	
	/**
	 * Simply adds the behavior
	 * 
	 * */	
	
	protected void addBehavior( CharacterBehavior pBehaviour ){
		
		mBehaviours.put( pBehaviour.getName(), pBehaviour );
	}
	
	protected void removeBehavior( String pName ){
		
		CharacterBehavior cb = mBehaviours.remove( pName );
		
		if( cb == null ){
			return;
		}
		
		cb.forceFinish();
	}

	
	protected void start( CharacterBehavior pDefaultBehaviour ){
		
		mActualBehavior = mDefaultBehaviour = pDefaultBehaviour;
		mDefaultBehaviour.start( this );
	}
	
	
	/**
	 * Manages income event and starts appropriate behavior reactions.
	 * 
	 * @param pEvent Name of behavior which should be started immediately or at the end 
	 * (depends on the implementation of isImmediate() method of the behavior).
	 * 
	 * */
	@Override
	public final void onEvent( String pEvent ) {
		
		Debug.d("onEvent", "Income event: "+pEvent);
		
		//finds appropriate reaction to the event
		CharacterBehavior reaction = mBehaviours.get(pEvent);
		
		//no assigned behaviour to event pEvent
		if( reaction == null ){			
			return;
		}
		
		//nothing in queue - go to the first and start
		if( mReactionsQueue.isEmpty() ){

			//stop default behav.
			mActualBehavior.pause();
			//add and start new reaction
			mReactionsQueue.addFirst( reaction );
			mActualBehavior = reaction;
			mActualBehavior.start(this);
			return;
		}
		
		//bezprostredni reakce - na zacatek fronty
		if( reaction.isImmediate() ){
			
			//aby nebyly dve stejne reakce za sebou
			if( !mActualBehavior.equals( reaction )){				
				
				mActualBehavior.pause();
				mReactionsQueue.addFirst( reaction );
				mActualBehavior = reaction;
				mActualBehavior.start(this);
				
			}
		
		//neni bezprostredni reakce - nakonec fronty
		}else{
			
			//aby nebyly dve stejne reakce za sebou
			if( !mReactionsQueue.getLast().equals( reaction )){
				mReactionsQueue.addLast( reaction );
			}	
			
		}
		
	}

	
	/**
	 * Implementation of IOnBehaviourFinishListener interface.
	 * It is called when the behavior is finished - its timerHandler is elapsed
	 * 
	 * */
	@Override
	public void onBehaviorFinish(CharacterBehavior pCharacterBehaviour) {		
		
				
		int i = mReactionsQueue.indexOf( pCharacterBehaviour);
		CharacterBehavior cb = mReactionsQueue.get( i );
		mReactionsQueue.remove( i );
		
		if( i == 0 ){
			
			if( !mReactionsQueue.isEmpty() ){
			
				//if not empty start next reaction
				mActualBehavior = mReactionsQueue.getFirst();
				mActualBehavior.start(this);			
				
			}else{
				
				//no reaction - run default behav.
				mActualBehavior = mDefaultBehaviour;
				mActualBehavior.resume();
				
			}
		
		}
		//TODO zavest metodu destroy() ktera se vola pri nasilnem ukonceni behavioru
		
		// if throws NullPointerException - Maybe duration of default behavior is not < 0
		//finish() se musi volat zde, pajc kdyz se vola mReactionsQueue.poll().finish(); 
		//nastane problem pri situaci kdz se v metode finish() vola na Character onEvent(..)
		cb.finish();
		
	}


	public void onSceneResume(){
			
			mActualBehavior.onSceneResume();
	}
	
	public void onScenePause(){
		
		mActualBehavior.onScenePause();
		
	}
	
	
	@Override
	public void load() {
		mIsLoaded = true;
	}

	@Override
	public void unload() {
		mIsLoaded = false;
	}

	@Override
	public boolean isLoaded() {		
		return mIsLoaded;
	}
	

	
	
	
}
