package co.tula.tacc.tables;

import co.tula.tacc.tools.TaccDbOpenHelper;

public class RecordsTable extends TaccDbOpenHelper
{
	private final static String TABLE_RECORDS = "Records";
	
	private final static String RECORDS_COL_ID 			= "_ID";			// long
	private final static String RECORDS_COL_CUSTOMER 	= "customer_id";	// long
	private final static String RECORDS_COL_BILL_TIME 	= "bill_time";		// integer
	private final static String RECORDS_COL_UNBILL_TIME = "unbill_time";	// integer
	private final static String RECORDS_COL_DESCRIPTION = "description";	// text
	private final static String RECORDS_COL_DATE 		= "date";			// long
	private final static String RECOREDS_COL_SOURCE 	= "source";			// varchar(50)

	@Override
	public String getTableName()
	{
		return TABLE_RECORDS;
	}

	@Override
	public String[] createIndicesSQL()
	{
		String[] indices = {
			String.format(template, TABLE_RECORDS, RECORDS_COL_CUSTOMER, TABLE_RECORDS, RECORDS_COL_CUSTOMER)
		};
		
		return indices;
	}

	@Override
	public String createTableSQL()
	{
		return "CREATE TABLE IF NOT EXISTS " + TABLE_RECORDS + "(" +
			RECORDS_COL_ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT, " +
			RECORDS_COL_CUSTOMER + " INTEGER NOT NULL, " +
			RECORDS_COL_BILL_TIME + " INTEGER NOT NULL, " +
			RECORDS_COL_UNBILL_TIME + " INTEGER NOT NULL, " +
			RECORDS_COL_DESCRIPTION + " TEXT NOT NULL, " +
			RECORDS_COL_DATE + " INTEGER NOT NULL, " +
			RECOREDS_COL_SOURCE + " VARCHAR(50) NOT NULL);";
	}

}
