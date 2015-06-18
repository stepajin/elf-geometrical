package cz.cvut.elf.geom.general.controls;

import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import cz.cvut.elf.geom.BaseScene;
import cz.cvut.elf.geom.GameHolder;
import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.general.GlobalAssetBundle;

public class TextButton extends Text {

	private BaseScene mNextScene;

	public TextButton(float pX, float pY, String pText, BaseScene pSceneName) {
		this(pX, pY, GlobalAssetBundle.getInstance().mDefaultFont, pText,
				GameHolder.getInstance().mVBOM, pSceneName);
	}

	public TextButton(float pX, float pY, Font pFont, String pText,
			VertexBufferObjectManager pVBOM, BaseScene pSceneName) {
		super(pX, pY, pFont, pText, pVBOM);
		mNextScene = pSceneName;
		setColor(Color.BLUE);
	}

	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
			SceneManager.getInstance().setScene(mNextScene);
			return true;
		}
		return false;
	}
}
