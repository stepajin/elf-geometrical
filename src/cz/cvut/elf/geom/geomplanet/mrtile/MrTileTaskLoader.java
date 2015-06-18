package cz.cvut.elf.geom.geomplanet.mrtile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.entity.IEntity;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.xml.sax.Attributes;

import android.util.Log;
import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.GameTaskLoader;
import cz.cvut.elf.geom.User;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.RigidSquareTile;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.SquareGrid;
import cz.cvut.elf.geom.geomplanet.mrtile.entity.SquareTile;

public class MrTileTaskLoader extends GameTaskLoader {
	private static final String TAG_GRID = "grid";

	protected static final String TAG_ENTITY = "entity";
	protected static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	protected static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	protected static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";

	protected static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRID = "grid";
	protected static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM2 = "platform2";
	protected static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM3 = "platform3";
	protected static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_COIN = "coin";

	protected static final String TAG_GRID_ATTRIBUTE_X = "x";
	protected static final String TAG_GRID_ATTRIBUTE_Y = "y";
	protected static final String TAG_GRID_ATTRIBUTE_EDGESIZE = "edgesize";
	protected static final String TAG_GRID_ATTRIBUTE_SEGMENTS = "segments";

	protected static final String TAG_TASK_TILE = "tasktile";
	protected static final String TAG_GRID_ATTRIBUTE_SEGMENTX = "segmentx";
	protected static final String TAG_GRID_ATTRIBUTE_SEGMENTY = "segmenty";
	protected static final String TAG_GRID_ATTRIBUTE_TILE_TYPE = "tiletype";

	/**
	 * Tiles are put to scene one next to another. The shift is made by
	 * multiplying by this counter;
	 */
	protected int mTileCnt = 0;

	/**
	 * Grid and taskGrid are main component of game scene. In taskGrid there is
	 * a pattern how the image should look like. User put tiles to grid to build
	 * same pattern.
	 */
	private SquareGrid mGrid;
	private SquareGrid mTaskGrid;
	private boolean mOnRight = true;
	private MrTileTaskScene mScene;
	private boolean mIsLoaded = false;

	public MrTileTaskLoader(MrTileTaskScene pScene,
			VertexBufferObjectManager pVBOM) {
		super(pScene, pVBOM);
		mScene = pScene;
	}

	@Override
	public void loadLevel(int pLevelID) {
		mTileCnt = 0;

		if (mIsLoaded) {
			unloadLevel();
		}

		mLevelLoader
				.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
						LevelConstants.TAG_LEVEL) {
					@Override
					public IEntity onLoadEntity(
							final String pEntityName,
							final IEntity pParent,
							final Attributes pAttributes,
							final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
							throws IOException {
						return MrTileTaskLoader.this.mScene;
					}
				});

