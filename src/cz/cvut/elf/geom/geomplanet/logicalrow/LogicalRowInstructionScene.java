package cz.cvut.elf.geom.geomplanet.logicalrow;

import org.andengine.entity.scene.background.IBackground;
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
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;

public class LogicalRowInstructionScene extends BaseScene {

	public static final LogicalRowInstructionScene INSTANCE = new LogicalRowInstructionScene();

	private BuildableBitmapTextureAtlas mAtlas;

	private IBackground bg;

	private LogicalRowInstructionScene() {
	}

	@Override
	public void onBuild() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("");		
		
		mAtlas = new BuildableBitmapTextureAtlas(
				GameHolder.getInstance().mActivity.getTextureManager(), 1024,
				1024, TextureOptions.BILINEAR);

		if (!LogicalRowAssetBundle.getInstance().isLoaded()) {
			LogicalRowAssetBundle.getInstance().load();
		}

		TextureRegion fingerRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mAtlas, GameHolder.getInstance().mActivity,
						"logicalrow/instruction/finger.png");

		TiledTextureRegion circleRegion = LogicalRowAssetBundle.getInstance().mBeetleTextureRegions[Beetle
				.getCode(Beetle.BEETLE_TYPE.CIRCLE)];

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

		float centerX = GameHolder.getInstance().mCamera.getCenterX();
		float centerY = GameHolder.getInstance().mCamera.getCenterY();

		bg = new SpriteBackground(new Sprite(centerX, centerY,
				LogicalRowAssetBundle.getInstance().mFieldRegion,
				GameHolder.getInstance().mVBOM));

		float width = GameHolder.getInstance().mCamera.getWidth();
		float height = GameHolder.getInstance().mCamera.getHeight();

		this.setBackground(bg);

		this.attachChild(new Sprite(centerX, centerY, LogicalRowAssetBundle
				.getInstance().mFibreRegion, GameHolder.getInstance().mVBOM));
		this.attachChild(new Sprite(width / 5, height * 2 / 5, circleRegion,
				GameHolder.getInstance().mVBOM));
		this.attachChild(new Sprite(width * 2 / 5, height * 4 / 11,
				LogicalRowAssetBundle.getInstance().mQuestionMarkRegion,
				GameHolder.getInstance().mVBOM));
		this.attachChild(new Sprite(width * 3 / 5, height * 4 / 11,
				circleRegion, GameHolder.getInstance().mVBOM));
		this.attachChild(new Sprite(width * 4 / 5, height * 2 / 5,
				circleRegion, GameHolder.getInstance().mVBOM));

		Sprite circle = new Sprite(centerX - 20, 100, circleRegion,
				GameHolder.getInstance().mVBOM);
		circle.setScale(0.7f);
		this.attachChild(circle);

		Sprite mSkipButton = new Sprite(650, 100, skipRegion,
				GameHolder.getInstance().mVBOM) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown())
					SceneManager.getInstance().setScene(
							LogicalRowTaskScene.INSTANCE);
				return true;
			}
		};
		this.attachChild(mSkipButton);
		this.registerTouchArea(mSkipButton);

		this.attachChild(new Text(
				GameHolder.getInstance().mCamera.getCenterX(), GameHolder
						.getInstance().mCamera.getCenterY(),
				mGlobalAssetBundle.mDefaultFont, "Instruktazni animace.",
				GameHolder.getInstance().mVBOM));

		this.attachChild(new Sprite(centerX + 50, 0, fingerRegion, GameHolder
				.getInstance().mVBOM));
	}

	@Override
	public void onStart() {
		onResume();
	}
	
	@Override
	public void onResume() {
		LogicalRowAssetBundle.getInstance().mTaskInstruction.play();
	}
	
	@Override
	public void onPause() {
		LogicalRowAssetBundle.getInstance().mTaskInstruction.pause();
	}
	
	@Override
	public void onStop() {
		LogicalRowAssetBundle.getInstance().mTaskInstruction.stop();
		this.onDestroy();
	}

	@Override
	public void onDestroy() {
		mAtlas.unload();
		this.detachChildren();
		this.detachSelf();
		setBuilt(false);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().setScene(LogicalRowIntroScene.INSTANCE);
	}
}