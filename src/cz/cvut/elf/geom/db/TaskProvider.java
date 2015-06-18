package cz.cvut.elf.geom.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class TaskProvider extends ContentProvider {

	static final UriMatcher uriMatcher;
	static final int TASKS = 1;
	static final int RESULTS = 2;
	static final String PROVIDER_NAME = "cz.cvut.elf.geom.provider";
	static final String URL = "content://" + PROVIDER_NAME;
	public static final Uri CONTENT_URI = Uri.parse(URL);

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "tasks", TASKS);
		uriMatcher.addURI(PROVIDER_NAME, "results", RESULTS);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db = (new DbOpenHelper(getContext()))
				.getReadableDatabase();
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		switch (uriMatcher.match(uri)) {
		case TASKS:
			queryBuilder.setTables(DbOpenHelper.TASK_TABLE_NAME);
			break;

		case RESULTS:
			queryBuilder.setTables(DbOpenHelper.RESULTS_TABLE_NAME);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return "";
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
