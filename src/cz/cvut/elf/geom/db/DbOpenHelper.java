package cz.cvut.elf.geom.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "GEOMETRICAL PLANET DB";
	public static final int DB_VERSION = 1;

	public static final String TASK_TABLE_NAME = "tasks";
	public static final String TASK_ID = "_id";
	public static final String TASK_NAME = "name";
	public static final String LOGICAL_TASK = "Víla Korálka";
	public static final String SORTING_TASK = "Hostina";
	public static final String MR_TILE_TASK = "Pan Kachlička";

	public static final String RESULTS_TABLE_NAME = "results";
	public static final String RESULTS_TASK_ID = "task_id";
	public static final String RESULTS_USER_ID = "user_id";
	public static final String RESULTS_DIFFICULTY = "difficulty";
	public static final String RESULTS_TIME = "time";
	public static final String RESULTS_ACCURACY = "accuracy";

	public DbOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTaskTable(db);
		createResultsTable(db);
	}

	private void createResultsTable(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE results (task_id INTEGER, user_id INTEGER, "
				+ "difficulty INTEGER, time INTEGER, accuracy INTEGER);");
	}

	private void createTaskTable(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);");

		ContentValues cv = new ContentValues();

		cv.put(TASK_NAME, LOGICAL_TASK);
		db.insert(TASK_TABLE_NAME, TASK_NAME, cv);

		cv.put(TASK_NAME, SORTING_TASK);
		db.insert(TASK_TABLE_NAME, TASK_NAME, cv);

		cv.put(TASK_NAME, MR_TILE_TASK);
		db.insert(TASK_TABLE_NAME, TASK_NAME, cv);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
