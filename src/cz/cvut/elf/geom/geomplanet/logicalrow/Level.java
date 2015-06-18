package cz.cvut.elf.geom.geomplanet.logicalrow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import android.graphics.PointF;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.User;

/**
 * 
 * @author Jindrich Stepanek
 * 
 * Main purpose of this class is to load level from XML file.
 * Level is in XML described just by different numbers for each different object 
 * and then the specific objects are generated randomly.
 * By this construction we can generate much more permutations of objects in one level.
 *
 *
 ***/

public class Level {
	
	private static final String TAG_ENTITY = "bead";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
	private static final String TAG_ENTITY_ATTRIBUTE_KNOWN = "known";
	private static final int MAX_LEVEL_LENGTH = 9;
	private static final int MIN_LEVEL_LENGTH = 4;
	private static final int LEVEL_LENGTHS = MAX_LEVEL_LENGTH - MIN_LEVEL_LENGTH + 1;
	
	private static final Beetle.BEETLE_TYPE[] ROW_OBJECT_TYPE_VALUES = Beetle.BEETLE_TYPE.values();
	public static final float FIBRE_X = 480f;
	public static final float FIBRE_Y = 240f;
	public static final int FIBRE_W = 300;
	public static final int FIBRE_H = 50;
	public static final PointF POSITIONS[][] = new PointF[LEVEL_LENGTHS][];
	public static final float BEAD_SCALES[] = new float[LEVEL_LENGTHS];
	
	/*********************
	 *
	 * Instance variables
	 * 
	 *********************/
	
	private List<Beetle> mBeetles = new ArrayList<Beetle>();
	private List<QuestionMark> mQuestionMarks = new ArrayList<QuestionMark>();

	private List<Beetle.BEETLE_TYPE> mUsedBeetleTypes = new ArrayList<Beetle.BEETLE_TYPE>(); 
	private boolean isRowObjectTypeUsed[] = new boolean[ROW_OBJECT_TYPE_VALUES.length];

	private int mRowObjectsCnt = 0;
	private int mNumberOfPositions;
	
	/*****************
	 * 
	 * Initialization 
	 *
	 ******************/
	
