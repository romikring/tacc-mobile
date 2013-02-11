package co.tulatacc;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RecordListAdapter extends BaseAdapter
{
	private Context mContext;
	private ArrayList<ListRecord> mRecords;
	
	@Override
	public int getCount() {
		return mRecords.size();
	}

	@Override
	public Object getItem(int position) {
		return mRecords.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListRecord lr;
		if (convertView == null) {
            lr = new ListRecord(mContext);
        } else {
            lr = (ListRecord) convertView;
        }
        
        return lr;
	}
}
