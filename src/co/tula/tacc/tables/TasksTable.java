package co.tula.tacc.tables;

import android.content.ContentValues;
import co.tula.tacc.tools.TaccDbOpenHelper;

public class TasksTable extends TaccDbOpenHelper
{
	private final static String TABLE_TASKS = "Tasks";
	
	private final static String TASKS_COL_ID 			= "_ID";			// long
	private final static String TASKS_COL_CUSTOMER 		= "customer_id";	// long
	private final static String TASKS_COL_DESCRIPTION 	= "description";	// text
	private final static String TASKS_COL_STATE 		= "state";			// integer(0,1)

	@Override
	final public String createTableSQL()
	{
		return "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " (" +
			TASKS_COL_ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT, " +
			TASKS_COL_CUSTOMER + " INTEGER NOT NULL, " +
			TASKS_COL_DESCRIPTION + " TEXT NOT NULL, " +
			TASKS_COL_STATE + " INTEGER NOT NULL);"; 
	}
	
	@Override
	final public String[] createIndicesSQL()
	{
		String[] indices = {
			String.format(template, TABLE_TASKS, TASKS_COL_CUSTOMER, TABLE_TASKS, TASKS_COL_CUSTOMER)
		};
		
		return indices;
	}

	@Override
	public String getTableName() {
		return TABLE_TASKS;
	}
	
	public long addTask(long project, final String desc)
	{
		final ContentValues cv = new ContentValues();
		cv.put(TASKS_COL_CUSTOMER, project);
		cv.put(TASKS_COL_DESCRIPTION, desc);
		cv.put(TASKS_COL_STATE, 1);
		
		return getWritableDatabase().insert(TABLE_TASKS, null, cv);
	}
}
