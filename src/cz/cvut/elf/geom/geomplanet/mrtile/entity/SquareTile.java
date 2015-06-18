package cz.cvut.elf.geom.geomplanet.mrtile.entity;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.util.algorithm.collision.EntityCollisionChecker;

import android.util.Log;
import android.view.MotionEvent;

/**
 * 
 * @author Tom
 * 
 *         Type of tile which can be touched (and moved).
 */
public class SquareTile extends RigidSquareTile {



	public SquareTile(float pX, float pY, SquareGrid pGrid, String pTileType,
			TextureRegion pTR) {
		super(pX, pY, pGrid, pTileType, pTR);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.setZIndex(1);
			this.getParent().sortChildren();
			break;
		case MotionEvent.ACTION_MOVE:
			this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			break;
		case MotionEvent.ACTION_UP:
			if (checkCollisionWithGrid()) {
				//Log.d("intersectTrue", "");
				this.mGrid.putTileToGrid(this);
				this.mGrid.getTaskScene().onTilePlaced(this);
			} else if (this.mInGrid) {
				this.setInGrid(false);
			}
			this.setZIndex(0);
			break;
		}
		return true;
	}
}
