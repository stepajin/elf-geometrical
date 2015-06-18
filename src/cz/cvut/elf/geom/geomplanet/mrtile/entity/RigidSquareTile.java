package cz.cvut.elf.geom.geomplanet.mrtile.entity;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.util.algorithm.collision.EntityCollisionChecker;

import cz.cvut.elf.geom.GameHolder;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 
 * @author Tom
 * 
 *         This is a tile for the game which will be used to show how the final
 *         pattern should look like. It won't be possible to move with this
 *         tile.
 * 
 *         The RigidSquareTile extends entity Rectangle. It's simplification -
 *         in next version there'll be a sprite instead.
 */
public class RigidSquareTile extends Sprite{
	protected final SquareGrid mGrid;
	protected int mSegmentX = -1;
	protected int mSegmentY = -1;
	protected boolean mInGrid = false;
	protected final String mTileType;

	public RigidSquareTile(float pX, float pY, SquareGrid pGrid,String pTileType,TextureRegion pTR) {
		super(pX,pY,pGrid.getTileSize(),pGrid.getTileSize(),pTR, GameHolder.getInstance().mVBOM );
		this.mGrid = pGrid;
		if (checkCollisionWithGrid()) {
			this.mGrid.putTileToGrid(this);
		}
		mTileType = pTileType;
	}
	
	public boolean isInGrid() {
		return mInGrid;
	}
	
	/**
	 * 
	 * @param inGrid
	 *
	 * Tiles at general can be in grid or out of the grid.
	 */
	public void setInGrid(boolean inGrid) {
		/**
		 * If the tile is in the grid and I wish to unset it.
		 */
		if (!inGrid && this.mInGrid) {
			/**
			 * Setting null value to the position, where this tile was in Grid.
			 */
			this.mGrid.decreaseTilesCnt();
			this.mGrid.addToTileArray(this.mSegmentX, this.mSegmentY, null);
		} else if (inGrid && !this.mInGrid) {
			this.mGrid.increaseTilesCnt();
		}
		this.mInGrid = inGrid;
	}

	public int getSegmentX() {
		return mSegmentX;
	}

	public void setSegmentX(int segmentX) {
		this.mSegmentX = segmentX;
	}

	public int getSegmentY() {
		return mSegmentY;
	}

	public void setSegmentY(int segmentY) {
		this.mSegmentY = segmentY;
	}
	
	public String getTileType(){
		return mTileType;
	}
	
	public boolean checkCollisionWithGrid() {
		return EntityCollisionChecker.checkCollision(mGrid.getBackgroundGrid(), this);
	}

	public boolean equals(RigidSquareTile otherTile) {
		if (otherTile == null) {
			return false;
		}
		return getTileType().equals(otherTile.getTileType());
	}
	
}
