package cz.cvut.elf.geom.geomplanet.mrtile.entity;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.animator.AlphaMenuSceneAnimator;
import org.andengine.entity.scene.menu.animator.IMenuSceneAnimator;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.general.GlobalAssetBundle;

public class DialogScene extends MenuScene {
	public static final int MESSAGE = 0;
	public static final int RESUME = 1;
	public static final int RESET = 2;
	public static final int MENU = 3;

	private Rectangle mBackground;
	private GameHolder mGH;
	public DialogScene(String pMessage, GameHolder pGH) {
		super(pGH.mCamera, new AlphaMenuSceneAnimator());
		mGH = pGH;
		
		mBackground = new Rectangle(mGH.mCamera.getCenterX(), mGH.mCamera.getCenterY(),
				mGH.mCamera.getWidth(), mGH.mCamera.getHeight(), mGH.mVBOM);
		mBackground.setColor(0.3F, 0.3F, 0.6F, 0.8F);
		attachChild(mBackground);
		
		final IMenuItem textItem = new ColorMenuItemDecorator(new TextMenuItem(
				MESSAGE, GlobalAssetBundle.getInstance().mDefaultFont, pMessage, mGH.mVBOM), new Color(1, 0, 0), new Color(0,
				0, 0));

		addMenuItem(textItem);
		unregisterTouchArea(textItem);
		
		//unregisterTouchArea(textItem);
		
		//buildAnimations();

		setBackgroundEnabled(false);	
	}

	public void addOption(final int pID, String pOptionMessage){
		addMenuItem(new ColorMenuItemDecorator(
				new TextMenuItem(pID,GlobalAssetBundle.getInstance().mDefaultFont, pOptionMessage, mGH.mVBOM), new Color(1, 0,
						0), new Color(0, 0, 1)));
	}
}
