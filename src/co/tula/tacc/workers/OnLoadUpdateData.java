package co.tula.tacc.workers;

import java.util.ArrayList;

import android.content.Context;
import co.tula.tacc.entity.Project;
import co.tula.tacc.tools.TaccDbOpenHelper;

public class OnLoadUpdateData
{
	private Context context;
	
	private ArrayList<Project> mProjects = null;
	
	public OnLoadUpdateData(Context context)
	{
		this.context = context;
	}
	
	public void update()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				TaccDbOpenHelper dbHelper = new TaccDbOpenHelper(context);
				
				if (mProjects != null && !mProjects.isEmpty()) {
					dbHelper.updateProjects(mProjects);
					mProjects = null;
				}
			}
		}).start();
	}
	
	public OnLoadUpdateData leadUpProjects(final ArrayList<Project> projects)
	{
		mProjects = projects;
		
		return this;
	}
}
