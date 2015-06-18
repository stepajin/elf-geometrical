package cz.cvut.elf.geom.geomplanet.logicalrow;

import cz.cvut.elf.geom.SceneManager;
import cz.cvut.elf.geom.Task;
import cz.cvut.elf.geom.geomplanet.GeomPlanetScene;

public class LogicalRowTask extends Task {

	private static LogicalRowTask INSTANCE;
	
	public static LogicalRowTask getInstance() {
		if (INSTANCE == null)
			INSTANCE = new LogicalRowTask();
		return INSTANCE;
	}
	
	private LogicalRowTask() {
	}
	
	public void start() {
		LogicalRowAssetBundle.getInstance().load();
		SceneManager.getInstance().setScene(LogicalRowIntroScene.INSTANCE);
	}
	
	public void finish() {
		SceneManager.getInstance().setScene(GeomPlanetScene.INSTANCE);
		this.destroy();
	}
	
	public void destroy() {
		LogicalRowAssetBundle.getInstance().unload();
	}
}
