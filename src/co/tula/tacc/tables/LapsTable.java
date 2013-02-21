package co.tula.tacc.tables;

import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import co.tula.tacc.tools.TaccDbOpenHelper;

public class LapsTable extends TaccDbOpenHelper
{
	private final static String TABLE_LAPS = "Laps";
	
	public final static String LAPS_COL_ID 			= "_ID";		// long
	public final static String LAPS_COL_START_TIME 	= "start_time";	// long
	public final static String LAPS_COL_END_TIME 	= "end_time";	// long
	public final static String LAPS_COL_TASK 		= "task_id";	// long		
	public final static String LAPS_COL_STATE 		= "state";		// integer(0,1)

	@Override
	public String getTableName()
	{
		return TABLE_LAPS; 
	}

	@Override
	public String[] createIndicesSQL()
	{
		String[] indices = {
			String.format(template, TABLE_LAPS, LAPS_COL_TASK, TABLE_LAPS, LAPS_COL_TASK),
			String.format(template, TABLE_LAPS, LAPS_COL_STATE, TABLE_LAPS, LAPS_COL_STATE)
		};
		
		return indices;
	}

	@Override
	public String createTableSQL()
	{
		return "CREATE TABLE IF NOT EXISTS " + TABLE_LAPS + " (" +
			LAPS_COL_ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT, " +
			LAPS_COL_START_TIME + " INTEGET NOT NULL, " +
			LAPS_COL_END_TIME + " INTEGET NOT NULL, " +
			LAPS_COL_TASK + " INTEGET NOT NULL, " +
			LAPS_COL_STATE + " INTEGER NOT NULL)";
	}
	
	/**
	 * Start new tracking lap
	 * 
	 * @param startTime
	 * @param task
	 * 
	 * @return long New row _ID
	 */
	public long startLap(long startTime, long task)
	{
		ContentValues cv = new ContentValues();
		
		cv.put(LAPS_COL_START_TIME, startTime);
		cv.put(LAPS_COL_END_TIME, 0);
		cv.put(LAPS_COL_STATE, 1);
		cv.put(LAPS_COL_TASK, task);
		
		return getWritableDatabase().insert(TABLE_LAPS, null, cv);
	}
	
	/**
	 * Stop tack
	 * 
	 * @param endTime
	 * @param lapId
	 * 
	 * @return Is some row updated?
	 */
	public boolean endLap(long endTime, long lapId)
	{
		ContentValues cv = new ContentValues();
		
		cv.put(LAPS_COL_END_TIME, endTime);
		cv.put(LAPS_COL_STATE, 0);
		
		String whereClause = LAPS_COL_ID + "=" + lapId;
		String whereArgs[] = null;
		
		return getWritableDatabase().update(TABLE_LAPS, cv, whereClause, whereArgs) > 0;
	}
	
	/**
	 * Return hash of active lap
	 *  
	 * @return HashMap<String, String> 
	 */
	public HashMap<String, String> findActiveLap() {
		String result[] = {LAPS_COL_ID, LAPS_COL_START_TIME, LAPS_COL_END_TIME, LAPS_COL_TASK, LAPS_COL_STATE};
		String whereClause = LAPS_COL_STATE + "=1";
		String whereArgs[] = null;
		
		Cursor cur = getWritableDatabase().query(TABLE_LAPS, result, whereClause, whereArgs, null, null, null);
		
		if (cur.getCount() < 1) {
			return null;
		}
		
		cur.moveToFirst();
		
		HashMap<String, String> hash = new HashMap<String, String>(5);
		for (String key : result) {
			hash.put(key, cur.getString(cur.getColumnIndex(key)));
		}
		
		cur.close();
		
		return hash;
	}
	
	/**
	 * Returns num of spent secs on one task in dates bounds
	 *  
	 * @param taskId
	 * @param startDate
	 * @param endDate
	 * 
	 * @return Number of seconds
	 */
	public long getTaskSpentTime(long taskId, long startDate, long endDate)
	{
		String whereArgs[] = null;
		String sql = "SELECT sum(" + LAPS_COL_END_TIME + "-" + LAPS_COL_START_TIME + ") AS time " +
			" FROM " + TABLE_LAPS + " WHERE (" + LAPS_COL_START_TIME + " >= " + startDate +
			") AND (" + LAPS_COL_START_TIME + " < " +endDate + ") AND (" + LAPS_COL_STATE + " = 0)";
		
		Cursor cur = getWritableDatabase().rawQuery(sql, whereArgs);
		cur.moveToFirst();
		long time = cur.getLong(cur.getColumnIndex("time"));
		cur.close();
		
		return time;
	}
}
