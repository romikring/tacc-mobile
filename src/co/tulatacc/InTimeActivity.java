package co.tulatacc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InTimeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_time_activity);
        
        final TextView timer = (TextView) findViewById(R.id.timer);
        Button playPauseBtn = (Button) findViewById(R.id.play_pause_button);
        
        playPauseBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int hours = Integer.parseInt(timer.getText().subSequence(0, 1).toString());
				int minutes = Integer.parseInt(timer.getText().subSequence(3, 4).toString());
				int seconds = Integer.parseInt(timer.getText().subSequence(6, 7).toString());
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.in_time_activity, menu);
        return true;
    }
}