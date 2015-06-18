package cz.cvut.elf.geom.geomplanet.sorting;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;
import cz.cvut.elf.geom.AssetBundle;

public class SortingAssetBundle extends AssetBundle {

	private static SortingAssetBundle INSTANCE = new SortingAssetBundle();
	

	// scena s domem, ve kterem bude hostina

	// scena s hostinou
	private BitmapTextureAtlas mBanquetSceneAtlas;
	public TextureRegion mBanquetBackground;

	// herni scena
	private BitmapTextureAtlas mTaskSceneAtlas;
	public TextureRegion mPlate;
	
	private BitmapTextureAtlas mTaskAtlas;
	public TextureRegion mBackground;
	
	// Sounds -------------------------------------------------------
	// --------------------------------------------------------------
	
	public Sound 		mIntro1, mIntro2, mInstructions, mTaskEndSpeak;
	public Sound [] 	mGoodJob, mBadJob;
	
	
	// --------------------------------------------------------------
	
	public static SortingAssetBundle getInstance() {
		Log.d("SortingAssetBundle", "getInstance()");
		return INSTANCE;
	}

	private SortingAssetBundle() {
		Log.d("SortingAssetBundle", "Constructor()");

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("sorting/");

		// Images & animations ------------------------------------------
		// --------------------------------------------------------------
		mBanquetSceneAtlas = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 1024, 1024);
		
		mBanquetBackground = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBanquetSceneAtlas, mActivity,
						"banquetbg.png",0,0);

		mTaskSceneAtlas = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 1024, 1024);
		
		mPlate = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mTaskSceneAtlas, mActivity,
						"plate.png",0,0);
		

		mTaskAtlas = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 1024, 1024);
		mBackground = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mTaskAtlas, mActivity,
						"taskbg.png",0,0);
		
		
		// Sounds -------------------------------------------------------
		// --------------------------------------------------------------
		
		SoundFactory.setAssetBasePath("sorting/sounds/");
		
		try {
			
			mIntro1 			= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "sorting_intro_part_1.mp3");
			mIntro2				= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "sorting_intro_part_2.mp3");
			mInstructions 		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "sorting_instruction.mp3");
			mTaskEndSpeak		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "sorting_task_end.mp3");
			
			mGoodJob = new Sound[3];
			mGoodJob[0] 		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "sorting_good_job_1.mp3");
			mGoodJob[1] 		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "sorting_good_job_2.mp3");
			mGoodJob[2] 		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "sorting_good_job_3.mp3");
			
			mBadJob = new Sound[3];
			mBadJob[0] 		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "sorting_bad_job_1.mp3");
			mBadJob[1] 		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "sorting_bad_job_2.mp3");
			mBadJob[2] 		= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), mActivity, "sorting_bad_job_3.mp3");
						
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SoundFactory.setAssetBasePath("");

	}

	@Override
	public void load() {
		mBanquetSceneAtlas.load();
		mTaskSceneAtlas.load();
		mTaskAtlas.load();
		super.load();
	}

	@Override
	public void unload() {
		mBanquetSceneAtlas.unload();
		mTaskSceneAtlas.unload();
		mTaskAtlas.unload();
		super.unload();
	}
}
