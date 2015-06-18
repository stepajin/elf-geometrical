package cz.cvut.elf.geom.general;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.entity.text.Text;

import android.app.Activity;
import android.widget.TextView;

/**
 * 
 * @author Tom
 * 
 *         GameTimer measure the time in separate thread. It has start and
 *         ending time constants and it can call a GameEvent to perform some
 *         action.
 */
public class GameTimer {
	private final static int START_TIME = 0;
	private final static int END_TIME = 1000;
	private boolean mStopped=false;
	private Activity mActivity;
	private Timer mTimer;
	private int mSecondCnt;
	private Text mTimerText;
	private ArrayList<GameEvent> mGameEvents = new ArrayList<GameEvent>();

	public GameTimer(Activity pActivity, Text pTimerText) {
		mTimer = new Timer();
		mSecondCnt = START_TIME;
		mActivity = pActivity;
		mTimerText = pTimerText;
		mTimerText.setText("�as: " + mSecondCnt);
		/**
		 * It will go through every second.
		 */
		mTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				mActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(!mStopped){
							mTimerText.setText("�as: " + mSecondCnt);
							mSecondCnt++;
							if (mSecondCnt == END_TIME) {
								mSecondCnt = START_TIME;
							}
							for (Iterator iterator = GameTimer.this.mGameEvents
									.iterator(); iterator.hasNext();) {
								GameEvent event = (GameEvent) iterator.next();
								if (event.proceed()
										&& event.getOnTime() == mSecondCnt) {
									event.processOnTime();
								}
							}
						}

					}
				});
			}
		}, 0, 1000);
	}
	public int getSecondCnt() {
		return mSecondCnt;
	}

	public void reset() {
		mSecondCnt = START_TIME;
	}
	public void stop(){
		mStopped=true;
	}
	public void go(){
		mStopped=false;
	}
	public void cancel(){
		stop();
		reset();
		unregisterAllGameEvents();
		mTimer.cancel();
	}
	public void registerGameEvent(GameEvent gameEvent) {
		this.mGameEvents.add(gameEvent);
	}

	public void unregisterGameEvent(GameEvent gameEvent) {
		this.mGameEvents.remove(gameEvent);
	}

	public void unregisterAllGameEvents() {
		this.mGameEvents.clear();
	}
}
