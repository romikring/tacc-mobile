package co.tula.tacc;

import co.tula.tacc.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddRecordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_record_activity, menu);
        return true;
    }
}