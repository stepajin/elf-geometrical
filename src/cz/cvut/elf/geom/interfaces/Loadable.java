package cz.cvut.elf.geom.interfaces;

/*
 * Interface for objects which stores some assets which could be load 
 * and unload from memory.
 * 
 * @author Petr Zavadil
 * 
 * */

public interface Loadable {

	public void load();
	public void unload();
	public boolean isLoaded();
	
}
