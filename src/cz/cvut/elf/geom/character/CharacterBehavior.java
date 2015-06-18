package cz.cvut.elf.geom.character;

import java.util.ArrayList;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import android.util.Log;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.util.PauseableTimerHandler;


/**
 * Abstract class for programing behavior of character.
 * 
 * Using methods onStar(), onStop(), onRestart(), onFinish() you can define behavior of the character.
 * 
 * @author Petr Zavadil
 * 
 * */

public abstract class CharacterBehavior implements ITimerCallback{

	public static final long DEFAULT_TIMEOUT = 2 * 1000;//in milliseconds
	
	protected long mStartTime, mPauseTime;
	protected long mTimeout = DEFAULT_TIMEOUT;
	protected float mBehaviourDuration;
	private String mName;
	private boolean mIsImmediate;
	
	private PauseableTimerHandler mTimer;
	
	private static float ANIMATION_TIME_OFSET = GameHolder.ANIMATION_FRAME_DURATION/1000;
	
	/**
	 * 
	 * 
	 * @param pName Define name of behavior. It is used for assigning behaviors to events in the Character.
	 * @param pDuration Define duration of behavior. If duration is elapsed behavior call onFinish() method. 
	 * If you create default behavior, set this variable to value < 0 (infinite duration)
	 * 
	 * */

	public CharacterBehavior( String pName, float pDuration, boolean pIsImmediate ) {
		
		mName = pName;					
		mBehaviourDuration = pDuration;
		mIsImmediate = pIsImmediate;

		//if infinite duration
		if( mBehaviourDuration < 0 ){
			mBehaviourDuration = Float.MAX_VALUE;			
		}
		mTimer = new PauseableTimerHandler( mBehaviourDuration, false, this );
		
	}
	
	/**
	 * 
	 * 
	 * @param pName Define name of behavior. It is used for assigning behaviors to events in the Character.
	 * @param pDuration Define duration of behavior. If duration is elapsed behavior call onFinish() method. 
	 * If you create default behavior, set this variable to value < 0 (infinite duration)
	 * @param pTimeout timeout of behavior, max duration of pause in seconds
	 * 
	 * */
	
	public CharacterBehavior( String pName, float pDuration, boolean pIsImmediate, float pTimeout ){
		this( pName, pDuration, pIsImmediate );
		
		mTimeout = (long)(pTimeout) * 1000;
		
	}

	
	
	/**
	 * Method is used for defining what happen on start of behavior.
	 * It starts animations, sounds etc.
	 * 
	 * */
	protected abstract void onStart();
	
	/**
	 * Method is used for defining what happen if behavior is paused.
	 * 				-> current behavior is replaced by other behavior
	 * 
	 * There should be stopped animations and sounds
	 * */
	protected abstract void onPause();
	
	
	/**
	 * Method is used for defining what happen on restart of behavior.
	 * There will be probably started animations and sounds again
	 * */
	protected abstract void onResume();	
	
	
	/**
	 * Method is used for defining what happen at the end of behavior.
	 * 
	 * */
	protected abstract void onFinish();
	
	

	
	// ----------------------------------------
	
	public void start( IOnBehaviourFinishListener pListener ){

		//if it has been already started and paused
		if( mTimer.isPaused() ){
			
			resume();
			return;
		}
		
		//TODO set atomic
		//if it is started first time
		registerFinishListener(pListener);	
		Log.d(getClass().getSimpleName(), "Behav: "+getName()+", onStart()");
		this.onStart();
		
		mStartTime = System.currentTimeMillis();
		GameHolder.getInstance().mEngine.registerUpdateHandler(mTimer);
		
		
	}
	
	//hack aby se nevytimeovalo chovani ktere bylo pausnuto Systemem a ne jinym chovanim
	public void onSceneResume(){
				
		mPauseTime = System.currentTimeMillis();
		resume();
	}
	
	public void onScenePause(){
		
		pause();
	}
	
	
	
	//life cycle methods
	
	public void pause(){
		
		mPauseTime = System.currentTimeMillis();
		
		mTimer.pause();
		Log.d(getClass().getSimpleName(), "Behav: "+getName()+", onPause()");
		this.onPause();
	}
	
	public void resume(){
		
		//pokud ubehl timeout poslu na chovani finish
		//TODO dodelat timeout chovani tak aby character postupne pomalu zapominal chovani ve fronto-zasobn�ku
		/*
		if( isTimedOut() ){
			Log.d(getClass().getSimpleName(), "Behav: "+getName()+", TIMED OUT!");
			onTimePassed( mTimer );
			return;
		}*/
		
		Log.d(getClass().getSimpleName(), "Behav: "+getName()+", onResume()");
		this.onResume();
		mTimer.resume();
	}	
	
	public void finish(){
		
		Log.d( getClass().getSimpleName(), "Behav: "+getName()+", onFinish()");
		mTimer.reset();
		this.onFinish();
	}
	
	public boolean isTimedOut(){
		
		if( mTimeout < 0 ){
			return false;
		}
		return ( System.currentTimeMillis() - mStartTime ) < mTimeout ;
	}
	
	// ----------------------------
	
	public boolean isImmediate(){
		
		return mIsImmediate;
	}	
	
	
	public void forceFinish(){
		
		Log.d(getClass().getSimpleName(), "Behav: "+getName()+", forceFinish()");
		onTimePassed( mTimer );
	}
	
	// finish listener registrations
	private ArrayList<IOnBehaviourFinishListener> mFinishListeners = new ArrayList<IOnBehaviourFinishListener>();
	
	public void registerFinishListener( IOnBehaviourFinishListener pListener ){
		
		mFinishListeners.add(pListener);
	}

	
	@Override
	public void onTimePassed(TimerHandler pTimerHandler) {
				
		GameHolder.getInstance().mEngine.unregisterUpdateHandler(pTimerHandler);
		
		for( IOnBehaviourFinishListener fl : mFinishListeners){
			fl.onBehaviorFinish(this);
		}
		mFinishListeners.clear();
		
	}
	
	
	// getters & setters *******************************************************
	//  ************************************************************************
	
	
	public String getName(){
		
		return mName;
	}

}
