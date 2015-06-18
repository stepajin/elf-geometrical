package cz.cvut.elf.geom.geomplanet.mrtile;

import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.Task;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;

public class MrTileTask extends Task {
	
	private static MrTileTask INSTANCE;
	
	public static MrTileTask getInstance() {
		if (INSTANCE == null)
			INSTANCE = new MrTileTask();
		return INSTANCE;
	}

	@Override
	public void start() {

		MrTileAssetBundle.getInstance().load();
		SceneManager.getInstance().setScene(MrTileIntroScene.INSTANCE);
	}

	@Override
	public void finish() {
		SceneManager.getInstance().setScene(GeomPlanetScene.INSTANCE);
		this.destroy();
	}
	
	public void destroy() {
		
		MrTileAssetBundle.getInstance().unload();
		
		MrTileIntroScene.INSTANCE.onDestroy();
		MrTileCaveScene.INSTANCE.onDestroy();
		MrTileTaskScene.INSTANCE.onDestroy();
		
	}

}
