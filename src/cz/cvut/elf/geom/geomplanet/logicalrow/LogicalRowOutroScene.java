package cz.cvut.elf.geom.geomplanet.logicalrow;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
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

public class LogicalRowOutroScene extends BaseScene {

	public static final LogicalRowOutroScene INSTANCE = new LogicalRowOutroScene();

	private BuildableBitmapTextureAtlas mAtlas;

	private Background bg;
	private BlinkButton mButton;
	private Text mText;

	private LogicalRowOutroScene() {
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

		mButton = new BlinkButton(500, 100,
				mGlobalAssetBundle.mDefaultHighlightingAnim, this,
				LogicalRowByeByeScene.INSTANCE);

		mText = new Text(GameHolder.getInstance().mCamera.getCenterX(),
				GameHolder.getInstance().mCamera.getCenterY(),
				mGlobalAssetBundle.mDefaultFont,
				"Konec hry, party hard na poli.",
				GameHolder.getInstance().mVBOM);

		this.setBackground(bg);
		this.attachChild(mButton);
		this.attachChild(mText);
		mButton.animate(GameHolder.getInstance().ANIMATION_FRAME_DURATION);
	}
	
	@Override
	public void onStart() {
		onResume();
	}
	
	@Override
	public void onResume() {
		LogicalRowAssetBundle.getInstance().mTaskEnd.play();
	}
	
	@Override
	public void onPause() {
		LogicalRowAssetBundle.getInstance().mTaskEnd.pause();
	}
	
	@Override
	public void onStop() {
		LogicalRowAssetBundle.getInstance().mTaskEnd.stop();
		this.onDestroy();
	}


	@Override
	public void onDestroy() {
		mAtlas.unload();
		clearTouchAreas();
		this.detachChildren();
		this.detachSelf();
		setBuilt(false);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().setScene(GeomPlanetScene.INSTANCE);
	}
}
