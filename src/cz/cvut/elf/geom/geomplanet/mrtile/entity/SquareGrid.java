package cz.cvut.elf.geom.geomplanet.mrtile.entity;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.IRectangleVertexBufferObject;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileTaskScene;
import android.util.Log;

/**
 * 
 * @author Tom
 * 
 *         Structure representing grid game object. It is basic canvas, where
 *         tiles will be attached. It has got some number of segments.
 */
public class SquareGrid {
	private ChessGrid mBackgroundGrid;
	private RigidSquareTile[][] mTileArray;
	private int mTilesCnt = 0;
	private MrTileTaskScene mScene;

	public SquareGrid(float pX, float pY, float pEdgeSize, int pSegments,
			TextureRegion pWhiteTR, TextureRegion pBlackTR, MrTileTaskScene pScene) {
		mBackgroundGrid = new ChessGrid(pX, pY, pEdgeSize, pSegments, pWhiteTR,
				pBlackTR);
		this.mTileArray = new RigidSquareTile[pSegments][pSegments];
		this.mScene = pScene;
		mBackgroundGrid.attachSegmentsToScene(mScene);
	}

	/**
	 * Setting tile to some segment in grid.
	 * 
	 * @param segmentX
	 * @param segmentY
	 * @param pTile
	 */
	public void putTileToGridToSegment(int segmentX, int segmentY,
			RigidSquareTile pTile) {
		/**
		 * Default coordinates if tile can't be put to some place in grid (when
		 * there's a collision with other tile)
		 */
		float newX = mBackgroundGrid.getBottomLeftCornerX() + mBackgroundGrid.getWidth()
				+ (2 * mBackgroundGrid.getSegmentSize() / 3);
		float newY = mBackgroundGrid.getBottomLeftCornerY() + (mBackgroundGrid.getSegmentSize() / 2)
				+ (2 * mBackgroundGrid.getSegmentSize() / 3);
		/**
		 * If tile position is behind grid border, it will put the tile to the
		 * last segment of the grid.
		 */
		if (segmentX >= mBackgroundGrid.getSegments()) {
			segmentX = mBackgroundGrid.getSegments() - 1;
		} else if (segmentX < 0) {
			segmentX = 0;
		}
		if (segmentY >= mBackgroundGrid.getSegments()) {
			segmentY = mBackgroundGrid.getSegments() - 1;
		} else if (segmentY < 0) {
			segmentY = 0;
		}
		/**
		 * If there is a free place in grid, tile can be placed there. Otherwise
		 * it will use default positioning out of the grid.
		 */
		if (addToTileArray(segmentX, segmentY, pTile)) {
			/**
			 * New position coordinate X is the center of the segment.
			 */
			newX = (segmentX * mBackgroundGrid.getSegmentSize()) + (mBackgroundGrid.getSegmentSize() / 2)
					+ mBackgroundGrid.getBottomLeftCornerX();
			newY = (segmentY * mBackgroundGrid.getSegmentSize()) + (mBackgroundGrid.getSegmentSize() / 2)
					+ mBackgroundGrid.getBottomLeftCornerY();
		} else {
			pTile.setInGrid(false);

		}
		pTile.setX(newX);
		pTile.setY(newY);
	}

	/**
	 * Tile placed somewhere in space, method puts tile to some grid segment
	 * according to its coordinates.
	 * 
	 * @param pTile
	 */
	public void putTileToGrid(RigidSquareTile pTile) {
		/**
		 * From pX of tile and bottomLeftCorner of Grid we can get distance
		 * between these two points.
		 */
		float distanceX = Math.abs(mBackgroundGrid.getBottomLeftCornerX() - pTile.getX());
		float distanceY = Math.abs(mBackgroundGrid.getBottomLeftCornerY() - pTile.getY());
		/**
		 * Grid in one row has some segments, we count which segment is the
		 * closest one to tile.
		 */
		int segmentX = (int) (distanceX / mBackgroundGrid.getSegmentSize());
		int segmentY = (int) (distanceY / mBackgroundGrid.getSegmentSize());
		putTileToGridToSegment(segmentX, segmentY, pTile);
	}

	/**
	 * Internal method for adding tile to grid's tileArray.
	 * 
	 * @param segmentX
	 * @param segmentY
	 * @param pTile
	 * @return false if there was other tile in the segment
	 */
	protected boolean addToTileArray(int segmentX, int segmentY,
			RigidSquareTile pTile) {
		if (pTile == null) {
			this.mTileArray[segmentX][segmentY] = null;
			return true;
		} else {
			if (pTile.isInGrid()) {
				/**
				 * The tile is already in the right segment.
				 */
				if (pTile.getSegmentX() == segmentX
						&& pTile.getSegmentY() == segmentY) {
					return true;
				}
				/**
				 * If the tile is in the grid already, we have to put null value
				 * to the segment, which will be left.
				 */
				this.mTileArray[pTile.getSegmentX()][pTile.getSegmentY()] = null;
			}
			/**
			 * If segment is free, we can put there the tile.
			 */
			if (this.mTileArray[segmentX][segmentY] == null) {
				this.mTileArray[segmentX][segmentY] = pTile;
				pTile.setInGrid(true);
				pTile.setSegmentX(segmentX);
				pTile.setSegmentY(segmentY);
				return true;
			}
			return false;
		}
	}

