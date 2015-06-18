package cz.cvut.elf.geom.geomplanet.logicalrow;

import java.io.IOException;

import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

import cz.cvut.elf.geom.AssetBundle;
import cz.cvut.elf.geom.GameHolder;

public class LogicalRowAssetBundle extends AssetBundle {

	private static LogicalRowAssetBundle INSTANCE = new LogicalRowAssetBundle();

	private boolean mIsLoaded;

	/******************
	 * Texture regions
	 ******************/
	private BuildableBitmapTextureAtlas mAtlas;
	public TextureRegion mFieldRegion;
	public TextureRegion mFibreRegion;
	public TextureRegion mQuestionMarkRegion;
	public TextureRegion mContinueRegion;
	
	/**********
	 * Beetles
	 **********/
	private ITexture[] mBeetleTextures = new ITexture[Beetle.BEETLE_TYPE
			.values().length];
	public TiledTextureRegion[] mBeetleTextureRegions = new TiledTextureRegion[Beetle.BEETLE_TYPE
			.values().length];

	/*************
	 * Animations 
	 *************/
	private BitmapTextureAtlas mLegsRunningAtlas;
	private BitmapTextureAtlas mLegsHoldingAtlas;
	public TiledTextureRegion mLegsRunningRegion;
	public TiledTextureRegion mLegsHoldingRegion;

	/*********
	 * Sounds
	 *********/
	public Sound mSuccesSound[] = new Sound[Sounds.SUCCESS_SOUNDS];
	public Sound mFailSound[] = new Sound[Sounds.FAIL_SOUNDS];
	public Sound mStartSound[] = new Sound[Sounds.START_SOUNDS];
	public Sound mVictorySound[] = new Sound[Sounds.VICTORY_SOUNDS];
	public Sound mIntroSpeak1, mIntroSpeak2, mTaskInstruction, mByeBye,
			mTaskEnd;

	public static LogicalRowAssetBundle getInstance() {
		return INSTANCE;
	}

	public LogicalRowAssetBundle() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("");

		/******************
		 * Texture regions
		 ******************/
		mAtlas = new BuildableBitmapTextureAtlas(
				GameHolder.getInstance().mActivity.getTextureManager(), 1024,
				1024, TextureOptions.BILINEAR);
		mFieldRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mAtlas, mActivity, "logicalrow/gfx/field.png");
		mFibreRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mAtlas, mActivity, "logicalrow/gfx/fibre.png");
		mQuestionMarkRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAtlas, mActivity,
						"logicalrow/gfx/questionmark.png");
		mContinueRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAtlas, mActivity,
						"logicalrow/gfx/continue.png");

		/***********
		 * Beetles
		 ***********/
		try {
			for (Beetle.BEETLE_TYPE b : Beetle.BEETLE_TYPE.values()) {
				String name = b.name().toLowerCase();
				int code = Beetle.getCode(b);
				mBeetleTextures[code] = new AssetBitmapTexture(
						mActivity.getTextureManager(), mActivity.getAssets(),
						"logicalrow/gfx/beetles/" + name + ".png",
						TextureOptions.BILINEAR);

				mBeetleTextureRegions[code] = TextureRegionFactory
						.extractTiledFromTexture(mBeetleTextures[code], 1, 1);
			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

		/*************
		 * Animations
		 *************/
		mLegsRunningAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(),
				490, 304, TextureOptions.DEFAULT);
		mLegsHoldingAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(),
				480, 525, TextureOptions.DEFAULT);
		mLegsRunningRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mLegsRunningAtlas, mActivity.getAssets(),
						"logicalrow/anim/legs_running.png", 0, 0, 2, 4);
		mLegsHoldingRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mLegsHoldingAtlas, mActivity.getAssets(),
						"logicalrow/anim/legs_holding.png", 0, 0, 2, 5);

		mIsLoaded = false;
	}

	@Override
	public void load() {
		loadGameGraphics();
		loadGameAudio();

		mIsLoaded = true;
	}

	private void loadGameGraphics() {
		try {
			this.mAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.mAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		for (ITexture t : mBeetleTextures) {
			t.load();
		}

		mLegsRunningAtlas.load();
		mLegsHoldingAtlas.load();
	}
	
	private void loadGameAudio() {
		SoundFactory.setAssetBasePath("");
		MusicFactory.setAssetBasePath("");

		Engine engine = GameHolder.getInstance().mEngine;

		try {
			for (int i = 0; i < mSuccesSound.length; i++) {
				mSuccesSound[i] = SoundFactory
						.createSoundFromAsset(engine.getSoundManager(),
								mActivity, "logicalrow/audio/logical_good_job_"
										+ (i + 1) + ".mp3");
			}
			for (int i = 0; i < mFailSound.length; i++) {
				mFailSound[i] = SoundFactory.createSoundFromAsset(
						engine.getSoundManager(), mActivity,
						"logicalrow/audio/logical_bad_job_" + (i + 1) + ".mp3");
			}
			for (int i = 0; i < mStartSound.length; i++) {
				mStartSound[i] = SoundFactory.createSoundFromAsset(
						engine.getSoundManager(), mActivity,
						"logicalrow/audio/start" + (i + 1) + ".wav");
			}
			for (int i = 0; i < mVictorySound.length; i++) {
				mVictorySound[i] = SoundFactory.createSoundFromAsset(
						engine.getSoundManager(), mActivity,
						"logicalrow/audio/logical_victory_" + (i + 1) + ".mp3");
			}

			mIntroSpeak1 = SoundFactory.createSoundFromAsset(
					mEngine.getSoundManager(), mActivity,
					"logicalrow/audio/logical_intro_part_1.mp3");
			mIntroSpeak2 = SoundFactory.createSoundFromAsset(
					mEngine.getSoundManager(), mActivity,
					"logicalrow/audio/logical_intro_part_2.mp3");
			mTaskInstruction = SoundFactory.createSoundFromAsset(
					mEngine.getSoundManager(), mActivity,
					"logicalrow/audio/logical_instruction.mp3");
			mTaskEnd = SoundFactory.createSoundFromAsset(
					mEngine.getSoundManager(), mActivity,
					"logicalrow/audio/logical_task_end.mp3");
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	@Override
	public void unload() {
		this.mAtlas.unload();
		this.mLegsRunningAtlas.unload();
		this.mLegsHoldingAtlas.unload();
		for (ITexture t : mBeetleTextures) {
			t.unload();
		}
		mIsLoaded = false;
	}

	@Override
	public boolean isLoaded() {
		return mIsLoaded;
	}
}
