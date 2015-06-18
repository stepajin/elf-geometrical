package cz.cvut.elf.geom;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.level.IEntityLoaderListener;
import org.andengine.util.level.LevelLoader;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.andengine.util.level.simple.SimpleLevelLoaderResult;

/**
 * Task is a kind of interface for loading task's level from a XML file. Its
 * purpose is to load (maybe also attach) some entities into the BaseScene. It
 * should keep note what was loaded so far.
 * 
 * @author Tom
 * 
 */
public abstract class GameTaskLoader {
	/* Attributes */
	protected ArrayList<IEntity> mLoadedEntities;
	protected BaseScene mScene;
	protected final LevelLoader<SimpleLevelEntityLoaderData, IEntityLoaderListener, SimpleLevelLoaderResult> mLevelLoader;

	/* Constructors */
	public GameTaskLoader(BaseScene pScene, VertexBufferObjectManager pVBOM) {
		mLoadedEntities = new ArrayList<IEntity>();
		mScene = pScene;
		mLevelLoader = new SimpleLevelLoader(pVBOM);
	}

	/* Abstract methods */
	/**
	 * Should load entities from XML file named like "IDofSomeLevel.lvl". Entity
	 * loaders in SimpleLevelLoader also attach loaded entities directly to some
	 * parent (for example we can use attribute mScene and attach them there).
	 * 
	 * @param pLevelID the number under which we can find a level file in level folder
	 */
	public abstract void loadLevel(int pLevelID);

	/**
	 * Loaded entities have to be unloadable if needed. For example if we need
	 * to load different levels in one scene.
	 */
	public abstract void unloadLevel();

	/**
	 * For checking whether the task is finished and we want to show a scene
	 * with results.
	 * 
	 * @return true if current task is completed
	 */
	public abstract boolean isTaskCompleted();

	/* Getters and setters */
	public ArrayList<IEntity> getLoadedEntities() {
		return mLoadedEntities;
	}
}