	/**
	 * @param segmentX
	 * @param segmentY
	 * @return null or SquareTile
	 */
	public RigidSquareTile getTileFromSegment(int segmentX, int segmentY) {
		if (segmentX < 0 || segmentY < 0 || segmentX >= mBackgroundGrid.getSegments()
				|| segmentY >= mBackgroundGrid.getSegments()) {
			return null;
		} else {
			return this.mTileArray[segmentX][segmentY];
		}
	}

	/**
	 * Controlling if pattern on other grid is the same on the other one. Counting how many differences are there.
	 * 
	 * @param otherGrid
	 * @return int number of differences
	 */
	public int countDifferenceInGrid(SquareGrid otherGrid) {
		int cntCorrect=0;
		if (this.getSegments() == otherGrid.getSegments()) {
			for (int i = 0; i < this.getSegments(); i++) {
				for (int j = 0; j < this.getSegments(); j++) {
					if (this.getTileFromSegment(i, j) == null
							&& otherGrid.getTileFromSegment(i, j) == null) {
					} else if (this.getTileFromSegment(i, j) == null
							|| otherGrid.getTileFromSegment(i, j) == null) {
						//Not both are null, so evidently there is something wrong
					} else if (this.getTileFromSegment(i, j).equals(
							otherGrid.getTileFromSegment(i, j))) {
						cntCorrect++;//Correct option
					} 
				}
			}
			return mTilesCnt-cntCorrect;
		}
		return this.getSegments();
	}
	public boolean isSameTileOnSamePosition(RigidSquareTile pTile){
		RigidSquareTile tmpTile = getTileFromSegment(pTile.getSegmentX(), pTile.getSegmentY());
		return pTile.equals(tmpTile);
	}
	/**
	 * It is set by RigidSquareTile while it's being attached or detached.
	 */
	public void increaseTilesCnt() {
		this.mTilesCnt++;
	}

	public void decreaseTilesCnt() {
		this.mTilesCnt--;
	}
	
	/****************Getters and setters*********************/
	
	/**
	 * While setting x or y coordinate it is necessary to move all its tiles.
	 */
	public void setX(float pX) {

		float dist = Math.abs(mBackgroundGrid.getX() - pX);
		if (pX < mBackgroundGrid.getX()) {
			dist = 0 - dist;
		}
		for (int i = 0; i < mBackgroundGrid.getSegments(); i++) {
			for (int j = 0; j < mBackgroundGrid.getSegments(); j++) {
				if (this.mTileArray[i][j] != null) {
					this.mTileArray[i][j].setX(this.mTileArray[i][j].getX()
							+ dist);
				}
			}
		}
		mBackgroundGrid.setX(pX);
	}

	public void setY(float pY) {
		float dist = Math.abs(mBackgroundGrid.getY() - pY);
		if (pY < mBackgroundGrid.getY()) {
			dist = 0 - dist;
		}
		for (int i = 0; i < mBackgroundGrid.getSegments(); i++) {
			for (int j = 0; j < mBackgroundGrid.getSegments(); j++) {
				if (this.mTileArray[i][j] != null) {
					this.mTileArray[i][j].setY(this.mTileArray[i][j].getY()
							+ dist);
				}
			}
		}
		mBackgroundGrid.setY(pY);
	}

	public float getX(){
		return mBackgroundGrid.getX();
	}
	
	public float getY(){
		return mBackgroundGrid.getY();
	}
	
	public void setPosition(float pX, float pY) {
		float distX = Math.abs(mBackgroundGrid.getX() - pX);
		if (pX < mBackgroundGrid.getX()) {
			distX = 0 - distX;
		}
		float distY = Math.abs(mBackgroundGrid.getY() - pY);
		if (pY < mBackgroundGrid.getY()) {
			distY = 0 - distY;
		}
		for (int i = 0; i < mBackgroundGrid.getSegments(); i++) {
			for (int j = 0; j < mBackgroundGrid.getSegments(); j++) {
				if (this.mTileArray[i][j] != null) {
					this.mTileArray[i][j].setX(this.mTileArray[i][j].getX()
							+ distX);
					this.mTileArray[i][j].setY(this.mTileArray[i][j].getY()
							+ distY);
				}
			}
		}
		mBackgroundGrid.setX(pX);
		mBackgroundGrid.setY(pY);
	}

	public void setVisible(final boolean pVisible) {
		mBackgroundGrid.setVisible(pVisible);
		for (int i = 0; i < this.mTileArray.length; i++) {
			for (int j = 0; j < this.mTileArray.length; j++) {
				if (this.mTileArray[i][j] != null) {
					this.mTileArray[i][j].setVisible(pVisible);
				}
			}
		}
	}

	public float getBottomLeftCornerX() {
		return mBackgroundGrid.getBottomLeftCornerX();
	}

	public float getBottomLeftCornerY() {
		return mBackgroundGrid.getBottomLeftCornerY();
	}

	public int getSegments() {
		return mBackgroundGrid.getSegments();
	}

	public float getTileSize() {
		float tmp = mBackgroundGrid.getSegmentSize()-0.1F*mBackgroundGrid.getSegmentSize();
		return tmp;
	}

	public int getTilesCnt() {
		return this.mTilesCnt;
	}
	public ChessGrid getBackgroundGrid(){
		return mBackgroundGrid;
	}
	public MrTileTaskScene getTaskScene(){
		return mScene;
	}
}
