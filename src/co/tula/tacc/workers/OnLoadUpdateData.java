package co.tula.tacc.workers;

import java.util.ArrayList;

import android.util.Log;
import co.tula.tacc.entity.Project;
import co.tula.tacc.tables.ProjectsTable;

public class OnLoadUpdateData
{
	private ProjectsTable projectsTable;
	
	private ArrayList<Project> mProjects = null;
	
	public OnLoadUpdateData()
	{
		projectsTable = new ProjectsTable();
	}
	
	public void update()
	{
		Log.d("tACC", "UPDATE");
		if (mProjects != null && !mProjects.isEmpty()) {
			projectsTable.updateProjects(mProjects);
			mProjects = null;
		}
	}
	
	public OnLoadUpdateData leadUpProjects(ArrayList<Project> projects)
	{
		mProjects = projects;
		
		return this;
	}
}
