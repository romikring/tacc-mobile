package co.tula.tacc.views;

import co.tula.tacc.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListRecord extends RelativeLayout {
	
	private TextView mTimes;
	private TextView mCustomer;
	private TextView mDescription;
	
	private int mBillHours = 0;
	private int mBillMins = 0;
	private int mUnBillHours = 0;
	private int mUnBillMins = 0;
	
	public ListRecord(Context c) {
		super(c);
		
		// Inflater service
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		
		li = (LayoutInflater) getContext().getSystemService(infService);
		li.inflate(R.layout.record_list_layout, this, true);
		
		mTimes = (TextView)findViewById(R.id.times_edit);
		mCustomer = (TextView)findViewById(R.id.customer_edit);
		mDescription = (TextView) findViewById(R.id.description_edit);
	}
	
	public ListRecord(Context c, AttributeSet attrs) {
		super(c, attrs);
	}
	
	public void setBillTime(int hours, int mins) {
		mBillHours = hours;
		mBillMins = mins;
		
		updateTimes();
	}
	
	public void setUnBillTime(int hours, int mins) {
		mUnBillHours = hours;
		mUnBillMins = mins;
		
		updateTimes();
	}
	
	private void updateTimes() {
		mTimes.setText(
			String.format("B: %2d:%2d\nU: %2d:%2d", mBillHours, mBillMins, mUnBillHours, mUnBillMins)
		);
	}
	
	public void setCustomer(final String customer) {
		mCustomer.setText(customer);
	}
	
	public void setDescription(final String desc) {
		mDescription.setText(desc);
	}
}