		mLevelLoader
				.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
						TAG_GRID) {
					public IEntity onLoadEntity(
							final String pEntityName,
							final IEntity pParent,
							final Attributes pAttributes,
							final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
							throws IOException {
						final int x = SAXUtils.getIntAttributeOrThrow(
								pAttributes, TAG_GRID_ATTRIBUTE_X);
						final int y = SAXUtils.getIntAttributeOrThrow(
								pAttributes, TAG_GRID_ATTRIBUTE_Y);
						final float edgesize = SAXUtils.getIntAttributeOrThrow(
								pAttributes, TAG_GRID_ATTRIBUTE_EDGESIZE);
						final int segments = SAXUtils.getIntAttributeOrThrow(
								pAttributes, TAG_GRID_ATTRIBUTE_SEGMENTS);

						/**
						 * Initializing both mGrid and mTaskGrid
						 * 
						 */
						MrTileTaskLoader.this.mTaskGrid = new SquareGrid(x, y,
								edgesize, segments, MrTileAssetBundle
										.getInstance().mWhiteDeskTR,
								MrTileAssetBundle.getInstance().mWhiteDeskTR,// MrTileAssetBundle.getInstance().mBlackDeskTR,
								mScene);
						MrTileTaskLoader.this.mGrid = new SquareGrid(x, y,
								edgesize, segments, MrTileAssetBundle
										.getInstance().mWhiteDeskTR,
								MrTileAssetBundle.getInstance().mWhiteDeskTR,// MrTileAssetBundle.getInstance().mBlackDeskTR,
								mScene);

						float centerX = GameHolder.getInstance().mCamera
								.getCenterX();
						float centerY = GameHolder.getInstance().mCamera
								.getCenterY();

						MrTileTaskLoader.this.mTaskGrid.setPosition(centerX - 3
								- edgesize / 2, centerY);
						MrTileTaskLoader.this.mGrid.setPosition(centerX + 3
								+ edgesize / 2, centerY);

						return null;
					}
				});

		mLevelLoader
				.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
						TAG_TASK_TILE) {
					public IEntity onLoadEntity(
							final String pEntityName,
							final IEntity pParent,
							final Attributes pAttributes,
							final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
							throws IOException {
						final int segmentX = SAXUtils.getIntAttributeOrThrow(
								pAttributes, TAG_GRID_ATTRIBUTE_SEGMENTX);
						final int segmentY = SAXUtils.getIntAttributeOrThrow(
								pAttributes, TAG_GRID_ATTRIBUTE_SEGMENTY);
						final int tileTypeNumber = SAXUtils
								.getIntAttributeOrThrow(pAttributes,
										TAG_GRID_ATTRIBUTE_TILE_TYPE);
						final RigidSquareTile taskTile;
						TextureRegion TR;
						String tileType;

						/**
						 * Choosing appropriate type of tile depending on loaded
						 * number tileTypeNumber.
						 */
						switch (tileTypeNumber) {
						case 0:
							TR = MrTileAssetBundle.getInstance().mBlueTileTR;
							tileType = "Blue";
							break;
						case 1:
							TR = MrTileAssetBundle.getInstance().mGreenTileTR;
							tileType = "Green";
							break;
						case 2:
							TR = MrTileAssetBundle.getInstance().mPinkTileTR;
							tileType = "Pink";
							break;
						case 3:
							TR = MrTileAssetBundle.getInstance().mBlueShadeTileTR;
							tileType = "BlueShade";
							break;
						case 4:
							TR = MrTileAssetBundle.getInstance().mPinkShadeTileTR;
							tileType = "PinkShade";
							break;
						case 5:
							TR = MrTileAssetBundle.getInstance().mBlueWhitePatternTileTR;
							tileType = "BlueWhitePattern";
							break;
						case 6:
							TR = MrTileAssetBundle.getInstance().mGreenOrangePatternTileTR;
							tileType = "GreenOrangePattern";
							break;
						case 7:
							TR = MrTileAssetBundle.getInstance().mVioletGreenPatternTR;
							tileType = "VioletGreenPattern";
							break;
						case 8:
							TR = MrTileAssetBundle.getInstance().mVioletOrangePatternTileTR;
							tileType = "VioletOrangePattern";
							break;
						case 9:
							TR = MrTileAssetBundle.getInstance().mVioletWhitePatternTileTR;
							tileType = "VioletWhitePattern";
							break;
						default:
							TR = MrTileAssetBundle.getInstance().mBlueTileTR;
							tileType = "BlueTile";
						}

						taskTile = new RigidSquareTile(0, 0,
								MrTileTaskLoader.this.mTaskGrid, tileType, TR);
						MrTileTaskLoader.this.mTaskGrid.putTileToGridToSegment(
								segmentX, segmentY, taskTile);

						final SquareTile tile = new SquareTile(
								getXForNextNewTile(),
								MrTileTaskLoader.this.mGrid.getTileSize() / 2 + 5,
								MrTileTaskLoader.this.mGrid, tileType, TR);

						MrTileTaskLoader.this.mScene.attachChild(tile);
						mLoadedEntities.add(tile);
						MrTileTaskLoader.this.mScene.registerTouchArea(tile);
						mLoadedEntities.add(taskTile);
						return taskTile;
					}
				});

		String levelAddress = "mrtile/level/"
				+ GameHolder.getInstance().mUser.getCurrentSkillType() + "/"
				+ pLevelID + ".lvl";
		Log.d("lvl addr", levelAddress);
		mLevelLoader.loadLevelFromAsset(
				GameHolder.getInstance().mActivity.getAssets(), levelAddress);
		mIsLoaded = true;
	}

	/**
	 * While unloading the level we have to detach objects from the scene and
	 * unregester touch areas.
	 */
	@Override
	public void unloadLevel() {
		if(mIsLoaded){
			for (Iterator<IEntity> iterator = this.mLoadedEntities.iterator(); iterator
					.hasNext();) {
				IEntity entity = iterator.next();
				this.mScene.detachChild(entity);
				this.mScene.unregisterTouchArea(entity);
				iterator.remove();
	
			}
			mGrid.getBackgroundGrid().detachSegments();
			mTaskGrid.getBackgroundGrid().detachSegments();
			mIsLoaded=false;
		}
	}

	@Override
	public boolean isTaskCompleted() {
		if (this.mGrid == null || this.mTaskGrid == null) {
			return false;
		}
		if (this.mGrid.getTilesCnt() != this.mTaskGrid.getTilesCnt()) {
			return false;
		}
		this.mScene.onTaskCompleted(mGrid.countDifferenceInGrid(mTaskGrid), mTileCnt);
		return true;
	}

	public SquareGrid getGrid() {
		return mGrid;
	}

	public SquareGrid getTaskGrid() {
		return mTaskGrid;
	}

	public float getXForNextNewTile() {
		float tmpX = GameHolder.getInstance().mCamera.getCenterX();
		mTileCnt++;
		if (mOnRight) {
			tmpX += (mTileCnt / 2)
					* (MrTileTaskLoader.this.mGrid.getTileSize() + 5);
		} else {
			tmpX -= (mTileCnt / 2)
					* (MrTileTaskLoader.this.mGrid.getTileSize() + 5);
		}
		mOnRight = !mOnRight;
		return tmpX;
	}
}
