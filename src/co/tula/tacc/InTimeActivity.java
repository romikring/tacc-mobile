package co.tula.tacc;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import co.tula.tacc.tools.TaccDbOpenHelper;
import co.tula.tacc.R;

import android.annotation.SuppressLint;
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
	
	private long taskId = 1;
	
	private TextView mTimerView;
	private Button mStartPauseButton;
	
	private TaccDbOpenHelper db;
	
	private long mills;
	private long lapId;
	
	private long mDayWorkedUnderTask;
	
	private long startDate; 	// 3.00
	private long endDate;		// 2.59
	
	final Runnable mRunnable = new Runnable() {
		private long delta;
		
		@Override
		public void run() {
			if (lapId != 0) {
				delta = System.currentTimeMillis() / 1000 - mills + mDayWorkedUnderTask;
				mTimerView.setText(calcTimerDisplay(delta));
			}
		}
	};
	
	@SuppressLint("DefaultLocale")
	private String calcTimerDisplay(long seconds)
	{
		long s = seconds % 3600 % 60;
		long m = (seconds - s) % 3600 / 60;
		long h = (seconds - s - m) / 3600 % 100; // 99 hours max
		
		return String.format("%02d:%02d:%02d", h, m, s);
	}

	private Timer mTimer;
	private TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(mRunnable);
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_time_activity);

        mills = 0l;
        lapId = 0l;
        
        mTimer = new Timer(TIMER_NAME, true);
        mTimerView = (TextView) findViewById(R.id.timer);
        mStartPauseButton = (Button) findViewById(R.id.start_pause_button);
        
        db = new TaccDbOpenHelper(this);
        
        mStartPauseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// Start tracking
				if (mStartPauseButton.getText() == getString(R.string.play_button)) {
					
					mills = System.currentTimeMillis() / 1000;
					lapId = db.startLap(mills, taskId);
					
					mStartPauseButton.setText(getText(R.string.pause_button));
				
				// Stop tracking
				} else {
					
					mStartPauseButton.setText(getText(R.string.play_button));
					
					db.endLap(System.currentTimeMillis() / 1000, lapId);
					
					mills = 0l;
					lapId = 0l;
					
				}
			}
		});
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
    	
    	Calendar cal = Calendar.getInstance();
    	int day = (cal.get(Calendar.HOUR) < 3) ? -1 : 1;
    	
    	cal.set(Calendar.HOUR_OF_DAY, 3);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	startDate = cal.getTimeInMillis() / 1000L;
    	
    	cal.add(Calendar.DAY_OF_MONTH, day);
    	endDate = cal.getTimeInMillis() / 1000L;
    	
    	mDayWorkedUnderTask = db.getTaskSpentTime(taskId, Math.min(startDate, endDate), Math.max(startDate, endDate));
    	if (mDayWorkedUnderTask > 0) {
    		mTimerView.setText(calcTimerDisplay(mDayWorkedUnderTask));
    	}
    	
    	HashMap<String, String> hash = db.findActiveLap();
    	
    	if (null == hash) {
    		
    		mills = 0l;
    		lapId = 0l;
    		
    		mStartPauseButton.setText(getString(R.string.play_button));
    		
    	} else {
  
	    	mills = Long.parseLong(hash.get(TaccDbOpenHelper.LAPS_COL_START_TIME));
	    	lapId = Long.parseLong(hash.get(TaccDbOpenHelper.LAPS_COL_ID));
	    	
	    	mStartPauseButton.setText(getString(R.string.pause_button));
	    	
    	}
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