package cz.cvut.elf.geom.general;

/**
 * 
 * @author Tom
 * 
 *         GameEvent is an class for proceeding an action on specific time. The
 *         variable proceed has to be set to true and there should be givin time
 *         (onTime) in seconds fot the event.
 */
public class GameEvent {
	private boolean mProceed = true;
	private int mOnTime = 0;

	public GameEvent(int pOnTime) {
		this.mOnTime = pOnTime;
	}

	public boolean proceed() {
		return mProceed;
	}

	public void setProceed(boolean proceed) {
		this.mProceed = proceed;
	}

	public int getOnTime() {
		return mOnTime;
	}

	public void setOnTime(int onTime) {
		this.mOnTime = onTime;
	}

	public void processOnTime() {

	}

}
