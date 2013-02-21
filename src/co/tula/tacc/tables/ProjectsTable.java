package co.tula.tacc.tables;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import co.tula.tacc.entity.Project;
import co.tula.tacc.tools.TaccDbOpenHelper;

public class ProjectsTable extends TaccDbOpenHelper
{
	private final static String TABLE_PROJECTS = "Projects";
	
	private final static String PROJECTS_COL_ID 	 = "_ID";		// long
	private final static String PROJECTS_COL_TACC_ID = "tacc_id";	// long
	private final static String PROJECTS_COL_NAME 	 = "name";		// varchar(200)
	
	@Override
	public String getTableName()
	{
		return TABLE_PROJECTS;
	}

	@Override
	public String[] createIndicesSQL() {
		String[] indices = {
			String.format(template, TABLE_PROJECTS, PROJECTS_COL_TACC_ID, TABLE_PROJECTS, PROJECTS_COL_TACC_ID)
		};
		
		return indices;
	}

	@Override
	public String createTableSQL() {
		return "CREATE TABLE IF NOT EXISTS " + TABLE_PROJECTS + " (" +
			PROJECTS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			PROJECTS_COL_TACC_ID + " INTEGER NOT NULL, " +
			PROJECTS_COL_NAME + " VARCHAR(200) NOT NULL)";
	}
	
	@SuppressLint("DefaultLocale")
	public void updateProjects(final ArrayList<Project> projects)
	{
		final ContentValues cv = new ContentValues();
		final String findSql = "SELECT " + PROJECTS_COL_NAME + " FROM " + TABLE_PROJECTS +
			" WHERE " + PROJECTS_COL_TACC_ID + " = %d";
		
		int count = projects.size();
		
		for (int i=0; i < count; ++i) {
			Project p = projects.get(i);
			
			Cursor findCursor = getWritableDatabase().rawQuery(String.format(findSql, p.getTaccId()), null);
			if (findCursor.getCount() > 0) {
				findCursor.moveToFirst();
				if (findCursor.getString(0) != p.getName()) {
					// Update current project
					String where = String.format(PROJECTS_COL_TACC_ID + " = %d", p.getTaccId());
					cv.clear();
					cv.put(PROJECTS_COL_NAME, p.getName());
					
					getWritableDatabase().update(TABLE_PROJECTS, cv, where, null);
				}
			} else {
				// Insert new project
				cv.clear();
				cv.put(PROJECTS_COL_NAME, p.getName());
				cv.put(PROJECTS_COL_TACC_ID, p.getTaccId());
				
				getWritableDatabase().insert(TABLE_PROJECTS, null, cv);
			}
			findCursor.close();
		}
	}
	
	public ArrayList<Project> getAllProjects()
	{
		
		final String sql = "SELECT " + PROJECTS_COL_ID + ", " + PROJECTS_COL_NAME + ", " + PROJECTS_COL_TACC_ID +
			" FROM " + TABLE_PROJECTS + " ORDER BY " + PROJECTS_COL_NAME + " ASC";
		
		Cursor cur = getWritableDatabase().rawQuery(sql, null);
		if (cur.getCount() < 1) {
			cur.close();
			return null;
		}
		
		final ArrayList<Project> projects = new ArrayList<Project>();
		
		while(cur.moveToNext()) {
			projects.add(new Project(
				cur.getLong(cur.getColumnIndex(PROJECTS_COL_ID)),
				cur.getLong(cur.getColumnIndex(PROJECTS_COL_TACC_ID)),
				cur.getString(cur.getColumnIndex(PROJECTS_COL_NAME))
			));
		}
		
		cur.close();
		
		return projects;
	}
}
