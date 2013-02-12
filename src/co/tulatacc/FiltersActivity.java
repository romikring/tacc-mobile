package co.tulatacc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FiltersActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filers_activity, menu);
        return true;
    }
}