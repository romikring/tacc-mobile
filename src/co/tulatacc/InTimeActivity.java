package co.tulatacc;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InTimeActivity extends Activity
{
	final public static String TIMER_NAME = "WORKING_TIMER";
	
	private TextView mTimerView;
	private Button mStartPauseButton;
	
	private TaccDbOpenHelper db;
	
	private long mills;
	private int lapId;
	
	final Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			long delta = (System.currentTimeMillis() - mills) / 1000;
			long seconds = delta % 3600 % 60;
			long minutes = (delta - seconds) % 3600 / 60;
			long hours = (delta - seconds - minutes) / 3600;
			
			mTimerView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
		}
	};

	Timer mTimer;
	TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(mRunnable);
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_time_activity);
        
        mTimerView = (TextView) findViewById(R.id.timer);
        mStartPauseButton = (Button) findViewById(R.id.start_pause_button);
        
        db = new TaccDbOpenHelper(this);
        
        mStartPauseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mStartPauseButton.getText() == getString(R.string.play_button)) {
					mills = System.currentTimeMillis();
					mStartPauseButton.setText(getText(R.string.pause_button));
					mTimer.schedule(mTimerTask, 0, 1000);
				} else {
					mStartPauseButton.setText(getText(R.string.play_button));
					mTimer.cancel();
				}
			}
		});
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	
    	mTimer = new Timer(TIMER_NAME, true);
    }
    
    @Override
    protected void onPause()
    {
    	super.onPause();
    	mTimer.cancel();
    }
    
    @Override
    protected void onResume()
    {
    	super.onResume();
    
    	mTimer.schedule(mTimerTask, 0, 1000);
    	HashMap<String, String> hash = db.findActiveLap();
    	if (null == hash)
    		return;
    	
    	mills = Long.parseLong(hash.get(TaccDbOpenHelper.LAPS_COL_START_TIME));
    	lapId = Integer.parseInt(hash.get(TaccDbOpenHelper.LAPS_COL_ID));
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.in_time_activity, menu);
        return true;
    }
}