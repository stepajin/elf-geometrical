package cz.cvut.elf.geom.db;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.elf.geom.GameHolder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbDAO {

	private static DbDAO INSTANCE = new DbDAO();

	public static final DbDAO getInstance() {
		return INSTANCE;
	}

	private DbDAO() {
	}


	public void writeResult(ContentValues cv) {
		DbOpenHelper db = new DbOpenHelper(GameHolder.getInstance().mActivity);
		db.getWritableDatabase().insert(DbOpenHelper.RESULTS_TABLE_NAME, DbOpenHelper.RESULTS_TASK_ID, cv);
		db.close();
	}
	
	public void printResults(Context context) {
		DbOpenHelper db = new DbOpenHelper(context);
		Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM " + DbOpenHelper.RESULTS_TABLE_NAME, null);

		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			System.out.println("***");
			System.out.println(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.RESULTS_TASK_ID)));
			System.out.println(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.RESULTS_USER_ID)));
			System.out.println(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.RESULTS_DIFFICULTY)));
			System.out.println(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.RESULTS_TIME)));
			System.out.println(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.RESULTS_ACCURACY)));
			cursor.moveToNext();
		}
	}
	
	public List<TaskCarrier> getTasks(Context context) {
		List<TaskCarrier> list = new ArrayList<TaskCarrier>();

		DbOpenHelper db = new DbOpenHelper(context);
		Cursor cursor = db.getReadableDatabase().rawQuery(
				"SELECT * " + "FROM " + DbOpenHelper.TASK_TABLE_NAME, null);

		System.out.println("VYBER " + cursor.getCount() + " TASKU");
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(cursor.getColumnIndex(DbOpenHelper.TASK_ID));
			String name = cursor.getString(cursor
					.getColumnIndex(DbOpenHelper.TASK_NAME));

			list.add(new TaskCarrier(id, name));
			cursor.moveToNext();
		}

		cursor.close();
		db.close();
		return list;
	}
	
	public class TaskCarrier {
		int id;
		String name;

		private TaskCarrier(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}
	}
}
