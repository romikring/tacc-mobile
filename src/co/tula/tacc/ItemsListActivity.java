package co.tula.tacc;

import co.tula.tacc.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ItemsListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_list_activity);
        
        Button tasksButton = (Button) findViewById(R.id.track_list_button);
        tasksButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItemsListActivity.this, SelectToTrackActivity.class);
				startActivity(intent);
			}
		});
        
        Button addTrackButton = (Button) findViewById(R.id.add_track_button);
        addTrackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ItemsListActivity.this, AddTrackingItemActivity.class);
				startActivity(intent);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items_list_activity, menu);
        return true;
    }
}