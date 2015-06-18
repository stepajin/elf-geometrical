package cz.cvut.elf.geom.geomplanet.logicalrow;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.debug.Debug;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.general.controls.BlinkButton;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;
import cz.cvut.elf.geom.geomplanet.logicalrow.entity.MissBeadIntroCharacter;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.MrTileInfrontCaveCharacter;

public class LogicalRowIntroScene extends BaseScene {

	public static final LogicalRowIntroScene INSTANCE = new LogicalRowIntroScene();

	private int idTask = 1;
	private BuildableBitmapTextureAtlas mAtlas;

	private Background bg;
	private Sprite girlSprite;
	public BlinkButton mButton;
	private Text mText;

	private MissBeadIntroCharacter mMissBead;
	private int mResumeCounter = 0;


	private LogicalRowIntroScene() {
	}

	@Override
	public void onBuild() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("");

		mAtlas = new BuildableBitmapTextureAtlas(
				GameHolder.getInstance().mActivity.getTextureManager(), 1024,
				1024, TextureOptions.BILINEAR);

		float centerX = GameHolder.getInstance().mCamera.getCenterX();
		float centerY = GameHolder.getInstance().mCamera.getCenterY();

		if (!LogicalRowAssetBundle.getInstance().isLoaded()) {
			LogicalRowAssetBundle.getInstance().load();
		}

		TextureRegion skipRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAtlas, GameHolder.getInstance().mActivity,
						"global/controls/butt_skip.png");

		try {
			this.mAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.mAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		bg = new SpriteBackground(new Sprite(centerX, centerY,
				LogicalRowAssetBundle.getInstance().mFieldRegion,
				GameHolder.getInstance().mVBOM));

		mText = new Text(GameHolder.getInstance().mCamera.getCenterX(),
				GameHolder.getInstance().mCamera.getHeight() * 3 / 4,
				mGlobalAssetBundle.mDefaultFont,
				"Skritek narazi na Koralku, ktere se rozutikaly koralky.",
				GameHolder.getInstance().mVBOM);

		Sprite mSkipButton = new Sprite(650, 100, skipRegion,
				GameHolder.getInstance().mVBOM) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown())
					SceneManager.getInstance().setScene(
							LogicalRowInstructionScene.INSTANCE);
				return true;
			}
		};

		this.setBackground(bg);
		this.attachChild(mText);
		this.attachChild(mSkipButton);
		this.registerTouchArea(mSkipButton);

		mMissBead = new MissBeadIntroCharacter(0, 0);
		mMissBead.load();
		attachChild(mMissBead);
		mMissBead.start();

		setBuilt(true);
	}

	@Override
	public void onResume() {

		if (mResumeCounter == 0) {
			// mMissBead has decided to say introduction
			mMissBead.onEvent("INTRO_SPEAK");

		} else {
			mMissBead.onSceneResume();
		}

		mResumeCounter++;
		super.onResume();
	}

	@Override
	public void onPause() {

		mMissBead.onScenePause();
		super.onPause();
	}

	@Override
	public void onStop() {
		this.onDestroy();
	}

	@Override
	public void onDestroy() {
		mAtlas.unload();
		this.detachChildren();
		this.detachSelf();
		this.clearTouchAreas();
		mMissBead.unload();
		mResumeCounter = 0;
		setBuilt(false);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().setScene(GeomPlanetScene.INSTANCE);
	}
}