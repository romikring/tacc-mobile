package co.tulatacc;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;

public class SelectToTrackActivity extends Activity {
	
	SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_to_track_activity);
        
        TaccDbOpenHelper taccDbHelper = new TaccDbOpenHelper(this);
        mDb = taccDbHelper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_to_track_activity, menu);
        return true;
    }
}