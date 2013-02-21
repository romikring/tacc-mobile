package co.tula.tacc.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import co.tula.tacc.tables.*;

abstract public class TaccDbOpenHelper extends SQLiteOpenHelper
{
	public static Context context = null;
	
	private final static int DATABASE_VERSION = 11;
	private final static String DATABASE_NAME = "Tacc.db";
	
	protected final String template = "CREATE INDEX IF NOT EXISTS %s_%s ON %s (%s)";
	
	private final String[] registered = {
		"LapsTable",
		"ProjectsTable",
		"RecordsTable",
		"TaskGroupsTable",
		"TasksTable"
	};
	
	public TaccDbOpenHelper()
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	abstract public String   getTableName();
	abstract public String[] createIndicesSQL();
	abstract public String   createTableSQL();

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		for (int i = 0; i < registered.length; ++i) {
			try {
				
				TaccDbOpenHelper obj = (TaccDbOpenHelper)
					Class.forName(registered[i]).newInstance();
				
				// Create TABLE
				db.execSQL(obj.createTableSQL());
				
				// Create INDICES
				String[] tableIndices = obj.createIndicesSQL();
				for (int idx = 0; idx < tableIndices.length; ++idx) {
					db.execSQL(tableIndices[idx]);
				}
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = 0; i < registered.length; ++i) {
			try {
				
				TaccDbOpenHelper obj = (TaccDbOpenHelper)
					Class.forName(registered[i]).newInstance();
				
				// Create TABLE
				db.execSQL("DROP TABLE IF EXISTS " + obj.getTableName());
				
				// Create INDICES
				String[] tableIndices = obj.createIndicesSQL();
				for (int idx = 0; idx < tableIndices.length; ++idx) {
					db.execSQL(tableIndices[idx]);
				}
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		onCreate(db);
	}
}
