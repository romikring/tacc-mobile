package co.tulatacc;

import java.util.HashMap;
import java.util.HashSet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaccDbOpenHelper extends SQLiteOpenHelper {
	private final static int DATABASE_VERSION = 9;
	
	private final static String DATABASE_NAME = "Tacc.db";
	
	// --- Laps Table ---
	private final static String TABLE_LAPS = "Laps";
	public final static String LAPS_COL_ID = "id";
	public final static String LAPS_COL_START_TIME = "start_time";
	public final static String LAPS_COL_END_TIME = "end_time";
	public final static String LAPS_COL_TASK = "task_id";
	public final static String LAPS_COL_STATE = "state";
	
	private final static String CREATE_TABLE_LAPS = "CREATE TABLE IF NOT EXISTS " + TABLE_LAPS + " (" +
		LAPS_COL_ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT, " +
		LAPS_COL_START_TIME + " INTEGET NOT NULL, " +
		LAPS_COL_END_TIME + " INTEGET NOT NULL, " +
		LAPS_COL_TASK + " INTEGET NOT NULL, " +
		LAPS_COL_STATE + " INTEGER NOT NULL);";
	
	private final static String CREATE_TABLE_LAPS_INDEX_TASK = "CREATE INDEX IF NOT EXISTS " +
		TABLE_LAPS + "_" + LAPS_COL_TASK + " ON " + TABLE_LAPS + " (" + LAPS_COL_TASK + "); ";
	
	private final static String CREATE_TABLE_LAPS_INDEX_STATE = "CREATE INDEX IF NOT EXISTS " +
			TABLE_LAPS + "_" + LAPS_COL_STATE + " ON " + TABLE_LAPS + " (" + LAPS_COL_STATE + "); ";
	
//	private final static String TABLE_TRACKERS = "Trackers";
	// -------------------
	
	// --- Tasks Table ---
	private final static String TABLE_TASKS = "Tasks";
	private final static String TASKS_COL_ID = "id";
	private final static String TASKS_COL_CUSTOMER = "customer_id";
	private final static String TASKS_COL_DESCRIPTION = "description";
	private final static String TASKS_COL_STATE = "state";
	
	private final static String CREATE_TABLE_TASKS = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " (" +
		TASKS_COL_ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT, " +
		TASKS_COL_CUSTOMER + " INTEGER NOT NULL, " +
		TASKS_COL_DESCRIPTION + " TEXT NOT NULL, " +
		TASKS_COL_STATE + " INTEGER NOT NULL);";
	
	private final static String CREATE_TABLE_TASKS_INDICES = "CREATE INDEX IF NOT EXISTS " +
		TABLE_TASKS + "_" + TASKS_COL_CUSTOMER + " ON " + TABLE_TASKS + " (" + TASKS_COL_CUSTOMER + ");";
	// -------------------
	
//	private final static String TABLE_TRACKS = "Tracks";
	
	// --- Records Table ---
	private final static String TABLE_RECORDS = "Records";
	private final static String RECORDS_COL_ID = "id";
	private final static String RECORDS_COL_CUSTOMER = "customer_id";
	private final static String RECORDS_COL_BILL_TIME = "bill_time";
	private final static String RECORDS_COL_UNBILL_TIME = "unbill_time";
	private final static String RECORDS_COL_DESCRIPTION = "description";
	private final static String RECORDS_COL_DATE = "date";
	private final static String RECOREDS_COL_SOURCE = "source";
	
	private final static String CREATE_TABLE_RECORDS = "CREATE TABLE IF NOT EXISTS " + TABLE_RECORDS + "(" +
		RECORDS_COL_ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT, " +
		RECORDS_COL_CUSTOMER + " INTEGER NOT NULL, " +
		RECORDS_COL_BILL_TIME + " INTEGER NOT NULL, " +
		RECORDS_COL_UNBILL_TIME + " INTEGER NOT NULL, " +
		RECORDS_COL_DESCRIPTION + " TEXT NOT NULL, " +
		RECORDS_COL_DATE + " INTEGER NOT NULL, " +
		RECOREDS_COL_SOURCE + " TEXT NOT NULL);";
	
	private final static String CREATE_TABLE_RECORDS_INDICES = "CREATE INDEX IF NOT EXISTS " +
		TABLE_RECORDS + "_" + RECORDS_COL_CUSTOMER + " ON " + TABLE_RECORDS + " (" + RECORDS_COL_CUSTOMER + ");";
	// ----------------------
	
//	private final static String TABLE_GROUPS = "Groups";
	
	// --- Customers table ---
	private final static String TABLE_CUSTOMERS = "Customers";
	private final static String CUSTOMERS_COL_ID = "id";
	private final static String CUSTOMERS_COL_TACC_ID = "tacc_id";
	private final static String CUSTOMERS_COL_NAME = "name";
	private final static String CUSTOMERS_COL_DESCRIPTION = "description";
	
	private final static String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMERS + " (" +
		CUSTOMERS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		CUSTOMERS_COL_TACC_ID + " INTEGER NOT NULL, " +
		CUSTOMERS_COL_NAME + " VARCHAR(200) NOT NULL, " +
		CUSTOMERS_COL_DESCRIPTION + " TEXT DEFAULT NULL);";
	
	private final static String CREATE_TABLE_CUSTOMERS_INDICES = "CREATE INDEX IF NOT EXISTS " +
		TABLE_CUSTOMERS + "_" + CUSTOMERS_COL_TACC_ID + " ON " + TABLE_CUSTOMERS + " (" + CUSTOMERS_COL_TACC_ID + ");";
	// -----------------------
	
	private final static String TABLE_PROJECTS = "Projects";
	private final static String PROJECTS_COL_ID = "id";
	private final static String PROJECTS_COL_TACC_ID = "tacc_id";
	private final static String PROJECTS_COL_NAME = "name";
	
	private final static String CREATE_TABLE_PROJECTS = "CREATE TABLE IF NOT EXISTS " + TABLE_PROJECTS + " (" +
		PROJECTS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		PROJECTS_COL_TACC_ID + " INTEGER NOT NULL, " +
		PROJECTS_COL_NAME + " VARCHAR(200) NOT NULL);";
	
	private final static String CREATE_TABLE_PROJECTS_INDICES = "CREATE INDEX IF NOT EXISTS " +
		TABLE_PROJECTS + "_" + PROJECTS_COL_TACC_ID + " ON " + TABLE_PROJECTS + " (" + PROJECTS_COL_TACC_ID + ");";
	
	public TaccDbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_LAPS);
		db.execSQL(CREATE_TABLE_LAPS_INDEX_STATE);
		db.execSQL(CREATE_TABLE_LAPS_INDEX_TASK);
		db.execSQL(CREATE_TABLE_RECORDS);
		db.execSQL(CREATE_TABLE_RECORDS_INDICES);
		db.execSQL(CREATE_TABLE_TASKS);
		db.execSQL(CREATE_TABLE_TASKS_INDICES);
		db.execSQL(CREATE_TABLE_CUSTOMERS);
		db.execSQL(CREATE_TABLE_CUSTOMERS_INDICES);
		db.execSQL(CREATE_TABLE_PROJECTS);
		db.execSQL(CREATE_TABLE_PROJECTS_INDICES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAPS + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS + ";");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS + ";");
		
		onCreate(db);
	}
	
	public long startLap(int startTime, int task)
	{
		ContentValues cv = new ContentValues();
		
		cv.put(LAPS_COL_START_TIME, startTime);
		cv.put(LAPS_COL_END_TIME, 0);
		cv.put(LAPS_COL_STATE, 1);
		cv.put(LAPS_COL_TASK, task);
		
		return getWritableDatabase().insert(TABLE_LAPS, null, cv);
	}
	
	public boolean endLap(int endTime, int lapId)
	{
		ContentValues cv = new ContentValues();
		
		cv.put(LAPS_COL_END_TIME, endTime);
		cv.put(LAPS_COL_STATE, 0);
		
		String whereClause = LAPS_COL_ID + "=" + lapId;
		String whereArgs[] = null;
		
		return getWritableDatabase().update(TABLE_LAPS, cv, whereClause, whereArgs) > 0;
	}
	
	public HashMap<String, String> findActiveLap() {
		String result[] = {LAPS_COL_ID, LAPS_COL_START_TIME, LAPS_COL_END_TIME, LAPS_COL_TASK, LAPS_COL_STATE};
		String whereClause = LAPS_COL_STATE + "=1";
		String whereArgs[] = null;
		
		Cursor cur = getWritableDatabase().query(TABLE_LAPS, result, whereClause, whereArgs, null, null, null);
		
		if (cur.getCount() < 1) {
			return null;
		}
		
		HashMap<String, String> hash = new HashMap<String, String>(5);
		for (String key : result) {
			hash.put(key, cur.getString(cur.getColumnIndex(key)));
		}
		
		return hash;
	}
}