	static {
		for (int i = 0; i < LEVEL_LENGTHS; i++)
			POSITIONS[i] = new PointF[i+MIN_LEVEL_LENGTH];
	
		float width = GameHolder.getInstance().mCamera.getWidth();
		float height = GameHolder.getInstance().mCamera.getHeight();

		//TODO FUNGUJE PRO SEDM POZIC, ZBYTEK DODELAT
		
		/* 4 positions */
		POSITIONS[0][0] = new PointF(width/5, height*2/5);
		POSITIONS[0][1] = new PointF(width*2/5, height*4/11);
		POSITIONS[0][2] = new PointF(width*3/5, height*4/11);
		POSITIONS[0][3] = new PointF(width*4/5, height*2/5);
		BEAD_SCALES[0] = 0.6f;
		
		/* 5 positions */
		POSITIONS[1][0] = new PointF(FIBRE_X - FIBRE_W * 2/3, FIBRE_Y);
		POSITIONS[1][1] = new PointF(FIBRE_X - FIBRE_W * 1/3, FIBRE_Y - FIBRE_H * 1/3);
		POSITIONS[1][2] =  new PointF(FIBRE_X, FIBRE_Y - FIBRE_H * 2/3);
		POSITIONS[1][3] = new PointF(FIBRE_X + FIBRE_W * 1/3, FIBRE_Y - FIBRE_H * 1/3);
		POSITIONS[1][4] = new PointF(FIBRE_X + FIBRE_W * 2/3, FIBRE_Y);
		BEAD_SCALES[1] = 0.3f;

		/* 6 positions */
		POSITIONS[2][0] = new PointF(FIBRE_X - FIBRE_W * 0.8f, FIBRE_Y);
		POSITIONS[2][1] = new PointF(FIBRE_X - FIBRE_W * 0.4f, FIBRE_Y - FIBRE_H * 1/3);
		POSITIONS[2][2] =  new PointF(FIBRE_X - FIBRE_W * 0.12f, FIBRE_Y - FIBRE_H * 2/3);
		POSITIONS[2][3] =  new PointF(FIBRE_X + FIBRE_W * 0.12f, FIBRE_Y - FIBRE_H * 2/3);
		POSITIONS[2][4] = new PointF(FIBRE_X + FIBRE_W * 0.4f, FIBRE_Y - FIBRE_H * 1/3);
		POSITIONS[2][5] = new PointF(FIBRE_X + FIBRE_W * 0.8f, FIBRE_Y);
		BEAD_SCALES[2] = 0.3f;

		/* 7 positions */
		POSITIONS[3][0] = new PointF(FIBRE_X - FIBRE_W * 0.85f, FIBRE_Y - FIBRE_H * 1/3);
		POSITIONS[3][1] = new PointF(FIBRE_X - FIBRE_W * 0.6f, FIBRE_Y - FIBRE_H * 2/3);
		POSITIONS[3][2] =  new PointF(FIBRE_X - FIBRE_W * 0.3f, FIBRE_Y - FIBRE_H * 0.9f);
		POSITIONS[3][3] =  new PointF(FIBRE_X, FIBRE_Y - FIBRE_H * 3/3);
		POSITIONS[3][4] =  new PointF(FIBRE_X + FIBRE_W * 0.3f, FIBRE_Y - FIBRE_H * 2/3);
		POSITIONS[3][5] = new PointF(FIBRE_X + FIBRE_W * 0.6f, FIBRE_Y - FIBRE_H * 1/3);
		POSITIONS[3][6] = new PointF(FIBRE_X + FIBRE_W * 0.85f, FIBRE_Y + FIBRE_H * 0.2f);
		BEAD_SCALES[3] = 0.25f;

		/* 8 positions */
		POSITIONS[4][0] = new PointF(FIBRE_X - FIBRE_W * 0.85f, FIBRE_Y - FIBRE_H * 1/3);
		POSITIONS[4][1] = new PointF(FIBRE_X - FIBRE_W * 0.6f, FIBRE_Y - FIBRE_H * 2/3);
		POSITIONS[4][2] =  new PointF(FIBRE_X - FIBRE_W * 0.3f, FIBRE_Y - FIBRE_H * 3/3);
		POSITIONS[4][3] =  new PointF(FIBRE_X - FIBRE_W * 0.1f, FIBRE_Y - FIBRE_H * 4/3);
		POSITIONS[4][4] =  new PointF(FIBRE_X + FIBRE_W * 0.1f, FIBRE_Y - FIBRE_H * 3/3);
		POSITIONS[4][5] = new PointF(FIBRE_X + FIBRE_W * 0.3f, FIBRE_Y - FIBRE_H * 3/3);
		POSITIONS[4][6] = new PointF(FIBRE_X + FIBRE_W * 0.6f, FIBRE_Y -  FIBRE_H * 2/3);
		POSITIONS[4][7] = new PointF(FIBRE_X + FIBRE_W * 0.85f, FIBRE_Y - FIBRE_H * 1/3);
		BEAD_SCALES[4] = 0.25f;

		/* 9 positions */
		POSITIONS[5][0] = new PointF(width*1/10, height*0.44f);
		POSITIONS[5][1] = new PointF(width*2/10, height*0.40f);
		POSITIONS[5][2] = new PointF(width*3/10, height*0.38f);
		POSITIONS[5][3] = new PointF(width*4/10, height*0.36f);
		POSITIONS[5][4] = new PointF(width*5/10, height*0.35f);
		POSITIONS[5][5] = new PointF(width*6/10, height*0.36f);
		POSITIONS[5][6] = new PointF(width*7/10, height*0.38f);
		POSITIONS[5][7] = new PointF(width*8/10, height*0.40f);
		POSITIONS[5][8] = new PointF(width*9/10, height*0.44f);
		BEAD_SCALES[5] = 0.2f;
	}
	
	/******************
	 * 
	 * Static getters
	 *
	 ******************/
	
	public static PointF[] getPositions(int numberOfPositions) {
		if (numberOfPositions > MAX_LEVEL_LENGTH || numberOfPositions < MIN_LEVEL_LENGTH)
			return POSITIONS[0];
		return POSITIONS[numberOfPositions-MIN_LEVEL_LENGTH];
	}
	
	public static PointF getPosition(int numberOfPositions, int positionNumber) {
		return getPositions(numberOfPositions)[positionNumber];
	}
	
	public static float getRowObjectScale(int numberOfPositions) {
		if (numberOfPositions > MAX_LEVEL_LENGTH || numberOfPositions < MIN_LEVEL_LENGTH)
			return BEAD_SCALES[0];
		return BEAD_SCALES[numberOfPositions-MIN_LEVEL_LENGTH];
	}
	
	/******************
	 * 
	 * Instance logic
	 *
	 *******************/
	
