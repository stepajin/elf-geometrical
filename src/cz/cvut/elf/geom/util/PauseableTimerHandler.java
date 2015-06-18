package cz.cvut.elf.geom.util;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

public class PauseableTimerHandler extends TimerHandler {

	private boolean mPause = false;

	public PauseableTimerHandler(float pTimerSeconds, boolean pAutoReset,
			ITimerCallback pTimerCallback) {
		super(pTimerSeconds, pAutoReset, pTimerCallback);
	}
	public boolean isPaused(){
		
		return mPause;
	}

	public void pause() {
		this.mPause = true;
	}

	public void resume() {
		this.mPause = false;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		if (!this.mPause) {
			super.onUpdate(pSecondsElapsed);
		}
	}
}
