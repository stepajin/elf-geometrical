package cz.cvut.elf.geom;


import org.andengine.util.debug.Debug;

import android.os.AsyncTask;
import android.util.Log;
import cz.cvut.elf.geom.interfaces.AsyncTaskLoaderParams;

/*
 * Class is used for asynchronous loading of scene where SceneManager.setScene() is called.
 * 
 * */

public class AsynctaskLoader extends AsyncTask< AsyncTaskLoaderParams, Integer, Boolean> {

	AsyncTaskLoaderParams [] mParams;
	long a;
	
	@Override
	protected void onPreExecute() {
		Log.d(getClass().toString(),"Async Start");
		a = System.currentTimeMillis();		
		
		super.onPreExecute();
	}
	
	@Override
	protected Boolean doInBackground(AsyncTaskLoaderParams... params) {
		
		mParams = params;
		
		mParams[0].workToDo();
			
		
		return true;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		
		Debug.d("Async Stop, time: "+(System.currentTimeMillis()-a));
		super.onPostExecute(result);
	}

}
