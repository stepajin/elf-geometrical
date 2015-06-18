package cz.cvut.elf.geom.geomplanet.sorting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import android.view.MotionEvent;
import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.ElfActivity;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.geomplanet.sorting.entity.MrTimTaskCharacter;

public class Level {
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";

	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATE1 = "plate1";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATE2 = "plate2";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATE3 = "plate3";

	private int mCount = 3;
	private SortingAssetBundle mBundle;
	private BaseScene mScene;
	private int mLevelID;
	private MrTimTaskCharacter mMrTim;

	private List<Sprite> mObjectsToSort;
	private List<Sprite> mSortPlaces;
	private int[] mResults;
	private int[] mPlacesOccupied;
	private Boolean mComplete;
	
	private static final Random random = new Random();

	public Level(SortingAssetBundle bundle, BaseScene scene,
			MrTimTaskCharacter pMrTim) {
		this.mBundle = bundle;
		this.mScene = scene;
		this.mMrTim = pMrTim;

		mObjectsToSort = new ArrayList<Sprite>();
		mSortPlaces = new ArrayList<Sprite>();
		
		mResults = new int[mCount];
		mPlacesOccupied = new int[mCount];

		if (GameHolder.getInstance().mUser.getCurrentSkillType().toString() == "SKILL_BEGINNER") {
			mLevelID = 1;
		}

		else if (GameHolder.getInstance().mUser.getCurrentSkillType()
				.toString() == "SKILL_INTERMEDIATE") {
			mLevelID = 2;
		}

		else {
			mLevelID = 3;
		}

	}

