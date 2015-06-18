package cz.cvut.elf.geom.geomplanet.mrtile.entity;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileAssetBundle;

public class ChessGrid extends Entity {
	/*protected TextureRegion mWhiteTR;
	protected TextureRegion mBlackTR;*/
	private int mSegments = 1;
	private float mBottomLeftCornerX = 0;
	private float mBottomLeftCornerY = 0;
	private Sprite[][] mChessGrid;
	
	
	public ChessGrid(float pX,float pY,float pEdgeSize, int pSegments,TextureRegion pWhiteTR, TextureRegion pBlackTR){
		super(pX,pY,pEdgeSize,pEdgeSize);
		/*mWhiteTR = pWhiteTR;
		mBlackTR = pBlackTR;*/
		boolean white=true;
		
		setBottomLeftCornerX();
		setBottomLeftCornerY();
		if(pSegments>mSegments)mSegments = pSegments;
		mChessGrid = new Sprite[mSegments][mSegments];

		TextureRegion tmpTR=pWhiteTR;
		/**
		 * Making the chessgrid.
		 */
		for(int i=0;i<mSegments;i++){
			if(i%2==0)white=true;
			else white=false;
			for(int j=0;j<mSegments;j++){
				/**
				 * Deciding between black and white.
				 */
				if(white)tmpTR=pWhiteTR;
				else tmpTR=pBlackTR;
				white=!white;
				/**
				 * Counting position of black or white piece.
				 */
				float newX = (i * getSegmentSize()) + (getSegmentSize() / 2)
						+ this.mBottomLeftCornerX;
				float newY = (j * getSegmentSize()) + (getSegmentSize() / 2)
						+ this.mBottomLeftCornerY;
				/**
				 * Making new sprite on position newX, newY with size of its edge getSegmentSize() based on image tmpTR.
				 */
				mChessGrid[i][j] = new Sprite(newX,newY,getSegmentSize(),getSegmentSize(),tmpTR, GameHolder.getInstance().mVBOM );
			}	
		}
		
	}
	public void attachSegmentsToScene(BaseScene pBaseScene){
		for(int i=0;i<mSegments;i++){
			for(int j=0;j<mSegments;j++){
				pBaseScene.attachChild(mChessGrid[i][j]);
			}
		}
	}
	
	public void detachSegments(){
		for(int i=0;i<mSegments;i++){
			for(int j=0;j<mSegments;j++){
				mChessGrid[i][j].detachSelf();
			}
		}
	}
	
	/*Setters and getters*/
	public float getBottomLeftCornerX() {
		return mBottomLeftCornerX;
	}

	public void setBottomLeftCornerX() {
		this.mBottomLeftCornerX = this.getX() - (this.getWidth() / 2);
	}

	public float getBottomLeftCornerY() {
		return mBottomLeftCornerY;
	}

	public void setBottomLeftCornerY() {
		this.mBottomLeftCornerY = this.getY() - (this.getHeight() / 2);
	}

	public int getSegments() {
		return mSegments;
	}

	public void setsegments(int segments) {
		this.mSegments = segments;
	}
	public float getSegmentSize() {
		return super.getWidth() / ((float) this.mSegments);
	}
	
	/**
	 * While setting x or y coordinate it is necessary to move all its segments.
	 */
	@Override
	public void setX(float pX) {
		float dist = Math.abs(this.mX - pX);
		if (pX < this.mX) {
			dist = 0 - dist;
		}
		for (int i = 0; i < this.mSegments; i++) {
			for (int j = 0; j < this.mSegments; j++) {
				if (mChessGrid[i][j] != null) {
					mChessGrid[i][j].setX(mChessGrid[i][j].getX()
							+ dist);
				}
			}
		}
		this.mX = pX;
		setBottomLeftCornerX();
	}

	@Override
	public void setY(float pY) {
		float dist = Math.abs(this.mY - pY);
		if (pY < this.mY) {
			dist = 0 - dist;
		}
		for (int i = 0; i < this.mSegments; i++) {
			for (int j = 0; j < this.mSegments; j++) {
				if (mChessGrid[i][j] != null) {
					mChessGrid[i][j].setY(mChessGrid[i][j].getY()
							+ dist);
				}
			}
		}
		this.mY = pY;
		setBottomLeftCornerY();
	}

	@Override
	public void setPosition(float pX, float pY) {
		float distX = Math.abs(this.mX - pX);
		if (pX < this.mX) {
			distX = 0 - distX;
		}
		float distY = Math.abs(this.mY - pY);
		if (pY < this.mY) {
			distY = 0 - distY;
		}
		for (int i = 0; i < this.mSegments; i++) {
			for (int j = 0; j < this.mSegments; j++) {
				if (mChessGrid[i][j] != null) {
					mChessGrid[i][j].setX(mChessGrid[i][j].getX()
							+ distX);
					mChessGrid[i][j].setY(mChessGrid[i][j].getY()
							+ distY);
				}
			}
		}
		this.mX = pX;
		setBottomLeftCornerX();
		this.mY = pY;
		setBottomLeftCornerY();
	}

	@Override
	public void setVisible(final boolean pVisible) {
		this.mVisible = pVisible;
		for (int i = 0; i < mChessGrid.length; i++) {
			for (int j = 0; j < mChessGrid.length; j++) {
				if (mChessGrid[i][j] != null) {
					mChessGrid[i][j].setVisible(pVisible);
				}
			}
		}
	}
}
