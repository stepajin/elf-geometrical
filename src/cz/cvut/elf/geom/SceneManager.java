package cz.cvut.elf.geom;

import cz.cvut.elf.geom.general.GameResultsScene;
import cz.cvut.elf.geom.general.LoadingScene;
import cz.cvut.elf.geom.geomplanet.logicalrow.LogicalRowIntroScene;
import cz.cvut.elf.geom.geomplanet.logicalrow.LogicalRowOutroScene;
import cz.cvut.elf.geom.geomplanet.mrtile.MrTileCaveScene;
import cz.cvut.elf.geom.geomplanet.sorting.SortingBanquetScene;
import cz.cvut.elf.geom.interfaces.AsyncTaskLoaderParams;

/**
 * Manager which switch between scenes. It doesn't decide which scene will
 * follow. SceneManager just listen if anyone call setScene(String pSceneName).
 * 
 * @author Jindrich Stepanek
 * 
 */

public class SceneManager {

	private static final SceneManager INSTANCE = new SceneManager();

	private static final int DEFAULT_MIN_LOADING_TIME = 20;

	BaseScene mActualScene;

	private SceneManager() {
	}

	public static SceneManager getInstance() {
		return INSTANCE;
	}

	public static enum GameType {
		SORTING, MRTILE, LOGICAL_ROW;
	}

	private GameType mPrevGame;

	/**
	 * Change displayed scene
	 * 
	 * @param pScene
	 *            Scene which should be displayed
	 * @param pMinDelay
	 *            Minimal delay in milliseconds to display loading animation
	 */
	public void setScene(final BaseScene pScene, final int pMinDelay) {

		if (pScene == null) {
			throw new IllegalArgumentException("Scene not found!");
		}

		// New work to do in asynchronous task
		final AsyncTaskLoaderParams params = new AsyncTaskLoaderParams() {

			@Override
			public void workToDo() {

				long a = System.currentTimeMillis();

				if (mActualScene != null) {
					mActualScene.onPause();
					mActualScene.onStop();
				}

				mActualScene = pScene;

				if (!pScene.isBuilt()) {
					pScene.onBuild();
				}

				pScene.onStart();
				pScene.onResume();

				long b = System.currentTimeMillis() - a;
				b = pMinDelay - b;
				if (b <= 0) {
					b = 0;
				}
				try {
					Thread.sleep(b);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// stop LoadingScene animation
				LoadingScene.INSTANCE.onStop();
				// set actual scene
				GameHolder.getInstance().mEngine.setScene(mActualScene);
			}
		};

		// run asynchronous task
		GameHolder.getInstance().mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				new AsynctaskLoader().execute(params);
			}
		});

		// show loading scene
		// if( neco ) Todo omezit zobrazovani sceny
		LoadingScene.INSTANCE.onResume();
		GameHolder.getInstance().mEngine.setScene(LoadingScene.INSTANCE);

	}

	/*
	 * Decorator method for setScene( BaseScene, int );
	 */

	public void setScene(final BaseScene pScene) {

		setScene(pScene, DEFAULT_MIN_LOADING_TIME);
	}

	public void setResultsScene(final GameType pPrevGameType) {
		mPrevGame = pPrevGameType;

		// prepnuti do vysledkove sceny
		setScene(GameResultsScene.INSTANCE);
	}

	public void getBackFromResultsScene() {

		System.out.println("get back from results scene do sceny "
				+ mPrevGame.toString());

		/*GameHolder.getInstance().mUser.saveResultToDB(
				GameHolder.getInstance().mUser.getCurrentIDTask(),
				GameHolder.getInstance().mUser.getCurrentMistakes(),
				GameHolder.getInstance().mUser.getCurrentTime());*/

		switch (mPrevGame) {
		case LOGICAL_ROW:
			setScene(LogicalRowOutroScene.INSTANCE);
			break;
		case MRTILE:
			setScene(MrTileCaveScene.INSTANCE);
			break;
		case SORTING:
			setScene(SortingBanquetScene.INSTANCE);
			break;
		default:
			break;
		}
	}

	public void setFirstScene(final BaseScene pScene) throws Exception {
		if (pScene == null) {
			throw new IllegalArgumentException("Scene not found!");
		}
		if (mActualScene != null) {
			throw new Exception(
					"Some scene was already set, not setting first scene.");
		}
		if (!pScene.isBuilt()) {
			pScene.onBuild();
		}
		pScene.onStart();
		pScene.onResume();

		mActualScene = pScene;
//		GameHolder.getInstance().mEngine.setScene(mActualScene);
		this.setScene(pScene, 300);
	}

	public void onPause() {
		System.out.println("PAUSE");
		/*
		 * if (mActualScene != null) mActualScene.onPause();
		 */
	}

	public void onResume() {
		if (mActualScene != null)
			mActualScene.onResume();
	}

	public BaseScene getActualScene() {
		return mActualScene;
	}
}