	public void loadLevel() throws IOException {
		
		//pozadi
		mScene.attachChild(new Sprite(ElfActivity.CAMERA_WIDTH / 2,
				ElfActivity.CAMERA_HEIGHT / 2, mBundle.mBackground, GameHolder
						.getInstance().mVBOM));
		
		for(int i = 0; i<mCount; i++){
			mResults[i] = -1;
			mPlacesOccupied[i] = -1;
		}
		
		mComplete = false;
		
		// level loader je tu skoro zbytecnej
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(
				GameHolder.getInstance().mVBOM);

		levelLoader
				.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
						LevelConstants.TAG_LEVEL) {
					public IEntity onLoadEntity(
							final String pEntityName,
							final IEntity pParent,
							final Attributes pAttributes,
							final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
							throws IOException {
						mCount = SAXUtils.getIntAttributeOrThrow(pAttributes,
								LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
						return SortingTaskScene.INSTANCE;
					}
				});

		levelLoader
				.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
						TAG_ENTITY) {
					public IEntity onLoadEntity(
							final String pEntityName,
							final IEntity pParent,
							final Attributes pAttributes,
							final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
							throws IOException {
						final int x = SAXUtils.getIntAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_X);
						final int y = SAXUtils.getIntAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
						final String type = SAXUtils.getAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);

						final Sprite levelObject;

						if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATE1)) {
							levelObject = new Sprite(x, y, mBundle.mPlate,
									GameHolder.getInstance().mVBOM);
							levelObject.setScale(0.2f);
							mSortPlaces.add(levelObject);
						} else if (type
								.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATE2)) {
							levelObject = new Sprite(x, y, mBundle.mPlate,
									GameHolder.getInstance().mVBOM);
							levelObject.setScale(0.35f);
							mSortPlaces.add(levelObject);
						} else if (type
								.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATE3)) {
							levelObject = new Sprite(x, y, mBundle.mPlate,
									GameHolder.getInstance().mVBOM);
							levelObject.setScale(0.5f);
							mSortPlaces.add(levelObject);
						}

						// pripadne pridat dalsi talire

						else {
							throw new IllegalArgumentException();
						}

						levelObject.setCullingEnabled(true);

						return levelObject;
					}
				});

		String levelAddress = "sorting/level/" + "SKILL_BEGINNER" + "/" + "1"
				+ ".lvl";
		levelLoader.loadLevelFromAsset(
				GameHolder.getInstance().mActivity.getAssets(), levelAddress);

		// naloadovat random entitu k razeni
		// je potreba asi nekde vytvorit enum s jednotlivymi jidly, podle levelu
		System.out.println("talire vytvoreny " + mSortPlaces.size());
		System.out.println("vytvareni hracich objektu, skill " + mLevelID);

		ObjectType type;
		ITexture texture;

		switch (mLevelID) {
		case (1): // level 1
			type = ObjectType.getRandomTypeLevel1();
			break;
		case (2): // level 2
			type = ObjectType.getRandomTypeLevel2();
			break;
		default: // level 3
			type = ObjectType.getRandomTypeLevel3();
			break;
		}

		for (int i = 0; i < mCount; i++) {
			// nacteni textury
			texture = createTexture(type.getImages().get(i));
			texture.load();
			ITextureRegion textureRegion = TextureRegionFactory
					.extractFromTexture(texture);

			// vytvoreni sprite na nahodnem miste
			int x = getRandomInt(400) + 100;
			int y = getRandomInt(180) + 250;
			System.out.println("souradnice " + x + "  " + y);
			
			mObjectsToSort.add(createSprite(textureRegion, x, y, i));

			// pokud je to level1, jeste upravit scale objektu
			if (mLevelID == 1) {
				mObjectsToSort.get(i).setScale(
						(float) (0.3 + ((float) i * 0.3)));
			}

		}

		System.out.println("objekty vytvoreny " + mObjectsToSort.size());
		// registrovani objektu na scenu
		for (int i = 0; i < mCount; i++) {
			mScene.attachChild(mObjectsToSort.get(i));
			mScene.registerTouchArea(mObjectsToSort.get(i));
		}
	}

	public void unloadLevel() {
		mObjectsToSort.clear();
		System.out.println("cleared, " + mObjectsToSort.size());
		mSortPlaces.clear();
		mScene.detachChildren();
		mScene.clearTouchAreas();
	}

	public void restartLevel() {
		this.unloadLevel();
		try {
			this.loadLevel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Sprite createSprite(ITextureRegion texture, float posX, float posY,
			final int pPos) {
		Sprite s = new Sprite(posX, posY, texture,
				GameHolder.getInstance().mVBOM) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case MotionEvent.ACTION_DOWN:
					this.setZIndex(1);
					this.getParent().sortChildren();
					break;
				case MotionEvent.ACTION_MOVE:
					this.setPosition(pSceneTouchEvent.getX(),
							pSceneTouchEvent.getY());
					break;
				case MotionEvent.ACTION_UP:
					checkCollision(this, pPos);
					break;
				}
				return true;
			}

		};
		return s;
	}

	private void checkComplete() {
		System.out.println("checkComplete");
		int collisions = 0;
		for(int i = 0; i<mCount; i++){
			
			for(int j = 0; j<mCount; j++){
				
				System.out.println("kontrola " + i + " x " + j);
				if(mObjectsToSort.get(i).collidesWith(mSortPlaces.get(j))){
					System.out.println("kolize");
					collisions++;
					break;
				}
				else{
					System.out.println("nekolize");					
				}
			}
		}
		System.out.println("pocet kolizi " + collisions);
		if(collisions == mCount){
			mComplete = true;
		}
	}

	public int checkResults() {
		// kontrolni vypis
		for (int i = 0; i < mCount; i++) {
			System.out.println(i + " - " + mResults[i]);
		}

		int mistakes = 0;
		for (int i = 0; i < mCount; i++) {
			System.out.println("nr " + i);
			if (mResults[i] != i) {
				mistakes++; // protoze je to vzestupne serazeny podle veliksoti
			}
		}

		System.out.println("mistakes: " + mistakes);
		return mistakes;

	}

	private void checkCollision(Sprite pSprite, int pPos) {
		System.out.println("check collision");		
		for (int i = 0; i < mCount; i++) { // sprite je na pozici pPos
			if (pSprite.collidesWith(mSortPlaces.get(i))) {
				System.out.println("kolize  " + pPos + " x " + i);
				
				mResults[pPos] = i;
				//mPlacesOccupied[i] = pPos;
				
				if (pPos == i) {
					// play spravny sound
					mMrTim.onEvent("GOOD_JOB");
				} else {
					// spatne sound
					mMrTim.onEvent("BAD_JOB");
				}
				checkComplete();
				break;
			}
		}
	}

	protected static int getRandomInt(int max) {
		return random.nextInt(max);
	}

	private ITexture createTexture(String adress) throws IOException {
		return new AssetBitmapTexture(
				GameHolder.getInstance().mActivity.getTextureManager(),
				GameHolder.getInstance().mActivity.getAssets(), adress,
				TextureOptions.BILINEAR);
	}
	
	public Boolean getComplete(){
		return mComplete;
	}
}
