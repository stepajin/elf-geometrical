package cz.cvut.elf.geom;

import org.andengine.engine.Engine;

import cz.cvut.elf.geom.interfaces.Loadable;


/*
 * AbstractAssetBundle prescribes form of general asset bundle which is used 
 * for storing assets references and sharing them between the set of scenes.
 * 
 * Programmer creating descendant of this class is responsible for correct 
 * loading and unloading of stored assets. In each method load() and unload() of
 * descendant should be call super.load() / unload()
 * 
 *  
 * @author Petr Zavadil
 * 
 * */


public abstract class AssetBundle implements Loadable{

	protected 		boolean 		mIsLoaded = false;
	protected 		ElfActivity		mActivity = GameHolder.getInstance().mActivity;
	protected		Engine			mEngine   = GameHolder.getInstance().mEngine;
	
	@Override
	public void load() {
		
		mIsLoaded = true;
	}
	
	@Override
	public void unload() {
		
		mIsLoaded = false;
	}
	
	@Override
	public boolean isLoaded() {
		return mIsLoaded;
	}
	
}
