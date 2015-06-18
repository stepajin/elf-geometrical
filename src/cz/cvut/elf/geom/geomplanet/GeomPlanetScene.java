package cz.cvut.elf.geom.geomplanet;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.general.GlobalAssetBundle;
import cz.cvut.elf.geom.general.controls.ActionButton;
import cz.cvut.elf.geom.geomplanet.entity.ElfNarrator;
import cz.cvut.elf.geom.geomplanet.logicalrow.LogicalRowIntroScene;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileTask;
import cz.cvut.elf.geom.geomplanet.sorting.SortingBanquetScene;
import cz.cvut.elf.geom.interfaces.ButtonAction;

public class GeomPlanetScene extends BaseScene {
	public static final GeomPlanetScene INSTANCE = new GeomPlanetScene();

	private BitmapTextureAtlas mBackgroundAtlas;

	private ActionButton mMrTileTask;
	private ActionButton mSortingTask;
	private ActionButton mLogicalRowTask;

	@Override
	public void onBuild() {
		/**
		 * Creating background
		 */
		mBackgroundAtlas = new BitmapTextureAtlas(
				GameHolder.getInstance().mActivity.getTextureManager(), 1280,
				768, TextureOptions.DEFAULT);
		TextureRegion bgRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBackgroundAtlas,
						GameHolder.getInstance().mActivity,
						"menu/planetmap.png", 0, 0);
		mBackgroundAtlas.load();
		setBackground(new SpriteBackground(new Sprite(
				GameHolder.getInstance().mCamera.getCenterX(),
				GameHolder.getInstance().mCamera.getCenterY(), bgRegion,
				GameHolder.getInstance().mVBOM)));

		mMrTileTask = new ActionButton(563, 255,
				GlobalAssetBundle.getInstance().mDefaultHighlightingAnim,
				new ButtonAction() {

					@Override
					public boolean execute() {
						// SceneManager.getInstance().setScene(MrTileIntroScene.INSTANCE);
						MrTileTask.getInstance().start();
						return false;
					}
				});
		attachChild(mMrTileTask);
		registerTouchArea(mMrTileTask);

		mSortingTask = new ActionButton(120, 87,
				GlobalAssetBundle.getInstance().mDefaultHighlightingAnim,
				new ButtonAction() {

					@Override
					public boolean execute() {
						SceneManager.getInstance().setScene(
								SortingBanquetScene.INSTANCE);
						return false;
					}
				});
		attachChild(mSortingTask);
		registerTouchArea(mSortingTask);

		mLogicalRowTask = new ActionButton(329, 170,
				GlobalAssetBundle.getInstance().mDefaultHighlightingAnim,
				new ButtonAction() {

					@Override
					public boolean execute() {
						SceneManager.getInstance().setScene(
								LogicalRowIntroScene.INSTANCE);
						return false;
					}
				});
		attachChild(mLogicalRowTask);
		registerTouchArea(mLogicalRowTask);

		ElfNarrator.getInstance().start();
		
		ActionButton mRocketButton = new ActionButton(745, 50,
				GlobalAssetBundle.getInstance().mDefaultHighlightingAnim,
				new ButtonAction() {

					@Override
					public boolean execute() {
						System.exit(0);
						return false;
					}
				});
		attachChild(mRocketButton);
		registerTouchArea(mRocketButton);
				
		this.mIsBuilt = true;
	}

	@Override
	public void onStart() {

		// long loading simulation
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.onStart();

		ElfNarrator.getInstance().onEvent("GEOM_PLANET_STORY");
	}

	@Override
	public void onResume() {
		// TODO ElfNarrator.getInstance().onSceneResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO ElfNarrator.getInstance().onScenePause();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}
}