	public void loadLevel(int pLevelId) {
		final VertexBufferObjectManager vbom = GameHolder.getInstance().mVBOM;
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(vbom);
	    
		/******************************
		 * Parsing number of positions
		 ******************************/
	    levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL)
	    {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException 
	        {
	        	mNumberOfPositions = 
	        			SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
	        	return LogicalRowTaskScene.INSTANCE;
	        }
	    });

	    /**********************
	     * Parsing logical row
	     **********************/
	    levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY)
	    {
	        public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
	        {
	        	final int rowObjectTypeNumber = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
	        	final boolean known = SAXUtils.getBooleanAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_KNOWN);
	           	Beetle.BEETLE_TYPE rowObjectType = null;  /* needs to initialized */
	            
	            if (rowObjectTypeNumber < 0 || rowObjectTypeNumber > mUsedBeetleTypes.size() + 1) {
		            /* invalid attribute */
	            	System.out.println("Chybny xml level");
	            	return null;
	            		
	            } else if (rowObjectTypeNumber == mUsedBeetleTypes.size() + 1) {
		        	/* new unknown bead type */
	            	rowObjectType = getRandomUnusedBeetleType();
	            	mUsedBeetleTypes.add(rowObjectType);
      	
	            } else  {
	            	/* already used row object type */	
	            	rowObjectType = getBeetleType(rowObjectTypeNumber);
	            }

            	PointF position = getPosition(mNumberOfPositions, mRowObjectsCnt);

	           	final Sprite sprite;

	            if (known) {
	            	/* attach beetle */
	            	Beetle beetle = new Beetle(rowObjectType, position.x, position.y, 
	            			LogicalRowAssetBundle.getInstance().mBeetleTextureRegions[Beetle.getCode(rowObjectType)]);
	            	beetle.setState(Beetle.STATE.OK);
	            	mBeetles.add(beetle);
	            		
	            	beetle.flipVertical();
	            	beetle.setScale(getRowObjectScale(mNumberOfPositions));
	            	beetle.catchFibre();
	            	sprite = beetle;
	            } else {
	            	/* attach question mark */
	            	QuestionMark q = new QuestionMark(rowObjectType, position.x, position.y, 
	            		LogicalRowAssetBundle.getInstance().mQuestionMarkRegion);
	            	mQuestionMarks.add(q);
		            q.setScale(getRowObjectScale(mNumberOfPositions));
	            	sprite = q;
	            }
	        
	            mRowObjectsCnt++;
	            return sprite;
	        }
	    });

	    /******************
	     * Load level file
	     ******************/
	    User.SkillType skillType = GameHolder.getInstance().mUser.getCurrentSkillType();
	    String levelAddress = "logicalrow/level/" + skillType.toString() + "/" + pLevelId + ".lvl";
	    levelLoader.loadLevelFromAsset(GameHolder.getInstance().mActivity.getAssets(), levelAddress);
	   
	    /* If level contains just one entity type, we need to add one more type to used entity types
	     * so menu won't contain just one type */
	    if (mUsedBeetleTypes.size() == 1) {
	    	mUsedBeetleTypes.add(getRandomUnusedBeetleType());
	    }
	}
	
	public void unloadLevel() {
		GameHolder.getInstance().mEngine.runOnUpdateThread(new Runnable() {
	        @Override
	        public void run() {
	        	for (Beetle r : mBeetles) {
	        		r.detachSelf();
	        	}
	        	
	        	for (QuestionMark q : mQuestionMarks) {
	        		q.detachSelf();
	        	}
        	}
	    });
	}
	
	private Beetle.BEETLE_TYPE getBeetleType(int pBeetleTypeNumber) {
		if (pBeetleTypeNumber-1 >= mUsedBeetleTypes.size()) {
			return mUsedBeetleTypes.get(0);
		}
		return mUsedBeetleTypes.get(pBeetleTypeNumber-1);
	}
	
	private Beetle.BEETLE_TYPE getRandomUnusedBeetleType() {
		Random random = new Random();
		int i = random.nextInt(ROW_OBJECT_TYPE_VALUES.length);
		while (true) {
			if (!isRowObjectTypeUsed[i]) {
				isRowObjectTypeUsed[i] = true;
				return ROW_OBJECT_TYPE_VALUES[i];
			}
			i = (i+1) % ROW_OBJECT_TYPE_VALUES.length;
		}
	}

	/************
	 *	
	 * Getters 
	 *
	 *-**********/
	
	public List<Beetle> getRowBeetles() {
		return mBeetles;
	}

	public List<Beetle.BEETLE_TYPE> getUsedBeetleTypes() {
		return mUsedBeetleTypes;
	}

	public List<QuestionMark> getQuestionMarks() {
		return mQuestionMarks;
	}

	public int getNumberOfPositions() {
		return mNumberOfPositions;
	}
}
