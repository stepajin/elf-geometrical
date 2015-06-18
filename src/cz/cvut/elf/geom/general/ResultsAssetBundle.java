package cz.cvut.elf.geom.general;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import cz.cvut.elf.geom.AssetBundle;
import cz.cvut.elf.geom.GameHolder;

public class ResultsAssetBundle extends AssetBundle {
	private static ResultsAssetBundle INSTANCE = new ResultsAssetBundle();

	private BitmapTextureAtlas mSortingAtlas, mLogicalRowAtlas, mMrTileAtlas;
	public TextureRegion mBackgroundSorting, mBackgroundLogicalRow, mBackgroundMrTile;

	private BitmapTextureAtlas mBottleAtlas;
	public TextureRegion mBottle;

	private BitmapTextureAtlas mManaAtlas, mSortingRewardAtlas, mLogicalRowRewardAtlas, mMrTileRewardAtlas;
	public TextureRegion mMana, mRewardSorting, mRewardLogicalRow, mRewardMrTile;

	private BitmapTextureAtlas mDefaultHighlightingAnimAtlas;
	public TiledTextureRegion mDefaultHighlightingAnim;

	//reward positions
	public final int[] mPos1 = new int[]{397,263};
	public final int[] mPos2 = new int[]{477,263};
	public final int[] mPos3 = new int[]{557,263};
	public final int[] mPos4 = new int[]{397,168};
	public final int[] mPos5 = new int[]{477,168};
	public final int[] mPos6 = new int[]{557,168};
	
	public final int[][] mPositions= {mPos1,mPos2,mPos3,mPos4,mPos5,mPos6,};
	
	public static ResultsAssetBundle getInstance() {
		return INSTANCE;
	}

	public ResultsAssetBundle() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("results/");

		mSortingAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 1024,
				1024);
		mBackgroundSorting = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mSortingAtlas, mActivity, "sorting.png", 0, 0);

		mLogicalRowAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 1024,
				1024);		
		mBackgroundLogicalRow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mLogicalRowAtlas, mActivity, "logicalRow.png", 0, 0);

		mMrTileAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 1024,
				1024);		
		mBackgroundMrTile = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mMrTileAtlas, mActivity, "mrTile.png", 0, 0);

		mBottleAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(),
				1024, 1024);
		mBottle = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mBottleAtlas, mActivity, "bottle.png", 0, 0);

		mManaAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(),
				1024, 1024);
		mMana = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mManaAtlas, mActivity, "mana.png", 0, 0);	

		mSortingRewardAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(),
				1024, 1024);
		mRewardSorting = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mSortingRewardAtlas, mActivity, "rewardSorting.png", 0, 0);	
		
		mLogicalRowRewardAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(),
				1024, 1024);	
		mRewardLogicalRow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mLogicalRowRewardAtlas, mActivity, "rewardLogicalRow.png", 0, 0);

		mMrTileRewardAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(),
				1024, 1024);	
		mRewardMrTile = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mMrTileRewardAtlas, mActivity, "rewardMrTile.png", 0, 0);
		

		mDefaultHighlightingAnimAtlas = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 550, 550, TextureOptions.DEFAULT);

		mDefaultHighlightingAnim = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mDefaultHighlightingAnimAtlas,
						GameHolder.getInstance().mActivity.getAssets(),
						"sparkle.png", 0, 0, 4, 4);

	}

	@Override
	public void load() {
		mBottleAtlas.load();
		mManaAtlas.load();
		mDefaultHighlightingAnimAtlas.load();
		
		mSortingAtlas.load();
		mLogicalRowAtlas.load();
		mMrTileAtlas.load();

		mSortingRewardAtlas.load();
		mLogicalRowRewardAtlas.load();
		mMrTileRewardAtlas.load();

		super.load();
	}

	@Override
	public void unload() {
		mBottleAtlas.unload();
		mManaAtlas.unload();
		mDefaultHighlightingAnimAtlas.unload();
		
		mSortingAtlas.unload();
		mLogicalRowAtlas.unload();
		mMrTileAtlas.unload();

		mSortingRewardAtlas.unload();
		mLogicalRowRewardAtlas.unload();
		mMrTileRewardAtlas.unload();

		super.unload();
	}
	
	public int getSortingReward(){
		//5 kol - max 15 spravnych odpovedi
		//15 spravne = 6 jablek
		//14-12 = 5
		//11-10 = 4
		//9-7 = 3
		//6-5 = 2
		//4-1 = 1
		int correct = GameHolder.getInstance().mUser.getTotal() - GameHolder.getInstance().mUser.getCurrentMistakes();
		if(correct == 15)
			return 6;
		else if(correct >=12)
			return 5;
		else if(correct >=10)
			return 4;
		else if(correct >=7)
			return 3;
		else if(correct >=5)
			return 2;
		else if(correct >=1)
			return 1;
		else			
			return 0;
	}

	public int getLogicalRowReward() {
		int attempts = GameHolder.getInstance().mUser.getTotal();
		//5 kol - max 5 spravnych odpovedi(vzdycky je 5 spravnych) ALE neomezene pokusu
		// max 1 chyba = 6	
		if(attempts < 7)
			return 6;
		if(attempts < 8)
			return 5;
		if(attempts < 9)
			return 4;
		if(attempts < 10) //4 chyby
			return 3;
		if(attempts < 12) //6
			return 2;
		if(attempts < 14) //8 chyb
			return 1;
		else
			return 0;
	}
	
	public int getMrTileReward(){
		int mistakes = GameHolder.getInstance().mUser.getCurrentMistakes();
		
		if(mistakes < 2)
			return 6;
		else if(mistakes < 3)
			return 5;
		else if(mistakes < 4)
			return 4;
		else if(mistakes < 5)
			return 3;
		else if(mistakes < 7)
			return 2;
		else if(mistakes < 10)
			return 1;
		else
			return 0;
	}
}
