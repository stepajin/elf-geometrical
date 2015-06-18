package cz.cvut.elf.geom;


/**
 * 
 * @author Tom
 * 
 *         Simple implementation of user, who should be a child. In future
 *         versions user will have more attributes, now we only need to know,
 *         how skilled the user is.
 */
public class User {
	/*
	 * Variables
	 */
	private SkillType mCurrentSkillType = SkillType.SKILL_INTERMEDIATE;

	private int mCurrentMistakes;
	private int mTotal; // celkovy pocet pokusu ktere hrac udelal
	private float mCurrentTime;
	private int mCurrentIDTask;
	private int mID;
	private String mName = "Nikdo neni prihlasen!";

	/**
	 * 
	 * We will work with 3 skill type so far. There should be more to
	 * differentiate better the capability of child user.
	 * 
	 */
	public static enum SkillType {
		SKILL_BEGINNER, SKILL_INTERMEDIATE, SKILL_EXPERT,
	}


	/*
	 * Getters and setters
	 */
	public void setName(String name) {
		this.mName = name;
	}

	public String getName() {
		return mName;
	}

	public SkillType getCurrentSkillType() {
		return mCurrentSkillType;
	}

	public void setId(int id) {
		this.mID = id;
	}
	
	public int getId() {
		return this.mID;
	}
	
	public int getCurrentMistakes() {
		return mCurrentMistakes;
	}

	public float getCurrentTime() {
		return mCurrentTime;
	}

	public void setCurrentMistakes(int pMistakes) {
		this.mCurrentMistakes = pMistakes;
	}

	public void setCurrentTime(float pTime) {
		this.mCurrentTime = pTime;
	}

	public void setCurrentIDTask(int pID) {
		this.mCurrentIDTask = pID;
	}

	public int getCurrentIDTask() {
		return this.mCurrentIDTask;
	}

	public int getTotal() {
		return this.mTotal;
	}

	public void setTotal(int pTotal) {
		this.mTotal = pTotal;
	}
}
