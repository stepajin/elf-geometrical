package cz.cvut.elf.geom.geomplanet.logicalrow;

import java.util.Random;

import org.andengine.audio.sound.Sound;

public class Sounds {
	
	public static final int FAIL_SOUNDS = 3;
	public static final int SUCCESS_SOUNDS = 3;
	public static final int START_SOUNDS = 3;
	public static final int VICTORY_SOUNDS = 2;
	
	private static LogicalRowAssetBundle assetBundle = LogicalRowAssetBundle.getInstance();
	private static Random random = new Random();
	
	public static Sound getStartSound() {
		if (!assetBundle.isLoaded()) {
			assetBundle.load();
		}
		
		return assetBundle.mStartSound[random.nextInt(START_SOUNDS)];
	}
	
	public static Sound getFailSound() {
		if (!assetBundle.isLoaded()) {
			assetBundle.load();
		}
		
		return assetBundle.mFailSound[random.nextInt(FAIL_SOUNDS)];
	}
	
	public static Sound getSuccessSound() {
		if (!assetBundle.isLoaded()) {
			assetBundle.load();
		}
		
		return assetBundle.mSuccesSound[random.nextInt(SUCCESS_SOUNDS)];
	}
	
	public static Sound getVictorySound() {
		if (!assetBundle.isLoaded()) {
			assetBundle.load();
		}
		
		return assetBundle.mVictorySound[random.nextInt(VICTORY_SOUNDS)];
	}

}
